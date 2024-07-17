package com.corbcc.music_sched_sys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserRoleResponseDto {
    private UUID userId;
    private String username;
    private List<String> roles;
}