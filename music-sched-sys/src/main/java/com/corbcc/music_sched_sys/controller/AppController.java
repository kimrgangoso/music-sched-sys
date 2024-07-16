package com.corbcc.music_sched_sys.controller;

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
	
	@RequestMapping(value = "/api/addchurchdetails", method = {RequestMethod.POST}, consumes = {
	"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> addChurchDetails(@RequestBody ChurchDetailsDto body){
		logger.info("Call addChurchDetails Service");
		ResponseEntity<?> response =  appService.addChurchDetails(body);
		logger.info("Call addChurchDetails Service ends");
		return response;
	}
}

