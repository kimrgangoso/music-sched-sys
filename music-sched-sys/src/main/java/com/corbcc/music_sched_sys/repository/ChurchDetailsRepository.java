package com.corbcc.music_sched_sys.repository;

import com.corbcc.music_sched_sys.domain.ChurchDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ChurchDetailsRepository extends JpaRepository<ChurchDetailsEntity, UUID> {
	List<ChurchDetailsEntity> findByChurchName(String churchName);
    List<ChurchDetailsEntity> findByAcronym(String acronym);
    List<ChurchDetailsEntity> findByChurchNameAndAcronym(String churchName, String acronym);
    
    @Transactional
	void deleteById(UUID id);
	
	
}
