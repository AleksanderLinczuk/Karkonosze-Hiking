package com.sda.karkonoszehiking.views;

import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class HikeForm extends FormLayout {

    Binder<HikeDto> binder = new BeanValidationBinder<>(HikeDto.class);
    DatePicker date = new DatePicker("Hike date");
    TimePicker duration = new TimePicker("Hike Duration");
    ComboBox<WaypointEntity> waypoint = new ComboBox<>("Start waypoint");
    ComboBox<AvailablePathsEntity> availablePath = new ComboBox<>("Next waypoint");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");


    public HikeForm(List<WaypointEntity> waypoints, List<AvailablePathsEntity>availablePaths) {
        addClassName("hike-form");
        binder.bindInstanceFields(this);

        waypoint.setItems(waypoints);
        waypoint.setItemLabelGenerator(WaypointEntity::getName);
        availablePath.setItems(availablePaths);
        availablePath.setItemLabelGenerator(AvailablePathsEntity::getName);


        add(
                date,
                duration,
                waypoint,
                availablePath,
                createButtonLayout()
        );

    }
    public void setHike (HikeDto hike){

        binder.setBean(hike);
    }


    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        if(binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<HikeForm> {
        private HikeDto hike;

        protected ContactFormEvent(HikeForm source, HikeDto hike) {
            super(source, false);
            this.hike = hike;
        }

        public HikeDto getContact() {
            return hike;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(HikeForm source, HikeDto hike) {
            super(source, hike);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(HikeForm source, HikeDto hike) {
            super(source, hike);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(HikeForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}
