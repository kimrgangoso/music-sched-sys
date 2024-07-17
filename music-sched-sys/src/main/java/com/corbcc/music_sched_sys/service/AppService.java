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
public class AppService {
	
	public static final Logger logger = LoggerFactory.getLogger(AppService.class);
	
	@Autowired
	private ChurchDetailsRepository churchDtlsRepo;
    
    @Autowired
    UserDetailsRepository userDetailsRepo;

	public ResponseEntity<?> saveChurchDetails(ChurchDetailsDto request) {
		ResponseEntity<?> msgresponse;
		try {
			ChurchDetailsEntity churchDetailsEntity = new ChurchDetailsEntity();
			churchDetailsEntity.setChurchName(request.getChurchName());
			churchDetailsEntity.setAcronym(request.getAcronym());
			   // Log the details to verify
	        logger.info("Adding ChurchDetailsEntity: {}", churchDetailsEntity);
			ChurchDetailsEntity savedEntity = churchDtlsRepo.save(churchDetailsEntity);
			 // Log the saved entity
	        logger.info("Saved ChurchDetailsEntity: {}", savedEntity);
			msgresponse = ResponseEntity.status(HttpStatus.CREATED).body("Church details added successfully!");
		} catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation", e);
            msgresponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Church name or acronym already exists!");
        } catch (Exception e) {
			logger.error("Error adding church details", e);
			msgresponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
		return msgresponse;
	}
	
	public ResponseEntity<?> viewChurchDetails(ChurchDetailsDto request) {
		 ResponseEntity<?> msgresponse;
	        try {
	            List<ChurchDetailsEntity> churchDetailsEntityList;

	            if ((request.getChurchName() == null || request.getChurchName().isEmpty()) &&
	                (request.getAcronym() == null || request.getAcronym().isEmpty())) {
	                churchDetailsEntityList = churchDtlsRepo.findAll();
	            } else if (request.getChurchName() != null && !request.getChurchName().isEmpty() &&
	                       (request.getAcronym() == null || request.getAcronym().isEmpty())) {
	                churchDetailsEntityList = churchDtlsRepo.findByChurchName(request.getChurchName());
	            } else if ((request.getChurchName() == null || request.getChurchName().isEmpty()) &&
	                       request.getAcronym() != null && !request.getAcronym().isEmpty()) {
	                churchDetailsEntityList = churchDtlsRepo.findByAcronym(request.getAcronym());
	            } else {
	                churchDetailsEntityList = churchDtlsRepo.findByChurchNameAndAcronym(request.getChurchName(), request.getAcronym());
	            }

	            logger.info("View ChurchDetailsEntity: {}", churchDetailsEntityList);

	            msgresponse = ResponseEntity.ok(churchDetailsEntityList);
	        } catch (Exception e) {
	            logger.error("Error viewing church details", e);
	            msgresponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
	        }
	        return msgresponse;
	}
	
    public ResponseEntity<?> deleteChurchDetails(ChurchDetailsDto request) {
        ResponseEntity<?> msgresponse;
        try {
            churchDtlsRepo.deleteById(request.getId());
            msgresponse = ResponseEntity.status(HttpStatus.OK).body("Church details deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting church details", e);
            msgresponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
        return msgresponse;
    }

  
    public ResponseEntity<?> updateChurchDetails(ChurchDetailsDto request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID must be provided");
            }
            
            ChurchDetailsEntity existingEntity = churchDtlsRepo.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Church not found"));
            
            if (request.getChurchName() != null && !request.getChurchName().isEmpty()) {
                existingEntity.setChurchName(request.getChurchName());
            }
            if (request.getAcronym() != null && !request.getAcronym().isEmpty()) {
                existingEntity.setAcronym(request.getAcronym());
            }

            churchDtlsRepo.save(existingEntity);
            return ResponseEntity.ok("Church details updated successfully");
        } catch (Exception e) {
            logger.error("Error updating church details", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }


}
