package com.example.application.views.plan;

import com.example.application.component.CalendarTable;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Plan")
@Route("plan")
@Menu(order = 1, icon = "line-awesome/svg/file.svg")
public class PlanView extends VerticalLayout {

    private CalendarTable calendarTable;

    public PlanView() {
        this.calendarTable = new CalendarTable();

        add(calendarTable);
        addClassName(LumoUtility.Height.FULL);
    }

    public void updateTexts() {
        this.calendarTable.updateTexts();
    }

}
