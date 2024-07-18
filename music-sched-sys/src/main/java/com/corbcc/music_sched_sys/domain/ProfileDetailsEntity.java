package com.corbcc.music_sched_sys.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_profiles")
public class ProfileDetailsEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "profilename")
    private String profileName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

    @Column(name = "status")
    private String status;
    
 // Assume many-to-many mapping with modules and actions
    @ManyToMany
    @JoinTable(
        name = "tbl_profilemodules",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private Set<ModulesEntity> modules;
    
    @ManyToMany
    @JoinTable(
        name = "tbl_profilemodules",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "action_id")
    )
    private Set<ActionEntity> actions;

}
