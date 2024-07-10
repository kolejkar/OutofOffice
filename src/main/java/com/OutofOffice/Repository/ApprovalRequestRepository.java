package com.OutofOffice.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OutofOffice.Lists.ApprovalRequest;

public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Integer> {

	List<ApprovalRequest> findByOrderByApprover();
	List<ApprovalRequest> findByOrderByLeaveRequest();
	List<ApprovalRequest> findByOrderByStatus();
	List<ApprovalRequest> findByOrderByComment();
}
