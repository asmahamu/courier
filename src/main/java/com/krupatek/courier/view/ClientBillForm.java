//package com.krupatek.courier.view;
//
//import com.krupatek.courier.model.AccountCopy;
//import com.krupatek.courier.model.BillGeneration;
//import com.krupatek.courier.model.Client;
//import com.krupatek.courier.model.Company;
//import com.krupatek.courier.repository.CompanyRepository;
//import com.krupatek.courier.service.*;
//import com.krupatek.courier.utils.DateUtils;
//import com.krupatek.courier.view.accountcopy.AccountCopyForm;
//import com.vaadin.flow.component.AbstractField;
//import com.vaadin.flow.component.HasValue;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.datepicker.DatePicker;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.html.Label;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.select.Select;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.value.ValueChangeMode;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
//public class ClientBillForm extends Div {
//    private String currentSelectedItem;
//    private boolean isDomestic = true; // False means International.
//    private boolean isGSTEnabled = true;
//    private boolean isFuelSurchargeEnabled = true;
//
//    public ClientBillForm(
//            AccountCopyService accountCopyService,
//            ClientService clientService,
//            RateMasterService rateMasterService,
//            RateIntMasterService rateIntMasterService,
//            PlaceGenerationService placeGenerationService,
//            NetworkService networkService,
//            CompanyRepository companyRepository,
//            BillingService billingService,
//            DateUtils dateUtils,
//            BillGeneration item) {
//        super();
//        Dialog dialog = new Dialog();
//
//        HorizontalLayout horizontalLayout = new HorizontalLayout();
//        horizontalLayout.setPadding(true);
//        horizontalLayout.setMargin(false);
//
//        VerticalLayout verticalLayout = new VerticalLayout();
//        verticalLayout.setSizeFull();
//
//        Label title = new Label();
//        title.setText("Bill Edit");
//
//        // Client selection
//        Select<String> clientSelect = new Select<>();
//        clientSelect.setLabel("Select Client Name : ");
//        clientSelect.setWidth("400px");
//
//        List<Client> clientList = clientService.findAll();
//        List<String> clientNameList = new ArrayList<>();
//        clientList.forEach(c -> clientNameList.add(c.getClientName()));
//        currentSelectedItem = item.getClientName();
//
//        clientSelect.setItems(clientNameList);
//        clientSelect.setValue(currentSelectedItem);
//        clientSelect.setReadOnly(true);
//
//        HorizontalLayout invoiceDateHorizontalLayout = new HorizontalLayout();
//        invoiceDateHorizontalLayout.setWidthFull();
//
//        HorizontalLayout dateHorizontalLayout = new HorizontalLayout();
//        dateHorizontalLayout.setWidthFull();
//
//        HorizontalLayout extraChargesHorizontalLayout = new HorizontalLayout();
//        extraChargesHorizontalLayout.setWidthFull();
//
//
//        item.getStartDate();
//        item.getEndDate();
//
//        Binder<DateFilter> binder = new Binder<>(DateFilter.class);
//
//        // Invoice Date
//        TextField currentDate = new TextField(item.getBillDate());
//        currentDate.setLabel("Invoice Date");
//
//        // Booking Type
//        Select<String> bookingTypeSelect = new Select<>();
//        bookingTypeSelect.setLabel("Booking Type : ");
//        bookingTypeSelect.setItems("Dom", "Inter");
//        bookingTypeSelect.setValue("Dom");
//
//        // Start Date
//        TextField startDate = new TextField(item.getStartDate());
//        startDate.setLabel("From Date : ");
//
//        // End Date
//        TextField endDate = new TextField(item.getEndDate());
//        endDate.setLabel("To Date : ");
//
//
//        // CGST %
//        TextField cgst = new TextField();
//        cgst.setLabel("CGST in % : ");
//        cgst.setValueChangeMode(ValueChangeMode.EAGER);
//
//        // SGST %
//        TextField sgst = new TextField();
//        sgst.setLabel("SGST in % : ");
//        sgst.setValueChangeMode(ValueChangeMode.EAGER);
//
//        // IGST %
//        TextField igst = new TextField();
//        igst.setLabel("IGST in % : ");
//        igst.setValueChangeMode(ValueChangeMode.EAGER);
//
//        // Fuel Charge
//        TextField fuelSurcharge = new TextField();
//        fuelSurcharge.setLabel("Fuel Surcharge : ");
//        fuelSurcharge.setValueChangeMode(ValueChangeMode.EAGER);
//
//        // Refresh
//        Button refreshButton = new Button("Refresh");
//        Button showButton = new Button("Show");
//
//
//        invoiceDateHorizontalLayout.add(bookingTypeSelect, clientSelect, );
//        invoiceDateHorizontalLayout.setAlignItems(HorizontalLayout.Alignment.END);
//
//        dateHorizontalLayout.add(startDatePicker, endDatePicker, refreshButton, showButton);
//        dateHorizontalLayout.setAlignItems(HorizontalLayout.Alignment.END);
//
//        extraChargesHorizontalLayout.add(cgst, sgst, igst, fuelSurcharge);
//        extraChargesHorizontalLayout.setAlignItems(HorizontalLayout.Alignment.END);
//
//        Grid<AccountCopy> accountCopyGrid = new Grid<>(AccountCopy.class);
//        accountCopyGrid.setWidth("1300px");
//        accountCopyGrid.setHeight("350px");
//        accountCopyGrid.setColumns("docNo", "podDate", "clientName", "destination", "weight", "otherCharges", "rate", "dP", "mode");
//
//        accountCopyGrid.getColumnByKey("docNo").setWidth("150px").setFlexGrow(0);
//        accountCopyGrid.getColumnByKey("podDate").setWidth("200px").setFlexGrow(0);
//        accountCopyGrid.getColumnByKey("clientName").setWidth("300px").setFlexGrow(0);
//        accountCopyGrid.getColumnByKey("destination").setWidth("150px").setFlexGrow(0);
//        accountCopyGrid.getColumnByKey("weight").setWidth("100px").setFlexGrow(0);
//        accountCopyGrid.getColumnByKey("otherCharges").setWidth("100px").setFlexGrow(0);
//        accountCopyGrid.getColumnByKey("rate").setWidth("100px").setFlexGrow(0);
//        accountCopyGrid.getColumnByKey("dP").setWidth("100px").setFlexGrow(0);
//        accountCopyGrid.getColumnByKey("mode").setWidth("100px").setFlexGrow(0);
//
//        accountCopyGrid.setColumnReorderingAllowed(false);
//        verticalLayout.add(title, invoiceDateHorizontalLayout, dateHorizontalLayout, extraChargesHorizontalLayout, accountCopyGrid);
//
//        HorizontalLayout footerLayout = new HorizontalLayout();
//
//        TextField grossTotalTF = new TextField();
//        grossTotalTF.setLabel("Gross Amount : ");
//        grossTotalTF.setReadOnly(true);
//
//        TextField netTotalTF = new TextField();
//        netTotalTF.setLabel("Net Amount : ");
//        netTotalTF.setReadOnly(true);
//
//        footerLayout.setAlignItems(HorizontalLayout.Alignment.CENTER);
//        footerLayout.add(grossTotalTF, netTotalTF);
//        verticalLayout.add(footerLayout);
//        horizontalLayout.add(verticalLayout);
//        dialog.add(horizontalLayout);
//
//        // Last Month
//        lastMonthButton.addClickListener( c -> {
//            int month = currentDate.getMonthValue();
//            LocalDate lastMonth = currentDate.withMonth( (month - 1) % 11);
//            dateFilter.setStartDate(lastMonth.withDayOfMonth(1));
//            dateFilter.setEndDate(lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));
//            binder.readBean(dateFilter);
//            load(
//                    accountCopyGrid,
//                    accountCopyService,
//                    dateFilter,
//                    bookingTypeSelect,
//                    grossTotalTF,
//                    netTotalTF,
//                    cgst,
//                    sgst,
//                    igst,
//                    fuelSurcharge);
//        });
//
//        currentMonthButton.addClickListener( c -> {
//            dateFilter.setStartDate(start);
//            dateFilter.setEndDate(end);
//            binder.readBean(dateFilter);
//            load(
//                    accountCopyGrid,
//                    accountCopyService,
//                    dateFilter,
//                    bookingTypeSelect,
//                    grossTotalTF,
//                    netTotalTF,
//                    cgst,
//                    sgst,
//                    igst,
//                    fuelSurcharge);
//        });
//
//        refreshButton.addClickListener( c -> load(
//                accountCopyGrid,
//                accountCopyService,
//                dateFilter,
//                bookingTypeSelect,
//                grossTotalTF,
//                netTotalTF,
//                cgst,
//                sgst,
//                igst,
//                fuelSurcharge));
//        showButton.addClickListener( c -> load(
//                accountCopyGrid,
//                accountCopyService,
//                dateFilter,
//                bookingTypeSelect,
//                grossTotalTF,
//                netTotalTF,
//                cgst,
//                sgst,
//                igst,
//                fuelSurcharge));
//
//        bookingTypeSelect.addValueChangeListener(e -> {
//            isDomestic = !e.getValue().equals("Inter");
//            updateClientName(clientSelect, rateMasterService, rateIntMasterService);
//        });
//
//        clientSelect.addValueChangeListener(event -> {
//            if(event.getValue() != null) {
//                currentSelectedItem = event.getValue();
//                Company company = companyRepository.findAll().get(0);
//                Client client = clientService.findAllByClientName(currentSelectedItem).get(0);
//                isGSTEnabled = client.getGstEnabled().equalsIgnoreCase("Yes");
//                isFuelSurchargeEnabled = client.getFsc().equalsIgnoreCase("Yes");
//                updateTax(cgst, sgst, igst, fuelSurcharge, company);
//                load(
//                        accountCopyGrid,
//                        accountCopyService,
//                        dateFilter,
//                        bookingTypeSelect,
//                        grossTotalTF,
//                        netTotalTF,
//                        cgst,
//                        sgst,
//                        igst,
//                        fuelSurcharge);
//            }
//        });
//
//        accountCopyGrid.addItemClickListener(listener -> {
//            AccountCopyForm accountCopyForm =  new AccountCopyForm(
//                    accountCopyService,
//                    clientService,
//                    rateMasterService,
//                    rateIntMasterService,
//                    placeGenerationService,
//                    networkService,
//                    dateUtils,
//                    listener.getItem());
//            add(accountCopyForm);
//        });
//
//        updateTax(cgst,sgst,igst, fuelSurcharge, companyRepository.findAll().get(0));
//        binder.readBean(dateFilter);
//
//
//        dialog.open();
//
//    }
//
//    private void updateTax(TextField cgst, TextField sgst, TextField igst, TextField fuelSurchage, Company company) {
//        if(isGSTEnabled){
//
//            if(isDomestic){
//                // DISABLE IGST
//                igst.setValue("0"); igst.setEnabled(false);
//
//                // ENABLE domestic
//                cgst.setEnabled(true); cgst.setValue(String.valueOf(company.getCgst()));
//                sgst.setEnabled(true); sgst.setValue(String.valueOf(company.getSgst()));
//
//            } else {
//                // DISABLE domestic
//                cgst.setValue("0"); cgst.setEnabled(false);
//                sgst.setValue("0"); sgst.setEnabled(false);
//
//                // ENABLE IGST
//                igst.setEnabled(true); igst.setValue(String.valueOf(company.getIgst()));
//            }
//        } else {
//            // Disable all
//            cgst.setValue("0"); cgst.setEnabled(false);
//            sgst.setValue("0"); sgst.setEnabled(false);
//            igst.setValue("0"); igst.setEnabled(false);
//        }
//        if(isFuelSurchargeEnabled){
//            // ENABLE Fuel Surcharge
//            fuelSurchage.setEnabled(true); fuelSurchage.setValue(String.valueOf(company.getFuelSurcharge()));
//
//        } else {
//            // DISABLE Fuel Surcharge
//            fuelSurchage.setValue("0"); fuelSurchage.setEnabled(false);
//
//        }
//    }
//
//    private void updateClientName(Select<String> clientSelect, RateMasterService rateMasterService, RateIntMasterService rateIntMasterService){
//        if(isDomestic){
//            Set<String> distinctClientName = rateMasterService.findDistinctClientName();
//            clientSelect.setItems(distinctClientName);
//            currentSelectedItem = distinctClientName.iterator().next();
//            clientSelect.setValue(currentSelectedItem);
//        } else {
//            Set<String> distinctClientName = rateIntMasterService.findDistinctClientName();
//            clientSelect.setItems(distinctClientName);
//            currentSelectedItem = distinctClientName.iterator().next();
//            clientSelect.setValue(currentSelectedItem);
//        }
//    }
//
//    private void load(
//            Grid<AccountCopy> accountCopyGrid,
//            AccountCopyService accountCopyService,
//            DateFilter dateFilter,
//            Select<String> bookingTypeSelect,
//            TextField grossTotalTF,
//            TextField netTotalTF,
//            TextField cgst,
//            TextField sgst,
//            TextField igst,
//            TextField fuelSurcharge){
//        List<AccountCopy> allByClientNameAndPodDateBetween = accountCopyService.findAllByClientNameAndPodDateBetweenAndType(
//                currentSelectedItem,
//                fromLocaleDate(dateFilter.getStartDate()),
//                fromLocaleDate(dateFilter.getEndDate()),
//                bookingTypeSelect.getValue());
//        accountCopyGrid.setItems(
//                allByClientNameAndPodDateBetween);
//        Integer grossTotal = allByClientNameAndPodDateBetween.parallelStream().map(AccountCopy::getRate).reduce(0, Math::addExact);
//        grossTotalTF.setValue(String.format("%.02f", grossTotal.floatValue()));
//        long subTotal = (long) (grossTotal +
//                Integer.parseInt(fuelSurcharge.getValue()) * grossTotal / 100.0);
//        Double netTotal = subTotal +
//                Float.parseFloat(cgst.getValue()) * subTotal / 100.0 +
//                Float.parseFloat(sgst.getValue()) * subTotal / 100.0 +
//                Float.parseFloat(igst.getValue()) * subTotal / 100.0;
//        netTotalTF.setValue(String.format("%.02f", netTotal.floatValue()));
//    }
//
//    private void showError(String error){
//        Notification.show(error);
//    }
//
//    private Date fromLocaleDate(LocalDate localDate){
//        return new Date(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
//    }
//}