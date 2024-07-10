package com.OutofOffice.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Lists.Project;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByOrderByFullname();
	List<Employee> findByOrderBySubdivision();
	List<Employee> findByOrderByPosition();
	List<Employee> findByOrderByActive();
	List<Employee> findByOrderByBalance();
	List<Employee> findByOrderByPartner();
	List<Employee> findByProject(Project project);
}
