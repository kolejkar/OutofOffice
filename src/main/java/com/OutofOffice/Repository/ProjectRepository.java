package com.OutofOffice.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OutofOffice.Lists.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findByOrderByProjectType();
	List<Project> findByOrderByStartDate();
	List<Project> findByOrderByEndDate();
	List<Project> findByOrderByComment();
	List<Project> findByOrderByStatus();
	List<Project> findByOrderByProjectManager();
}
