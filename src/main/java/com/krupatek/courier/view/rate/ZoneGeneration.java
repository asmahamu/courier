package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.PlaceGeneration;
import com.krupatek.courier.model.Zones;
import com.krupatek.courier.service.PlaceGenerationService;
import com.krupatek.courier.service.ZonesService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ZoneGeneration extends Div {

    private boolean zoneSelected = false;
    public ZoneGeneration(ZonesService zonesService, PlaceGenerationService placeGenerationService){
        super();

        Dialog dialog = new Dialog();
        dialog.setWidth("600px");
        dialog.setHeight("600px");

        VerticalLayout zoneGenerationContainer = new VerticalLayout();
        zoneGenerationContainer.setWidth("100%");
        zoneGenerationContainer.setHeight("100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.setHeight("100%");
        horizontalLayout.setPadding(true);
        horizontalLayout.setMargin(false);

        // Title
        H4 title = new H4("Zone Generation");
        title.setWidth("100%");
        zoneGenerationContainer.add(title);

        // Zone Select
        ComboBox<String> zoneSelect = new ComboBox<>();
        zoneSelect.setWidth("70%");
        zoneSelect.setLabel("Zone : ");

        TreeSet<String> zonesSource = zonesService.findAll().parallelStream().map(Zones::getZoneCode).collect(Collectors.toCollection(TreeSet::new));
        zoneSelect.setItems(zonesSource);
        zoneSelect.setValue(zonesSource.first());
        zoneGenerationContainer.add(zoneSelect);

        // Title
        H4 selectedListBoxTitle = new H4("Selected (Read Only)");
        selectedListBoxTitle.setWidth("40%");
        Label emptyLabel = new Label();
        emptyLabel.setWidth("20%");
        H4 allListBoxTitle = new H4("All (Select here)");
        allListBoxTitle.setWidth("40%");

        HorizontalLayout listBoxTitleContainer = new HorizontalLayout();
        listBoxTitleContainer.setWidth("100%");
        listBoxTitleContainer.setHeight("10%");
        listBoxTitleContainer.add(selectedListBoxTitle, emptyLabel, allListBoxTitle);

        // Select City
        HorizontalLayout listBoxContainer = new HorizontalLayout();
        listBoxContainer.setWidthFull();
        listBoxContainer.setHeight("60%");

        MultiSelectListBox<String> selectedListBox = new MultiSelectListBox<>();
        selectedListBox.setReadOnly(true);

        selectedListBox.setWidth("40%");
        TreeSet<String> selectedCitiesSource = placeGenerationService.findAllByPlaceCode(zonesSource.first()).parallelStream().map(PlaceGeneration::getCityName).filter(str -> !str.isEmpty()).collect(Collectors.toCollection(TreeSet::new));
        selectedListBox.setItems(selectedCitiesSource);
        selectedListBox.setValue(selectedCitiesSource);

        VerticalLayout listBoxButtonContainer = new VerticalLayout();

        Button addBtn = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
        Button removeBtn = new Button(VaadinIcon.ANGLE_DOUBLE_RIGHT.create());
        listBoxButtonContainer.add(addBtn, removeBtn);
        listBoxButtonContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        listBoxButtonContainer.setWidth("20%");

        MultiSelectListBox<String> allListBox = new MultiSelectListBox<>();

        allListBox.setWidth("40%");
        TreeSet<String> allCitiesSource = placeGenerationService.findDistinctCityName().parallelStream().filter(str -> !str.isEmpty()).collect(Collectors.toCollection(TreeSet::new));

        allListBox.setItems(allCitiesSource);

        allListBox.select(selectedCitiesSource);

        allListBox.addSelectionListener(event -> {
            if(!zoneSelected) {
                selectedCitiesSource.addAll(event.getAddedSelection());
                selectedCitiesSource.removeAll(event.getRemovedSelection());
                selectedListBox.clear();
                selectedListBox.setItems(selectedCitiesSource);
                selectedListBox.select(selectedCitiesSource);
            }
        });

        listBoxContainer.add(selectedListBox, listBoxButtonContainer, allListBox);

        HorizontalLayout buttonContainer = new HorizontalLayout();
        buttonContainer.setWidth("100%");
        buttonContainer.setHeight("10%");
        Label emptyLabelTwo = new Label();
        emptyLabelTwo.setWidth("60%");
        Button saveBtn = new Button("Save");
        saveBtn.setWidth("20%");
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setWidth("20%");
        buttonContainer.add(emptyLabelTwo, saveBtn, cancelBtn);
        saveBtn.addClickListener(event -> {
            String zoneSelectValue = zoneSelect.getValue();
            List<String> oldCities = getCitiesForZone(zoneSelectValue, placeGenerationService);

            // New Cities added = New - Old
            List<String> newCitiesAdded = selectedCitiesSource.parallelStream().filter(city -> !oldCities.contains(city)).collect(Collectors.toList());

            // Old cities removed = Old - New
            List<String> oldCitiesRemoved = oldCities.parallelStream().filter(city -> !selectedCitiesSource.contains(city)).collect(Collectors.toList());

            for(String city : newCitiesAdded){
                placeGenerationService.updateCityWithZone(city, zoneSelectValue);
            }

            for(String city : oldCitiesRemoved){
                placeGenerationService.updateCityWithZone(city, "");
            }

            Notification.show(zoneSelectValue +" has new values Added "+ Arrays.toString(newCitiesAdded.toArray())+", Removed : "+Arrays.toString(oldCitiesRemoved.toArray()));
        });
        cancelBtn.addClickListener(
                event -> dialog.close()
        );
        buttonContainer.setAlignItems(FlexComponent.Alignment.END);

        zoneGenerationContainer.add(buttonContainer, listBoxTitleContainer, listBoxContainer);


        // Add listener
        zoneSelect.addValueChangeListener(event -> {
            zoneSelected = true;
            selectedCitiesSource.clear();
            selectedListBox.clear();
            selectedCitiesSource.addAll(getCitiesForZone(event.getValue(), placeGenerationService));
            selectedListBox.setItems(selectedCitiesSource);
            allListBox.deselectAll();
            allListBox.select(selectedCitiesSource);
            zoneSelected = false;
        });

        zoneGenerationContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.add(zoneGenerationContainer);
        dialog.add(horizontalLayout);
        dialog.open();

    }
    private List<String> getCitiesForZone(String zone, PlaceGenerationService placeGenerationService){
        return placeGenerationService.findAllByPlaceCode(zone).stream().map(PlaceGeneration::getCityName).filter(str -> !str.isEmpty()).collect(Collectors.toList());
    }
}
