package com.corbcc.music_sched_sys.service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corbcc.music_sched_sys.domain.ActionEntity;
import com.corbcc.music_sched_sys.domain.ModulesEntity;
import com.corbcc.music_sched_sys.domain.ProfileDetailsEntity;
import com.corbcc.music_sched_sys.domain.ProfileModulesEntity;
import com.corbcc.music_sched_sys.dto.ProfileModuleDto;
import com.corbcc.music_sched_sys.dto.ActionDto;
import com.corbcc.music_sched_sys.dto.MainModuleDto;
import com.corbcc.music_sched_sys.dto.ModuleDto;
import com.corbcc.music_sched_sys.dto.ProfileDetailsDto;
import com.corbcc.music_sched_sys.dto.ProfileMenuDto;
import com.corbcc.music_sched_sys.repository.ActionsRepository;
import com.corbcc.music_sched_sys.repository.MainModuleRepository;
import com.corbcc.music_sched_sys.repository.ModulesRepository;
import com.corbcc.music_sched_sys.repository.ProfileModuleRepository;
import com.corbcc.music_sched_sys.repository.ProfileRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepository profileDetailsRepository;

    @Autowired
    private ProfileModuleRepository profileModulesRepository;
    
    @Autowired
    private ModulesRepository modulesRepository;

    @Autowired
    private ActionsRepository actionsRepository;
    
    @Autowired
    private MainModuleRepository mainModuleRepository;
    
    @Transactional
    public ResponseEntity<?> saveProfile(ProfileDetailsDto request) {
        try {
            logger.info("Creating profile: {}", request.getProfileName());

            // Create profile details entity
            ProfileDetailsEntity profileDetailsEntity = new ProfileDetailsEntity();
            profileDetailsEntity.setProfileName(request.getProfileName());
            profileDetailsEntity.setCreatedBy(request.getCreatedBy());
            profileDetailsEntity.setStatus("CREATED");
            profileDetailsEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));

            // Save profile details
            profileDetailsEntity = profileDetailsRepository.save(profileDetailsEntity);

            // Save profile modules and actions mappings
            for (String moduleId : request.getModules()) {
                ModulesEntity modulesEntity = modulesRepository.findById(UUID.fromString(moduleId))
                        .orElseThrow(() -> new RuntimeException("Module not found for ID: " + moduleId));

                for (String actionId : request.getActions()) {
                    ActionEntity actionsEntity = actionsRepository.findById(UUID.fromString(actionId))
                            .orElseThrow(() -> new RuntimeException("Action not found for ID: " + actionId));

                    // Check if action belongs to the module
                    if (actionsEntity.getModuleId().equals(UUID.fromString(moduleId))) {
                        // Create profile modules entity
                        ProfileModulesEntity profileModulesEntity = new ProfileModulesEntity();
                        profileModulesEntity.setProfile(profileDetailsEntity);
                        profileModulesEntity.setModule(modulesEntity);
                        profileModulesEntity.setAction(actionsEntity);
                        profileModulesEntity.setProfileName(profileDetailsEntity.getProfileName());
                        profileModulesEntity.setModuleName(modulesEntity.getModule());
                        profileModulesEntity.setActionName(actionsEntity.getAction());

                        profileModulesRepository.save(profileModulesEntity);
                    } else {
                        logger.warn("Action {} does not belong to module {}, skipping...", actionId, moduleId);
                    }
                }
            }

            logger.info("Profile created successfully: {}", profileDetailsEntity.getId());
            return ResponseEntity.ok("Profile created successfully!");
        } catch (Exception e) {
            logger.error("Failed to create profile: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create profile: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<?> updateProfile(ProfileDetailsDto request) {
        try {
            logger.info("Updating profile: {}", request.getProfileName());

            // Fetch the profile details entity
            ProfileDetailsEntity profileDetailsEntity = profileDetailsRepository.findById(request.getProfileId())
                    .orElseThrow(() -> new RuntimeException("Profile not found for ID: " + request.getProfileId()));

            // Update profile details
            profileDetailsEntity.setProfileName(request.getProfileName());
            profileDetailsEntity.setLastModifiedBy(request.getLastModifiedBy());
            profileDetailsEntity.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

            // Save updated profile details
            profileDetailsEntity = profileDetailsRepository.save(profileDetailsEntity);

            // Remove existing profile modules and actions (optional, if you want to replace all)
            profileModulesRepository.deleteByProfileId(request.getProfileId());

            // Save updated profile modules and actions mappings
            for (String moduleId : request.getModules()) {
                ModulesEntity modulesEntity = modulesRepository.findById(UUID.fromString(moduleId))
                        .orElseThrow(() -> new RuntimeException("Module not found for ID: " + moduleId));

                for (String actionId : request.getActions()) {
                    ActionEntity actionsEntity = actionsRepository.findById(UUID.fromString(actionId))
                            .orElseThrow(() -> new RuntimeException("Action not found for ID: " + actionId));

                    // Validate that action belongs to the module (optional, if required)
                    if (!actionsEntity.getModuleId().equals(UUID.fromString(moduleId))) {
                        logger.warn("Action {} does not belong to module {}, skipping...", actionId, moduleId);
                        continue; // Skip saving this action if it doesn't belong to the module
                    }

                    ProfileModulesEntity profileModulesEntity = new ProfileModulesEntity();
                    profileModulesEntity.setProfile(profileDetailsEntity);
                    profileModulesEntity.setModule(modulesEntity);
                    profileModulesEntity.setAction(actionsEntity);
                    profileModulesEntity.setProfileName(profileDetailsEntity.getProfileName());
                    profileModulesEntity.setModuleName(modulesEntity.getModule());
                    profileModulesEntity.setActionName(actionsEntity.getAction());

                    profileModulesRepository.save(profileModulesEntity);
                }
            }

            logger.info("Profile updated successfully: {}", profileDetailsEntity.getId());
            return ResponseEntity.ok("Profile updated successfully!");
        } catch (Exception e) {
            logger.error("Failed to update profile: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> viewProfile(UUID profileId) {
        if (profileId == null) {
            // Handle fetching all profiles
            logger.info("Fetching all profiles");
            List<ProfileDetailsEntity> profiles = profileDetailsRepository.findAll();
            List<ProfileDetailsDto> profileDtos = profiles.stream().map(profile -> {
                ProfileDetailsDto dto = new ProfileDetailsDto();
                dto.setProfileId(profile.getId());
                dto.setProfileName(profile.getProfileName());
                dto.setCreatedBy(profile.getCreatedBy());
                dto.setLastModifiedBy(profile.getLastModifiedBy());
                dto.setCreationDate(profile.getCreationDate());
                dto.setLastModifiedDate(profile.getLastModifiedDate());

                // Fetch modules and actions
                List<ProfileModuleDto> moduleDtos = profileModulesRepository.findByProfile(profile).stream()
                    .collect(Collectors.groupingBy(
                        profileModule -> profileModule.getModule().getModule(),
                        Collectors.mapping(profileModule -> profileModule.getAction().getAction(), Collectors.toList())
                    ))
                    .entrySet().stream()
                    .map(entry -> {
                        ProfileModuleDto moduleDto = new ProfileModuleDto();
                        moduleDto.setModuleName(entry.getKey());
                        moduleDto.setActions(entry.getValue());
                        return moduleDto;
                    })
                    .collect(Collectors.toList());

                dto.setModules(null); // Set modules to null
                dto.setActions(null); // Set actions to null
                dto.setModule(moduleDtos);

                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(profileDtos);
        } else {
            // Fetch profile for the specified ID
            logger.info("Fetching profile for ID: {}", profileId);
            ProfileDetailsEntity profile = profileDetailsRepository.findById(profileId).orElse(null);

            if (profile == null) {
                logger.error("Profile not found for ID: {}", profileId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found for ID: " + profileId);
            }

            ProfileDetailsDto dto = new ProfileDetailsDto();
            dto.setProfileId(profile.getId());
            dto.setProfileName(profile.getProfileName());
            dto.setCreatedBy(profile.getCreatedBy());
            dto.setLastModifiedBy(profile.getLastModifiedBy());
            dto.setCreationDate(profile.getCreationDate());
            dto.setLastModifiedDate(profile.getLastModifiedDate());

            // Fetch modules and actions
            List<ProfileModuleDto> moduleDtos = profileModulesRepository.findByProfile(profile).stream()
                .collect(Collectors.groupingBy(
                    profileModule -> profileModule.getModule().getModule(),
                    Collectors.mapping(profileModule -> profileModule.getAction().getAction(), Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> {
                    ProfileModuleDto moduleDto = new ProfileModuleDto();
                    moduleDto.setModuleName(entry.getKey());
                    moduleDto.setActions(entry.getValue());
                    return moduleDto;
                })
                .collect(Collectors.toList());

            dto.setModules(null); // Set modules to null
            dto.setActions(null); // Set actions to null
            dto.setModule(moduleDtos);

            return ResponseEntity.ok(dto);
        }
    }

    public ResponseEntity<?> getAllMainModules() {
        try {
            List<Object[]> results = mainModuleRepository.findAllMainModuleWithModulesAndActions();

            // Transform the result to a suitable DTO
            Map<UUID, MainModuleDto> mainModuleMap = new HashMap<>();

            for (Object[] row : results) {
                UUID mainModuleId = (UUID) row[0];
                String mainModuleName = (String) row[1];
                UUID moduleId = (UUID) row[2];
                String moduleName = (String) row[3];
                String moduleDescription = (String) row[4];
                UUID actionId = (UUID) row[5];
                String actionName = (String) row[6];
                String actionDescription = (String) row[7];

                MainModuleDto mainModuleDto = mainModuleMap.computeIfAbsent(mainModuleId, id -> {
                    MainModuleDto dto = new MainModuleDto();
                    dto.setId(id);
                    dto.setMenu(mainModuleName);
                    dto.setModules(new ArrayList<>());
                    return dto;
                });

                if (moduleId != null) {
                    ModuleDto moduleDto = mainModuleDto.getModules().stream()
                            .filter(m -> m.getId().equals(moduleId))
                            .findFirst()
                            .orElse(null);

                    if (moduleDto == null) {
                        moduleDto = new ModuleDto();
                        moduleDto.setId(moduleId);
                        moduleDto.setModule(moduleName);
                        moduleDto.setDescription(moduleDescription);
                        moduleDto.setActions(new ArrayList<>());
                        mainModuleDto.getModules().add(moduleDto);
                    }

                    if (actionId != null) {
                        ActionDto actionDto = new ActionDto();
                        actionDto.setId(actionId);
                        actionDto.setAction(actionName);
                        actionDto.setDescription(actionDescription);
                        moduleDto.getActions().add(actionDto);
                    }
                }
            }

            List<MainModuleDto> mainModuleDtos = new ArrayList<>(mainModuleMap.values());
            return ResponseEntity.ok(mainModuleDtos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve main modules: " + e.getMessage());
        }
    }


    
}

