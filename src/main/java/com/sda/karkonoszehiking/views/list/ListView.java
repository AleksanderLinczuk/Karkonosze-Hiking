package com.sda.karkonoszehiking.views.list;

import com.sda.karkonoszehiking.model.dto.HikeDto;
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

    public ListView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(
          getToolbar(),
          grid
        );
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter  by date");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addNewHikeButton = new Button("Add new hike");
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addNewHikeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("hike-grid");
        grid.setSizeFull();
        grid.setColumns("date", "duration", "distance");
        grid.addColumn(hike -> hike.getRouteByWaypoints()).setHeader("Route");
        grid.addColumn(hike -> hike.getPace()).setHeader("Pace [min/km]");
        grid.addColumn(hike -> hike.getSpeed()).setHeader("Speed [km/h]");
        grid.getColumns().forEach(each -> each.setAutoWidth(true));
    }
}
