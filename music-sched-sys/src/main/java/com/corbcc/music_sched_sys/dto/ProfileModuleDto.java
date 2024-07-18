package com.corbcc.music_sched_sys.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProfileModuleDto {

  
    private String moduleName;
    private List<String> actions;

}
