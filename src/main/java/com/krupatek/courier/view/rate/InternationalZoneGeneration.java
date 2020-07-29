package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.Courier;
import com.krupatek.courier.model.Network;
import com.krupatek.courier.service.CountryService;
import com.krupatek.courier.service.CourierService;
import com.krupatek.courier.service.NetworkService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class InternationalZoneGeneration extends Div {
    private String selectedNetwork = "FEDEX";
    private String selectedZone = "ZONE A";
    private boolean zoneSelected = false;
    private TreeSet<String> selectedCountriesSource = new TreeSet<>();
    private TreeSet<String> allCountriesSource = new TreeSet<>();

    public InternationalZoneGeneration(CourierService courierService, NetworkService networkService, CountryService countryService){
        super();

        Dialog dialog = new Dialog();
        dialog.setWidth("600px");
        dialog.setHeight("600px");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.setHeight("100%");
        horizontalLayout.setPadding(true);
        horizontalLayout.setMargin(false);

        VerticalLayout zoneGenerationContainer = new VerticalLayout();
        zoneGenerationContainer.setWidth("100%");
        zoneGenerationContainer.setHeight("100%");

        // Title
        H4 title = new H4("International Zone Generation");
        title.setWidth("100%");
        zoneGenerationContainer.add(title);

        // Select Network
        HorizontalLayout selectContainerHZLayout = new HorizontalLayout();
        selectContainerHZLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        selectContainerHZLayout.setWidth("100%");

        ComboBox<String> networkSelect = new ComboBox<>();
        networkSelect.setWidth("30%");
        networkSelect.setLabel("Select Network Name : ");
        networkSelect.setItems(getNetworks(courierService));
        networkSelect.setValue(selectedNetwork);


        // Select Zone
        ComboBox<String> zoneSelect = new ComboBox<>();
        zoneSelect.setWidth("30%");
        zoneSelect.setLabel("Zone : ");
        zoneSelect.setItems(getZones(selectedNetwork, networkService));

        networkSelect.addValueChangeListener(event ->{
            selectedNetwork = event.getValue();
            zoneSelect.setItems(getZones(selectedNetwork, networkService));
            zoneSelect.setValue(selectedZone);
        });
        zoneSelect.setValue(selectedZone);

        selectContainerHZLayout.add(networkSelect, zoneSelect);


        // Twin Column Selection List
        // Title
        HorizontalLayout listBoxTitleContainer = new HorizontalLayout();
        listBoxTitleContainer.setWidth("100%");
        listBoxTitleContainer.setHeight("10%");

        H4 selectedListBoxTitle = new H4("Selected (Read Only)");
        selectedListBoxTitle.setWidth("40%");
        Label emptyLabel = new Label();
        emptyLabel.setWidth("20%");
        H4 allListBoxTitle = new H4("All (Select here)");
        allListBoxTitle.setWidth("40%");

        listBoxTitleContainer.add(selectedListBoxTitle, emptyLabel, allListBoxTitle);


        // Lists
        HorizontalLayout listBoxContainer = new HorizontalLayout();
        listBoxContainer.setWidth("100%");
        listBoxContainer.setHeight("60%");

        // Selected Countries
        MultiSelectListBox<String> selectedListBox = new MultiSelectListBox<>();
        selectedListBox.setReadOnly(true);
        selectedListBox.setWidth("50%");

        updateSelectedList(networkService, selectedListBox);

        // All Countries
        MultiSelectListBox<String> allListBox = new MultiSelectListBox<>();
        allListBox.setWidth("50%");

        allCountriesSource.addAll(getAllCountries(countryService));
        allListBox.setItems(allCountriesSource);

        updateAllList(allListBox);

        listBoxContainer.add(selectedListBox, allListBox);

        zoneSelect.addValueChangeListener(event -> {
            zoneSelected = true;
            selectedZone = event.getValue();
            updateSelectedList(networkService, selectedListBox);
            updateAllList(allListBox);
            zoneSelected = false;
        });

        allListBox.addSelectionListener(event -> {
            if(!zoneSelected) {
                selectedCountriesSource.addAll(event.getAddedSelection());
                selectedCountriesSource.removeAll(event.getRemovedSelection());
                selectedListBox.clear();
                selectedListBox.setItems(selectedCountriesSource);
                selectedListBox.select(selectedCountriesSource);
            }
        });
        Button saveBtn = new Button("Save");
        saveBtn.setWidth("20%");
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setWidth("20%");
        selectContainerHZLayout.add(saveBtn, cancelBtn);

        saveBtn.addClickListener(event -> {
            Set<String> oldCountries = getSelectedCountries(selectedNetwork, selectedZone, networkService);

            // New Countries added = New - Old
            List<String> newCountriesAdded = selectedCountriesSource.parallelStream().filter(city -> !oldCountries.contains(city)).collect(Collectors.toList());

            // Old Countries removed = Old - New
            List<String> oldCountriesRemoved = oldCountries.parallelStream().filter(city -> !selectedCountriesSource.contains(city)).collect(Collectors.toList());

            for(String country : newCountriesAdded){
                Network network = new Network();
                network.setNetName(selectedNetwork);
                network.setZoneName(selectedZone);
                network.setCountryName(country);
                networkService.save(network);
            }

            for(String country : oldCountriesRemoved){
                Network network = new Network();
                network.setNetName(selectedNetwork);
                network.setZoneName(selectedZone);
                network.setCountryName(country);
                networkService.delete(network);
            }

            Notification.show(selectedNetwork+", "+selectedZone +" has new values Added "+
                    Arrays.toString(newCountriesAdded.toArray())+", Removed : "+Arrays.toString(oldCountriesRemoved.toArray()));
        });
        cancelBtn.addClickListener(
                event -> dialog.close()
        );

        zoneGenerationContainer.add(selectContainerHZLayout, listBoxTitleContainer, listBoxContainer);
        horizontalLayout.add(zoneGenerationContainer);
        dialog.add(horizontalLayout);
        dialog.open();

    }

    private void updateAllList(MultiSelectListBox<String> allListBox) {
        allListBox.deselectAll();
        allListBox.select(selectedCountriesSource);
    }

    private void updateSelectedList(NetworkService networkService, MultiSelectListBox<String> selectedListBox) {
        selectedCountriesSource.clear();
        selectedListBox.clear();
        selectedCountriesSource.addAll(getSelectedCountries(selectedNetwork, selectedZone, networkService));
        selectedListBox.setItems(selectedCountriesSource);
        selectedListBox.select(selectedCountriesSource);
    }

    private Set<String> getSelectedCountries(String selectedNetwork, String selectedZone, NetworkService networkService) {
        return networkService.findCountryByNetworkAndZone(selectedNetwork, selectedZone);
    }

    private Set<String> getAllCountries(CountryService countryService) {
        return countryService.findAllOrderByCountryName();
    }

    private Set<String> getNetworks(CourierService courierService) {
        return courierService.findAll().parallelStream().map(Courier::getCourierName).collect(Collectors.toSet());
    }

    private Set<String> getZones(String zone, NetworkService networkService){
        return networkService.findDistinctZonesForNetwork(zone);
    }
}
