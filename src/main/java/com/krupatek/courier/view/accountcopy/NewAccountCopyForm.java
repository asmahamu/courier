package com.krupatek.courier.view.accountcopy;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.model.Client;
import com.krupatek.courier.model.Destination;
import com.krupatek.courier.service.AccountCopyService;
import com.krupatek.courier.service.ClientService;
import com.krupatek.courier.service.DestinationService;
import com.krupatek.courier.utils.DateUtils;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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

import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class NewAccountCopyForm extends Div {

    private AccountCopy accountCopy;

    public NewAccountCopyForm(
            AccountCopyService accountCopyService,
            ClientService clientService,
            DestinationService destinationService,
            DateUtils dateUtils) {
        super();
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("25em", 2),
                new FormLayout.ResponsiveStep("25em", 3),
                new FormLayout.ResponsiveStep("25em", 4));

        accountCopy = new AccountCopy();

        Label title = new Label();
        title.setSizeFull();
        title.setText("Account Copy");
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

        List<Client> clientList = clientService.findAll();

        List<String> clientNameList = clientList.parallelStream().map(Client::getClientName).collect(Collectors.toList());
        ComboBox<String> clientsComboBox = new ComboBox<>();
//        clientsComboBox.setDataProvider(createClientDataProvider(clientService));
        clientsComboBox.setLabel("Client Name : ");
//        clientsComboBox.setItemLabelGenerator(Client::getClientName);
        clientsComboBox.setItems(clientNameList);
        clientsComboBox.setClearButtonVisible(true);
        binder.bind(clientsComboBox, AccountCopy::getClientName, AccountCopy::setClientName);

        // Destination
        List<Destination> destinationList = destinationService.findAll();
        List<String> destinationNameList = destinationList.parallelStream().map(Destination::getDestName).collect(Collectors.toList());

        ComboBox<String> destinationComboBox = new ComboBox<>();
        destinationComboBox.setLabel("Destination : ");
        destinationComboBox.setItems(destinationNameList);
        destinationComboBox.setClearButtonVisible(true);
        binder.bind(destinationComboBox, AccountCopy::getDestination, AccountCopy::setDestination);
        formLayout.add(clientsComboBox, destinationComboBox);

        formLayout.setColspan(clientsComboBox, 2);
        formLayout.setColspan(destinationComboBox, 2);


        // PinCode
        TextField pincode = new TextField();
        pincode.setLabel("Pincode : ");
        pincode.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(pincode).asRequired("Every Account copy must have pincode").
                bind(
                AccountCopy::getPlaceCode,
                AccountCopy::setPlaceCode);

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
        bookingTypeSelect.setItems("Dom", "Int");
        bookingTypeSelect.setValue("Dom");

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

        Button save = new Button("Save",
                event -> {
                    try {
                        binder.writeBean(accountCopy);
                        accountCopyService.saveAndFlush(accountCopy);
                        // A real application would also save the updated person
                        // using the application's backend
                    } catch (ValidationException e) {
                        Notification.show("Account Copy could not be saved, " +
                                "please check error messages for each field.");
                    }
                });
        Button reset = new Button("Reset",
                event -> binder.readBean(accountCopy));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset);
        save.getStyle().set("marginRight", "10px");
        formLayout.add(actions);
        add(formLayout);

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
        weight.addKeyDownListener(Key.ENTER, e -> rate.focus());
        rate.addKeyDownListener(Key.ENTER, e -> save.focus());

        docNo.focus();

        binder.readBean(accountCopy);
    }

/*
    DataProvider<Client, String>
    createClientDataProvider(ClientService service)
    {
        return DataProvider.fromFilteringCallbacks(query -> {
            // getFilter returns Optional<String>
            String filter = query.getFilter().orElse("");
            return service.fetch(query.getOffset(),
                    query.getLimit(), filter).stream();
        }, query -> {
            String filter = query.getFilter().orElse("");
            return service.getCount(filter);
        });
    }
*/
}
