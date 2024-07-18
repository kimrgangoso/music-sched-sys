package com.corbcc.music_sched_sys.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

@Table(name = "tbl_profilemodules")
public class ProfileModulesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private ProfileDetailsEntity profile;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	private ModulesEntity module;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "action_id")
	private ActionEntity action;
	
	@Column(name = "profile_name")
    private String profileName;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "action_name")
    private String actionName;
	
	}

