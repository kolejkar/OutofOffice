package com.OutofOffice.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OutofOffice.Lists.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

	List<LeaveRequest> findByOrderByEmployee();
	List<LeaveRequest> findByOrderByStartDate();
	List<LeaveRequest> findByOrderByEndDate();
	List<LeaveRequest> findByOrderByComment();
	List<LeaveRequest> findByOrderByStatus();
	List<LeaveRequest> findByOrderByAbsenceReason();
}
