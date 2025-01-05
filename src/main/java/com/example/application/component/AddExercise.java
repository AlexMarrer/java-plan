package com.example.application.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;

public class AddExercise {
    private Div container;
    private Button background;
    private boolean isAddExerciseOpen = false;

    public AddExercise() {
        this.container = new Div();
        this.background = new Button();

        this.container.addClassNames("add-exercise");
        this.background.addClassNames("add-exercise-background");
        this.background.setHeight("100%");
        this.background.setWidth("100%");

        this.background.addClickListener(event -> deactivateAddExercise());
    }

    public Div getContainer() {
        return this.container;
    }

    public Button getBackground() {
        return this.background;
    }

    public boolean isAddExerciseOpen() {return this.isAddExerciseOpen;}

    public void activateAddExercise() {
        this.container.addClassNames("add-exercise--active");
        this.background.addClassNames("add-exercise-background--active");

        this.isAddExerciseOpen = true;
    }

    public void deactivateAddExercise() {
        this.container.removeClassNames("add-exercise--active");
        this.background.removeClassNames("add-exercise-background--active");

        this.isAddExerciseOpen = false;
    }
}
