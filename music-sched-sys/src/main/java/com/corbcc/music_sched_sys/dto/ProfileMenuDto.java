package com.corbcc.music_sched_sys.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProfileMenuDto  {
    
	private UUID mainmoduleId;
    private String mainmoduleName;
    
    private UUID moduleId;
    private String moduleName;
    
    private UUID actionId;
    private String actionName;
}
