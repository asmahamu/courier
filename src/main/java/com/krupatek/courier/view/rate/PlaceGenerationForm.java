package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.model.PlaceGeneration;
import com.krupatek.courier.model.State;
import com.krupatek.courier.model.Zones;
import com.krupatek.courier.repository.PlaceGenerationRepository;
import com.krupatek.courier.repository.StateRepository;
import com.krupatek.courier.service.PlaceGenerationService;
import com.krupatek.courier.service.StateService;
import com.krupatek.courier.service.ZonesService;
import com.krupatek.courier.utils.ViewUtils;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.button.Button;
//import org.hibernate.sql.Select;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PlaceGenerationForm extends Div {
    public PlaceGenerationForm(ZonesService zonesService, PlaceGenerationRepository placeGenerationRepository,
                               PlaceGenerationService placeGenerationService, StateService stateService){
        super();


        Dialog dialog=new Dialog();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPadding(true);
        horizontalLayout.setMargin(false);

        Binder<PlaceGeneration> binder=new Binder<>(PlaceGeneration.class);
        PlaceGeneration placeGeneration=placeGenerationRepository.findAll().get(0);

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("25em", 2));
        formLayout.setMaxWidth("25em");

        formLayout.add(ViewUtils.addCloseButton(dialog), 2);

        H4 title = new H4();
        title.setSizeFull();
        title.setText("Place Generation");
        formLayout.add(title, 2);


        TextField cityTxt = new TextField();
        cityTxt.setLabel("City : ");
        cityTxt.setValueChangeMode(ValueChangeMode.EAGER);
        formLayout.add(cityTxt, 2);

        binder.forField(cityTxt).asRequired("City name is mandatory").bind(str -> "", PlaceGeneration::setCityName);

        cityTxt.addValueChangeListener(e -> {
            if (e.getValue() != null && e.getValue().length() > 3) {
                String cityName = e.getValue();

                // TODO
                // PlaceGeneration - cityName
                Optional<PlaceGeneration> placeGenerationOptional = placeGenerationService.findByCityName(cityName);
                if(placeGenerationOptional.isPresent()){
                    // Show error
                   cityTxt.setInvalid(true);
                   cityTxt.setErrorMessage("City Already Exists !!");

                }
                else
                {
                    cityTxt.setInvalid(false);
                    binder.readBean(placeGenerationOptional.get());

                }
            }
        });
//        cityTxt.setValue((placeGeneration.getCityName()));

        ComboBox<String> stateSelect=new ComboBox<>();
        stateSelect.setWidth("50");
        stateSelect.setLabel("State :");
        formLayout.add(stateSelect,2);

        TreeSet<String> stateSource=stateService.findAll().parallelStream().map(State::getStateName).collect(Collectors.toCollection(TreeSet::new));
        stateSelect.setItems(stateSource);
        //stateSelect.setValue(stateSource.first());






        ComboBox<String> placeCodeSelect=new ComboBox<>();
        placeCodeSelect.setWidth("50");
        placeCodeSelect.setLabel("Place code :");
        formLayout.add(placeCodeSelect,2);

        TreeSet<String> placeCodeSource= zonesService.findAll().parallelStream().map(Zones::getZoneCode).collect(Collectors.toCollection(TreeSet::new));
        placeCodeSelect.setItems(placeCodeSource);
        //placeCodeSelect.setValue(placeCodeSource.first());


        //ComboBox<String> stateComboBox = new ComboBox<>();
     //   stateComboBox.setLabel("State :");
      //  formLayout.add(stateComboBox,2);
     //   List<PlaceGeneration> placeGenerationList=placeGenerationService.findAll();
     //   List<String> stateNameList=new ArrayList<>();
      //  placeGenerationList.forEach(c->stateNameList.add(c.getStateName()));

     //   stateComboBox.setItems(stateNameList);
        //binder.bind(stateComboBox,PlaceGeneration::getStateName,PlaceGeneration::setStateName);

      //  ComboBox<String> placeCodeComboBox = new ComboBox<>();
      //  placeCodeComboBox.setLabel("Place Code :");
     //   formLayout.add(placeCodeComboBox,2);
     //   List<String> placeCodeNameList=new ArrayList<>();
     //   placeGenerationList.forEach(c->placeCodeNameList.add(c.getPlaceCode()));

     //   placeCodeComboBox.setItems(placeCodeNameList);




        Button save=new Button("Save",
                event->{
            try{
                binder.writeBean(placeGeneration);

                placeGeneration.setCityName(cityTxt.getValue());
                placeGeneration.setStateName(stateSelect.getValue());
                placeGeneration.setPlaceCode(placeCodeSelect.getValue());


                placeGenerationService.saveAndFlush(placeGeneration);
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
