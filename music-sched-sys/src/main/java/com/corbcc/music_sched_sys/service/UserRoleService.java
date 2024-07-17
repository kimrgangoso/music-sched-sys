package com.corbcc.music_sched_sys.service;

import com.corbcc.music_sched_sys.domain.UserDetailsEntity;
import com.corbcc.music_sched_sys.domain.RoleEntity;
import com.corbcc.music_sched_sys.domain.UserRoleEntity;
import com.corbcc.music_sched_sys.dto.UserRoleDto;
import com.corbcc.music_sched_sys.dto.UserRoleResponseDto;
import com.corbcc.music_sched_sys.repository.UserDetailsRepository;
import com.corbcc.music_sched_sys.repository.RoleRepository;
import com.corbcc.music_sched_sys.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserRoleService {

    public static final Logger logger = LoggerFactory.getLogger(UserRoleService.class);

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public ResponseEntity<?> assignRoles(UserRoleDto request) {
        try {
            // Validate userId
            UserDetailsEntity userDetails = userDetailsRepository.findById(request.getUserId()).orElse(null);
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found!");
            }

            // Validate roleIds
            List<RoleEntity> roles = request.getRoleIds().stream()
                    .map(roleId -> roleRepository.findById(roleId).orElse(null))
                    .collect(Collectors.toList());

            if (roles.contains(null)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One or more roles not found!");
            }

            // Remove existing roles
            userRoleRepository.deleteByUserId(userDetails.getId());

            // Assign new roles
            for (UUID roleId : request.getRoleIds()) {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setUserId(userDetails.getId());
                userRoleEntity.setRoleId(roleId);
                userRoleRepository.save(userRoleEntity);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Roles assigned successfully!");
        } catch (Exception e) {
            logger.error("Error assigning roles", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }


    public ResponseEntity<?> viewUserRoles(UUID userId) {
        try {
            // Fetch all user roles if userId is null, otherwise fetch roles for the specific userId
            List<UserRoleEntity> userRoleEntities;
            if (userId == null) {
                userRoleEntities = userRoleRepository.findAll();
            } else {
                userRoleEntities = userRoleRepository.findByUserId(userId);
                if (userRoleEntities.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No roles found for the specified user!");
                }
            }

            // Build the response list
            List<UserRoleResponseDto> responseList = new ArrayList<>();
            Map<UUID, UserRoleResponseDto> responseMap = new HashMap<>();

            for (UserRoleEntity userRoleEntity : userRoleEntities) {
                UUID userIdKey = userRoleEntity.getUserId();
                UserDetailsEntity userDetails = userDetailsRepository.findById(userIdKey).orElse(null);
                RoleEntity role = roleRepository.findById(userRoleEntity.getRoleId()).orElse(null);

                if (userDetails != null && role != null) {
                    UserRoleResponseDto userRoleResponse = responseMap.computeIfAbsent(userIdKey, k -> {
                        UserRoleResponseDto dto = new UserRoleResponseDto();
                        dto.setUserId(userIdKey);
                        dto.setUsername(userDetails.getUsername());
                        dto.setRoles(new ArrayList<>());
                        return dto;
                    });

                    userRoleResponse.getRoles().add(role.getRole());
                }
            }

            responseList.addAll(responseMap.values());

            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching user roles: " + e.getMessage());
        }
    }
}
