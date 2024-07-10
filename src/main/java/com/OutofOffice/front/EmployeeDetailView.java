package com.OutofOffice.front;

import org.springframework.beans.factory.annotation.Autowired;

import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Lists.Project;
import com.OutofOffice.Repository.EmployeeRepository;
import com.OutofOffice.Repository.ProjectRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("lists/employee/detail/:employeeID")
public class EmployeeDetailView extends VerticalLayout implements BeforeEnterObserver {

	private Integer employeeID;
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	Label info;
	Button button;
	
	EmployeeDetailView()
	{		
		info = new Label("*");
		
		button = new Button("Unactivate employee");		
		
		add(info, button);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {		
	    try 
	    {	
            employeeID = Integer.parseInt(event.getRouteParameters().get("employeeID").get());
	    }
	    catch (NumberFormatException e) {
		    employeeID = 0;
	    }
	    
	    Employee employee = employeeRepo.findById(employeeID).get();
		
		String detail = "Employee detail:\n\n" + "Fullname: " + employee.getFullname();
		detail += "\nSubdivision: " + employee.getSubdivision();
		detail += "\nPosition: " + employee.getPosition();
		detail += "\nStatus: " + employee.isActive();
		detail += "\nOut of balance: " +employee.getBalance();
		try
		{
			detail += "\n\nPartner: " + employee.getPartner().getFullname();
		}
		catch (Exception e)
		{
			
		}
		
		Project project = employee.getProject();
		
		if (project != null)
		{		
			detail += "\n\nAssigned project info: ";
			detail += "\n\nProject type: " + project.getProjectType();
			detail += "\nStatus: " + project.isStatus();
			detail += "\nStart date: " + project.getStartDate();
			detail += "\nEnd date: " + project.getEndDate();
			detail += "\nProject manager: " + project.getProjectManager().getFullname();
		}
		
		info.setText(detail);
		
		button.addClickListener(clickEvent -> {
			Employee employee1 = employeeRepo.findById(employeeID).get();
			employee1.setActive(false);
			employeeRepo.save(employee1);
		});
	}
}
