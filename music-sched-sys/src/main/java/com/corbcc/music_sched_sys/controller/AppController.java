package com.corbcc.music_sched_sys.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.corbcc.music_sched_sys.domain.ChurchDetailsEntity;
import com.corbcc.music_sched_sys.dto.ChurchDetailsDto;
import com.corbcc.music_sched_sys.dto.LoginDto;
import com.corbcc.music_sched_sys.dto.ProfileDetailsDto;
import com.corbcc.music_sched_sys.dto.RoleDto;
import com.corbcc.music_sched_sys.dto.UserDetailsDto;
import com.corbcc.music_sched_sys.dto.UserRoleDto;
import com.corbcc.music_sched_sys.service.ChurchDetailsService;
import com.corbcc.music_sched_sys.service.ProfileService;
import com.corbcc.music_sched_sys.service.RoleService;
import com.corbcc.music_sched_sys.service.UserDetailsService;
import com.corbcc.music_sched_sys.service.UserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

	public static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@Autowired
	ChurchDetailsService churchDetailsService;
	
	@Autowired
    UserDetailsService userDetailsService;
	
	@Autowired
    RoleService roleService;
	
	@Autowired
    UserRoleService userRoleService;
	  
	@Autowired
	ProfileService profileService;
	
	//*********************************************************************************************	
	//***********************************CHURCH DETAILS SERVICES **********************************
	//*********************************************************************************************
	@RequestMapping(value = "/api/churchDetails/view", method = {RequestMethod.POST}, consumes = {
	"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> viewChurchDetails(@RequestBody ChurchDetailsDto request){
		logger.info("Call viewChurchDetails Service");
		ResponseEntity<?> response =  churchDetailsService.viewChurchDetails(request);
		logger.info("Call viewChurchDetails Service ends");
		return response;
	}
	@RequestMapping(value = "/api/churchDetails/save", method = {RequestMethod.POST}, consumes = {
	"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> saveChurchDetails(@RequestBody ChurchDetailsDto body){
		logger.info("Call saveChurchDetails Service");
		ResponseEntity<?> response =  churchDetailsService.saveChurchDetails(body);
		logger.info("Call saveChurchDetails Service ends");
		return response;
	}	
	@RequestMapping(value = "/api/churchDetails/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateChurchDetails(@RequestBody ChurchDetailsDto request) {
	    logger.info("Call updateChurchDetails Service");
	    ResponseEntity<?> response = churchDetailsService.updateChurchDetails(request);
	    logger.info("Call updateChurchDetails Service ends");
	    return response;
	}	
	@RequestMapping(value = "/api/churchDetails/delete", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteChurchDetails(@RequestBody ChurchDetailsDto request) {
        logger.info("Call deleteChurchDetails Service");
        ResponseEntity<?> response = churchDetailsService.deleteChurchDetails(request);
        logger.info("Call deleteChurchDetails Service ends");
        return response;
    }

	
	//*********************************************************************************************	
	//***********************************USER DETAILS SERVICES ************************************
	//*********************************************************************************************
	@RequestMapping(value = "/api/userDetails/view", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<?> viewUserDetails() {
        logger.info("Call viewUserDetails Service");
        ResponseEntity<?> response = userDetailsService.viewUserDetails();
        logger.info("Call viewUserDetails Service ends");
        return response;
    }
	@RequestMapping(value = "/api/userDetails/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveUserDetails(@RequestBody UserDetailsDto request) {
        logger.info("Call addUserDetails Service");
        ResponseEntity<?> response = userDetailsService.saveUserDetails(request);
        logger.info("Call addUserDetails Service ends");
        return response;
    }
	@RequestMapping(value = "/api/userDetails/update", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> updateUserDetails(@RequestBody UserDetailsDto request) {
        logger.info("Call updateUserDetails Service");
        ResponseEntity<?> response = userDetailsService.updateUserDetails(request);
        logger.info("Call updateUserDetails Service ends");
        return response;
    }
	@RequestMapping(value = "/api/userDetails/delete", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> deleteUserDetails(@RequestBody UserDetailsDto request) {
        logger.info("Call deleteUserDetails Service");
        ResponseEntity<?> response = userDetailsService.deleteUserDetails(request);
        logger.info("Call deleteUserDetails Service ends");
        return response;
    }
  
	//*********************************************************************************************	
	//***********************************ROLE DETAILS SERVICES ************************************
	//*********************************************************************************************
	@RequestMapping(value = "/api/roleDetails/view", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> viewRoleDetails() {
        logger.info("Call viewRoleDetails Service");
        ResponseEntity<?> response = roleService.viewRoleDetails();
        logger.info("Call viewRoleDetails Service ends");
        return response;
    }
	@RequestMapping(value = "/api/roleDetails/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveRoleDetails(@RequestBody RoleDto request) {
        logger.info("Call saveRoleDetails Service");
        ResponseEntity<?> response = roleService.saveRoleDetails(request);
        logger.info("Call saveRoleDetails Service ends");
        return response;
    }
    @RequestMapping(value = "/api/roleDetails/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateRoleDetails(@RequestBody RoleDto request) {
        logger.info("Call updateRoleDetails Service");
        ResponseEntity<?> response = roleService.updateRoleDetails(request);
        logger.info("Call updateRoleDetails Service ends");
        return response;
    }
    @RequestMapping(value = "/api/roleDetails/delete", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> deleteRoleDetails(@RequestBody RoleDto request) {
        logger.info("Call deleteRoleDetails Service");
        ResponseEntity<?> response = roleService.deleteRoleDetails(request);
        logger.info("Call deleteRoleDetails Service ends");
        return response;
    }
    //*********************************************************************************************	
  	//***********************************USER ROLE  SERVICES **************************************
  	//*********************************************************************************************
    @RequestMapping(value = "/api/userRoles/assign", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> assignRoles(@RequestBody UserRoleDto request) {
    	logger.info("Call assignRoles Service");
    	ResponseEntity<?> response = userRoleService.assignRoles(request);
    	logger.info("Call assignRoles Service ends");
    	return response;
    } 
    @RequestMapping(value = "/api/userRoles/view", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> viewUserRoles(@RequestBody UserRoleDto request) {
    	logger.info("Call viewUserRoles Service");
    	ResponseEntity<?> response =  userRoleService.viewUserRoles(request.getUserId());
        logger.info("Call viewUserRoles Service ends");
    	return response;
    }
    //*********************************************************************************************	
  	//***********************************LOGIN SERVICE ********************************************
  	//*********************************************************************************************
    @RequestMapping(value = "/api/login", method = {RequestMethod.POST}, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
    	logger.info("Call viewUserRoles Service");
    	ResponseEntity<?> response = userDetailsService.loginUser(request);
    	logger.info("Call viewUserRoles Service ends");
    	return response;
    }
    //*********************************************************************************************	
  	//***********************************PROFILE SERVICES *****************************************
  	//*********************************************************************************************
  
    @RequestMapping(value = "/api/profileDetails/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createProfile(@RequestBody ProfileDetailsDto request) {
        logger.info("Call createProfile Service");    
        ResponseEntity<?> response = profileService.saveProfile(request);
        logger.info("Call createProfile Service ends");
        return response;
    }
    @RequestMapping(value = "/api/profileDetails/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileDetailsDto request) {
        // Validate profileId
        if (request.getProfileId() == null) {
            logger.error("Profile ID is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile ID is required");
        }
        // Validate other fields if needed
        // Example: if (request.getProfileName() == null) { ... }
        logger.info("Update Profile request for profileId: {}", request.getProfileId());       
        // Process update profile request
        ResponseEntity<?> response = profileService.updateProfile(request);     
        logger.info("Update Profile request processed for profileId: {}", request.getProfileId());
        return response;
    }
    @RequestMapping(value = "/api/profileDetails/view", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> viewProfile(@RequestBody ProfileDetailsDto request) {
        UUID profileId = request.getProfileId();
        logger.info("Calling viewProfile Service for profileId: {}", profileId);
        ResponseEntity<?> response = profileService.viewProfile(profileId);
        logger.info("ViewProfile Service call ends");
        return response;
    }
}

