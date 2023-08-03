package com.sda.karkonoszehiking.views.list;

import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.sda.karkonoszehiking.service.HikeService;
import com.sda.karkonoszehiking.service.RouteService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HikeForm extends FormLayout {

    private final HikeService hikeService;
    private final RouteService routeService;
    Binder<HikeDto> binder = new BeanValidationBinder<>(HikeDto.class);
    DatePicker date = new DatePicker("Hike date");
    TimePicker duration = new TimePicker("Hike Duration");
    ComboBox<WaypointEntity> waypoint = new ComboBox<>("Start waypoint");
    ComboBox<AvailablePathsEntity> availablePath = new ComboBox<>("Next waypoint");
    private List<ComboBox<AvailablePathsEntity>> comboBoxList = new ArrayList<>();

    static List<RouteEntity> routes = new ArrayList<>();
    int comboBoxCounter = 1;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");


    public HikeForm(List<WaypointEntity> waypoints, List<AvailablePathsEntity> availablePaths, HikeService hikeService, RouteService routeService) {
        this.hikeService = hikeService;
        this.routeService = routeService;
        addClassName("hike-form");
        date.setRequired(true);
        date.addValueChangeListener(change -> date.getValue());
        duration.addValueChangeListener(change -> duration.getValue());
        binder.bindInstanceFields(this);

        waypoint.setItems(waypoints);
        waypoint.setItemLabelGenerator(WaypointEntity::getName);

        waypoint.addValueChangeListener(e -> {
            WaypointEntity selectedWaypoint = e.getValue();
            if (selectedWaypoint != null) {
                availablePath.setItems(selectedWaypoint.getAvailablePaths());
                add(availablePath);
            } else {
                availablePath.setItems(Collections.emptyList());
            }
        });


        availablePath.setItemLabelGenerator(AvailablePathsEntity::getName);
        availablePath.setPlaceholder("Select next waypoint");
        availablePath.addValueChangeListener(e -> availablePath.getValue());


        add(date, duration, waypoint);

        Button addNewComboBoxButton = new Button("Add new waypoint");
        addNewComboBoxButton.addClickListener(e -> addNewComboBox());
        add(addNewComboBoxButton, createButtonLayout());

    }

    private void addNewComboBox() {
        if (waypoint.getValue() != null && availablePath.getValue() != null) {
            ComboBox<AvailablePathsEntity> newComboBox = new ComboBox<>();
            if (comboBoxList.isEmpty()) {
                newComboBox.setItems(hikeService.mapAvailablePathsToWaypoints(availablePath.getValue()).getAvailablePaths());
            } else {
                AvailablePathsEntity selectedPath = comboBoxList.get(comboBoxList.size() - 1).getValue();
                newComboBox.setItems(hikeService.mapAvailablePathsToWaypoints(selectedPath).getAvailablePaths());
            }
            newComboBox.setItemLabelGenerator(AvailablePathsEntity::getName);
            newComboBox.setPlaceholder("Select next waypoint");
            newComboBox.addValueChangeListener(e -> availablePath.getValue());
            comboBoxList.add(newComboBox);
            add(newComboBox);
        }


    }

    public void setHike(HikeDto hike) {
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
        if(nullCheck()){
            routes.add(routeService.findRouteEntityByStartAndEnd(waypoint.getValue().getName(), availablePath.getValue().getName()).get());
            System.out.println(comboBoxList);
            System.out.println(comboBoxList.size());
            for (int i = 0; i < comboBoxList.size(); i++) {
                if (i == 0) {
                    String waypoint2 = comboBoxList.get(i).getValue().getName();
                    String waypoint1 = availablePath.getValue().getName();
                    routes.add(routeService.findRouteEntityByStartAndEnd(waypoint1, waypoint2).get());
                } else {
                    String waypoint1 = comboBoxList.get(i - 1).getValue().getName();
                    String waypoint2 = comboBoxList.get(i).getValue().getName();
                    routes.add(routeService.findRouteEntityByStartAndEnd(waypoint1, waypoint2).get());
                }
            }
            if (binder.isValid()) {
                fireEvent(new SaveEvent(this, binder.getBean()));
            }
        }
    }

    private boolean nullCheck() {
        if (this.date != null && !this.date.isEmpty() && this.duration != null && !this.duration.isEmpty() && this.waypoint != null && !this.waypoint.isEmpty() && this.availablePath != null && !this.availablePath.isEmpty()) {
            return true;
        }
        return false;
    }
    private void clearComboBoxList() {
        comboBoxList.forEach(this::remove);
        comboBoxList.clear();
    }


    public void resetWaypoints() {
        waypoint.clear();
        availablePath.clear();
        clearComboBoxList();
        routes.clear();
    }

    public void resetForm() {
        date.clear();
        duration.clear();
        waypoint.clear();
        availablePath.clear();
        clearComboBoxList();
        routes.clear();
    }


    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<HikeForm> {
        private HikeDto hike;

        protected ContactFormEvent(HikeForm source, HikeDto hike) {
            super(source, false);
            this.hike = hike;
        }

        public HikeDto getHike() {
            hike.setRoutes(routes);
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
