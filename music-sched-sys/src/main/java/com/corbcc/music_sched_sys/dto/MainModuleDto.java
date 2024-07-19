package com.corbcc.music_sched_sys.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MainModuleDto {
    private UUID id;
    private String menu;
    private List<ModuleDto> modules;

}