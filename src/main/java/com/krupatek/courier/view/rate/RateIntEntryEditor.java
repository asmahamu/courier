package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.Client;
import com.krupatek.courier.model.RateIntEntry;
import com.krupatek.courier.service.ClientService;
import com.krupatek.courier.service.CourierService;
import com.krupatek.courier.service.RateIntMasterService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
        verticalLayout.setMargin(false);
        verticalLayout.setPadding(false);

        H4 title = new H4();
        title.setSizeFull();
        title.setText("Rate Entry");

        // Client selection
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");

        Select<String> clientSelect = new Select<>();
        clientSelect.setLabel("Select Client Name : ");
        clientSelect.setWidth("50%");

        horizontalLayout.add(clientSelect);

        List<Client> clientList = clientService.findAll();
        List<String> clientNameList = new ArrayList<>();
        clientList.forEach(c -> clientNameList.add(c.getClientName()));
        currentSelectedClient = clientNameList.get(0);

        clientSelect.setItems(clientNameList);
        clientSelect.setValue(currentSelectedClient);

        Grid<RateIntEntry> rateEntryGrid = new Grid<>(RateIntEntry.class);
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

        rateEntryGrid.getColumnByKey("rateIntMasterId").setWidth("10%").setFlexGrow(0);
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

        List<RateIntEntry> rateEntryList = rateIntMasterService.findAllByClientName(currentSelectedClient);
        rateEntryGrid.setItems(rateEntryList);

        clientSelect.addValueChangeListener(event -> {
            currentSelectedClient = event.getValue();
            load(rateEntryGrid, rateIntMasterService);
        });

        verticalLayout.add(title, horizontalLayout, rateEntryGrid);
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