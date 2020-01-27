package com.krupatek.courier.view;

import com.krupatek.courier.model.Company;
import com.krupatek.courier.repository.CompanyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;

public class SystemSettingsForm extends Div {
    public SystemSettingsForm(CompanyRepository companyRepository) {
        super();

        Binder<Company> binder = new Binder<>();
        Company company = companyRepository.findAll().get(0);

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("25em", 2),
                new FormLayout.ResponsiveStep("25em", 3),
                new FormLayout.ResponsiveStep("25em", 4));

        Label title = new Label();
        title.setSizeFull();
        title.setText("Company Information");
        formLayout.add(title, 4);


        // Company Origin

        TextField origin = new TextField();
        origin.setLabel("Origin : ");
        origin.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(origin, 2);
        binder.bind(origin,
                Company::getOrigin,
                Company::setOrigin);
        origin.setValue(company.getOrigin());
        formLayout.add(new Label(""), 2);


        // Company Prefix

        TextField companyPrefix = new TextField();
        companyPrefix.setLabel("Company Prefix : ");
        companyPrefix.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(companyPrefix,
                Company::getCompanyPrefix,
                Company::setCompanyPrefix);
        formLayout.add(companyPrefix, 2);

        formLayout.add(new Label(""), 2);

        // Company Name

        TextField companyName = new TextField();
        companyName.setLabel("Company Name : ");
        companyName.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(companyName,
                Company::getCompanyName,
                Company::setCompanyName);
        formLayout.add(companyName, 2);

        formLayout.add(new Label(""), 2);

        // Company Address

        TextField companyAddress = new TextField();
        companyAddress.setLabel("Company Address : ");
        companyAddress.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(companyAddress,
                Company::getCompanyAddress1,
                Company::setCompanyAddress1);
        formLayout.add(companyAddress, 2);

        formLayout.add(new Label(""), 2);

        // City

        TextField city = new TextField();
        city.setLabel("City : ");
        city.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(city,
                Company::getCity,
                Company::setCity);

        // Phone Number
        TextField phoneNumber = new TextField();
        phoneNumber.setLabel("Phone Number : ");
        phoneNumber.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(phoneNumber,
                Company::getPhone,
                Company::setPhone);

        formLayout.add(city, phoneNumber);

        formLayout.add(new Label(""), 2);

        // GSTIN Number
        TextField gstin = new TextField();
        gstin.setLabel("GSTIN : ");
        gstin.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(gstin,
                Company::getGstin,
                Company::setGstin);
        formLayout.add(gstin, 2);
        formLayout.add(new Label(""), 2);


        // CGST %
        TextField cgst = new TextField();
        cgst.setLabel("CGST in % : ");
        cgst.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(cgst,
                c -> c.getCgst().toString(),
                (c, t) -> c.setCgst(Double.valueOf(t)));

        // SGST %
        TextField sgst = new TextField();
        sgst.setLabel("SGST in % : ");
        sgst.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(sgst,
                c -> c.getSgst().toString(),
                (c, t) -> c.setSgst(Double.valueOf(t)));

        // IGST %
        TextField igst = new TextField();
        igst.setLabel("IGST in % : ");
        igst.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(igst,
                c -> c.getIgst().toString(),
                (c, t) -> c.setIgst(Double.valueOf(t)));

        // Fuel Charge
        TextField fuelSurchage = new TextField();
        fuelSurchage.setLabel("Fuel Surchage : ");
        fuelSurchage.setValueChangeMode(ValueChangeMode.EAGER);
        binder.bind(fuelSurchage,
                c -> c.getFuelSurcharge().toString(),
                (c, t) -> c.setFuelSurcharge(Integer.valueOf(t)));


        formLayout.add(
                cgst,
                sgst,
                igst,
                fuelSurchage);


        Button save = new Button("Save",
                event -> {
                    try {
                        binder.writeBean(company);
                        companyRepository.saveAndFlush(company);
                        // A real application would also save the updated person
                        // using the application's backend
                    } catch (ValidationException e) {
                        Notification.show("Person could not be saved, " +
                                "please check error messages for each field.");
                    }
                });
        Button reset = new Button("Reset",
                event -> binder.readBean(company));

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset);
        save.getStyle().set("marginRight", "10px");
        formLayout.add(actions);
        add(formLayout);
        binder.readBean(company);
    }
}
