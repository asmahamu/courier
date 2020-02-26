package com.krupatek.courier.view.accountcopy;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.model.AccountCopyFilter;
import com.krupatek.courier.service.*;
import com.krupatek.courier.utils.DateUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.data.domain.Page;

import java.util.logging.Logger;

@SpringComponent
@UIScope
public class AccountCopyEditor extends Div {
    private  AccountCopyFilter filter;
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

        Label title = new Label();
        title.setSizeFull();
        title.setText("Edit Account Copy");

        TextField docNo = new TextField();
        docNo.setPlaceholder("Filter by Doc No");
        docNo.setValueChangeMode(ValueChangeMode.EAGER);

        TextField clientName = new TextField();
        clientName.setPlaceholder("Filter by Client Name");
        clientName.setValueChangeMode(ValueChangeMode.EAGER);

        Grid<AccountCopy> accountCopyGrid = new Grid<>(AccountCopy.class);
        accountCopyGrid.setPageSize(PAGE_SIZE);
        accountCopyGrid.setWidth("1200px");
        accountCopyGrid.setHeight("650px");
        accountCopyGrid.setColumns("docNo", "podDate", "clientName", "destination", "weight", "otherCharges", "rate", "dP", "mode");

        accountCopyGrid.getColumnByKey("docNo").setWidth("150px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("podDate").setWidth("200px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("clientName").setWidth("300px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("destination").setWidth("150px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("weight").setWidth("100px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("otherCharges").setWidth("75px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("rate").setWidth("75px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("dP").setWidth("75px").setFlexGrow(0);
        accountCopyGrid.getColumnByKey("mode").setWidth("75px").setFlexGrow(0);

        HeaderRow hr = accountCopyGrid.prependHeaderRow();
        hr.getCell(accountCopyGrid.getColumnByKey("docNo")).setComponent(docNo);
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

                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Original offset : "+offset+", limit :"+limit);

                            offset = offset/PAGE_SIZE;
                            limit = Math.min(limit, PAGE_SIZE);

                            AccountCopyFilter accountCopyFilter = query.getFilter().orElse(new AccountCopyFilter());
                            String docNoFilter = accountCopyFilter.getDocNoFilter();
                            String clientNameFilter = accountCopyFilter.getClientNameFilter();


                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Corrected offset : "+offset+", limit :"+limit);

                            Page<AccountCopy> accountCopies = accountCopyService
                                    .findByDocNoStartsWithAndClientNameStartsWith(offset, limit, docNoFilter, clientNameFilter);
                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Total pages : "+accountCopies.getTotalElements());
                            return accountCopies.stream();
                        },
                        // Second callback fetches the number of items
                        // for a query
                        query -> {
                            AccountCopyFilter accountCopyFilter = query.getFilter().orElse(new AccountCopyFilter());
                            String docNoFilter = accountCopyFilter.getDocNoFilter();
                            String clientNameFilter = accountCopyFilter.getClientNameFilter();
                            return Math.toIntExact(accountCopyService.countByDocNoStartsWithAndClientNameStartsWith(docNoFilter, clientNameFilter));
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

        Button addNewBtn = new Button("New Account Copy", VaadinIcon.PLUS.create());
        addNewBtn.addClickListener(e -> add(new AccountCopyForm(
                accountCopyService,
                clientService,
                rateMasterService,
                rateIntMasterService,
                placeGenerationService,
                networkService,
                dateUtils,
                new AccountCopy())));
        verticalLayout.add(title ,accountCopyGrid, addNewBtn);

        add(verticalLayout);
    }
}
