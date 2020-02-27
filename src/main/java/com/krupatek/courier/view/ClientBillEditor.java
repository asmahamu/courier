package com.krupatek.courier.view;

import com.krupatek.courier.model.BillGeneration;
import com.krupatek.courier.model.ClientBillFilter;
import com.krupatek.courier.repository.CompanyRepository;
import com.krupatek.courier.service.*;
import com.krupatek.courier.utils.DateUtils;
import com.krupatek.courier.view.accountcopy.AccountCopyEditor;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.data.domain.Page;

import java.util.logging.Logger;

public class ClientBillEditor extends Div {
    private ClientBillFilter filter;
    private final int PAGE_SIZE = 1000;

    public ClientBillEditor(
            BillingService billingService,
            AccountCopyService accountCopyService,
            ClientService clientService,
            RateMasterService rateMasterService,
            RateIntMasterService rateIntMasterService,
            PlaceGenerationService placeGenerationService,
            NetworkService networkService,
            CompanyRepository companyRepository,
            DateUtils dateUtils) {
        super();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        Label title = new Label();
        title.setSizeFull();
        title.setText("Client Bill Reprinting");

        TextField billNo = new TextField();
        billNo.setPlaceholder("Filter by Bill No");
        billNo.setValueChangeMode(ValueChangeMode.EAGER);

        TextField billDate = new TextField();
        billDate.setPlaceholder("Filter by Invoice Date");
        billDate.setValueChangeMode(ValueChangeMode.EAGER);

        TextField clientName = new TextField();
        clientName.setPlaceholder("Filter by Client Name");
        clientName.setValueChangeMode(ValueChangeMode.EAGER);

        Grid<BillGeneration> clientBillGrid = new Grid<>(BillGeneration.class);
        clientBillGrid.setPageSize(PAGE_SIZE);
        clientBillGrid.setWidth("1300px");
        clientBillGrid.setHeight("500px");
        clientBillGrid.setColumns("billNo", "billDate", "clientName", "billAmount", "netAmount");

        clientBillGrid.getColumnByKey("billNo").setWidth("200px").setFlexGrow(0);
        clientBillGrid.getColumnByKey("billDate").setWidth("200px").setFlexGrow(0);
        clientBillGrid.getColumnByKey("clientName").setWidth("300px").setFlexGrow(0);
        clientBillGrid.getColumnByKey("billAmount").setWidth("200px").setFlexGrow(0);
        clientBillGrid.getColumnByKey("netAmount").setWidth("200px").setFlexGrow(0);

        HeaderRow hr = clientBillGrid.prependHeaderRow();
        hr.getCell(clientBillGrid.getColumnByKey("billNo")).setComponent(billNo);
        hr.getCell(clientBillGrid.getColumnByKey("billDate")).setComponent(billDate);
        hr.getCell(clientBillGrid.getColumnByKey("clientName")).setComponent(clientName);

        clientBillGrid.setColumnReorderingAllowed(false);

        DataProvider<BillGeneration, ClientBillFilter> dataProvider =
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

                            ClientBillFilter clientBillFilter = query.getFilter().orElse(new ClientBillFilter());
                            String billNoFilter = clientBillFilter.getBillNoFilter();
                            String invoiceDateFilter = clientBillFilter.getInvoiceDateFilter();
                            String clientNameFilter = clientBillFilter.getClientNameFilter();


                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Corrected offset : "+offset+", limit :"+limit);

                            Page<BillGeneration> accountCopies = billingService
                                    .findByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(offset, limit, billNoFilter, invoiceDateFilter, clientNameFilter);
                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Total pages : "+accountCopies.getTotalElements());
                            return accountCopies.stream();
                        },
                        // Second callback fetches the number of items
                        // for a query
                        query -> {
                            ClientBillFilter clientBillFilter = query.getFilter().orElse(new ClientBillFilter());
                            String billNoFilter = clientBillFilter.getBillNoFilter();
                            String invoiceDateFilter = clientBillFilter.getInvoiceDateFilter();
                            String clientNameFilter = clientBillFilter.getClientNameFilter();
                            return Math.toIntExact(billingService.countByBillNoStartsWithAndBillDateStartsWithAndClientNameStartsWith(billNoFilter, invoiceDateFilter,clientNameFilter));
                        });

        filter = new ClientBillFilter();
        ConfigurableFilterDataProvider<BillGeneration, Void, ClientBillFilter> wrapper =
                dataProvider.withConfigurableFilter();
        wrapper.setFilter(filter);
        clientBillGrid.setDataProvider(wrapper);

        billNo.addValueChangeListener(event -> {
            filter.setBillNoFilter(event.getValue());
            wrapper.setFilter(filter);
//            wrapper.refreshAll();
        });

        billDate.addValueChangeListener(event -> {
            filter.setInvoiceDateFilter(event.getValue());
            wrapper.setFilter(filter);
//            wrapper.refreshAll();
        });

        clientName.addValueChangeListener(event -> {
            filter.setClientNameFilter(event.getValue());
            wrapper.setFilter(filter);
//            wrapper.refreshAll();
        });
        verticalLayout.add(title ,clientBillGrid);

        clientBillGrid.addItemClickListener(listener -> {
            String invoiceNo = listener.getItem().getBillNo();
//            List<AccountCopy> accountCopies = accountCopyService.findAllByBillNo(invoiceNo+" ");
//            Client client = clientService.findAllByClientName(listener.getItem().getClientName()).get(0);
//            Company company = companyRepository.findAll().get(0);
            add(new ClientBillForm(
                    accountCopyService,
                    clientService,
                    rateMasterService,
                    rateIntMasterService,
                    placeGenerationService,
                    networkService,
                    companyRepository,
                    billingService,
                    dateUtils,
                    listener.getItem()
            ));
        });

        add(verticalLayout);
    }
}
