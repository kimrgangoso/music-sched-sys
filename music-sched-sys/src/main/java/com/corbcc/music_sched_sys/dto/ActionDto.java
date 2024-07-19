package com.corbcc.music_sched_sys.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ActionDto {
    
	private UUID id;
    private String action;
    private String description;
}
