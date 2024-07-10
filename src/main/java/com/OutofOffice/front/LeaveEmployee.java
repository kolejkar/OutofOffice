package com.OutofOffice.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.OutofOffice.Lists.ApprovalRequest;
import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Lists.LeaveRequest;
import com.OutofOffice.Lists.Project;
import com.OutofOffice.Repository.ApprovalRequestRepository;
import com.OutofOffice.Repository.EmployeeRepository;
import com.OutofOffice.Repository.LeaveRequestRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("lists/leaveRequest/edit/:leaveID")
public class LeaveEmployee extends VerticalLayout implements BeforeEnterObserver {
	
	private Integer leaveID;
	
	Label info;
	ComboBox<Employee> comboEmployee;
	ComboBox<String> comboAbsense;
	DatePicker startPicker;
	DatePicker endPicker;
	Button save;
	Button cancel;
	Button sumbit;
	
	List<String> absense;
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	@Autowired
	LeaveRequestRepository leaveRequestRepo;
	
	@Autowired
	ApprovalRequestRepository approvalRequestRepo;
	
	LeaveEmployee()
	{
		absense = new ArrayList<String>();
		absense.add("Holiday");
		absense.add("Parental");
		absense.add("Unpaid");
		
		info = new Label("Create leave request: ");
		comboEmployee = new ComboBox<Employee>("Employee: ");
		startPicker = new DatePicker("Start date");
		endPicker = new DatePicker("End date");
		comboAbsense = new ComboBox<String>("Absense reason:");
		comboAbsense.setItems(absense);
				
		save = new Button("Save");
		cancel = new Button("Cancel");
		sumbit = new Button("Sumbit");
		
		add(info, comboEmployee, startPicker, endPicker, comboAbsense, save, sumbit, cancel);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {		
	    try 
	    {	
            leaveID = Integer.parseInt(event.getRouteParameters().get("leaveID").get());
	    }
	    catch (NumberFormatException e) {
		    leaveID = 0;
	    }
	    
	    comboEmployee.setItems(employeeRepo.findAll());
		comboEmployee.setItemLabelGenerator(Employee::getFullname);
		comboEmployee.setRenderer(new TextRenderer<>(Employee::getFullname));
	    
	    if (leaveID > 0)
		{
	    	LeaveRequest leave = leaveRequestRepo.findById(leaveID).get();
	    	
	    	if (leave.getStatus().equals("Submitted") || leave.getStatus().equals("Canceled"))
	    	{
	    		UI.getCurrent().getPage().setLocation("/lists/leaveRequest/detail/" + leave.getId());
	    	}
			
			info.setText("Edit leave request: ");			
			comboAbsense.setValue(leave.getAbsenceReason());
			comboEmployee.setValue(leave.getEmployee());
			startPicker.setInitialPosition(leave.getStartDate().toLocalDate());
			startPicker.setValue(leave.getStartDate().toLocalDate());
			endPicker.setInitialPosition(leave.getEndDate().toLocalDate());
			endPicker.setValue(leave.getEndDate().toLocalDate());
			//textComment.setValue(leave.getComment());
		}
	   		
		save.addClickListener(clickEvent -> {
			LeaveRequest leave = new LeaveRequest();
			
			if (leaveID > 0)
			{
				leave = leaveRequestRepo.findById(leaveID).get();
			}
			
			//leave.setComment(textComment.getValue());
			leave.setAbsenceReason(comboAbsense.getValue());
			leave.setEmployee(comboEmployee.getValue());
			leave.setStartDate(startPicker.getValue().atStartOfDay());
			leave.setEndDate(endPicker.getValue().atStartOfDay());
			leave.setStatus("New");
			
			leaveRequestRepo.save(leave);
			UI.getCurrent().getPage().setLocation("/lists/leaveRequest");
		});
		
		cancel.addClickListener(clickEvent -> {
			LeaveRequest leave = new LeaveRequest();
			
			if (leaveID > 0)
			{
				leave = leaveRequestRepo.findById(leaveID).get();
			}
			
			//leave.setComment(textComment.getValue());
			leave.setAbsenceReason(comboAbsense.getValue());
			leave.setEmployee(comboEmployee.getValue());
			leave.setStartDate(startPicker.getValue().atStartOfDay());
			leave.setEndDate(endPicker.getValue().atStartOfDay());
			leave.setStatus("Canceled");
			
			leaveRequestRepo.save(leave);
			UI.getCurrent().getPage().setLocation("/lists/leaveRequest");
		});
		
		sumbit.addClickListener(clickEvent -> {
			LeaveRequest leave = new LeaveRequest();
			
			if (leaveID > 0)
			{
				leave = leaveRequestRepo.findById(leaveID).get();
			}
			
			//leave.setComment(textComment.getValue());
			leave.setAbsenceReason(comboAbsense.getValue());
			leave.setEmployee(comboEmployee.getValue());
			leave.setStartDate(startPicker.getValue().atStartOfDay());
			leave.setEndDate(endPicker.getValue().atStartOfDay());
			leave.setStatus("Submitted");
			
			leaveRequestRepo.save(leave);
			
			ApprovalRequest approvalRequest = new ApprovalRequest();
			
			
			approvalRequest.setApprover(leave.getEmployee().getPartner());
			approvalRequest.setLeaveRequest(leave);
			approvalRequest.setStatus("New");
			
			approvalRequestRepo.save(approvalRequest);
			
			UI.getCurrent().getPage().setLocation("/lists/leaveRequest");
		});
	    
	}
}
