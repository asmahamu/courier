package com.krupatek.courier.view.pod;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.service.AccountCopyService;
import com.krupatek.courier.utils.DateUtils;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.time.LocalDate;

public class PODEntryForm extends Div {
    AccountCopy accountCopy = new AccountCopy();

    public PODEntryForm(AccountCopyService accountCopyService, DateUtils dateUtils){
        Dialog dialog = new Dialog();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();


        Binder<AccountCopy> binder = new Binder<>(AccountCopy.class);

        H4 title = new H4();
        title.setSizeFull();
        title.setText("POD Entry Form");

        HorizontalLayout containerHorizonalLayout = new HorizontalLayout();

        VerticalLayout leftVerticalLayout = new VerticalLayout();
        // Left part Doc, Status Date, Remark

        // Doc Number
        TextField docNo = new TextField();
        docNo.setLabel("Doc No. : ");
        docNo.setValueChangeMode(ValueChangeMode.LAZY);
        docNo.addValueChangeListener(e -> {
           if(e.getValue() != null && e.getValue().length() > 4){
                String newDocNo = e.getValue();
                accountCopy =  accountCopyService.findOneByDocNo(newDocNo);
                if(accountCopy != null){
                    binder.readBean(accountCopy);
                }
           }
        });

        docNo.focus();

        // Status Date
        LocalDate currentDate = LocalDate.now();
        DatePicker statusDate = new DatePicker(currentDate);
        statusDate.setLabel("Status Date : ");
        binder.bind(statusDate, date -> currentDate, (model, date) -> model.setStatusDate(dateUtils.asDate(date))  );

        // Status
        Select<String> statusTypeSelect = new Select<>();
        statusTypeSelect.setLabel("Status : ");
        statusTypeSelect.setItems("DELIVERED", "SIGN", "STAMP");
        binder.bind(statusTypeSelect, status -> "", AccountCopy::setStatus);

        docNo.addKeyDownListener(Key.ENTER, event ->
                statusTypeSelect.focus());


        // Receiver's Name
        TextField receiverName = new TextField();
        receiverName.setLabel("Receiver's Name : ");
        receiverName.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(receiverName, receiverNm -> "", AccountCopy::setReceiverName);

        // Remark
        TextField remark = new TextField();
        remark.setLabel("Remark : ");
        remark.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(remark, rem -> "", AccountCopy::setRemark);

        receiverName.addKeyDownListener(Key.ENTER, event ->
                remark.focus());


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


        leftVerticalLayout.add(docNo, statusDate, statusTypeSelect, receiverName, remark, actions);


        VerticalLayout rightVerticalLayout = new VerticalLayout();

        FormLayout rightTopFormLayout = new FormLayout();
        rightTopFormLayout.setMaxWidth("40em");
        rightTopFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("20em", 1),
                new FormLayout.ResponsiveStep("20em", 2));


        HorizontalLayout documentDetailsLayout = new HorizontalLayout();
        Label documentDetailsLabel = new Label();
        documentDetailsLabel.setSizeFull();
        documentDetailsLabel.setText("Document Details : ");
        documentDetailsLayout.add(documentDetailsLabel);
        documentDetailsLayout.setAlignItems(HorizontalLayout.Alignment.CENTER);

        // Con No
        TextField conNoLabel = new TextField();
        conNoLabel.setReadOnly(true);
        binder.bind(conNoLabel, AccountCopy::getDocNo, AccountCopy::setDocNo);

        rightTopFormLayout.add(new Label("Con No : "), conNoLabel);

        // Con Date
        TextField conDateLabel = new TextField();
        binder.bind(conDateLabel,  d -> dateUtils.ddmmyyFormat(d.getPodDate()), null);
        conDateLabel.setReadOnly(true);

        rightTopFormLayout.add(new Label( "Con Date : "), conDateLabel);


        // Destination
        TextField conDestinationLabel = new TextField();
        conDestinationLabel.setReadOnly(true);
        binder.bind(conDestinationLabel, AccountCopy::getDestination, AccountCopy::setDestination);

        rightTopFormLayout.add(new Label( "Destination : "), conDestinationLabel);

        // Courier Name
        TextField conCourierLabel = new TextField();
        conCourierLabel.setReadOnly(true);
        binder.forField(conCourierLabel).bind(AccountCopy::getToParty, AccountCopy::setToParty);

        rightTopFormLayout.add(new Label( "Courier : "), conCourierLabel);

        // Customer
        TextField conCustomerLabel = new TextField();
        conCustomerLabel.setReadOnly(true);
        binder.bind(conCustomerLabel, AccountCopy::getClientName, AccountCopy::setClientName);

        rightTopFormLayout.add(new Label( "Customer : "), conCustomerLabel);

        // Status title
        HorizontalLayout statusDetailsLayout = new HorizontalLayout();
        Label statusDetailsLabel = new Label();
        statusDetailsLabel.setSizeFull();
        statusDetailsLabel.setText("Status Details : ");
        statusDetailsLayout.add(statusDetailsLabel);
        statusDetailsLayout.setAlignItems(HorizontalLayout.Alignment.CENTER);

        FormLayout rightBottomFormLayout = new FormLayout();
        rightBottomFormLayout.setMaxWidth("60em");
        rightBottomFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("30em", 1),
                new FormLayout.ResponsiveStep("30em", 2));

        // Status
        TextField statusLabel = new TextField();
        binder.bind(statusLabel, AccountCopy::getStatus, null);
        statusLabel.setReadOnly(true);

        rightBottomFormLayout.add(new Label( "Status : "), statusLabel);

        // Status date
        TextField dateLabel = new TextField();
        dateLabel.setReadOnly(true);
        binder.bind(dateLabel,
                d -> accountCopy.getStatusDate() != null ? dateUtils.ddmmyyFormat(accountCopy.getStatusDate()) : "",
                null);

        rightBottomFormLayout.add(new Label( "Status Date : "), dateLabel);

        // Receiver's Name
        TextField receiverNameLabel = new TextField();
        receiverNameLabel.setReadOnly(true);
        binder.bind(receiverNameLabel, AccountCopy::getReceiverName, AccountCopy::setReceiverName);

        rightBottomFormLayout.add(new Label( "Receiver's Name : "), receiverNameLabel);

        // Remark
        TextField remarkLabel = new TextField();
        remarkLabel.setReadOnly(true);
        binder.bind(remarkLabel, AccountCopy::getRemark, AccountCopy::setRemark);

        rightBottomFormLayout.add(new Label( "Remark : "), remarkLabel);

        rightVerticalLayout.add(documentDetailsLayout, rightTopFormLayout,
                statusDetailsLayout, rightBottomFormLayout);

        containerHorizonalLayout.add(leftVerticalLayout,rightVerticalLayout);
        verticalLayout.add(title, containerHorizonalLayout);
        dialog.add(verticalLayout);

        dialog.open();

    }
}
