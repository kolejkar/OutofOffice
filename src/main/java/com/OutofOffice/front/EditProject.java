package com.OutofOffice.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Lists.Project;
import com.OutofOffice.Repository.EmployeeRepository;
import com.OutofOffice.Repository.ProjectRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("lists/project/edit/:projectID")
public class EditProject extends VerticalLayout implements BeforeEnterObserver {

	private Integer projectID;
	
	Label info;
	ComboBox<String> comboType;
	ComboBox<Employee> comboPM;		
	Checkbox checkStatus;
	DatePicker startPicker;
	DatePicker endPicker;
	TextArea textComment;
	Button save;
	
	List<String> projType;
	List<Employee> pm;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	EditProject()
	{
		projType = new ArrayList<String>();
		projType.add("IT");
		projType.add("Production");
		projType.add("Technical");
		
		info = new Label("New project: ");
		
		comboType = new ComboBox<String>("Project type:");
		comboType.setItems(projType);
		comboPM = new ComboBox<Employee>("Project manager: ");
		checkStatus = new Checkbox(false);
		startPicker = new DatePicker("Start date");
		endPicker = new DatePicker("End date");
		textComment = new TextArea();
		textComment.setLabel("Comment: ");
		textComment.setMaxLength(256);
		
		save = new Button("Save");
				
		add(info, comboType, comboPM, checkStatus, startPicker, endPicker, textComment, save);
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
	    
		pm = new ArrayList<Employee>();
		for (Employee e : employeeRepo.findAll())
		{
			if (e.getPosition().equals("Project Manager"))
			{
				pm.add(e);
			}
		}
		
		comboPM.setItems(pm);
		comboPM.setItemLabelGenerator(Employee::getFullname);
		comboPM.setRenderer(new TextRenderer<>(Employee::getFullname));
	    
	    if (projectID > 0)
		{
			Project project = projectRepo.findById(projectID).get();
			
			info.setText("Edit project: ");			
			comboType.setValue(project.getProjectType());
			comboPM.setValue(project.getProjectManager());
			checkStatus.setValue(project.isStatus());
			startPicker.setInitialPosition(project.getStartDate().toLocalDate());
			startPicker.setValue(project.getStartDate().toLocalDate());
			endPicker.setInitialPosition(project.getEndDate().toLocalDate());
			endPicker.setValue(project.getEndDate().toLocalDate());
			textComment.setValue(project.getComment());
		}
	    
	    save.addClickListener(clickEvent -> {
			Project project = new Project();
			
			if (projectID > 0)
			{
				project = projectRepo.findById(projectID).get();
			}
			
			project.setProjectType(comboType.getValue());
			project.setComment(textComment.getValue());
			project.setStatus(checkStatus.getValue());
			project.setProjectManager(comboPM.getValue());
			project.setStartDate(startPicker.getValue().atStartOfDay());
			project.setEndDate(endPicker.getValue().atStartOfDay());
			
			projectRepo.save(project);
			UI.getCurrent().getPage().setLocation("/lists/project");
		});
	}
}
