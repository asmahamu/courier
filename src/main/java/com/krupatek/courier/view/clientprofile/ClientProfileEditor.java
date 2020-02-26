package com.krupatek.courier.view.clientprofile;

import com.krupatek.courier.model.Client;
import com.krupatek.courier.service.ClientService;
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
import org.springframework.data.domain.Page;

import java.util.logging.Logger;

public class ClientProfileEditor extends Div {

    private final int PAGE_SIZE = 50;
    private String clientNameFilter;
    public  ClientProfileEditor(ClientService clientService){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        Label title = new Label();
        title.setSizeFull();
        title.setText("Edit Client Profile");

        TextField clientName = new TextField();
        clientName.setPlaceholder("Filter by Client Name");
        clientName.setValueChangeMode(ValueChangeMode.EAGER);
        Grid<Client> clientGrid = new Grid<>(Client.class);
        clientGrid.setPageSize(PAGE_SIZE);
        clientGrid.setWidth("1200px");
        clientGrid.setHeight("650px");
        clientGrid.setColumns("clientCode", "clientName", "city", "phone", "branch_name", "gstNo", "gstEnabled", "fsc");

        clientGrid.getColumnByKey("clientCode").setWidth("50px").setFlexGrow(0);
        clientGrid.getColumnByKey("clientName").setWidth("350px").setFlexGrow(0);
        clientGrid.getColumnByKey("city").setWidth("200px").setFlexGrow(0);
        clientGrid.getColumnByKey("phone").setWidth("150px").setFlexGrow(0);
        clientGrid.getColumnByKey("branch_name").setWidth("75px").setFlexGrow(0);
        clientGrid.getColumnByKey("gstNo").setWidth("150px").setFlexGrow(0);
        clientGrid.getColumnByKey("gstEnabled").setWidth("100px").setFlexGrow(0);
        clientGrid.getColumnByKey("fsc").setWidth("100px").setFlexGrow(0);

        HeaderRow hr = clientGrid.prependHeaderRow();
        hr.getCell(clientGrid.getColumnByKey("clientName")).setComponent(clientName);

        clientGrid.setColumnReorderingAllowed(false);

        DataProvider<Client, String> dataProvider =
                DataProvider.fromFilteringCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            Logger.getLogger(ClientProfileEditor.class.getName()).info("offset : "+offset+", limit :"+limit);

                            offset = offset/PAGE_SIZE;
                            limit = PAGE_SIZE;

                            clientNameFilter = query.getFilter().orElse("");
                            Page<Client> accountCopies = clientService
                                    .findByClientNameStartsWith(offset, limit, clientNameFilter);
                            Logger.getLogger(ClientProfileEditor.class.getName()).info("First callback output for  : "+offset+", limit :"+limit);
                            Logger.getLogger(ClientProfileEditor.class.getName()).info("offset : "+offset+", limit :"+limit);
                            Logger.getLogger(ClientProfileEditor.class.getName()).info("pages: "+accountCopies.getNumber());
                            Logger.getLogger(ClientProfileEditor.class.getName()).info("numberOfElements : "+accountCopies.getNumberOfElements());
                            Logger.getLogger(ClientProfileEditor.class.getName()).info("size : "+accountCopies.getSize());
                            Logger.getLogger(ClientProfileEditor.class.getName()).info("totalElements : "+accountCopies.getTotalElements());
                            Logger.getLogger(ClientProfileEditor.class.getName()).info("totalPages : "+accountCopies.getTotalPages());
                            return accountCopies.stream();
                        },
                        // Second callback fetches the number of items
                        // for a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            clientNameFilter = query.getFilter().orElse("");

                            return Math.toIntExact(clientService.countByClientNameStartsWith(clientNameFilter));
                        });

        ConfigurableFilterDataProvider<Client, Void, String> wrapper =
                dataProvider.withConfigurableFilter();
        wrapper.setFilter(clientNameFilter);
        clientGrid.setDataProvider(wrapper);

        clientName.addValueChangeListener(event -> {
            wrapper.setFilter(event.getValue());
//            clientNameFilter = event.getValue();
//            wrapper.refreshAll();
        });


        clientGrid.addItemClickListener(listener -> {
            ClientProfileForm clientProfileForm =  new ClientProfileForm(
                    clientService,
                    listener.getItem());
            add(clientProfileForm);
        });

        Button addNewBtn = new Button("New Account Copy", VaadinIcon.PLUS.create());
        addNewBtn.addClickListener(e -> add(new ClientProfileForm(clientService, new Client())));

        verticalLayout.add(title ,clientGrid, addNewBtn);

        add(verticalLayout);
    }
}
