package com.OutofOffice.front;

import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Repository.EmployeeRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

@Route("lists/employee")
public class EmployeeView extends VerticalLayout {

	public EmployeeView(EmployeeRepository employeeRepo) {
		
		Label info = new Label("Employees lists:");
		
		Grid<Employee> grid= new Grid<Employee>(Employee.class);
		
		SingleSelect<Grid<Employee>, Employee> employeeSelect =
		        grid.asSingleSelect();
		
		Button button = new Button("Detail >>");
		
		button.addClickListener(clickEvent -> {
			Employee employee = employeeSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/lists/employee/detail/" + employee.getId());
		});
		
		grid.addComponentColumn(item -> {return button; });
		
		Button edit = new Button("Edit");
		
		edit.addClickListener(clickEvent -> {
			Employee employee = employeeSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/lists/employee/edit/" + employee.getId());
		});
		
		grid.addComponentColumn(item -> {return edit; });		
		grid.setItems(employeeRepo.findAll());
		
		Button add = new Button("Add employee");
		
		add.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/lists/employee/edit/0");
		});
		
		add(info, add, grid);
	}
}
