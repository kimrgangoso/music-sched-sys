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
public class UserDetailsDto {

    private UUID id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String userUpdatedBy;
    private Boolean accountEnabled;
    private Boolean passwordReset;
    private Integer failedLogin;
    private Timestamp lastGoodLogin;
    private Timestamp lastBadLogin;
    private String passwordHistory;
    private Timestamp lastPasswordChange;
    private Timestamp userDateCreated;
    private String userCreatedBy;
    private String passwordChangeBy;
    private String status;
    private String mobileNumber;
    private UUID churchId;
    
    private List<ProfileDetailsDto> profiles; 
}
