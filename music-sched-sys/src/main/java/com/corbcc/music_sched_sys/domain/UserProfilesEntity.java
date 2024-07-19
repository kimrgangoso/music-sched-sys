package com.corbcc.music_sched_sys.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_userprofiles")
@Data
@NoArgsConstructor
public class UserProfilesEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

	@ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserDetailsEntity user;

    @ManyToOne
    @JoinColumn(name = "profileId", nullable = false)
    private ProfileDetailsEntity profile;

    @Column(name = "profile_name", nullable = false)
    private String profileName;
    

}
