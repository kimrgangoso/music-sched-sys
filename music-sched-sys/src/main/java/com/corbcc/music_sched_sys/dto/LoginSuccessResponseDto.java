package com.corbcc.music_sched_sys.dto;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSuccessResponseDto {
	

    private String username;
    private String firstName;
    private String lastName;
    private String status;
    private List<UUID> profileIdList;
}
