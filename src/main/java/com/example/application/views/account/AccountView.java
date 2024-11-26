package com.example.application.views.account;

import com.vaadin.flow.component.charts.Chart;

import com.vaadin.flow.component.charts.model.ChartType;

import com.vaadin.flow.component.charts.model.ListSeries;

import com.vaadin.flow.component.avatar.Avatar;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Account")
@Route("account")
@Menu(order = 1, icon = "line-awesome/svg/file.svg")
public class AccountView extends VerticalLayout {

    public AccountView() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);
Chart chart = new Chart(ChartType.LINE);
chart.setMinHeight("400px");
chart.getConfiguration().setTitle("Sales 2023");
chart.getConfiguration().getxAxis().setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
chart.getConfiguration().getyAxis().setTitle("Euro (€)");
ListSeries listseries = new ListSeries("Product A");
listseries.setData(42112, 58698, 12276, 33202, 74518, 45498, 42477, 17896, 44297, 22456, 38547, 12621);
chart.getConfiguration().addSeries(listseries);
ListSeries listseries2 = new ListSeries("Product B");
listseries2.setData(70972, 48589, 94434, 58270, 77282, 7108, 54085, 44401, 28868, 79643, 14383, 76036);
chart.getConfiguration().addSeries(listseries2);
Avatar avatar = new Avatar();

        H2 header = new H2("This place intentionally left empty");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
add(chart);
add(avatar);
        add(header);
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
