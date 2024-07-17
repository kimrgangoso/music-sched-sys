package com.corbcc.music_sched_sys.repository;

import com.corbcc.music_sched_sys.domain.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, UUID> {
	
	@Transactional
	void deleteById(UUID id);
}
