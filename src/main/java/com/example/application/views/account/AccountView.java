package com.example.application.views.account;

import com.vaadin.flow.component.charts.Chart;

import com.vaadin.flow.component.charts.model.ChartType;

import com.vaadin.flow.component.charts.model.ListSeries;

import com.vaadin.flow.component.avatar.Avatar;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Account")
@Route("account")
@Menu(order = 1, icon = "line-awesome/svg/file.svg")
public class AccountView extends HorizontalLayout {

    public AccountView() {
        Div chartDiv = new Div();
        Chart chartVolume = new Chart(ChartType.LINE);
        chartVolume.setMinHeight("200px");
        chartVolume.getConfiguration().setTitle("Volume Progress");
        chartVolume.getConfiguration().getxAxis().setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        chartVolume.getConfiguration().getyAxis().setTitle("Volumen (KG)");
        ListSeries listseriesVolume = new ListSeries("Volumen");
        listseriesVolume.setData(42112, 58698, 12276, 33202, 74518, 45498, 42477, 17896, 44297, 22456, 38547, 12621);
        chartVolume.getConfiguration().addSeries(listseriesVolume);

        Chart chartWeight = new Chart(ChartType.LINE);
        chartWeight.setMinHeight("200px");
        chartWeight.getConfiguration().setTitle("Weight Progress");
        chartWeight.getConfiguration().getxAxis().setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        chartWeight.getConfiguration().getyAxis().setTitle("Gewicht (KG)");
        ListSeries listseriesWeight = new ListSeries("Gewicht");
        listseriesWeight.setData(42112, 58698, 12276, 33202, 74518, 45498, 42477, 17896, 44297, 22456, 38547, 12621);
        chartWeight.getConfiguration().addSeries(listseriesWeight);

        Avatar avatar = new Avatar();

        Div info = new Div();
        Div weight = new Div();

        chartDiv.addClassNames("account__chart");
        avatar.addClassNames("account__avatar");
        info.addClassNames("account__info");
        weight.addClassNames("account__weight");

        chartDiv.add(chartVolume);
        weight.add(chartWeight);
        add(info, weight, avatar, chartDiv);

        addClassName("account-view");

        setSizeFull();
    }

}
