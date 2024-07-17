package com.corbcc.music_sched_sys.repository;

import com.corbcc.music_sched_sys.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
	
	boolean existsByRoleIgnoreCase(String role);
    boolean existsByRoleIgnoreCaseAndIdNot(String role, UUID id);
		
	@Transactional
	void deleteById(UUID id);
}
