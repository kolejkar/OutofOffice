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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("lists/employee/edit/:employeeID")
public class EditEmployee extends VerticalLayout implements BeforeEnterObserver {
	
	private Integer employeeID;
	
	Label info;
	TextField textFullname;
	TextField textBalance;
	ComboBox<String> comboPosition;
	ComboBox<String> comboSubdivision;
	Checkbox checkActive;		
	ComboBox<Project> comboProject;
	ComboBox<Employee> comboHR;
	
	Button save;

	List<Employee> hr;
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	@Autowired
	ProjectRepository projectRepo;
	
	EditEmployee()
	{	
		List<String> position = new ArrayList<String>();
		List<String> subdivision = new ArrayList<String>();
		hr = new ArrayList<Employee>();
		
		position.add("HR Manager");
		position.add("Project Manager");
		position.add("Employee");
		
		subdivision.add("HR");
		subdivision.add("Production");
		subdivision.add("Marketing");
		
		info = new Label("New employee: ");
		textFullname = new TextField("Fullname: ");
		textBalance = new TextField("Out-of-office Balance: ");
		comboPosition = new ComboBox<String>("Position: ");
		comboPosition.setItems(position);
		comboSubdivision = new ComboBox<String>("Subdivision: ");
		comboSubdivision.setItems(subdivision);
		checkActive = new Checkbox("Active");
		comboProject = new ComboBox<Project>("Select project: ");
		comboHR = new ComboBox<Employee>("People partner: ");
		
		save = new Button("Save");
				
		add(info, textFullname, textBalance, comboPosition, comboSubdivision, checkActive,
				comboProject, comboHR, save);
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
	    		
		for (Employee e : employeeRepo.findAll())
		{
			if (e.getPosition().equals("HR Manager"))
			{
				hr.add(e);
			}
		}
		
		comboHR.setItems(hr);
		comboHR.setItemLabelGenerator(Employee::getFullname);
		comboHR.setRenderer(new TextRenderer<>(Employee::getFullname));
		
		comboProject.setItems(projectRepo.findAll());
		comboProject.setItemLabelGenerator(Project::getProjectType);
		comboProject.setRenderer(new TextRenderer<>(Project::getProjectType));
		comboProject.setItems(projectRepo.findAll());
	    
	    if (employeeID > 0)
		{
			Employee employee = employeeRepo.findById(employeeID).get();
			
			info.setText("Edit employee: ");
			textFullname.setValue(employee.getFullname());
			textBalance.setValue(employee.getBalance().toString());
			comboPosition.setValue(employee.getPosition());
			comboSubdivision.setValue(employee.getSubdivision());
			checkActive.setValue(employee.isActive());
			if (employee.getProject() != null)
			{
				comboProject.setValue(employee.getProject());
			}
			if (employee.getPartner() != null)
			{
				comboHR.setValue(employee.getPartner());
			}
		}
	    
	    save.addClickListener(clickEvent -> {
			Employee employee = new Employee();
			
			if (employeeID > 0)
			{
				employee = employeeRepo.findById(employeeID).get();
			}
			
			employee.setFullname(textFullname.getValue());
			try {
			      Integer balance = Integer.parseInt(textBalance.getValue());
			      employee.setBalance(balance);
			} catch (Exception e) {
			      Notification.show("Bad Out-of-office Balance!");
			}
			employee.setPosition(comboPosition.getValue());
			employee.setSubdivision(comboSubdivision.getValue());
			employee.setActive(checkActive.getValue());
			if (comboProject.getValue() != null)
			{
				employee.setProject(comboProject.getValue());
			}
			if (comboHR.getValue() != null)
			{
				employee.setPartner(comboHR.getValue());
			}
			
			employeeRepo.save(employee);
			UI.getCurrent().getPage().setLocation("/lists/employee");
		});
	    
	}
}
