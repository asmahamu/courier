package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.Country;
import com.krupatek.courier.model.PlaceGeneration;
import com.krupatek.courier.repository.CountryRepository;
import com.krupatek.courier.service.CountryService;
import com.krupatek.courier.utils.ViewUtils;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.button.Button;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class CountryGenerationForm extends Div {
    public CountryGenerationForm(CountryRepository countryRepository,CountryService countryService){
        super();

        com.vaadin.flow.component.dialog.Dialog dialog=new Dialog();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPadding(true);
        horizontalLayout.setMargin(false);

        Binder<Country> binder=new Binder<>(Country.class);
        Country country= countryRepository.findAll().get(0);
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("25em", 2));
        formLayout.setMaxWidth("25em");

        formLayout.add(ViewUtils.addCloseButton(dialog), 2);

        H4 title = new H4();
        title.setSizeFull();
        title.setText("Country Generation");
        formLayout.add(title, 2);

        TextField countryTxt=new TextField();
        countryTxt.setLabel("Country :");
        countryTxt.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(countryTxt,2);

        binder.forField(countryTxt).asRequired("Country name not entered").bind(str->"",Country::setCountryName);


        countryTxt.addValueChangeListener(e->{
            if(e.getValue()!=null && e.getValue().length()>3){
                String countryName=e.getValue();

                Optional<Country> countryOptional=countryService.findByCountryName(countryName);
                if(countryOptional.isPresent()){
                    countryTxt.setInvalid(true);
                    countryTxt.setErrorMessage("Country Already exists");
                }
                else{
                    countryTxt.setInvalid(false);
                    binder.readBean(countryOptional.get());
                }





            }
        });



        Button save=new Button("Save",event->{
            try{

                binder.writeBean(country);
                country.setCountryName(countryTxt.getValue());

                countryService.saveAndFlush(country);
                Notification.show("Details saved successfully");


            }catch (ValidationException e){
                Notification.show("Details could not be saved");
            }
        });
        Button cancel=new Button("Cancel",event->dialog.close());

        HorizontalLayout actions=new HorizontalLayout();
        actions.add(save,cancel);
        formLayout.add(actions,4);
        horizontalLayout.add(formLayout);
        dialog.add(horizontalLayout);
        dialog.open();






    }
}
