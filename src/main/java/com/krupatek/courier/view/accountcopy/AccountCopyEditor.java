package com.krupatek.courier.view.accountcopy;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.model.AccountCopyFilter;
import com.krupatek.courier.service.*;
import com.krupatek.courier.utils.DateUtils;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@SpringComponent
@UIScope
public class AccountCopyEditor extends Div {
    private AccountCopyFilter filter;
    private final int PAGE_SIZE = 1000;

    public AccountCopyEditor(
            AccountCopyService accountCopyService,
            ClientService clientService,
            RateMasterService rateMasterService,
            RateIntMasterService rateIntMasterService,
            PlaceGenerationService placeGenerationService,
            NetworkService networkService,
            DateUtils dateUtils) {
        super();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setMargin(false);
        verticalLayout.setPadding(false);

        H4 title = new H4();
        title.setSizeFull();
        title.setText("Edit Account Copy");

        TextField docNo = new TextField();
        docNo.setPlaceholder("Filter by Doc No");
        docNo.setValueChangeMode(ValueChangeMode.LAZY);

        TextField podDate = new TextField();
        podDate.setPlaceholder("Filter by Date");
        podDate.setValueChangeMode(ValueChangeMode.LAZY);

        TextField clientName = new TextField();
        clientName.setPlaceholder("Filter by Client Name");
        clientName.setValueChangeMode(ValueChangeMode.LAZY);

        Grid<AccountCopy> accountCopyGrid = new Grid<>(AccountCopy.class, false);
        accountCopyGrid.setPageSize(PAGE_SIZE);

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

        HeaderRow hr = accountCopyGrid.prependHeaderRow();
        hr.getCell(accountCopyGrid.getColumnByKey("docNo")).setComponent(docNo);
        hr.getCell(accountCopyGrid.getColumnByKey("podDate")).setComponent(podDate);
        hr.getCell(accountCopyGrid.getColumnByKey("clientName")).setComponent(clientName);

        accountCopyGrid.setColumnReorderingAllowed(false);

        DataProvider<AccountCopy, AccountCopyFilter> dataProvider =
                DataProvider.fromFilteringCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Original offset : " + offset + ", limit :" + limit);

                            offset = offset / PAGE_SIZE;
                            limit = Math.min(limit, PAGE_SIZE);

                            AccountCopyFilter accountCopyFilter = query.getFilter().orElse(new AccountCopyFilter());
                            String docNoFilter = accountCopyFilter.getDocNoFilter();
                            String clientNameFilter = accountCopyFilter.getClientNameFilter();
                            Date dateFilter = accountCopyFilter.getDateFilter();

                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Corrected offset : " + offset + ", limit :" + limit);
                            Page<AccountCopy> accountCopies = dateFilter != null ?
                                    accountCopyService
                                            .findByDocNoStartsWithAndClientNameStartsWithAndPodDate(offset, limit, docNoFilter, clientNameFilter, dateFilter) :
                                    accountCopyService
                                            .findByDocNoStartsWithAndClientNameStartsWith(offset, limit, docNoFilter, clientNameFilter);
                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Total pages : " + accountCopies.getTotalElements());
                            return accountCopies.stream();
                        },
                        // Second callback fetches the number of items
                        // for a query
                        query -> {
                            AccountCopyFilter accountCopyFilter = query.getFilter().orElse(new AccountCopyFilter());
                            String docNoFilter = accountCopyFilter.getDocNoFilter();
                            String clientNameFilter = accountCopyFilter.getClientNameFilter();
                            Date dateFilter = accountCopyFilter.getDateFilter();
                            return Math.toIntExact(
                                    dateFilter != null ?
                                            accountCopyService.countByDocNoStartsWithAndClientNameStartsWithAndPodDate(docNoFilter, clientNameFilter, dateFilter) :
                                    accountCopyService.countByDocNoStartsWithAndClientNameStartsWith(docNoFilter, clientNameFilter));
                        });

        filter = new AccountCopyFilter();
        ConfigurableFilterDataProvider<AccountCopy, Void, AccountCopyFilter> wrapper =
                dataProvider.withConfigurableFilter();
        wrapper.setFilter(filter);
        accountCopyGrid.setDataProvider(wrapper);

        docNo.addValueChangeListener(event -> {
            filter.setDocNoFilter(event.getValue());
            wrapper.setFilter(filter);
//            wrapper.refreshAll();
        });

        clientName.addValueChangeListener(event -> {
            filter.setClientNameFilter(event.getValue());
            wrapper.setFilter(filter);
//            wrapper.refreshAll();
        });

        podDate.addValueChangeListener(event -> {
            try {
                Date date = new SimpleDateFormat("dd/MM/yy").parse(event.getValue());
                filter.setDateFilter(date);
                wrapper.setFilter(filter);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            wrapper.refreshAll();
        });

        accountCopyGrid.addItemClickListener(listener -> {
            AccountCopyForm accountCopyForm = new AccountCopyForm(
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

        Button addNewBtn = new Button("New Account Copy", VaadinIcon.PLUS.create());
        addNewBtn.setWidth("100%");
        addNewBtn.addClickListener(e -> add(new AccountCopyForm(
                accountCopyService,
                clientService,
                rateMasterService,
                rateIntMasterService,
                placeGenerationService,
                networkService,
                dateUtils,
                new AccountCopy())));
        verticalLayout.add(title, accountCopyGrid, addNewBtn);

        add(verticalLayout);
    }
}
