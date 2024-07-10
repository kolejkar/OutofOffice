package com.OutofOffice.front;

import com.OutofOffice.Lists.ApprovalRequest;
import com.OutofOffice.Repository.ApprovalRequestRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

@Route("lists/approvalRequest")
public class ApprovalRequestView extends VerticalLayout {
	
	public ApprovalRequestView(ApprovalRequestRepository approvalRequestRepo) {
		
		Label info = new Label("Leave Request lists:");		
		
		Grid<ApprovalRequest> grid= new Grid<ApprovalRequest>(ApprovalRequest.class);
		
		SingleSelect<Grid<ApprovalRequest>, ApprovalRequest> approvalRequestSelect =
		        grid.asSingleSelect();
		
		Button button = new Button("Detail >>");
		
		button.addClickListener(clickEvent -> {
			ApprovalRequest approvalRequest = approvalRequestSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/lists/approvalRequest/detail/" + approvalRequest.getId());
		});
		
		
		grid.addComponentColumn(item -> {return button; });
		grid.setItems(approvalRequestRepo.findAll());
				
		add(info, grid);
	}
}
