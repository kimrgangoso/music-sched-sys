package com.corbcc.music_sched_sys.repository;

import com.corbcc.music_sched_sys.domain.UserDetailsEntity;
import com.corbcc.music_sched_sys.domain.UserProfilesEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfilesEntity, UUID> {
	List<UserProfilesEntity> findByUserId(UUID userId);
	
	 void deleteByUserId(UUID userId);
}
