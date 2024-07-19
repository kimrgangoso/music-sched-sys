package com.corbcc.music_sched_sys.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ModuleDto {
    
	private UUID id;
    private String module;
    private String description;
    private List<ActionDto> actions;
}
