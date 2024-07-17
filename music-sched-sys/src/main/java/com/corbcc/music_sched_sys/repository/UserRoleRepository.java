package com.corbcc.music_sched_sys.repository;

import com.corbcc.music_sched_sys.domain.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UUID> {
    
	List<UserRoleEntity> findByUserId(UUID userId);
    List<UserRoleEntity> findByUserIdAndRoleId(UUID userId, UUID roleId);
	
    void deleteByUserId(UUID userId);
}
