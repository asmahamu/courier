package com.krupatek.courier.view.accountcopy;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.model.AccountCopyFilter;
import com.krupatek.courier.service.AccountCopyService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

@SpringComponent
@UIScope
public class AccountCopyEditor extends Div {
    private  AccountCopyFilter filter;
    public AccountCopyEditor(
            AccountCopyService accountCopyService) {
        super();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPadding(true);
        horizontalLayout.setMargin(false);

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
        accountCopyGrid.setWidth("1300px");
        accountCopyGrid.setHeight("500px");
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

                            AccountCopyFilter accountCopyFilter = query.getFilter().orElse(new AccountCopyFilter());
                            String docNoFilter = accountCopyFilter.getDocNoFilter();
                            String clientNameFilter = accountCopyFilter.getClientNameFilter();


                            Page<AccountCopy> accountCopies = accountCopyService
                                    .findByDocNoStartsWithAndClientNameStartsWith(offset, limit, docNoFilter, clientNameFilter);
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

//        listAccountCopy("", accountCopyGrid, accountCopyService);

        filter = new AccountCopyFilter();
        ConfigurableFilterDataProvider<AccountCopy, Void, AccountCopyFilter> wrapper =
                dataProvider.withConfigurableFilter();
        wrapper.setFilter(filter);
        accountCopyGrid.setDataProvider(wrapper);

        docNo.addValueChangeListener(event -> {
            filter.setDocNoFilter(event.getValue());
            wrapper.refreshAll();
        });

        clientName.addValueChangeListener(event -> {
            filter.setClientNameFilter(event.getValue());
            wrapper.refreshAll();
        });

        verticalLayout.add(title ,accountCopyGrid);
        horizontalLayout.add(verticalLayout);
        add(horizontalLayout);

//        docNo.addValueChangeListener(e -> listAccountCopy(e.getValue(), accountCopyGrid, accountCopyService));
    }

    void listAccountCopy(String filterText,
                         Grid<AccountCopy> accountCopyGrid,
                         AccountCopyService accountCopyService){
        if (StringUtils.isEmpty(filterText)) {
            accountCopyGrid.setItems(accountCopyService.findAll());
        }
        else {
            accountCopyGrid.setItems(accountCopyService.findByDocNoStartsWith(filterText));
        }
    }
}
