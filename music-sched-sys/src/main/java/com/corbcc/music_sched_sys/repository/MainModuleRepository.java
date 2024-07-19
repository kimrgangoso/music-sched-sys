package com.corbcc.music_sched_sys.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.corbcc.music_sched_sys.domain.MainModuleEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface MainModuleRepository extends JpaRepository<MainModuleEntity, UUID> {

	@Query(value = "SELECT mm.id AS mainModuleId, mm.menu AS mainModuleName, "
			+ "m.id AS moduleId, m.module AS moduleName, m.description AS moduleDescription, "
            + "a.id AS actionId, a.action AS actionName, a.description AS actionDescription "
            + "FROM cmssdb.tbl_mainmodules mm "
            + "LEFT JOIN cmssdb.tbl_modules m ON mm.id = m.mainmodule_id "
            + "LEFT JOIN cmssdb.tbl_actions a ON m.id = a.module_id "
            + "ORDER BY mm.id, m.id, a.id", nativeQuery = true)
	    List<Object[]> findAllMainModuleWithModulesAndActions();
}
