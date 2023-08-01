package com.sda.karkonoszehiking.views;

import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class HikeForm extends FormLayout {
    TextField date = new TextField("Hike date");
    TextField duration = new TextField("Hike Duration");
    ComboBox<WaypointDto> routes = new ComboBox<>("Route");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");


    public HikeForm(List<WaypointDto> waypoints) {
        addClassName("hike-form");
        routes.setItems(waypoints);
        routes.setItemLabelGenerator(WaypointDto::getName);
        add(
                date,
                duration,
                routes,
                createButtonLayout();
        );

    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);



        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return null;
    }
}
