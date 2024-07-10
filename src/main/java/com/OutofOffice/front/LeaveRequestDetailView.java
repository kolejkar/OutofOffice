package com.OutofOffice.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Lists.LeaveRequest;
import com.OutofOffice.Repository.EmployeeRepository;
import com.OutofOffice.Repository.LeaveRequestRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("lists/leaveRequest/detail/:leaveRequestID")
public class LeaveRequestDetailView extends VerticalLayout implements BeforeEnterObserver {

	private Integer leaveRequestID;
	
	Label info;
	
	@Autowired
	LeaveRequestRepository leaveRequestRepo;
	
	LeaveRequestDetailView()
	{					
		info = new Label("*");
		
		add(info);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {		
	    try 
	    {	
            leaveRequestID = Integer.parseInt(event.getRouteParameters().get("leaveRequestID").get());
	    }
	    catch (NumberFormatException e) {
		    leaveRequestID = 0;
	    }
	    
		LeaveRequest leaveRequest = leaveRequestRepo.findById(leaveRequestID).get();
		
		String detail = "\n\nProject details: ";
		detail += "\n\nAbsence Reason: " + leaveRequest.getAbsenceReason();
		detail += "\nStatus: " + leaveRequest.getStatus();
		detail += "\nStart date: " + leaveRequest.getStartDate();
		detail += "\nEnd date: " + leaveRequest.getEndDate();
		detail += "\n\nEmployee: " + leaveRequest.getEmployee().getFullname();
		detail += "\nComment: " + leaveRequest.getComment();
		
		info.setText(detail);
	}
}
