package com.example.application.views.helloworld;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.EmailField;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Hello World")
@Route("")
@Menu(order = 0, icon = "line-awesome/svg/globe-solid.svg")
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;
    private Grid<MainLayout.DataEntity> table;

    public HelloWorldView() {
        this.table = new Grid<>(MainLayout.DataEntity.class);
        this.table.addColumn(MainLayout.DataEntity::monday).setHeader("Monday");
        this.table.addColumn(MainLayout.DataEntity::tuesday).setHeader("Tuesday");
        this.table.addColumn(MainLayout.DataEntity::wednesday).setHeader("Wednesday");
        this.table.addColumn(MainLayout.DataEntity::thursday).setHeader("Thursday");
        this.table.addColumn(MainLayout.DataEntity::friday).setHeader("Friday");
        this.table.addColumn(MainLayout.DataEntity::saturday).setHeader("Saturday");
        this.table.addColumn(MainLayout.DataEntity::sunday).setHeader("Sunday");

        this.table.setItems(new MainLayout.DataEntity("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        this.table.addClassNames("content__table");
        add(this.table);

        addClassName(LumoUtility.Height.FULL);
    }

}
