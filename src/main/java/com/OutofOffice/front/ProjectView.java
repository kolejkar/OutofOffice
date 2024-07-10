package com.OutofOffice.front;

import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Lists.Project;
import com.OutofOffice.Repository.ProjectRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

@Route("lists/project")
public class ProjectView extends VerticalLayout {

	public ProjectView(ProjectRepository projectRepo) {
		
		Label info = new Label("Projects lists:");		
		
		Grid<Project> grid= new Grid<Project>(Project.class);
		
		SingleSelect<Grid<Project>, Project> projectSelect =
		        grid.asSingleSelect();
		
		Button button = new Button("Detail >>");
		
		button.addClickListener(clickEvent -> {
			Project project = projectSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/lists/project/detail/" + project.getId());
		});
		
		Button edit = new Button("Edit");
		
		edit.addClickListener(clickEvent -> {
			Project project = projectSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/lists/project/edit/" + project.getId());
		});
				
		grid.addComponentColumn(item -> {return button; });
		
		Button add = new Button("Add project");
		
		add.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/lists/project/edit/" + 0);
		});
		
		grid.addComponentColumn(item -> {return edit; });
		grid.setItems(projectRepo.findAll());
				
		add(info, add, grid);
	}
}
