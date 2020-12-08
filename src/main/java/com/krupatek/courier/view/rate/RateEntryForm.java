package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.Client;
import com.krupatek.courier.model.Courier;
import com.krupatek.courier.model.RateEntry;
import com.krupatek.courier.model.Zones;
import com.krupatek.courier.service.ClientService;
import com.krupatek.courier.service.CourierService;
import com.krupatek.courier.service.RateMasterService;
import com.krupatek.courier.service.ZonesService;
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
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RateEntryForm extends Div {
    private RateEntry rateEntry;

    public RateEntryForm(
            ClientService clientService,
            CourierService courierService,
            ZonesService zonesService,
            RateMasterService rateMasterService,
            RateEntry rateEntry,
            UpdateRateEntry updateRateEntry){
        super();

        this.rateEntry = rateEntry;

        boolean isNewRateEntry = rateEntry.getRateMasterId() == null;

        Binder<RateEntry> binder = new Binder<>();

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

        if(isNewRateEntry){
            // New Rate Entry Case

            Select<String> clientSelect = new Select<>();
            clientSelect.setLabel("Select Client Name : ");

            List<Client> clientList = clientService.findAll();
            List<String> clientNameList = new ArrayList<>();
            clientList.forEach(c -> clientNameList.add(c.getClientName()));

            clientSelect.setItems(clientNameList);
            binder.bind(clientSelect, RateEntry::getClientName, RateEntry::setClientName);

            formLayout.add(clientSelect, 1);

        } else {
            // Rate Entry Exists case

            TextField clientName = new TextField();
            clientName.setLabel("Client Name : ");
            clientName.setValueChangeMode(ValueChangeMode.EAGER);
            formLayout.add(clientName, 1);
            binder.bind(clientName,
                    RateEntry::getClientName,
                    RateEntry::setClientName);
            clientName.setReadOnly(true);
        }



        // Courier Name


        if(isNewRateEntry){
            // New Rate Entry Case

            // Courier selection
            Select<String> courierSelect = new Select<>();
            courierSelect.setLabel("Select Courier Name : ");


            List<Courier> couriersList = courierService.findAll();
            List<String> courierNameList = new ArrayList<>();
            couriersList.forEach(c -> courierNameList.add(c.getCourierName()));
            courierSelect.setItems(courierNameList);
            binder.bind(courierSelect, RateEntry::getCourier, RateEntry::setCourier);

            formLayout.add(courierSelect, 1);

        } else {
            // Rate Entry Exists case

            TextField courierName = new TextField();
            courierName.setLabel("Courier Name : ");
            courierName.setValueChangeMode(ValueChangeMode.EAGER);
            formLayout.add(courierName, 1);
            binder.bind(courierName,
                    RateEntry::getCourier,
                    RateEntry::setCourier);
            courierName.setReadOnly(true);
        }

        if(isNewRateEntry) {
            // New Rate Entry Case

            // State code

            ComboBox<String> zoneSelect = new ComboBox<>();
            zoneSelect.setLabel("State Code : ");

            TreeSet<String> zonesSource = zonesService.findAll().parallelStream().map(Zones::getZoneCode).collect(Collectors.toCollection(TreeSet::new));
            zoneSelect.setItems(zonesSource);

            binder.bind(zoneSelect,
                    RateEntry::getStateCode,
                    RateEntry::setStateCode);

            formLayout.add(zoneSelect, 1);
        } else {
            // Rate Entry Exists case

            TextField stateCode = new TextField();
            stateCode.setLabel("State Code : ");
            stateCode.setValueChangeMode(ValueChangeMode.EAGER);
            formLayout.add(stateCode, 1);
            binder.bind(stateCode,
                    RateEntry::getStateCode,
                    RateEntry::setStateCode);
            stateCode.setReadOnly(true);
        }

        // POD Type

        if(isNewRateEntry){
            // New Rate Entry Case

            Select<String> selectDocumentOrParcelType = new Select<>();
            selectDocumentOrParcelType.setLabel("POD Type : ");
            selectDocumentOrParcelType.setItems("D", "P");
            binder.bind(selectDocumentOrParcelType, RateEntry::getPodType, RateEntry::setPodType);
            formLayout.add(selectDocumentOrParcelType, 1);

        } else {
            // Rate Entry Exists case

            TextField podType = new TextField();
            podType.setLabel("POD Type : ");
            podType.setValueChangeMode(ValueChangeMode.EAGER);
            formLayout.add(podType, 1);
            binder.bind(podType,
                    RateEntry::getPodType,
                    RateEntry::setPodType);
            podType.setReadOnly(true);
        }



        // Mode

        if(isNewRateEntry){
            // New Rate Entry Case
            // Mode
            Select<String> modeSelect = new Select<>();
            modeSelect.setLabel("Mode : ");
            modeSelect.setItems("S", "L", "A");
            binder.bind(modeSelect, RateEntry::getMode, RateEntry::setMode);
            formLayout.add(modeSelect, 1);

        } else {
            // Rate Entry Exists case

            TextField mode = new TextField();
            mode.setLabel("Mode : ");
            mode.setValueChangeMode(ValueChangeMode.EAGER);
            formLayout.add(mode, 1);
            binder.bind(mode,
                    RateEntry::getMode,
                    RateEntry::setMode);
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
        to.setLabel("To : ");
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
        

        // Add Weight
        
        TextField addWeight = new TextField();
        addWeight.setLabel("Add Weight : ");
        addWeight.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(addWeight, 1);
        binder.bind(addWeight,
                c -> c.getAddWt().toString(),
                (c, t) -> c.setAddWt(Double.valueOf(t)));

        // Add Rate

        TextField addRate = new TextField();
        addRate.setLabel("Add Rate : ");
        addRate.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(addRate, 1);
        binder.bind(addRate,
                c -> c.getAddRt().toString(),
                (c, t) -> c.setAddRt(Integer.valueOf(t)));



        Button save = new Button("Save",
                event -> {
                    try {
                        binder.writeBean(this.rateEntry);
                        if(isNewRateEntry){
                            this.rateEntry.setRateMasterId(rateMasterService.latestMasterId() + 1);
                            RateEntry existingRateEntry =
                                    rateMasterService.findByClientNameAndCourierAndStateCodeAndPodTypeAndMode(
                                            this.rateEntry.getClientName(),
                                            this.rateEntry.getCourier(),
                                            this.rateEntry.getStateCode(),
                                            this.rateEntry.getPodType(),
                                            this.rateEntry.getMode()
                                    );
                            if(existingRateEntry != null){
                                Notification.show("Rate is already defined for client "+rateEntry.getClientName()+
                                        ", to party :  "+rateEntry.getCourier()+", state code : "+rateEntry.getStateCode()+", d/p : "+rateEntry.getPodType()+", mode : "+rateEntry.getMode());
                            } else {

                                rateMasterService.saveAndFlush(this.rateEntry);
                                updateRateEntry.update(this.rateEntry);
                                Notification.show("Rate entry saved successfully.");
                            }

                        } else {
                            rateMasterService.saveAndFlush(this.rateEntry);
                            updateRateEntry.update(this.rateEntry);
                            Notification.show("Rate entry updated successfully.");
                            // A real application would also save the updated person
                            // using the application's backend
                        }
                    } catch (ValidationException e) {
                        Notification.show("Rate Entry could not be saved, " +
                                "please check error messages for each field.");
                    }
                });
        Button reset = new Button("Reset",
                event -> binder.readBean(this.rateEntry));

        Button cancel = new Button("Cancel", event -> dialog.close());

        if(!isNewRateEntry) {
            Button delete = new Button("Delete", event -> {
                try {
                    binder.writeBean(this.rateEntry);
                    rateMasterService.delete(this.rateEntry);
                    updateRateEntry.update(this.rateEntry);
                    Notification.show("Rate Entry deleted successfully for client " + rateEntry.getClientName() +
                            ", to party :  " + rateEntry.getCourier() + ", state code : " + rateEntry.getStateCode() + ", d/p : " + rateEntry.getPodType() + ", mode : " + rateEntry.getMode());
                } catch (Exception e) {
                    Notification.show("Rate could not be deleted for client " + rateEntry.getClientName() +
                            ", to party :  " + rateEntry.getCourier() + ", state code : " + rateEntry.getStateCode() + ", d/p : " + rateEntry.getPodType() + ", mode : " + rateEntry.getMode());
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
        binder.readBean(rateEntry);

    }
}
