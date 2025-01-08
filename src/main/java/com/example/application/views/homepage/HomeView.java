package com.example.application.views.homepage;

import com.example.application.component.AddExercise;
import com.example.application.component.CalendarTable;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Home")
@Route("")
public class HomeView extends HorizontalLayout {

    private final CalendarTable calendarTable;
    private final AddExercise addExercise;

    public HomeView() {
        this.calendarTable = new CalendarTable();
        this.addExercise = new AddExercise();

        add(addExercise, calendarTable);
        addClassName(LumoUtility.Height.FULL);
    }

    public void updateTexts() {
        this.calendarTable.updateTexts();
    }
}