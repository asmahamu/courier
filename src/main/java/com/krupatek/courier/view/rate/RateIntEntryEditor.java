package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.Client;
import com.krupatek.courier.model.RateIntEntry;
import com.krupatek.courier.service.ClientService;
import com.krupatek.courier.service.CourierService;
import com.krupatek.courier.service.RateIntMasterService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;

import java.util.ArrayList;
import java.util.List;

public class RateIntEntryEditor extends Div {
    private String currentSelectedClient;

    public RateIntEntryEditor(ClientService clientService,
                              CourierService courierService,
                              RateIntMasterService rateIntMasterService){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        Label title = new Label();
        title.setSizeFull();
        title.setText("Rate Entry");

        // Client selection
        Select<String> clientSelect = new Select<>();
        clientSelect.setLabel("Select Client Name : ");
        clientSelect.setWidthFull();


        List<Client> clientList = clientService.findAll();
        List<String> clientNameList = new ArrayList<>();
        clientList.forEach(c -> clientNameList.add(c.getClientName()));
        currentSelectedClient = clientNameList.get(0);

        clientSelect.setItems(clientNameList);
        clientSelect.setValue(currentSelectedClient);

        Grid<RateIntEntry> rateEntryGrid = new Grid<>(RateIntEntry.class);
        rateEntryGrid.setWidth("1300px");
        rateEntryGrid.setHeight("400px");
        rateEntryGrid.setColumns(
                "rateIntMasterId",
                "clientName",
                "stateCode",
                "podType",
                "mode",
                "from1",
                "to1",
                "rate",
                "addWt",
                "addRt");

        rateEntryGrid.getColumnByKey("rateIntMasterId").setWidth("100px").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("clientName").setWidth("250px").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("stateCode").setWidth("250px").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("podType").setWidth("100px").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("mode").setWidth("100px").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("from1").setWidth("100px").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("to1").setWidth("100px").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("rate").setWidth("100px").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("addWt").setWidth("100px").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("addRt").setWidth("100px").setFlexGrow(0);

        rateEntryGrid.setColumnReorderingAllowed(false);

        List<RateIntEntry> rateEntryList = rateIntMasterService.findAllByClientName(currentSelectedClient);
        rateEntryGrid.setItems(rateEntryList);

        clientSelect.addValueChangeListener(event -> {
            currentSelectedClient = event.getValue();
            load(rateEntryGrid, rateIntMasterService);
        });

        verticalLayout.add(title, clientSelect, rateEntryGrid);
        add(verticalLayout);
        rateEntryGrid.addItemClickListener(listener -> {
            RateIntEntryForm rateIntEntryForm =  new RateIntEntryForm(
                    rateIntMasterService,
                    listener.getItem(),
                    item -> {
                        load(rateEntryGrid, rateIntMasterService);
                    });
            add(rateIntEntryForm);
        });

    }

    private void load(Grid<RateIntEntry> rateEntryGrid, RateIntMasterService rateIntMasterService){
        rateEntryGrid.setItems(rateIntMasterService.findAllByClientName(currentSelectedClient));
    }

}

interface UpdateRateIntEntry{
    void update(RateIntEntry rateEntry);
}