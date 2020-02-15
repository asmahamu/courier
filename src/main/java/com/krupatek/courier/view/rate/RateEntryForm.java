package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.RateEntry;
import com.krupatek.courier.service.RateMasterService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;

public class RateEntryForm extends Div {
    private RateEntry rateEntry;
    public RateEntryForm(
            RateMasterService rateMasterService,
            RateEntry rateEntry,
            UpdateRateEntry updateRateEntry){
        super();

        this.rateEntry = rateEntry;

        Binder<RateEntry> binder = new Binder<>();

        Dialog dialog = new Dialog();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPadding(true);
        horizontalLayout.setMargin(false);
        FormLayout formLayout = new FormLayout();
        formLayout.setMaxWidth("30em");
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("30em", 1));

        Label title = new Label();
        title.setSizeFull();
        title.setText("Rate Entry Details");
        formLayout.add(title, 1);

        // Client Name

        TextField clientName = new TextField();
        clientName.setLabel("Client Name : ");
        clientName.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(clientName, 1);
        binder.bind(clientName,
                RateEntry::getClientName,
                RateEntry::setClientName);
        clientName.setReadOnly(true);
        

        // Courier Name

        
        TextField courierName = new TextField();
        courierName.setLabel("Courier Name : ");
        courierName.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(courierName, 1);
        binder.bind(courierName,
                RateEntry::getCourier,
                RateEntry::setCourier);
        courierName.setReadOnly(true);
        

        // State code
        
        TextField stateCode = new TextField();
        stateCode.setLabel("State Code : ");
        stateCode.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(stateCode, 1);
        binder.bind(stateCode,
                RateEntry::getStateCode,
                RateEntry::setStateCode);
        stateCode.setReadOnly(true);
        

        // POD Type
        
        TextField podType = new TextField();
        podType.setLabel("POD Type : ");
        podType.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(podType, 1);
        binder.bind(podType,
                RateEntry::getPodType,
                RateEntry::setPodType);
        podType.setReadOnly(true);
        

        // Mode
        
        TextField mode = new TextField();
        mode.setLabel("Mode : ");
        mode.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(mode, 1);
        binder.bind(mode,
                RateEntry::getMode,
                RateEntry::setMode);
        mode.setReadOnly(true);
        

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
                        binder.writeBean(this.rateEntry);
                        rateMasterService.saveAndFlush(this.rateEntry);
                        updateRateEntry.update(this.rateEntry);
                        Notification.show("Rate entry updated successfully.");
                        // A real application would also save the updated person
                        // using the application's backend
                    } catch (ValidationException e) {
                        Notification.show("Person could not be saved, " +
                                "please check error messages for each field.");
                    }
                });
        Button reset = new Button("Reset",
                event -> binder.readBean(this.rateEntry));

        Button cancel = new Button("Cancel", event -> dialog.close());

        HorizontalLayout actions = new HorizontalLayout();
        actions.setAlignItems(HorizontalLayout.Alignment.END);
        actions.add(save, reset, cancel);
        save.getStyle().set("marginRight", "10px");
        
        formLayout.add(actions, 1);
        
        horizontalLayout.add(formLayout);
        dialog.add(horizontalLayout);
        dialog.open();
        binder.readBean(rateEntry);

    }
}
