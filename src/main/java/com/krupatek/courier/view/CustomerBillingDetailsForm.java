package com.krupatek.courier.view;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.model.Client;
import com.krupatek.courier.service.*;
import com.krupatek.courier.utils.DateUtils;
import com.krupatek.courier.view.accountcopy.AccountCopyForm;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringComponent
@UIScope
public class CustomerBillingDetailsForm extends Div {
    private String currentSelectedItem;

    public CustomerBillingDetailsForm(
            AccountCopyService accountCopyService,
            ClientService clientService,
            RateMasterService rateMasterService,
            RateIntMasterService rateIntMasterService,
            PlaceGenerationService placeGenerationService,
            NetworkService networkService,
            PODSummaryService podSummaryService,
            DateUtils dateUtils) {
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

        LocalDate currentDate = LocalDate.now();
        LocalDate start = currentDate.withDayOfMonth(1);
        LocalDate end = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        // Last Month button
        Button lastMonthButton = new Button("Last Month");

        // Current Month button
        Button currentMonthButton = new Button("Current Month");


        DateFilter dateFilter = new DateFilter(start, end);

        Binder<DateFilter> binder = new Binder<>(DateFilter.class);

        // Start Date
        DatePicker startDatePicker = new DatePicker(start);
        startDatePicker.setLabel("From Date : ");
        binder.bind(startDatePicker, DateFilter::getStartDate, DateFilter::setStartDate);

        // End Date
        DatePicker endDatePicker = new DatePicker(end);
        endDatePicker.setLabel("To Date : ");
        binder.bind(endDatePicker, DateFilter::getEndDate, DateFilter::setEndDate);


        HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate>> StartDateChangeListener = event -> {
            LocalDate selectedDate = event.getValue();
            LocalDate endDate = endDatePicker.getValue();
            if (selectedDate != null) {
                dateFilter.setStartDate(selectedDate);
                endDatePicker.setMin(selectedDate.plusDays(1));
//                if (endDate == null) {
//                    endDatePicker.setOpened(true);
//                    showError("Select the ending date");
//                } else {
//                    showError(
//                            "Selected period:\nFrom " + selectedDate.toString()
//                                    + " to " + endDate.toString());
//                }
            } else {
                endDatePicker.setMin(null);
                showError("Select the starting date");
            }
        };
        startDatePicker.addValueChangeListener(StartDateChangeListener);

        HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate>> endDateChangeListener = event -> {
            LocalDate selectedDate = event.getValue();
            LocalDate startDate = startDatePicker.getValue();
            if (selectedDate != null) {
                dateFilter.setEndDate(selectedDate);
                startDatePicker.setMax(selectedDate.minusDays(1));
//                if (startDate != null) {
//                    showError(
//                            "Selected period:\nFrom " + startDate.toString()
//                                    + " to " + selectedDate.toString());
//                } else {
//                    showError("Select the starting date");
//                }
            } else {
                startDatePicker.setMax(null);
                if (startDate != null) {
                    showError("Select the ending date");
                } else {
                    showError("No date is selected");
                }
            }
        };
        endDatePicker.addValueChangeListener(endDateChangeListener);

        // Refresh
        Button refreshButton = new Button("Refresh");
        Button showButton = new Button("Show");



        dateHorizontalLayout.add(startDatePicker, endDatePicker, lastMonthButton, currentMonthButton, refreshButton, showButton);
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

        HorizontalLayout footerLayout = new HorizontalLayout();

        TextField sumTextField = new TextField();
        sumTextField.setLabel("Total : ");
        sumTextField.setReadOnly(true);

        Anchor podSummaryDownloadLink = new Anchor(new StreamResource("pod-summary.pdf", () -> {
            try {
                Client client = clientService.findAllByClientName(currentSelectedItem).get(0);
                List<AccountCopy> allByClientNameAndPodDateBetween = accountCopyService.findAllByClientNameAndPodDateBetween(
                        currentSelectedItem,
                        fromLocaleDate(dateFilter.getStartDate()),
                        fromLocaleDate(dateFilter.getEndDate()));
                File pdfFile = podSummaryService.generateInvoiceFor(client, allByClientNameAndPodDateBetween, Locale.getDefault());
                return  new FileInputStream(pdfFile);
            } catch (IOException e1) {
                return new ByteArrayInputStream(new byte[]{});
            }
        }), "Download POD Summary");
        podSummaryDownloadLink.getElement().setAttribute("download", true);

        footerLayout.setAlignItems(HorizontalLayout.Alignment.CENTER);
        footerLayout.add(sumTextField, podSummaryDownloadLink);
        verticalLayout.add(footerLayout);
        add(verticalLayout);

        // Last Month
        lastMonthButton.addClickListener( c -> {
            int month = currentDate.getMonthValue();
            LocalDate lastMonth = currentDate.withMonth( (month - 1) % 11);
            dateFilter.setStartDate(lastMonth.withDayOfMonth(1));
            dateFilter.setEndDate(lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));
            binder.readBean(dateFilter);
            load(accountCopyGrid, accountCopyService, dateFilter ,sumTextField);
        });

        currentMonthButton.addClickListener( c -> {
            dateFilter.setStartDate(start);
            dateFilter.setEndDate(end);
            binder.readBean(dateFilter);
            load(accountCopyGrid, accountCopyService, dateFilter, sumTextField);
        });

        refreshButton.addClickListener( c -> load(accountCopyGrid, accountCopyService, dateFilter, sumTextField));
        showButton.addClickListener( c -> load(accountCopyGrid, accountCopyService, dateFilter, sumTextField));


//        podSummary.addClickListener(e -> {
//            Client client = clientService.findAllByClientName(currentSelectedItem).get(0);
//            List<AccountCopy> allByClientNameAndPodDateBetween = accountCopyService.findAllByClientNameAndPodDateBetween(
//                    currentSelectedItem,
//                    fromLocaleDate(dateFilter.getStartDate()),
//                    fromLocaleDate(dateFilter.getEndDate()));
//            try {
//
//                File pdfFile = podSummaryService.generateInvoiceFor(client, allByClientNameAndPodDateBetween, Locale.getDefault());
//                getElement().setAttribute("data", new StreamResource("pod-summary.pdf", () -> {
//                    try {
//                        return  new FileInputStream(pdfFile);
//                    } catch (FileNotFoundException e1) {
//                        return new ByteArrayInputStream(new byte[]{});
//                    }
//                }));

// A button to open the printer-friendly page.
//                Button print = new Button(new Icon(VaadinIcon.PRINT));
//                opener.extend(print);


//                VerticalLayout v = new VerticalLayout();
//                v.setSizeFull();
//
//
//                v.add(new EmbeddedPdfDocument(new StreamResource("book-of-vaadin.pdf", () -> {
//                    try {
//                        return  new FileInputStream(pdfFile);
//                    } catch (FileNotFoundException e1) {
//                        return new ByteArrayInputStream(new byte[]{});
//                    }
//                })));
//
//                Dialog dialog = new Dialog();
//                dialog.add(v);
//                dialog.open();

//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        });

        cashCreditSelect.addValueChangeListener(event -> {
            currentSelectedItem = event.getValue();
            load(accountCopyGrid, accountCopyService, dateFilter, sumTextField);
        });

        accountCopyGrid.addItemClickListener(listener -> {
            AccountCopyForm accountCopyForm =  new AccountCopyForm(
                    accountCopyService,
                    clientService,
                    rateMasterService,
                    rateIntMasterService,
                    placeGenerationService,
                    networkService,
                    dateUtils,
                    listener.getItem());
            add(accountCopyForm);
        });

        binder.readBean(dateFilter);
    }

    private void load(
            Grid<AccountCopy> accountCopyGrid,
            AccountCopyService accountCopyService,
            DateFilter dateFilter,
            TextField sumTextField){
        List<AccountCopy> allByClientNameAndPodDateBetween = accountCopyService.findAllByClientNameAndPodDateBetween(
                currentSelectedItem,
                fromLocaleDate(dateFilter.getStartDate()),
                fromLocaleDate(dateFilter.getEndDate()));
        accountCopyGrid.setItems(
                allByClientNameAndPodDateBetween);
        long sum = allByClientNameAndPodDateBetween.parallelStream().map(AccountCopy::getRate).reduce(0, Math::addExact);
        sumTextField.setValue(Long.toString(sum));

    }

    private void showError(String error){
        Notification.show(error);
    }

    private Date fromLocaleDate(LocalDate localDate){
        return new Date(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}

class DateFilter{
    private LocalDate startDate;
    private LocalDate endDate;

    public DateFilter(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}