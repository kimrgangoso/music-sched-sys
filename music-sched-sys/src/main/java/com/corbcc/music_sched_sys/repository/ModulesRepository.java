package com.corbcc.music_sched_sys.repository;

import com.corbcc.music_sched_sys.domain.ChurchDetailsEntity;
import com.corbcc.music_sched_sys.domain.ModulesEntity;
import com.corbcc.music_sched_sys.domain.ProfileDetailsEntity;
import com.corbcc.music_sched_sys.domain.ProfileModulesEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ModulesRepository extends JpaRepository<ModulesEntity, UUID> {
	
}
