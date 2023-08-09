package com.sda.karkonoszehiking.views;

import com.sda.karkonoszehiking.security.SecurityService;
import com.sda.karkonoszehiking.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    private final SecurityService securityService;
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Karkonosze hiking application");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Logout", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listView = new RouterLink("All hikes", ListView.class);
        RouterLink visitedWaypoints = new RouterLink("Visited Waypoints", VisitedWaypointsView.class);
        RouterLink unvisitedWaypoints = new RouterLink("Unvisited Waypoints", UnvisitedWaypointsView.class);
        listView.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
               listView,visitedWaypoints, unvisitedWaypoints)
        );
    }
}
