package com.corbcc.music_sched_sys.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.corbcc.music_sched_sys.domain.ChurchDetailsEntity;
import com.corbcc.music_sched_sys.dto.ChurchDetailsDto;
import com.corbcc.music_sched_sys.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

	public static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@Autowired
	AppService appService;
	
	
	@RequestMapping(value = "/api/saveChurchDetails", method = {RequestMethod.POST}, consumes = {
	"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> saveChurchDetails(@RequestBody ChurchDetailsDto body){
		logger.info("Call saveChurchDetails Service");
		ResponseEntity<?> response =  appService.saveChurchDetails(body);
		logger.info("Call saveChurchDetails Service ends");
		return response;
	}
	
	@RequestMapping(value = "/api/viewChurchDetails", method = {RequestMethod.POST}, consumes = {
	"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> viewChurchDetails(@RequestBody ChurchDetailsDto request){
		logger.info("Call viewChurchDetails Service");
		ResponseEntity<?> response =  appService.viewChurchDetails(request);
		logger.info("Call viewChurchDetails Service ends");
		return response;
	}
	
	@RequestMapping(value = "/api/deleteChurchDetails", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteChurchDetails(@RequestBody ChurchDetailsDto request) {
        logger.info("Call deleteChurchDetails Service");
        ResponseEntity<?> response = appService.deleteChurchDetails(request);
        logger.info("Call deleteChurchDetails Service ends");
        return response;
    }

	@RequestMapping(value = "/api/updateChurchDetails", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateChurchDetails(@RequestBody ChurchDetailsDto body) {
	    logger.info("Call updateChurchDetails Service");
	    ResponseEntity<?> response = appService.updateChurchDetails(body);
	    logger.info("Call updateChurchDetails Service ends");
	    return response;
	}
}

