package com.krupatek.courier.view;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.model.Client;
import com.krupatek.courier.service.AccountCopyService;
import com.krupatek.courier.service.ClientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringComponent
@UIScope
public class CustomerBillingDetailsForm extends Div {
    private String currentSelectedItem;
    private LocalDate startDate;
    private LocalDate endDate;

    public CustomerBillingDetailsForm(
            AccountCopyService accountCopyService,
            ClientService clientService) {
        super();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        Label title = new Label();
        title.setSizeFull();
        title.setText("Check List");


        // Client selection
        Select<String> cashCreditSelect = new Select<>();
        cashCreditSelect.setLabel("Select Client Name : ");
        cashCreditSelect.setWidthFull();


        List<Client> clientList = clientService.findAll();
        List<String> clientNameList = new ArrayList<>();
        clientList.forEach(c -> clientNameList.add(c.getClientName()));
        currentSelectedItem = clientNameList.get(0);

        cashCreditSelect.setItems(clientNameList);
        cashCreditSelect.setValue(currentSelectedItem);

        HorizontalLayout dateHorizontalLayout = new HorizontalLayout();
        dateHorizontalLayout.setWidthFull();

        // Start Date
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setLabel("From Date : ");

        // End Date
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setLabel("To Date : ");


        startDatePicker.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
            LocalDate endDate = endDatePicker.getValue();
            if (selectedDate != null) {
                startDate = selectedDate;
                endDatePicker.setMin(selectedDate.plusDays(1));
                if (endDate == null) {
                    endDatePicker.setOpened(true);
                    showError("Select the ending date");
                } else {
                    showError(
                            "Selected period:\nFrom " + selectedDate.toString()
                                    + " to " + endDate.toString());
                }
            } else {
                endDatePicker.setMin(null);
                showError("Select the starting date");
            }
        });

        endDatePicker.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
            LocalDate startDate = startDatePicker.getValue();
            if (selectedDate != null) {
                endDate = selectedDate;
                startDatePicker.setMax(selectedDate.minusDays(1));
                if (startDate != null) {
                    showError(
                            "Selected period:\nFrom " + startDate.toString()
                                    + " to " + selectedDate.toString());
                } else {
                    showError("Select the starting date");
                }
            } else {
                startDatePicker.setMax(null);
                if (startDate != null) {
                    showError("Select the ending date");
                } else {
                    showError("No date is selected");
                }
            }
        });
        // Refresh
        Button refreshButton = new Button("Refresh");
        Button showButton = new Button("Show");

        dateHorizontalLayout.add(startDatePicker, endDatePicker, refreshButton, showButton);
        dateHorizontalLayout.setAlignItems(HorizontalLayout.Alignment.END);

        Grid<AccountCopy> accountCopyGrid = new Grid<>(AccountCopy.class);
        accountCopyGrid.setWidth("1300px");
        accountCopyGrid.setHeight("400px");
        accountCopyGrid.setColumns("docNo", "podDate", "clientName", "destination", "weight", "otherCharges", "rate", "dP", "mode");

        accountCopyGrid.getColumnByKey("docNo").setWidth("150px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("podDate").setWidth("200px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("clientName").setWidth("300px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("destination").setWidth("150px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("weight").setWidth("100px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("otherCharges").setWidth("100px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("rate").setWidth("100px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("dP").setWidth("100px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("mode").setWidth("100px").setFlexGrow(0);

        accountCopyGrid.setColumnReorderingAllowed(false);
        verticalLayout.add(title, cashCreditSelect, dateHorizontalLayout, accountCopyGrid);

        add(verticalLayout);

        refreshButton.addClickListener( c -> load(accountCopyGrid, accountCopyService));
        showButton.addClickListener( c -> load(accountCopyGrid, accountCopyService));

        cashCreditSelect.addValueChangeListener(event -> {
            currentSelectedItem = event.getValue();
            load(accountCopyGrid, accountCopyService);
        });
    }

    private void load(Grid<AccountCopy> accountCopyGrid, AccountCopyService accountCopyService){
        accountCopyGrid.setItems(accountCopyService.findAllByClientNameAndPodDateBetween(currentSelectedItem, fromLocaleDate(startDate), fromLocaleDate(endDate)));
    }

    private void showError(String error){
        Notification.show(error);
    }

    private Date fromLocaleDate(LocalDate localDate){
        return new Date(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
