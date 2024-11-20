package com.example.application.views.homepage;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.UI;

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

    private Grid<MainLayout.DataEntity> table;

    private Grid.Column<MainLayout.DataEntity> mondayColumn;
    private Grid.Column<MainLayout.DataEntity> tuesdayColumn;
    private Grid.Column<MainLayout.DataEntity> wednesdayColumn;
    private Grid.Column<MainLayout.DataEntity> thursdayColumn;
    private Grid.Column<MainLayout.DataEntity> fridayColumn;
    private Grid.Column<MainLayout.DataEntity> saturdayColumn;
    private Grid.Column<MainLayout.DataEntity> sundayColumn;

    public HomeView() {
        createTable();
        add(this.table);
        addClassName(LumoUtility.Height.FULL);
    }

    private void createTable() {
        this.table = new Grid<>(MainLayout.DataEntity.class, false);
        addClassName("hello-world-view-grid-1");

        Locale locale = UI.getCurrent().getLocale();

        this.mondayColumn = this.table.addColumn(MainLayout.DataEntity::monday).setHeader(I18NProvider.getTranslation("table.montag", locale));
        this.tuesdayColumn = this.table.addColumn(MainLayout.DataEntity::tuesday).setHeader(I18NProvider.getTranslation("table.dienstag", locale));
        this.wednesdayColumn = this.table.addColumn(MainLayout.DataEntity::wednesday).setHeader(I18NProvider.getTranslation("table.mittwoch", locale));
        this.thursdayColumn = this.table.addColumn(MainLayout.DataEntity::thursday).setHeader(I18NProvider.getTranslation("table.donnerstag", locale));
        this.fridayColumn = this.table.addColumn(MainLayout.DataEntity::friday).setHeader(I18NProvider.getTranslation("table.freitag", locale));
        this.saturdayColumn = this.table.addColumn(MainLayout.DataEntity::saturday).setHeader(I18NProvider.getTranslation("table.samstag", locale));
        this.sundayColumn = this.table.addColumn(MainLayout.DataEntity::sunday).setHeader(I18NProvider.getTranslation("table.sonntag", locale));

        this.table.setItems(new MainLayout.DataEntity("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        this.table.addClassNames("content__table", LumoUtility.AlignSelf.END);

        Button button = new Button();
        var buttonIcon = LineAwesomeIcon.PLUS_CIRCLE_SOLID.create();
        buttonIcon.setSize("2.5rem");
        button.addClickListener(clickEvent -> {
        });

        button.setIcon(buttonIcon);
        button.addClassNames("table__add-button");

        add(button);
    }

    public void updateTexts() {
        Locale locale = UI.getCurrent().getLocale();

        this.mondayColumn.setHeader(I18NProvider.getTranslation("table.montag", locale));
        this.tuesdayColumn.setHeader(I18NProvider.getTranslation("table.dienstag", locale));
        this.wednesdayColumn.setHeader(I18NProvider.getTranslation("table.mittwoch", locale));
        this.thursdayColumn.setHeader(I18NProvider.getTranslation("table.donnerstag", locale));
        this.fridayColumn.setHeader(I18NProvider.getTranslation("table.freitag", locale));
        this.saturdayColumn.setHeader(I18NProvider.getTranslation("table.samstag", locale));
        this.sundayColumn.setHeader(I18NProvider.getTranslation("table.sonntag", locale));
    }
}
