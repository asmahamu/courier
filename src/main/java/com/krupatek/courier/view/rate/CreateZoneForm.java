package com.krupatek.courier.view.rate;

import com.krupatek.courier.model.Zones;
import com.krupatek.courier.service.ZonesService;
import com.krupatek.courier.utils.ViewUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.HashSet;
import java.util.Set;


public class CreateZoneForm  extends Div {

    public CreateZoneForm(ZonesService zonesService){
        super();

        Dialog dialog = new Dialog();

        H4 createZoneTitle = new H4("Zone Generation");

        TextField newZoneName = new TextField();
        newZoneName.setLabel("Zone Name : ");

        HorizontalLayout buttonContainer = new HorizontalLayout();
        Button createZone = new Button("Create Zone");
        Button cancelZone = new Button("Cancel");

        buttonContainer.add(createZone,cancelZone);

        H4 label = new H4("Zones");

        MultiSelectListBox<String> zones = new MultiSelectListBox<>();
        updateZones(zonesService, zones);


        Button deleteBtn = new Button("Delete");

        dialog.add(ViewUtils.addCloseButton(dialog),createZoneTitle, newZoneName, buttonContainer, label, zones, deleteBtn);

        createZone.addClickListener(buttonClickEvent -> {
            Zones newZone = new Zones();
            newZone.setZoneCode(newZoneName.getValue());
            zonesService.save(newZone);
            updateZones(zonesService, zones);
        });

        deleteBtn.addClickListener(buttonClickEvent -> {
            Set<String> selectedItems = zones.getSelectedItems();
            if(selectedItems.size() > 0){
                Set<Zones> selectedZones = new HashSet<>();
                for(String zone : selectedItems){
                    Zones newZone = new Zones();
                    newZone.setZoneCode(zone);
                    selectedZones.add(newZone);
                    
                }
                zonesService.deleteAll(selectedZones);
                updateZones(zonesService, zones);
            } else {
                Notification.show("Please select at least one zone to delete.");
            }
        });

        cancelZone.addClickListener(event -> {
            dialog.close();
        });
        dialog.open();
    }

    private void updateZones(ZonesService zonesService, MultiSelectListBox<String> zones) {
        zones.clear();
        zones.setItems(zonesService.findAll().parallelStream().map(Zones::getZoneCode));
    }
}
