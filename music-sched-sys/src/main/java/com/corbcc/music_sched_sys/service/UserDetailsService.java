package com.corbcc.music_sched_sys.service;

import com.corbcc.music_sched_sys.domain.ChurchDetailsEntity;
import com.corbcc.music_sched_sys.domain.UserDetailsEntity;
import com.corbcc.music_sched_sys.dto.ChurchDetailsDto;
import com.corbcc.music_sched_sys.dto.UserDetailsDto;
import com.corbcc.music_sched_sys.repository.ChurchDetailsRepository;
import com.corbcc.music_sched_sys.repository.UserDetailsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserDetailsService {
	
	public static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class); 
    @Autowired
    UserDetailsRepository userDetailsRepo;

    public ResponseEntity<?> saveUserDetails(UserDetailsDto request) {
        try {
            UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
            userDetailsEntity.setUsername(request.getUsername());
            userDetailsEntity.setFirstname(request.getFirstname());
            userDetailsEntity.setLastname(request.getLastname());
            userDetailsEntity.setEmail(request.getEmail());
            userDetailsEntity.setPassword(request.getPassword());
            userDetailsEntity.setMobileNumber(request.getMobileNumber());
            userDetailsEntity.setChurchId(request.getChurchId());
            userDetailsEntity.setUserCreatedBy(request.getUserCreatedBy()); 

            // Set default values for excluded fields
            userDetailsEntity.setAccountEnabled(true);
            userDetailsEntity.setPasswordReset(false);
            userDetailsEntity.setFailedLogin(0);
            userDetailsEntity.setStatus("ACTIVE");
            userDetailsEntity.setUserDateCreated(new java.sql.Timestamp(System.currentTimeMillis()));

            // Set the following fields to null for initial creation
            userDetailsEntity.setLastGoodLogin(null);
            userDetailsEntity.setLastBadLogin(null);
            userDetailsEntity.setPasswordHistory(null);
            userDetailsEntity.setLastPasswordChange(null);
            userDetailsEntity.setProfileUpdatedBy(null);
            
            userDetailsRepo.save(userDetailsEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body("User details added successfully!");
        } catch (Exception e) {
            logger.error("Error saving user details", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

}
