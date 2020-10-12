package com.krupatek.courier.view;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.model.Client;
import com.krupatek.courier.model.Company;
import com.krupatek.courier.repository.CompanyRepository;
import com.krupatek.courier.service.*;
import com.krupatek.courier.utils.DateUtils;
import com.krupatek.courier.utils.NumberUtils;
import com.krupatek.courier.view.accountcopy.AccountCopyForm;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class CustomerBillingDetailsForm extends Div {
    private final String CLIENT_SELECT_ALL = "ALL";
    private String currentSelectedItem;

    public CustomerBillingDetailsForm(
            AccountCopyService accountCopyService,
            ClientService clientService,
            RateMasterService rateMasterService,
            RateIntMasterService rateIntMasterService,
            PlaceGenerationService placeGenerationService,
            NetworkService networkService,
            PODSummaryService podSummaryService,
            DailyReportService dailyReportService,
            CompanyRepository companyRepository,
            DateUtils dateUtils,
            NumberUtils numberUtils) {
        super();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);
        verticalLayout.setPadding(false);

        H4 title = new H4();
        title.setSizeFull();
        title.setText("POD Summary / Daily Report");


        // Client selection
        Select<String> clientSelect = new Select<>();
        clientSelect.setWidth("18%");
        clientSelect.setLabel("Select Client Name : ");

        List<Client> clientList = clientService.findAll();
        List<String> clientNameList = new ArrayList<>();
        clientNameList.add("ALL");
        clientList.forEach(c -> clientNameList.add(c.getClientName()));
        currentSelectedItem = clientNameList.get(0);

        clientSelect.setItems(clientNameList);
        clientSelect.setValue(currentSelectedItem);

        HorizontalLayout dateHorizontalLayout = new HorizontalLayout();
        dateHorizontalLayout.setWidth("100%");

        LocalDate currentDate = LocalDate.now();
        LocalDate start = currentDate.withDayOfMonth(1);
        LocalDate end = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        // Last Month button
        Button lastMonthButton = new Button("Last Month");
        lastMonthButton.setWidth("8%");

        // Current Month button
        Button currentMonthButton = new Button("Current Month");
        currentMonthButton.setWidth("10%");

        DateFilter dateFilter = new DateFilter(start, end);

        Binder<DateFilter> binder = new Binder<>(DateFilter.class);

        // Start Date
        DatePicker startDatePicker = new DatePicker(start);
        startDatePicker.setLabel("From Date : ");
        startDatePicker.setWidth("10%");
        binder.bind(startDatePicker, DateFilter::getStartDate, DateFilter::setStartDate);

        // End Date
        DatePicker endDatePicker = new DatePicker(end);
        endDatePicker.setLabel("To Date : ");
        endDatePicker.setWidth("10%");
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
        Button refreshButton = new Button("Refresh", VaadinIcon.REFRESH.create());
        refreshButton.setWidth("8%");

        Button showButton = new Button("Show");
        showButton.setWidth("8%");

        Button podSummaryReport = new Button("POD Summary");
        podSummaryReport.setWidth("12%");

        Button dailyReport = new Button("Daily Report");
        dailyReport.setWidth("8%");

        dateHorizontalLayout.add(clientSelect, lastMonthButton, currentMonthButton, startDatePicker, endDatePicker, refreshButton, showButton, podSummaryReport, dailyReport);
        dateHorizontalLayout.setAlignItems(HorizontalLayout.Alignment.END);

        Grid<AccountCopy> accountCopyGrid = new Grid<>(AccountCopy.class, false);
        accountCopyGrid.setWidthFull();
        accountCopyGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        accountCopyGrid.addColumn(AccountCopy::getDocNo).setKey("docNo");
        accountCopyGrid.addColumn(accountCopy -> dateUtils.ddmmyyFormat(accountCopy.getPodDate())).setKey("podDate");
        accountCopyGrid.addColumn(AccountCopy::getClientName).setKey("clientName");
        accountCopyGrid.addColumn(AccountCopy::getDestination).setKey("destination");
        accountCopyGrid.addColumn(AccountCopy::getWeight).setKey("weight");
        accountCopyGrid.addColumn(AccountCopy::getOtherCharges).setKey("otherCharges");
        accountCopyGrid.addColumn(AccountCopy::getRate).setKey("rate");
        accountCopyGrid.addColumn(AccountCopy::getdP).setKey("dP");
        accountCopyGrid.addColumn(AccountCopy::getMode).setKey("mode");

        accountCopyGrid.getColumnByKey("docNo").setHeader(new Html("<b>Doc No</b>")).setWidth("10%").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("podDate").setHeader(new Html("<b>POD Date</b>")).setWidth("10%").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("clientName").setHeader(new Html("<b>Client Name</b>")).setWidth("25%").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("destination").setHeader(new Html("<b>Destination</b>")).setWidth("10%").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("weight").setHeader(new Html("<b>Weight</b>")).setWidth("8%").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("otherCharges").setHeader(new Html("<b>Other Charges</b>")).setWidth("11%").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("rate").setHeader(new Html("<b>Rate</b>")).setWidth("10%").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("dP").setHeader(new Html("<b>D/P</b>")).setWidth("8%").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("mode").setHeader(new Html("<b>Mode</b>")).setWidth("8%").setFlexGrow(0);

        accountCopyGrid.getColumnByKey("docNo").setTextAlign(ColumnTextAlign.END);
        accountCopyGrid.getColumnByKey("weight").setTextAlign(ColumnTextAlign.END);
        accountCopyGrid.getColumnByKey("otherCharges").setTextAlign(ColumnTextAlign.END);
        accountCopyGrid.getColumnByKey("rate").setTextAlign(ColumnTextAlign.END);

        accountCopyGrid.setColumnReorderingAllowed(false);

        HorizontalLayout reportGenerationButtonLayout = new HorizontalLayout();

        // Set Total and Gross Total
        Label totalDocNoLbl = new Label();
        totalDocNoLbl.setWidth("5%");
        totalDocNoLbl.addClassName("bold-label");
        totalDocNoLbl.setText("Total : ");

        TextField totalDocNoTF = new TextField();
        totalDocNoTF.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        totalDocNoTF.setWidth("5%");
        totalDocNoTF.addClassName("bold-label");
        totalDocNoTF.setReadOnly(true);

        Label leftEmptyLabelGrossTotalFooterHLayout = new Label();
        leftEmptyLabelGrossTotalFooterHLayout.setWidth("53%");

        Label grossTotalLbl = new Label();
        grossTotalLbl.setText("Gross Amount : ");
        grossTotalLbl.setWidth("11%");

        TextField grossTotalTF = new TextField();
        grossTotalTF.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        grossTotalTF.setWidth("10%");
        grossTotalTF.setReadOnly(true);

        Label rightEmptyLabelGrossTotalFooterHLayout = new Label();
        rightEmptyLabelGrossTotalFooterHLayout.setWidth("16%");


        HorizontalLayout grossTotalFooterHLayout = new HorizontalLayout();
        grossTotalFooterHLayout.setMargin(false);
        grossTotalFooterHLayout.setPadding(false);
        grossTotalFooterHLayout.setSpacing(false);
        grossTotalFooterHLayout.setWidth("100%");

        grossTotalFooterHLayout.add(totalDocNoLbl, totalDocNoTF, leftEmptyLabelGrossTotalFooterHLayout, grossTotalLbl, grossTotalTF, rightEmptyLabelGrossTotalFooterHLayout);

        podSummaryReport.addClickListener(event -> {
            if(currentSelectedItem.equalsIgnoreCase(CLIENT_SELECT_ALL)){
                showError("Please select client name and try again !");
            } else {
                VerticalLayout embeddedPdfVLayout = new VerticalLayout();
                embeddedPdfVLayout.setSizeFull();

                Client client = clientService.findAllByClientName(currentSelectedItem).get(0);
                List<AccountCopy> allByClientNameAndPodDateBetween = accountCopyService.findAllByClientNameAndPodDateBetween(
                        currentSelectedItem,
                        fromLocaleDate(dateFilter.getStartDate()),
                        fromLocaleDate(dateFilter.getEndDate()));
                try {
                    File pdfFile = podSummaryService.generateInvoiceFor(client, allByClientNameAndPodDateBetween, Locale.getDefault());
                    StreamResource resource = new StreamResource("POD-Summary.pdf", () -> {
                        try {
                            return new FileInputStream(pdfFile);
                        } catch (FileNotFoundException e1) {
                            return new ByteArrayInputStream(new byte[]{});
                        }
                    });
                    String width = "1300px";
                    String height = "500px";

                    HorizontalLayout buttonPanelReportPreview = new HorizontalLayout();

                    Label leftEmptyLbl = new Label();
                    leftEmptyLbl.setWidth("81%");

                    Anchor podSummaryDownloadLink = new Anchor(resource, "Download POD Summary");
                    podSummaryDownloadLink.setWidth("14%");
                    podSummaryDownloadLink.getElement().setAttribute("download", true);
                    Button closeButton = new Button("", VaadinIcon.CLOSE.create());
                    closeButton.setWidth("5%");

                    buttonPanelReportPreview.add(leftEmptyLbl, podSummaryDownloadLink, closeButton);
                    buttonPanelReportPreview.setWidth(width);
                    buttonPanelReportPreview.setAlignItems(FlexComponent.Alignment.CENTER);

                    embeddedPdfVLayout.add(buttonPanelReportPreview);
                    embeddedPdfVLayout.add(new EmbeddedPdfDocument(resource, width, height));


                    Dialog dialog = new Dialog();
                    dialog.add(embeddedPdfVLayout);
                    closeButton.addClickListener( e -> {
                                dialog.close();
                            }
                    );
                    dialog.open();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        dailyReport.addClickListener(e -> {
            VerticalLayout embeddedPdfVLayout = new VerticalLayout();
            embeddedPdfVLayout.setSizeFull();

                Company company = companyRepository.findAll().get(0);
                List<AccountCopy> allByClientNameAndPodDateBetween = accountCopyService.findAllByPodDateBetween(
                        fromLocaleDate(dateFilter.getStartDate()),
                        fromLocaleDate(dateFilter.getEndDate()));
            try {
                File pdfFile = dailyReportService.generateInvoiceFor(company, allByClientNameAndPodDateBetween, grossTotalTF.getValue(),  Locale.getDefault());
                StreamResource resource = new StreamResource("Daily-Report.pdf", () -> {
                    try {
                        return new FileInputStream(pdfFile);
                    } catch (FileNotFoundException e1) {
                        return new ByteArrayInputStream(new byte[]{});
                    }
                });
                String width = "1300px";
                String height = "500px";

                HorizontalLayout buttonPanelReportPreview = new HorizontalLayout();

                Label leftEmptyLbl = new Label();
                leftEmptyLbl.setWidth("81%");

                Anchor podSummaryDownloadLink = new Anchor(resource, "Download Daily Report");
                podSummaryDownloadLink.setWidth("14%");
                podSummaryDownloadLink.getElement().setAttribute("download", true);
                Button closeButton = new Button("", VaadinIcon.CLOSE.create());
                closeButton.setWidth("5%");

                buttonPanelReportPreview.add(leftEmptyLbl, podSummaryDownloadLink, closeButton);
                buttonPanelReportPreview.setWidth(width);
                buttonPanelReportPreview.setAlignItems(FlexComponent.Alignment.CENTER);

                embeddedPdfVLayout.add(buttonPanelReportPreview);
                embeddedPdfVLayout.add(new EmbeddedPdfDocument(resource, width, height));


                Dialog dialog = new Dialog();
                dialog.add(embeddedPdfVLayout);
                closeButton.addClickListener( event -> {
                            dialog.close();
                        }
                );
                dialog.open();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        verticalLayout.add(title, dateHorizontalLayout, reportGenerationButtonLayout, accountCopyGrid, grossTotalFooterHLayout);
        add(verticalLayout);

        // Last Month
        lastMonthButton.addClickListener( c -> {
            int month = currentDate.getMonthValue();
            LocalDate lastMonth = currentDate.withMonth( (month - 1) % 11);
            dateFilter.setStartDate(lastMonth.withDayOfMonth(1));
            dateFilter.setEndDate(lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));
            binder.readBean(dateFilter);
            load(accountCopyGrid, accountCopyService, dateFilter ,grossTotalTF, totalDocNoTF);
        });

        currentMonthButton.addClickListener( c -> {
            dateFilter.setStartDate(start);
            dateFilter.setEndDate(end);
            binder.readBean(dateFilter);
            load(accountCopyGrid, accountCopyService, dateFilter, grossTotalTF, totalDocNoTF);
        });

        refreshButton.addClickListener( c -> load(accountCopyGrid, accountCopyService, dateFilter, grossTotalTF, totalDocNoTF));
        showButton.addClickListener( c -> load(accountCopyGrid, accountCopyService, dateFilter, grossTotalTF, totalDocNoTF));


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

        clientSelect.addValueChangeListener(event -> {
            currentSelectedItem = event.getValue();
            load(accountCopyGrid, accountCopyService, dateFilter, grossTotalTF, totalDocNoTF);
        });

        accountCopyGrid.asSingleSelect().addValueChangeListener(gridAccountCopyComponentValueChangeEvent -> {
            accountCopyGrid.select(gridAccountCopyComponentValueChangeEvent.getValue());
        });


//        ShortcutEventListener shortcutEventListener = (ShortcutEventListener) shortcutEvent -> {
//            if(accountCopyGrid.getSelectedItems().size() > 0){
//                loadAccountForm(accountCopyService, clientService, rateMasterService, rateIntMasterService, placeGenerationService, networkService, dateUtils, numberUtils, accountCopyGrid.getSelectedItems().stream().findFirst().get());
//            }
//        };
//        Shortcuts.addShortcutListener(accountCopyGrid, shortcutEventListener, Key.ENTER);


        accountCopyGrid.addSelectionListener(selectionEvent -> {
            if(selectionEvent.isFromClient() && selectionEvent.getFirstSelectedItem().isPresent()){
                loadAccountForm(accountCopyService, clientService, rateMasterService, rateIntMasterService, placeGenerationService, networkService, dateUtils, numberUtils, selectionEvent.getFirstSelectedItem().get());
            }
        });

        binder.readBean(dateFilter);
    }

    private void loadAccountForm(AccountCopyService accountCopyService, ClientService clientService, RateMasterService rateMasterService, RateIntMasterService rateIntMasterService, PlaceGenerationService placeGenerationService, NetworkService networkService, DateUtils dateUtils, NumberUtils numberUtils, AccountCopy accountCopy) {
        AccountCopyForm accountCopyForm =  new AccountCopyForm(
                accountCopyService,
                clientService,
                rateMasterService,
                rateIntMasterService,
                placeGenerationService,
                networkService,
                dateUtils,
                numberUtils,
                accountCopy
                );
        add(accountCopyForm);
    }

    private void load(
            Grid<AccountCopy> accountCopyGrid,
            AccountCopyService accountCopyService,
            DateFilter dateFilter,
            TextField grossTotalTF,
            TextField totalDocNoTF){
        List<AccountCopy> allByClientNameAndPodDateBetween = new ArrayList<>();
        if(currentSelectedItem.equalsIgnoreCase("ALL")){
            allByClientNameAndPodDateBetween.addAll(accountCopyService.findAllByPodDateBetween(
                    fromLocaleDate(dateFilter.getStartDate()),
                    fromLocaleDate(dateFilter.getEndDate())));
        } else {
            allByClientNameAndPodDateBetween.addAll(accountCopyService.findAllByClientNameAndPodDateBetween(
                    currentSelectedItem,
                    fromLocaleDate(dateFilter.getStartDate()),
                    fromLocaleDate(dateFilter.getEndDate())));
        }
        accountCopyGrid.setItems(
                allByClientNameAndPodDateBetween);
        Integer grossTotal = allByClientNameAndPodDateBetween.parallelStream().map(AccountCopy::getRate).reduce(0, Math::addExact);
        grossTotalTF.setValue(String.format("%.02f", grossTotal.floatValue()));
        totalDocNoTF.setValue(String.valueOf(allByClientNameAndPodDateBetween.size()));
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