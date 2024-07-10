package com.OutofOffice.front;

import com.OutofOffice.Lists.LeaveRequest;
import com.OutofOffice.Repository.LeaveRequestRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

@Route("lists/leaveRequest")
public class LeaveRequestView extends VerticalLayout {
	
	public LeaveRequestView(LeaveRequestRepository leaveRequestRepo) {
		
		Label info = new Label("Leave Request lists:");		
		
		Grid<LeaveRequest> grid= new Grid<LeaveRequest>(LeaveRequest.class);
		
		SingleSelect<Grid<LeaveRequest>, LeaveRequest> leaveRequestSelect =
		        grid.asSingleSelect();
		
		Button button = new Button("Detail >>");
		
		button.addClickListener(clickEvent -> {
			LeaveRequest leaveRequest = leaveRequestSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/lists/leaveRequest/detail/" + leaveRequest.getId());
		});
		
		grid.addComponentColumn(item -> {return button; });
		
		Button edit = new Button("Edit");
		
		edit.addClickListener(clickEvent -> {
			LeaveRequest leaveRequest = leaveRequestSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/lists/leaveRequest/edit/" + leaveRequest.getId());
		});
		
		grid.addComponentColumn(item -> {return edit; });
		
		Button add = new Button("New");
		
		add.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/lists/leaveRequest/edit/0");
		});
		
		grid.setItems(leaveRequestRepo.findAll());
				
		add(info, add, grid);
	}
	
	
}
