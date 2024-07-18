package com.corbcc.music_sched_sys.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProfileDetailsDto {
	
	private UUID profileId;
    private String profileName;
    private String createdBy;
    private String lastModifiedBy;
    private Timestamp creationDate;
    private Timestamp lastModifiedDate;
    private List<String> modules; // IDs of modules from tbl_modules
    private List<String> actions; // IDs of actions from tbl_actions
    private List<ProfileModuleDto> module;
}
