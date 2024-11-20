package com.example.application.views.homepage;

import com.example.application.component.CalendarTable;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.UI;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import com.example.application.utilities.I18NProvider;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.Locale;

@PageTitle("Home")
@Route("")
@Menu(order = 0, icon = "line-awesome/svg/globe-solid.svg")
public class HomeView extends HorizontalLayout {

    private CalendarTable calendarTable;

    public HomeView() {
        var tableWrapper = new Div();
        tableWrapper.addClassNames("table-wrapper");
        this.calendarTable = new CalendarTable();

        for (var grid : this.calendarTable.createTable()) {
            tableWrapper.add(grid);
        }

        Button button = new Button();
        var buttonIcon = LineAwesomeIcon.PLUS_CIRCLE_SOLID.create();
        buttonIcon.setSize("2.5rem");
        button.addClickListener(clickEvent -> {
        });

        button.setIcon(buttonIcon);
        button.addClassNames("table__add-button");

        add(tableWrapper, button);

        addClassName(LumoUtility.Height.FULL);
    }

    public void updateTexts() {
        this.calendarTable.updateTexts();
    }
}
