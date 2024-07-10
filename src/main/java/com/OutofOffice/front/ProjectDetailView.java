package com.OutofOffice.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Lists.Project;
import com.OutofOffice.Repository.EmployeeRepository;
import com.OutofOffice.Repository.ProjectRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("lists/employee/detail/:projectID")
public class ProjectDetailView extends VerticalLayout implements BeforeEnterObserver {

	private Integer projectID;
	
	Button button;
	Label info;
	Grid<Employee> grid;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	ProjectDetailView()
	{	
		grid= new Grid<Employee>(Employee.class);		
				
		Label info = new Label("*");
		
		button = new Button("Unactivate project");
		
		
		
		add(info, grid, button);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {		
	    try 
	    {	
            projectID = Integer.parseInt(event.getRouteParameters().get("projectID").get());
	    }
	    catch (NumberFormatException e) {
		    projectID = 0;
	    }
	    
	    Project project = projectRepo.findById(projectID).get();
		
		String detail = "\n\nProject details: ";
		detail += "\n\nProject type: " + project.getProjectType();
		detail += "\nStatus: " + project.isStatus();
		detail += "\nStart date: " + project.getStartDate();
		detail += "\nEnd date: " + project.getEndDate();
		detail += "\nProject manager: " + project.getProjectManager().getFullname();
		
		List<Employee> employeeList = employeeRepo.findByProject(project);
		
		detail += "\nEmployees in project: ";
		
		info.setText(detail);
				
		grid.setItems(employeeList);
	    
	    button.addClickListener(clickEvent -> {
			Project project1 = projectRepo.findById(projectID).get();
			project1.setStatus(false);
			projectRepo.save(project1);
		});
	}
}
