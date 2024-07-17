package com.corbcc.music_sched_sys.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.corbcc.music_sched_sys.domain.ChurchDetailsEntity;
import com.corbcc.music_sched_sys.dto.ChurchDetailsDto;
import com.corbcc.music_sched_sys.dto.UserDetailsDto;
import com.corbcc.music_sched_sys.service.AppService;
import com.corbcc.music_sched_sys.service.UserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

	public static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@Autowired
	AppService appService;
	
	@Autowired
    UserDetailsService userDetailsService;
	
	//*********************************************************************************************	
	//***********************************CHURCH DETAILS SERVICES START*****************************
	//*********************************************************************************************
	@RequestMapping(value = "/api/churchDetails/save", method = {RequestMethod.POST}, consumes = {
	"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> saveChurchDetails(@RequestBody ChurchDetailsDto body){
		logger.info("Call saveChurchDetails Service");
		ResponseEntity<?> response =  appService.saveChurchDetails(body);
		logger.info("Call saveChurchDetails Service ends");
		return response;
	}
	
	@RequestMapping(value = "/api/churchDetails/view", method = {RequestMethod.POST}, consumes = {
	"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> viewChurchDetails(@RequestBody ChurchDetailsDto request){
		logger.info("Call viewChurchDetails Service");
		ResponseEntity<?> response =  appService.viewChurchDetails(request);
		logger.info("Call viewChurchDetails Service ends");
		return response;
	}
	
	@RequestMapping(value = "/api/churchDetails/delete", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteChurchDetails(@RequestBody ChurchDetailsDto request) {
        logger.info("Call deleteChurchDetails Service");
        ResponseEntity<?> response = appService.deleteChurchDetails(request);
        logger.info("Call deleteChurchDetails Service ends");
        return response;
    }

	@RequestMapping(value = "/api/churchDetails/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateChurchDetails(@RequestBody ChurchDetailsDto request) {
	    logger.info("Call updateChurchDetails Service");
	    ResponseEntity<?> response = appService.updateChurchDetails(request);
	    logger.info("Call updateChurchDetails Service ends");
	    return response;
	}
	//*********************************************************************************************	
	//***********************************CHURCH DETAILS SERVICES END*******************************
	//*********************************************************************************************
	
	@RequestMapping(value = "/api/userDetails/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveUserDetails(@RequestBody UserDetailsDto request) {
        logger.info("Call addUserDetails Service");
        ResponseEntity<?> response = userDetailsService.saveUserDetails(request);
        logger.info("Call addUserDetails Service ends");
        return response;
    }

  
	
}

