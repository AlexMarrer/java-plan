package com.example.application.views.homepage;

import com.example.application.component.AddExercise;
import com.example.application.component.CalendarTable;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Home")
@Route("")
@Menu(order = 0, icon = "line-awesome/svg/globe-solid.svg")
public class HomeView extends HorizontalLayout {

    private CalendarTable calendarTable;
    private AddExercise addExercise;

    public HomeView() {
        var tableWrapper = new Div();
        tableWrapper.addClassNames("table-wrapper");
        this.calendarTable = new CalendarTable();
        this.addExercise = new AddExercise();

        for (var grid : this.calendarTable.createTable()) {
            tableWrapper.add(grid);
        }

        var tableView = this.calendarTable.getTableView();
        var addExerciseBackground = this.calendarTable.getAddExercise().getBackground();

        add(tableWrapper, this.addExercise, tableView, addExerciseBackground);

        addClassName(LumoUtility.Height.FULL);
    }

    public void updateTexts() {
        this.calendarTable.updateTexts();
    }
}
