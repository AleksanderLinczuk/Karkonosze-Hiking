package com.sda.karkonoszehiking.views.list;

import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.service.AvailablePathService;
import com.sda.karkonoszehiking.service.HikeService;
import com.sda.karkonoszehiking.service.RouteService;
import com.sda.karkonoszehiking.service.WaypointService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Hike - Karkonosze")
@Route(value = "")
public class ListView extends VerticalLayout {
    Grid<HikeDto> grid = new Grid<>(HikeDto.class);
    TextField filterText = new TextField();
    HikeForm form;
    private final HikeService hikeService;
    private final WaypointService waypointService;
    private final RouteService routeService;
    private final AvailablePathService availablePathService;

    public ListView(HikeService hikeService, WaypointService waypointService, RouteService routeService, AvailablePathService availablePathService) {
        this.hikeService = hikeService;
        this.waypointService = waypointService;
        this.routeService = routeService;
        this.availablePathService = availablePathService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(
          getToolbar(),
          getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setHike(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(hikeService.getHikes());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new HikeForm(waypointService.findAll(),availablePathService.findAll(), hikeService, routeService);
        form.setWidth("25em");

        form.addSaveListener(this::saveHike);
        form.addDeleteListener(this::deleteHike);
        form.addCloseListener(e -> closeEditor());
    }

    private void deleteHike(HikeForm.DeleteEvent event) {
        hikeService.deleteHike(event.getHike());
        updateList();
        closeEditor();
    }

    private void saveHike(HikeForm.SaveEvent event) {

        hikeService.save(event.getHike());

        updateList();
        closeEditor();

    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter  by date");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addNewHikeButton = new Button("Add new hike");
        addNewHikeButton.addClickListener(e -> addHike());

        HorizontalLayout toolbar = new HorizontalLayout( addNewHikeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addHike() {

        grid.asSingleSelect().clear();
        form.resetForm();
        editHike(new HikeDto());
    }

    private void configureGrid() {
        grid.addClassName("hike-grid");
        grid.setSizeFull();
        grid.setColumns("date", "duration", "distance");
        grid.addColumn(hike -> hike.getRouteByWaypoints()).setHeader("Route");
        grid.addColumn(hike -> hike.getPace()).setHeader("Pace [min/km]");
        grid.addColumn(hike -> hike.getSpeed()).setHeader("Speed [km/h]");
        grid.getColumns().forEach(each -> each.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editHike(e.getValue()));
        grid.asSingleSelect().addValueChangeListener(e -> form.resetWaypoints());
    }

    private void editHike(HikeDto hike) {
        if (hike == null){
            closeEditor();
        }else{
            form.setHike(hike);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
