package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.Client;
import com.krupatek.courier.model.Courier;
import com.krupatek.courier.model.RateEntry;
import com.krupatek.courier.service.ClientService;
import com.krupatek.courier.service.CourierService;
import com.krupatek.courier.service.RateMasterService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;

import java.util.ArrayList;
import java.util.List;

public class RateEntryEditor extends Div {
    private String currentSelectedClient;
    private String currentSelectedCourier;
    private List<RateEntry> rateEntryList;

    public RateEntryEditor(ClientService clientService,
                           CourierService courierService,
                           RateMasterService rateMasterService){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setMargin(false);
        verticalLayout.setPadding(false);

        Label title = new Label();
        title.setSizeFull();
        title.setText("Rate Entry");

        // Client selection
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");

        Select<String> clientSelect = new Select<>();
        clientSelect.setLabel("Select Client Name : ");
        clientSelect.setWidth("50%");


        List<Client> clientList = clientService.findAll();
        List<String> clientNameList = new ArrayList<>();
        clientList.forEach(c -> clientNameList.add(c.getClientName()));
        currentSelectedClient = clientNameList.get(0);

        clientSelect.setItems(clientNameList);
        clientSelect.setValue(currentSelectedClient);

        // Courier selection
        Select<String> courierSelect = new Select<>();
        courierSelect.setLabel("Select Courier Name : ");
        courierSelect.setWidth("50%");


        List<Courier> couriersList = courierService.findAll();
        List<String> courierNameList = new ArrayList<>();
        couriersList.forEach(c -> courierNameList.add(c.getCourierName()));
        currentSelectedCourier = courierNameList.get(0);

        courierSelect.setItems(courierNameList);
        courierSelect.setValue(currentSelectedCourier);

        horizontalLayout.add(clientSelect, courierSelect);

        Grid<RateEntry> rateEntryGrid = new Grid<>(RateEntry.class);
        rateEntryGrid.setColumns(
                "rateMasterId",
                "clientName",
                "stateCode",
                "podType",
                "mode",
                "from1",
                "to1",
                "rate",
                "addWt",
                "addRt");

        rateEntryGrid.getColumnByKey("rateMasterId").setWidth("10%").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("clientName").setWidth("20%").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("stateCode").setWidth("14%").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("podType").setWidth("8%").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("mode").setWidth("8%").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("from1").setWidth("8%").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("to1").setWidth("8%").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("rate").setWidth("8%").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("addWt").setWidth("8%").setFlexGrow(0);
        rateEntryGrid.getColumnByKey("addRt").setWidth("8%").setFlexGrow(0);

        rateEntryGrid.setColumnReorderingAllowed(false);

        rateEntryList = rateMasterService.findAllByClientNameAndCourier(currentSelectedClient, currentSelectedCourier);
        rateEntryGrid.setItems(rateEntryList);

        clientSelect.addValueChangeListener(event -> {
            currentSelectedClient = event.getValue();
            load(rateEntryGrid, rateMasterService);
        });

        courierSelect.addValueChangeListener(event -> {
            currentSelectedCourier = event.getValue();
            load(rateEntryGrid, rateMasterService);
        });

        verticalLayout.add(title, horizontalLayout, rateEntryGrid);
        add(verticalLayout);
        rateEntryGrid.addItemClickListener(listener -> {
            RateEntryForm rateEntryForm =  new RateEntryForm(
                    rateMasterService,
                    listener.getItem(),
                    item -> {
                        load(rateEntryGrid, rateMasterService);
                    });
            add(rateEntryForm);
        });

    }

    private void load(Grid<RateEntry> rateEntryGrid, RateMasterService rateMasterService){
        rateEntryGrid.setItems(rateMasterService.findAllByClientNameAndCourier(currentSelectedClient, currentSelectedCourier));
    }

}

interface UpdateRateEntry{
    void update(RateEntry rateEntry);
}