package com.OutofOffice.front;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.OutofOffice.Lists.ApprovalRequest;
import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Lists.LeaveRequest;
import com.OutofOffice.Lists.Project;
import com.OutofOffice.Repository.ApprovalRequestRepository;
import com.OutofOffice.Repository.EmployeeRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("lists/approvalRequest/detail/:approvalRequestID")
public class ApprovalRequestDetailView extends VerticalLayout implements BeforeEnterObserver {

	private Integer approvalRequestID;
	
	Button approve;
	Button reject;
	Label info;
	
	@Autowired
	ApprovalRequestRepository approvalRequestRepo;
	
	@Autowired
	EmployeeRepository employeeRepo;
		
	ApprovalRequestDetailView()
	{
		
		approve = new Button("Approve Request");
		
		reject = new Button("Reject Request");
		
		info = new Label("*");
		
		add(info, approve, reject);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {		
	    try 
	    {	
	    	approvalRequestID = Integer.parseInt(event.getRouteParameters().get("approvalRequestID").get());
	    }
	    catch (NumberFormatException e) {
	    	approvalRequestID = 0;
	    }
	    
	    ApprovalRequest approvalRequest = approvalRequestRepo.findById(approvalRequestID).get();
		
		String detail = "Employee detail:\n\n Status: " + approvalRequest.getStatus();
		detail += "\n\nApprover: " + approvalRequest.getApprover().getFullname();
		detail += "\nComment: " + approvalRequest.getComment();
		
		LeaveRequest leaveRequest = approvalRequest.getLeaveRequest();
		
		detail += "\n\nAssigned Leave Request info: ";
		
		detail += "\n\nAbsence Reason: " + leaveRequest.getAbsenceReason();
		detail += "\nStatus: " + leaveRequest.getStatus();
		detail += "\nStart date: " + leaveRequest.getStartDate();
		detail += "\nEnd date: " + leaveRequest.getEndDate();
		detail += "\n\nEmployee: " + leaveRequest.getEmployee().getFullname();
		
		info.setText(detail);
		
		approve.addClickListener(clickEvent -> {
			ApprovalRequest approvalRequest1 = approvalRequestRepo.findById(approvalRequestID).get();
			LeaveRequest leaveRequest1 = approvalRequest1.getLeaveRequest();
			Integer days = (int) ChronoUnit.DAYS.between(leaveRequest1.getStartDate().toLocalDate(),
					leaveRequest1.getEndDate().toLocalDate());
			
			Employee employee1 = leaveRequest1.getEmployee();
			employee1.setBalance(leaveRequest.getEmployee().getBalance() - days + 1);
			employeeRepo.save(employee1);
			
			approvalRequest1.setStatus("Approved");
			approvalRequestRepo.save(approvalRequest1);
			UI.getCurrent().getPage().setLocation("/lists/approvalRequest");
		});
		
		reject.addClickListener(clickEvent -> {
			ApprovalRequest approvalRequest1 = approvalRequestRepo.findById(approvalRequestID).get();
			approvalRequest1.setStatus("Rejected");
			approvalRequest1.setComment("Rejection should be possible.");
			approvalRequestRepo.save(approvalRequest1);
			UI.getCurrent().getPage().setLocation("/lists/approvalRequest");
		});
		
	}
}
