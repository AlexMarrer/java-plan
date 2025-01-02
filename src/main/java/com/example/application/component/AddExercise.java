package com.example.application.component;

import com.vaadin.flow.component.html.Div;

public class AddExercise {
    private Div container;
    private Div background;

    public AddExercise() {
        this.container = new Div();
        this.background = new Div();

        this.container.addClassNames("add-exercise");
        this.background.addClassNames("add-exercise-background");
        this.background.setHeight("100%");
        this.background.setWidth("100%");
    }

    public Div getContainer() {
        return this.container;
    }

    public Div getBackground() {
        return this.background;
    }

    public void activateAddExercise() {
        this.container.addClassNames("add-exercise--active");
        this.background.addClassNames("add-exercise-background--active");
    }

    public void deactivateAddExercise() {
        this.container.removeClassNames("add-exercise--active");
        this.background.removeClassNames("add-exercise-background--active");
    }
}
