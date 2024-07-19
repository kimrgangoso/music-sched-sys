package com.corbcc.music_sched_sys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilesDto {
    private UUID userId;
    private UUID profileId;
    private String profileName;
}
