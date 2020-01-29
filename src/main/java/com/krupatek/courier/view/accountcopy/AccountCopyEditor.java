package com.krupatek.courier.view.accountcopy;

import com.krupatek.courier.model.AccountCopy;
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

import java.util.logging.Logger;

@SpringComponent
@UIScope
public class AccountCopyEditor extends Div {
    private  String filter;
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

        accountCopyGrid.setColumnReorderingAllowed(false);


        DataProvider<AccountCopy, String> dataProvider =
                DataProvider.fromFilteringCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            String filter = query.getFilter().orElse("");


                            Page<AccountCopy> accountCopies = accountCopyService
                                    .findByDocNoStartsWith(offset, limit, filter);
                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Filter is "+filter+" and count from fetch is "+accountCopies.getTotalElements());
                            return accountCopies.stream();
                        },
                        // Second callback fetches the number of items
                        // for a query
                        query -> {
                            String filter = query.getFilter().orElse("");
                            Integer count = Math.toIntExact(accountCopyService.countByDocNoStartsWith(filter));
                            Logger.getLogger(AccountCopyEditor.class.getName()).info("Filter is "+filter+" and count is "+count);
                            return count;
                        });

//        listAccountCopy("", accountCopyGrid, accountCopyService);


        ConfigurableFilterDataProvider<AccountCopy, Void, String> wrapper =
                dataProvider.withConfigurableFilter();
        wrapper.setFilter(filter);
        accountCopyGrid.setDataProvider(wrapper);

        docNo.addValueChangeListener(event -> {
            filter = event.getValue();
            if (filter.trim().isEmpty()) {
                // null disables filtering
                filter = null;
            }
            Logger.getLogger(AccountCopyEditor.class.getName()).info("Filter is "+filter);
            wrapper.setFilter(filter);
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
