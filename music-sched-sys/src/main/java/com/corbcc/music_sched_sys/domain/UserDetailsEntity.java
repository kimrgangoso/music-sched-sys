package com.corbcc.music_sched_sys.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_users")
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_updated_by")
    private String profileUpdatedBy;

    @Column(name = "account_enabled")
    private Boolean accountEnabled;

    @Column(name = "password_reset")
    private Boolean passwordReset;

    @Column(name = "failed_login")
    private Integer failedLogin;

    @Column(name = "last_good_login")
    private Timestamp lastGoodLogin;

    @Column(name = "last_bad_login")
    private Timestamp lastBadLogin;

    @Column(name = "password_history")
    private String passwordHistory;

    @Column(name = "last_password_change")
    private Timestamp lastPasswordChange;

    @Column(name = "user_date_created")
    private Timestamp userDateCreated;

    @Column(name = "user_created_by")
    private String userCreatedBy;

    @Column(name = "password_change_by")
    private String passwordChangeBy;

    @Column(name = "status")
    private String status = "active";

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "church_id")
    private UUID churchId;
}
