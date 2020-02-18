package com.krupatek.courier.view.accountcopy;

import com.krupatek.courier.model.*;
import com.krupatek.courier.service.*;
import com.krupatek.courier.utils.DateUtils;
import com.krupatek.courier.utils.RateUtils;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

@SpringComponent
@UIScope
public class AccountCopyForm extends Div {

    private boolean isDomestic = true; // False means International.

    public AccountCopyForm(
            AccountCopyService accountCopyService,
            ClientService clientService,
            RateMasterService rateMasterService,
            RateIntMasterService rateIntMasterService,
            PlaceGenerationService placeGenerationService,
            NetworkService networkService,
            DateUtils dateUtils,
            AccountCopy accountCopy) {
        super();

        boolean isNewAccountCopy = accountCopy.getDocNo() == null || accountCopy.getDocNo().isEmpty();

        Dialog dialog = new Dialog();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPadding(true);
        horizontalLayout.setMargin(false);
        FormLayout formLayout = new FormLayout();
        formLayout.setMaxWidth("60em");
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("15em", 1),
                new FormLayout.ResponsiveStep("15em", 2),
                new FormLayout.ResponsiveStep("15em", 3),
                new FormLayout.ResponsiveStep("15em", 4));

        Label title = new Label();
        title.setSizeFull();
        title.setText("Create New Account Copy");
        formLayout.add(title, 4);

        Binder<AccountCopy> binder = new Binder<>(AccountCopy.class);

        // Doc Number
        TextField docNo = new TextField();
        docNo.setLabel("Doc No. : ");
        docNo.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(docNo).asRequired("Every Account copy must have Doc no").bind(AccountCopy::getDocNo, AccountCopy::setDocNo);

        // Date
        DatePicker podDate = new DatePicker();
        podDate.setLabel("Date : ");
        binder.bind(podDate,
                    d -> dateUtils.asLocalDate(accountCopy.getPodDate()),
                    (a, d) ->  a.setPodDate(dateUtils.asDate(d)));


        // Cash / Credit
        Select<String> cashCreditSelect = new Select<>();
        cashCreditSelect.setLabel("Cash / Cr : ");
        cashCreditSelect.setItems("Cash", "Cr");
        binder.bind(cashCreditSelect, AccountCopy::getPodType, AccountCopy::setPodType);

        // Document / Parcel
        Select<String> selectDocumentOrParcelType = new Select<>();
        selectDocumentOrParcelType.setLabel("Document / Parcel : ");
        selectDocumentOrParcelType.setItems("D", "P");
        binder.bind(selectDocumentOrParcelType, AccountCopy::getdP, AccountCopy::setdP);

        formLayout.add(docNo, podDate, cashCreditSelect, selectDocumentOrParcelType);

        // Client Name

        if(!isNewAccountCopy){
            isDomestic =  !accountCopy.getType().equals("Inter");
        }

        ComboBox<String> clientsComboBox = new ComboBox<>();
        clientsComboBox.setLabel("Client Name : ");
        clientsComboBox.setClearButtonVisible(true);
        updateClientName(clientsComboBox, rateMasterService, rateIntMasterService);
        binder.bind(clientsComboBox, AccountCopy::getClientName, AccountCopy::setClientName);

        // Destination
        ComboBox<String> destinationComboBox = new ComboBox<>();
        destinationComboBox.setLabel("Destination : ");
        destinationComboBox.setClearButtonVisible(true);
        updateDestination(destinationComboBox, placeGenerationService, networkService);
        binder.bind(destinationComboBox, AccountCopy::getDestination,  (e, r) -> {
            e.setDestination(r);
            e.setPlaceCode(r);
            if(isDomestic) {
                PlaceGeneration placeGeneration = placeGenerationService.findByCityName(accountCopy.getDestination());
                e.setStateCode(placeGeneration.getPlaceCode());
            } else {
                Optional<Network> network = networkService.findOne(r);
                network.ifPresent(value -> e.setStateCode(value.getZoneName()));
            }
        });
        formLayout.add(clientsComboBox, destinationComboBox);

        formLayout.setColspan(clientsComboBox, 2);
        formLayout.setColspan(destinationComboBox, 2);


        // PinCode
        TextField pincode = new TextField();
        pincode.setLabel("Pincode : ");
        pincode.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(pincode,
                AccountCopy::getArea,
                AccountCopy::setArea);

        // Receiver Name
        TextField receiverName = new TextField();
        receiverName.setLabel("Receiver Name : ");
        receiverName.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(receiverName).asRequired("Every Account copy must receiver name").bind(AccountCopy::getReceiverName, AccountCopy::setReceiverName);

        // Courier Name
        Select<String> courierSelect = new Select<>();
        courierSelect.setLabel("Courier Name : ");
        courierSelect.setItems("TRACKON", "HORIZON", "BLUE DART", "PAFEX", "FEDEX", "PRIME TRACK");
        courierSelect.setValue("TRACKON");
        binder.bind(courierSelect, AccountCopy::getToParty, AccountCopy::setToParty);

        formLayout.add(pincode, receiverName, courierSelect);

        formLayout.setColspan(pincode, 1);
        formLayout.setColspan(receiverName, 2);
        formLayout.setColspan(courierSelect, 1);

        // Booking Type
        Select<String> bookingTypeSelect = new Select<>();
        bookingTypeSelect.setLabel("Booking Type : ");
        bookingTypeSelect.setItems("Dom", "Inter");
        bookingTypeSelect.setValue("Dom");
        binder.bind(bookingTypeSelect, AccountCopy::getType, AccountCopy::setType);
        if(isNewAccountCopy) {
            bookingTypeSelect.addValueChangeListener(e -> {
                isDomestic = !e.getValue().equals("Inter");
               updateClientName(clientsComboBox, rateMasterService, rateIntMasterService);
               updateDestination(destinationComboBox, placeGenerationService, networkService);
            });
        } else {
            bookingTypeSelect.setReadOnly(true);
        }
        // Mode
        Select<String> modeSelect = new Select<>();
        modeSelect.setLabel("Mode : ");
        modeSelect.setItems("S", "L", "A");
        binder.bind(modeSelect, AccountCopy::getMode, AccountCopy::setMode);

        // Weight
        TextField weight = new TextField();
        weight.setLabel("Weight : ");
        weight.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(weight).withConverter(
                new StringToDoubleConverter("Not a number")).bind(
                AccountCopy::getWeight,
                AccountCopy::setWeight);


        // Rate
        TextField rate = new TextField();
        rate.setLabel("Rate : ");
        rate.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(rate).withConverter(
                new StringToIntegerConverter("Not a number")).bind(
                AccountCopy::getRate,
                AccountCopy::setRate);

        formLayout.add(bookingTypeSelect, modeSelect, weight, rate);

        // Status
        Select<String> statusTypeSelect = new Select<>();
        statusTypeSelect.setLabel("Status : ");
        statusTypeSelect.setItems("Delivered", "Sign", "Stamp");
        statusTypeSelect.setValue("Delivered");
        binder.bind(statusTypeSelect, AccountCopy::getStatus, AccountCopy::setStatus);


        // Status Date
        DatePicker statusDate = new DatePicker();
        podDate.setLabel("Status Date : ");
        binder.bind(statusDate,
                d -> dateUtils.asLocalDate(accountCopy.getStatusDate() != null ? accountCopy.getStatusDate() : new Date()),
                (a, d) ->  a.setStatusDate(dateUtils.asDate(d)));


        // Remark
        TextField remark = new TextField();
        remark.setLabel("Remark : ");
        remark.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(remark,
                AccountCopy::getRemark,
                AccountCopy::setRemark);

        formLayout.add(statusTypeSelect, statusDate, remark);

        Button save = new Button("Save",
                event -> {
                    try {
                        binder.writeBean(accountCopy);
                        accountCopyService.saveAndFlush(accountCopy);
                        Notification.show("Account copy updated successfully.");
                        // A real application would also save the updated person
                        // using the application's backend
                    } catch (ValidationException e) {
                        Notification.show("Account Copy could not be saved, " +
                                "please check error messages for each field.");
                    }
                });
        Button reset = new Button("Reset",
                event -> binder.readBean(accountCopy));

        Button cancel = new Button("Cancel", event -> dialog.close());

        HorizontalLayout actions = new HorizontalLayout();
        actions.setAlignItems(HorizontalLayout.Alignment.END);
        actions.add(save, reset, cancel);
        formLayout.add(actions, 2);
        horizontalLayout.add(formLayout);
        dialog.add(horizontalLayout);

        dialog.open();

        docNo.addKeyDownListener(Key.ENTER, event ->
                podDate.focus());

        podDate.addValueChangeListener(e -> cashCreditSelect.focus());
        cashCreditSelect.addValueChangeListener(e -> selectDocumentOrParcelType.focus());
        selectDocumentOrParcelType.addValueChangeListener(e -> clientsComboBox.focus());
        clientsComboBox.addValueChangeListener(e -> destinationComboBox.focus());
        destinationComboBox.addValueChangeListener(e -> pincode.focus());
        pincode.addKeyDownListener(Key.ENTER, e -> receiverName.focus());
        receiverName.addKeyDownListener(Key.ENTER, e -> courierSelect.focus());
        courierSelect.addValueChangeListener(e -> bookingTypeSelect.focus());
        bookingTypeSelect.addValueChangeListener(e -> modeSelect.focus());
        modeSelect.addValueChangeListener(e -> weight.focus());
        weight.addKeyDownListener(Key.ENTER, e -> {
            try {
                binder.writeBean(accountCopy);
                Logger.getLogger(AccountCopyForm.class.getName()).info("AccountCopy : "+
                        accountCopy.getClientName()+", "+
                        accountCopy.getToParty()+", "+
                        accountCopy.getStateCode()+", "+
                        accountCopy.getdP()+", "+
                        accountCopy.getMode());
                if(isDomestic) {
                    RateEntry rateEntry =
                            rateMasterService.findByClientNameAndCourierAndStateCodeAndPodTypeAndMode(
                                    accountCopy.getClientName(),
                                    accountCopy.getToParty(),
                                    accountCopy.getStateCode(),
                                    accountCopy.getdP(),
                                    accountCopy.getMode()
                            );
                    Logger.getLogger(AccountCopyForm.class.getName()).info("RateEntry : " + rateEntry);
                    Double rateText = new RateUtils().charges(Double.valueOf(weight.getValue()), rateEntry.getFrom1(), rateEntry.getTo1(), rateEntry.getRate(), rateEntry.getAddWt(), (double) rateEntry.getAddRt());
                    rate.setValue(Integer.toString(rateText.intValue()));
                } else {
                    RateIntEntry rateIntEntry = rateIntMasterService.findByClientNameAndStateCodeAndPodTypeAndMode(
                      accountCopy.getClientName(),
                      accountCopy.getStateCode(),
                      accountCopy.getdP(),
                      accountCopy.getMode()
                    );
                    Logger.getLogger(AccountCopyForm.class.getName()).info("RateIntEntry : " + rateIntEntry);
                    Double rateText = new RateUtils().charges(Double.valueOf(weight.getValue()), rateIntEntry.getFrom1(), rateIntEntry.getTo1(), rateIntEntry.getRate(), rateIntEntry.getAddWt(), (double) rateIntEntry.getAddRt());
                    rate.setValue(Integer.toString(rateText.intValue()));
                }
                rate.focus();
            } catch (ValidationException ex) {
                Notification.show("Account Copy could not be saved, " +
                        "please check error messages for each field.");
            }
        });
        rate.addKeyDownListener(Key.ENTER, e -> save.focus());

        docNo.focus();

        binder.readBean(accountCopy);
    }

    private void updateClientName(ComboBox<String> clientComboBox, RateMasterService rateMasterService, RateIntMasterService rateIntMasterService){
        if(isDomestic){
            clientComboBox.setItems(rateMasterService.findDistinctClientName());
        } else {
            clientComboBox.setItems(rateIntMasterService.findDistinctClientName());
        }
    }
    private void updateDestination(ComboBox<String> destinationComboBox, PlaceGenerationService placeGenerationService, NetworkService networkService){
        if(isDomestic){
            destinationComboBox.setItems(placeGenerationService.findDistinctCityName());
        } else {
            destinationComboBox.setItems(networkService.findDistinctCountry());
        }
    }
}
