package com.krupatek.courier.view;

import com.krupatek.courier.model.Client;
import com.krupatek.courier.service.ClientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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

public class ClientProfileForm extends Div {
    private Client client;

    public ClientProfileForm(ClientService clientService) {
        super();

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("25em", 2),
                new FormLayout.ResponsiveStep("25em", 3),
                new FormLayout.ResponsiveStep("25em", 4));

        Binder<Client> binder = new Binder<>();

        Label title = new Label();
        title.setSizeFull();
        title.setText("Client Details");
        formLayout.add(title, 4);


        // Client Code

        TextField clientCode = new TextField();
        clientCode.setLabel("Client Code : ");
        clientCode.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(clientCode, 2);
        formLayout.add(new Label(""), 2);
        binder.bind(clientCode,
                c -> c.getClientCode().toString(),
                (c, t) -> c.setClientCode(Integer.valueOf(t)));

        // Client Name

        List<Client> clientList = clientService.findAll();
        client = clientList.get(0);

        List<String> clientNameList = new ArrayList<>();
        clientList.forEach(c -> clientNameList.add(c.getClientName()));

        ComboBox<String> clientsComboBox = new ComboBox<>();
        clientsComboBox.setLabel("Client Name : ");
        clientsComboBox.setItems(clientNameList);
        clientsComboBox.setClearButtonVisible(true);
        formLayout.add(clientsComboBox, 2);

//        clientsComboBox.addCustomValueSetListener(
//                event -> {
//                    title.setText(event.getDetail());
//                    clientNameList.add(event.getDetail());
//                    clientsComboBox.clear();
//                    clientsComboBox.setItems(clientNameList);
//                    clientsComboBox.setValue(event.getDetail());
//                });

        clientsComboBox.addValueChangeListener(event -> {
            if(event.getValue() == null){
                title.setText("event.getValue()");
            } else {
                title.setText(event.getValue());
                List<Client> searchResult = clientService.findAllByClientName(event.getValue());
                if (searchResult.size() > 0) {
                    client = searchResult.get(0);
                    binder.readBean(client);
                }
            }
        });

        formLayout.add(new Label(""), 2);

        // Client Address Name

        TextField clientAddress = new TextField();
        clientAddress.setLabel("Client Address : ");
        clientAddress.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(clientAddress, 2);

        formLayout.add(new Label(""), 2);
        binder.bind(clientAddress,
                Client::getClientAddress1,
                Client::setClientAddress1);

        // City

        TextField city = new TextField();
        city.setLabel("City : ");
        city.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(city,
                Client::getCity,
                Client::setCity);

        // Phone Number
        TextField phoneNumber = new TextField();
        phoneNumber.setLabel("Phone Number : ");
        phoneNumber.setValueChangeMode(ValueChangeMode.EAGER);

        formLayout.add(city, phoneNumber);

        formLayout.add(new Label(""), 2);
        binder.bind(phoneNumber,
                Client::getPhone,
                Client::setPhone);

        // GST select
        Select<String> gstSelect = new Select<>();
        gstSelect.setLabel("GST : ");
        gstSelect.setItems("Yes", "No");
        gstSelect.setValue("Yes");
        formLayout.add(gstSelect, 1);
        binder.bind(gstSelect,
                Client::getGstEnabled,
                Client::setGstEnabled);

        // GSTIN Number
        TextField gstin = new TextField();
        gstin.setLabel("GSTIN : ");
        gstin.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(gstin, 1);
        binder.bind(gstin,
                Client::getGstNo,
                Client::setGstNo);


        formLayout.add(new Label(""), 2);

        // Fuel Charge
        TextField fuelSurchage = new TextField();
        fuelSurchage.setLabel("Fuel Surcharge : ");
        fuelSurchage.setValueChangeMode(ValueChangeMode.EAGER);

        // Fuel Surcharge
        Select<String> fuelSurchargeSelect = new Select<>();
        fuelSurchargeSelect.setLabel("Fuel Surcharge : ");
        fuelSurchargeSelect.setItems("Yes", "No");
        fuelSurchargeSelect.setValue("Yes");

        formLayout.add(fuelSurchargeSelect, 1);
        formLayout.add(fuelSurchage, 1);
        formLayout.add(new Label(""), 2);


        Button add = new Button("Add", event -> {
           client = new Client();
           binder.readBean(client);
        });
        Button save = new Button("Save",
                event -> {
                    try {
                        binder.writeBean(client);
                        clientService.saveAndFlush(client
                        );
                        // A real application would also save the updated person
                        // using the application's backend
                    } catch (ValidationException e) {
                        Notification.show("Person could not be saved, " +
                                "please check error messages for each field.");
                    }
                });
        Button reset = new Button("Reset",
                event -> binder.readBean(client));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(add, save, reset);
        save.getStyle().set("marginRight", "10px");
        formLayout.add(actions);
        add(formLayout);
//        binder.readBean(company);
    }
}
