package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.Client;
import com.krupatek.courier.model.Courier;
import com.krupatek.courier.model.RateIntEntry;
import com.krupatek.courier.service.ClientService;
import com.krupatek.courier.service.CourierService;
import com.krupatek.courier.service.NetworkService;
import com.krupatek.courier.service.RateIntMasterService;
import com.krupatek.courier.utils.ViewUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RateIntEntryForm extends Div {
    private String selectedNetwork = "FEDEX";
    private RateIntEntry rateIntEntry;

    public RateIntEntryForm(
            ClientService clientService,
            NetworkService networkService,
            CourierService courierService,
            RateIntMasterService rateIntMasterService,
            RateIntEntry rateIntEntry,
            UpdateRateIntEntry updateRateIntEntry){
        super();

        this.rateIntEntry = rateIntEntry;

        boolean isNewRateIntEntry = rateIntEntry.getRateIntMasterId() == null;

        Binder<RateIntEntry> binder = new Binder<>();

        Dialog dialog = new Dialog();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPadding(true);
        horizontalLayout.setMargin(false);
        FormLayout formLayout = new FormLayout();
        formLayout.setMaxWidth("30em");
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("30em", 1));

        formLayout.add(ViewUtils.addCloseButton(dialog), 12);

        Label title = new Label();
        title.setSizeFull();
        title.setText("Rate Entry Details");
        formLayout.add(title, 1);
        

        // Client Name

        if(isNewRateIntEntry){
            Select<String> clientSelect = new Select<>();
            clientSelect.setLabel("Select Client Name : ");

            List<Client> clientList = clientService.findAll();
            List<String> clientNameList = new ArrayList<>();
            clientList.forEach(c -> clientNameList.add(c.getClientName()));

            clientSelect.setItems(clientNameList);
            binder.bind(clientSelect, RateIntEntry::getClientName, RateIntEntry::setClientName);

            formLayout.add(clientSelect, 1);


            // Zones
            ComboBox<String> zoneSelect = new ComboBox<>();
            zoneSelect.setLabel("State Code : ");
            zoneSelect.setItems(getZones(selectedNetwork, networkService));
            binder.bind(zoneSelect, RateIntEntry::getStateCode, RateIntEntry::setStateCode);
            formLayout.add(zoneSelect, 1);


            // POD Type
            Select<String> selectDocumentOrParcelType = new Select<>();
            selectDocumentOrParcelType.setLabel("POD Type : ");
            selectDocumentOrParcelType.setItems("D", "P");
            binder.bind(selectDocumentOrParcelType, RateIntEntry::getPodType, RateIntEntry::setPodType);
            formLayout.add(selectDocumentOrParcelType, 1);

            // Mode

            Select<String> modeSelect = new Select<>();
            modeSelect.setLabel("Mode : ");
            modeSelect.setItems("S", "L", "A");
            binder.bind(modeSelect, RateIntEntry::getMode, RateIntEntry::setMode);
            formLayout.add(modeSelect, 1);

        } else {
            TextField clientName = new TextField();
            clientName.setLabel("Client Name : ");
            clientName.setValueChangeMode(ValueChangeMode.EAGER);
            formLayout.add(clientName, 1);
            binder.bind(clientName,
                    RateIntEntry::getClientName,
                    RateIntEntry::setClientName);
            clientName.setReadOnly(true);



            // State code

            TextField stateCode = new TextField();
            stateCode.setLabel("State Code : ");
            stateCode.setValueChangeMode(ValueChangeMode.EAGER);
            formLayout.add(stateCode, 1);
            binder.bind(stateCode,
                    RateIntEntry::getStateCode,
                    RateIntEntry::setStateCode);
            stateCode.setReadOnly(true);


            // POD Type

            TextField podType = new TextField();
            podType.setLabel("POD Type : ");
            podType.setValueChangeMode(ValueChangeMode.EAGER);
            formLayout.add(podType, 1);
            binder.bind(podType,
                    RateIntEntry::getPodType,
                    RateIntEntry::setPodType);
            podType.setReadOnly(true);


            // Mode

            TextField mode = new TextField();
            mode.setLabel("Mode : ");
            mode.setValueChangeMode(ValueChangeMode.EAGER);
            formLayout.add(mode, 1);
            binder.bind(mode,
                    RateIntEntry::getMode,
                    RateIntEntry::setMode);
            mode.setReadOnly(true);

        }
        
        // From
        
        TextField from = new TextField();
        from.setLabel("From : ");
        from.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(from, 1);
        binder.bind(from,
                c -> c.getFrom1().toString(),
                (c, t) -> c.setFrom1(Double.valueOf(t)));
        

        // to
        
        TextField to = new TextField();
        to.setLabel("From : ");
        to.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(to, 1);
        binder.bind(to,
                c -> c.getTo1().toString(),
                (c, t) -> c.setTo1(Double.valueOf(t)));
        

        // Rate
        
        TextField rate = new TextField();
        rate.setLabel("Rate : ");
        rate.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(rate, 1);
        binder.bind(rate,
                c -> c.getRate().toString(),
                (c, t) -> c.setRate(Integer.valueOf(t)));
        

        // Add Rate
        
        TextField addRate = new TextField();
        addRate.setLabel("Add Rate : ");
        addRate.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(addRate, 1);
        binder.bind(addRate,
                c -> c.getAddRt().toString(),
                (c, t) -> c.setAddRt(Integer.valueOf(t)));
        

        // Add Weight
        
        TextField addWeight = new TextField();
        addWeight.setLabel("Add Weight : ");
        addWeight.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(addWeight, 1);
        binder.bind(addWeight,
                c -> c.getAddWt().toString(),
                (c, t) -> c.setAddWt(Double.valueOf(t)));
        
        Button save = new Button("Save",
                event -> {
                    try {
                        binder.writeBean(this.rateIntEntry);

                        if(isNewRateIntEntry) {
                            rateIntEntry.setRateIntMasterId(rateIntMasterService.latestIntMasterId() + 1);
                            RateIntEntry existingRateIntEntry = rateIntMasterService.findByClientNameAndStateCodeAndPodTypeAndMode(
                                    rateIntEntry.getClientName(),
                                    rateIntEntry.getStateCode(),
                                    rateIntEntry.getPodType(),
                                    rateIntEntry.getMode()
                            );

                            if(existingRateIntEntry != null){
                                Notification.show("Rate is already defined for client "+rateIntEntry.getClientName()+
                                        ", to party :  "+
                                        selectedNetwork+", state code : "+rateIntEntry.getStateCode()+", d/p : "+
                                        rateIntEntry.getPodType()+", mode : "+rateIntEntry.getMode());

                            } else {
                                rateIntMasterService.saveAndFlush(this.rateIntEntry);
                                updateRateIntEntry.update(this.rateIntEntry);
                                Notification.show("Rate entry saved successfully.");
                            }
                        }
                        else {
                            rateIntMasterService.saveAndFlush(this.rateIntEntry);
                            updateRateIntEntry.update(this.rateIntEntry);
                            Notification.show("Rate entry updated successfully.");
                            // A real application would also save the updated person
                            // using the application's backend
                        }
                    } catch (ValidationException e) {
                        Notification.show("Person could not be saved, " +
                                "please check error messages for each field.");
                    }
                });
        Button reset = new Button("Reset",
                event -> binder.readBean(this.rateIntEntry));

        Button cancel = new Button("Cancel", event -> dialog.close());

        if(!isNewRateIntEntry){
            Button delete = new Button("Delete", event -> {
                try {
                    binder.writeBean(this.rateIntEntry);
                    rateIntMasterService.delete(this.rateIntEntry);
                    updateRateIntEntry.update(this.rateIntEntry);
                    Notification.show("Rate Entry deleted successfully for client " + rateIntEntry.getClientName() +
                            ", to party :  " + selectedNetwork + ", state code : " + rateIntEntry.getStateCode() +
                            ", d/p : " + rateIntEntry.getPodType() + ", mode : " + rateIntEntry.getMode());
                } catch (Exception e) {
                    Notification.show("Rate could not be deleted for client " + rateIntEntry.getClientName() +
                            ", to party :  " + selectedNetwork + ", state code : " +
                            rateIntEntry.getStateCode() + ", d/p : " + rateIntEntry.getPodType() + ", mode : " + rateIntEntry.getMode());
                }
                dialog.close();
            });
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

            HorizontalLayout actions = new HorizontalLayout();
            actions.setAlignItems(HorizontalLayout.Alignment.END);
            actions.add(save, reset, cancel, delete);
            formLayout.add(actions, 1);

        } else {
            HorizontalLayout actions = new HorizontalLayout();
            actions.setAlignItems(HorizontalLayout.Alignment.END);
            actions.add(save, reset, cancel);

            formLayout.add(actions, 1);
        }


        horizontalLayout.add(formLayout);
        dialog.add(horizontalLayout);
        dialog.open();
        binder.readBean(rateIntEntry);

    }
    private Set<String> getNetworks(CourierService courierService) {
        return courierService.findAll().parallelStream().map(Courier::getCourierName).collect(Collectors.toSet());
    }

    private Set<String> getZones(String zone, NetworkService networkService){
        return networkService.findDistinctZonesForNetwork(zone);
    }

}
