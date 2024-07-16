package com.corbcc.music_sched_sys.repository;

import com.corbcc.music_sched_sys.domain.ChurchDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ChurchDetailsRepository extends JpaRepository<ChurchDetailsEntity, Long> {
	
	
}
