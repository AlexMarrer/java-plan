package com.example.application.views.workout;

import com.example.application.component.CreateExercise;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Workout")
@Route("workout")
@Menu(order = 1, icon = "line-awesome/svg/file.svg")
public class WorkoutView extends HorizontalLayout {

    public WorkoutView() {
        var createExercise = new CreateExercise();
        add(createExercise);
    }

}
