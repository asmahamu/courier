package com.krupatek.courier.view;

import com.krupatek.courier.Constants;
import com.krupatek.courier.model.*;
import com.krupatek.courier.repository.CompanyRepository;
import com.krupatek.courier.service.AccountCopyService;
import com.krupatek.courier.service.BillingService;
import com.krupatek.courier.service.ClientService;
import com.krupatek.courier.service.InvoiceService;
import com.krupatek.courier.view.accountcopy.AccountCopyEditor;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyDownEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamResource;
import org.springframework.data.domain.Page;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientBillRePrintingForm extends Div {
    private ClientBillFilter filter;
    private final int PAGE_SIZE = 1000;

    public ClientBillRePrintingForm(
            BillingService billingService,
            AccountCopyService accountCopyService,
            InvoiceService invoiceService,
            ClientService clientService,
            CompanyRepository companyRepository) {
        super();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);
        verticalLayout.setPadding(false);
        verticalLayout.setSizeFull();

        H4 title = new H4();
        title.setSizeFull();
        title.setText("Client Bill Reprinting");

        TextField billNo = new TextField();
        billNo.setPlaceholder("Filter by Bill No");
        billNo.setValueChangeMode(ValueChangeMode.LAZY);
        billNo.setValueChangeTimeout(Constants.TEXT_FIELD_TIMEOUT);

        TextField billDate = new TextField();
        billDate.setPlaceholder("Filter by Invoice Date");
        billDate.setValueChangeMode(ValueChangeMode.LAZY);
        billDate.setValueChangeTimeout(Constants.TEXT_FIELD_TIMEOUT);

        TextField clientName = new TextField();
        clientName.setPlaceholder("Filter by Client Name");
        clientName.setValueChangeMode(ValueChangeMode.LAZY);
        clientName.setValueChangeTimeout(Constants.TEXT_FIELD_TIMEOUT);

        Grid<BillGeneration> clientBillGrid = new Grid<>(BillGeneration.class, false);
        clientBillGrid.setPageSize(PAGE_SIZE);

        clientBillGrid.addColumn(BillGeneration::getBillNo).setKey("srNo").setHeader(new Html("<b>Sr No</b>")).
                setTextAlign(ColumnTextAlign.END).setWidth("8%").setFlexGrow(0).getElement().executeJs("this.renderer = function(root, column, rowData) {root.textContent = rowData.index + 1}");;


        clientBillGrid.addColumn(BillGeneration::getBillNo).setKey("billNo").setHeader(new Html("<b>Bill No</b>")).setTextAlign(ColumnTextAlign.END).setWidth("14%").setFlexGrow(0);
        clientBillGrid.addColumn(BillGeneration::getBillDate).setKey("billDate").setHeader(new Html("<b>Bill Date</b>")).setWidth("14%").setFlexGrow(0);
        clientBillGrid.addColumn(BillGeneration::getClientName).setKey("clientName").setHeader(new Html("<b>Client Name</b>")).setWidth("30%").setFlexGrow(0);
        clientBillGrid.addColumn(BillGeneration::getBillAmount).setKey("billAmount").setHeader(new Html("<b>Gross Amount</b>")).setTextAlign(ColumnTextAlign.END).setWidth("16.5%").setFlexGrow(0);
        clientBillGrid.addColumn(BillGeneration::getNetAmount).setKey("netAmount").setHeader(new Html("<b>Net Amount</b>")).setTextAlign(ColumnTextAlign.END).setWidth("16.5%").setFlexGrow(0);


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

        clientBillGrid.asSingleSelect().addValueChangeListener(gridBillGenerationComponentValueChangeEvent -> {
           clientBillGrid.select(gridBillGenerationComponentValueChangeEvent.getValue());
        });

        clientBillGrid.addSelectionListener(selectionEvent -> {
            if(selectionEvent.isFromClient() && selectionEvent.getFirstSelectedItem().isPresent()) {
                String invoiceNo = selectionEvent.getFirstSelectedItem().get().getBillNo();
                List<AccountCopy> accountCopies = accountCopyService.findAllByBillNo(invoiceNo + " ");
                Client client = clientService.findAllByClientName(selectionEvent.getFirstSelectedItem().get().getClientName()).get(0);
                Company company = companyRepository.findAll().get(0);

                try {
                    File pdfFile = invoiceService.generateInvoiceFor(company, client, selectionEvent.getFirstSelectedItem().get(), accountCopies, Locale.getDefault());

                    VerticalLayout embeddedPdfVLayout = new VerticalLayout();
                    embeddedPdfVLayout.setSizeFull();

                    StreamResource resource = new StreamResource("invoice.pdf", () -> {
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
                    leftEmptyLbl.setWidth("85%");

                    Anchor podSummaryDownloadLink = new Anchor(resource, "Download Invoice");
                    podSummaryDownloadLink.setWidth("10%");
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
                    closeButton.addClickListener(event -> {
                                dialog.close();
                            }
                    );
                    dialog.open();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        add(verticalLayout);
    }
}
