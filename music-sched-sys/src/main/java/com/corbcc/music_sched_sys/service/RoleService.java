package com.corbcc.music_sched_sys.service;

import com.corbcc.music_sched_sys.domain.RoleEntity;
import com.corbcc.music_sched_sys.dto.RoleDto;
import com.corbcc.music_sched_sys.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    public static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    public ResponseEntity<?> saveRoleDetails(RoleDto request) {
        try {
            String roleUpper = request.getRole().toUpperCase();
            if (roleRepository.existsByRoleIgnoreCase(request.getRole())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Role already exists");
            }

            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRole(roleUpper);

            roleRepository.save(roleEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body("Role details added successfully");
        } catch (Exception e) {
            logger.error("Error saving role details", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    public ResponseEntity<?> updateRoleDetails(RoleDto request) {
        try {
            String roleUpper = request.getRole().toUpperCase();
            if (roleRepository.existsByRoleIgnoreCaseAndIdNot(roleUpper, request.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Role already exists");
            }

            RoleEntity roleEntity = roleRepository.findById(request.getId()).orElse(null);
            if (roleEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
            }
            roleEntity.setRole(roleUpper);

            roleRepository.save(roleEntity);

            return ResponseEntity.ok("Role details updated successfully");
        } catch (Exception e) {
            logger.error("Error updating role details", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    public ResponseEntity<?> viewRoleDetails() {
        try {
            List<RoleEntity> roleDetailsEntityList = roleRepository.findAll();

            return ResponseEntity.ok(roleDetailsEntityList);
        } catch (Exception e) {
            logger.error("Error fetching role details!", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    public ResponseEntity<?> deleteRoleDetails(RoleDto request) {
        try {
            roleRepository.deleteById(request.getId());

            return ResponseEntity.ok("Role deleted successfully!");
        } catch (Exception e) {
            logger.error("Error deleting role details!", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }
    
    
    
}
