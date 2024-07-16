package com.corbcc.music_sched_sys.service;

import com.corbcc.music_sched_sys.domain.ChurchDetailsEntity;
import com.corbcc.music_sched_sys.dto.ChurchDetailsDto;
import com.corbcc.music_sched_sys.repository.ChurchDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AppService {
	
	public static final Logger logger = LoggerFactory.getLogger(AppService.class);
	
	@Autowired
	private ChurchDetailsRepository churchDtlsRepo;

	public ResponseEntity<?> addChurchDetails(ChurchDetailsDto request) {
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
			msgresponse = ResponseEntity.status(HttpStatus.CREATED).body("Church details added successfully");
		} catch (Exception e) {
			logger.error("Error adding church details", e);
			msgresponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
		return msgresponse;
	}
}
