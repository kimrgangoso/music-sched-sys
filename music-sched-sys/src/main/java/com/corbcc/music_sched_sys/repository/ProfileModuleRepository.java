package com.corbcc.music_sched_sys.repository;

import com.corbcc.music_sched_sys.domain.ActionEntity;
import com.corbcc.music_sched_sys.domain.ChurchDetailsEntity;
import com.corbcc.music_sched_sys.domain.ModulesEntity;
import com.corbcc.music_sched_sys.domain.ProfileDetailsEntity;
import com.corbcc.music_sched_sys.domain.ProfileModulesEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ProfileModuleRepository extends JpaRepository<ProfileModulesEntity, UUID> {

	@Transactional
    void deleteByProfileId(UUID id);

    List<ProfileModulesEntity> findByProfile(ProfileDetailsEntity profile);
    
    List<ProfileModulesEntity> findByProfileAndModuleAndAction(ProfileDetailsEntity profile, ModulesEntity module, ActionEntity action);
    List<ProfileModulesEntity> findByProfileIdAndModuleId(UUID profileId, UUID moduleId);
    List<ProfileModulesEntity> findByProfileAndModule(ProfileDetailsEntity profile, ModulesEntity module);
	
}
