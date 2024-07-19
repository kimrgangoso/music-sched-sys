package com.corbcc.music_sched_sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corbcc.music_sched_sys.domain.ProfileDetailsEntity;
import com.corbcc.music_sched_sys.domain.UserDetailsEntity;
import com.corbcc.music_sched_sys.domain.UserProfilesEntity;
import com.corbcc.music_sched_sys.dto.ProfileDetailsDto;
import com.corbcc.music_sched_sys.dto.UserDetailsDto;
import com.corbcc.music_sched_sys.dto.UserProfilesDto;
import com.corbcc.music_sched_sys.repository.ProfileRepository;
import com.corbcc.music_sched_sys.repository.UserDetailsRepository;
import com.corbcc.music_sched_sys.repository.UserProfilesRepository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserProfilesService {

    @Autowired
    private UserProfilesRepository userProfilesRepository;

    @Autowired
    private UserDetailsRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Transactional
    public ResponseEntity<?> assignProfilesToUser(UserDetailsDto request) {
        try {
            // Fetch the user entity
            UserDetailsEntity userEntity = userRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("User not found for ID: " + request.getId()));

            for (ProfileDetailsDto profileDto : request.getProfiles()) {
                ProfileDetailsEntity profileEntity = profileRepository.findById(profileDto.getProfileId())
                        .orElseThrow(() -> new RuntimeException("Profile not found for ID: " + profileDto.getProfileId()));

                // Create UserProfilesEntity
                UserProfilesEntity userProfilesEntity = new UserProfilesEntity();
                userProfilesEntity.setUser(userEntity);
                userProfilesEntity.setProfile(profileEntity);
                userProfilesEntity.setProfileName(profileDto.getProfileName());

                userProfilesRepository.save(userProfilesEntity);
            }

            return ResponseEntity.ok("Profiles assigned to user successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign profiles to user: " + e.getMessage());
        }
    }
    
    @Transactional
    public ResponseEntity<?> updateProfilesForUser(UserDetailsDto request) {
        try {
            // Fetch the user entity
            UserDetailsEntity userEntity = userRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("User not found for ID: " + request.getId()));

            // Remove existing profiles assigned to the user
            userProfilesRepository.deleteByUserId(request.getId());

            // Assign new profiles to the user
            for (ProfileDetailsDto profileDto : request.getProfiles()) {
                ProfileDetailsEntity profileEntity = profileRepository.findById(profileDto.getProfileId())
                        .orElseThrow(() -> new RuntimeException("Profile not found for ID: " + profileDto.getProfileId()));

                // Create UserProfilesEntity
                UserProfilesEntity userProfilesEntity = new UserProfilesEntity();
                userProfilesEntity.setUser(userEntity);
                userProfilesEntity.setProfile(profileEntity);
                userProfilesEntity.setProfileName(profileDto.getProfileName());

                userProfilesRepository.save(userProfilesEntity);
            }

            return ResponseEntity.ok("Profiles updated for user successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profiles for user: " + e.getMessage());
        }
    }
    
    
    public ResponseEntity<?> viewUserProfiles(UUID userId) {
        List<UserProfilesEntity> userProfiles;

        if (userId == null) {
            userProfiles = userProfilesRepository.findAll();
        } else {
            userProfiles = userProfilesRepository.findByUserId(userId);
        }

        // Group profiles by user ID (UUID)
        Map<UUID, List<UserProfilesEntity>> groupedByUser = userProfiles.stream()
            .collect(Collectors.groupingBy(profile -> profile.getUser().getId()));

        // Transform grouped data into the desired format
        List<Map<String, Object>> response = groupedByUser.entrySet().stream().map(entry -> {
            Map<String, Object> userProfileMap = new LinkedHashMap<>();
            UUID userIdKey = entry.getKey();
            userProfileMap.put("userId", userIdKey);

            List<UserProfilesEntity> profiles = entry.getValue();

            // Collect profile IDs and names
            List<UUID> profileIds = profiles.stream()
                .map(profile -> profile.getProfile().getId())
                .distinct() // Remove duplicates if needed
                .collect(Collectors.toList());

            List<String> profileNames = profiles.stream()
                .map(UserProfilesEntity::getProfileName)
                .distinct() // Remove duplicates if needed
                .collect(Collectors.toList());

            userProfileMap.put("profileId", profileIds);
            userProfileMap.put("profileName", profileNames);

            return userProfileMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    
    
}
