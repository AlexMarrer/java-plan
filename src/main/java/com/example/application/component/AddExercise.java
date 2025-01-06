package com.example.application.component;

import com.example.application.utilities.Exercise;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class AddExercise {
    private Div container;
    private Button background;
    private boolean isAddExerciseOpen = false;

    public AddExercise() {
        this.background = new Button();

        this.background.addClassNames("add-exercise-background");
        this.background.setHeight("100%");
        this.background.setWidth("100%");

        this.container = new Div();
        this.container.addClassNames("add-exercise");

        Select<Exercise> select = new Select<>();
        select.setOverlayWidth("350px");

        List<Exercise> exercises = Exercise.getExercises();
        select.setLabel("Exercise");
        select.setItems(exercises);
        select.setItemLabelGenerator(Exercise::getName);
        select.setRequiredIndicatorVisible(true);

        IntegerField sets = new IntegerField();
        sets.setLabel("Sets");
        sets.setHelperText("Min 1 set");
        sets.setRequiredIndicatorVisible(true);
        sets.setMin(1);
        sets.setValue(3);
        sets.setStepButtonsVisible(true);

        sets.setI18n(new IntegerField.IntegerFieldI18n()
                .setRequiredErrorMessage("Field is required")
                .setBadInputErrorMessage("Invalid number format")
                .setMinErrorMessage("Quantity must be at least 1"));

        IntegerField reps = new IntegerField();
        reps.setLabel("Reps");
        reps.setHelperText("Min 1 rep");
        reps.setRequiredIndicatorVisible(true);
        reps.setMin(1);
        reps.setValue(12);
        reps.setStepButtonsVisible(true);

        reps.setI18n(new IntegerField.IntegerFieldI18n()
                .setRequiredErrorMessage("Field is required")
                .setBadInputErrorMessage("Invalid number format")
                .setMinErrorMessage("Quantity must be at least 1"));

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Working days");
        checkboxGroup.setItems("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday");
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        FormLayout formLayout = new FormLayout();
        formLayout.add(select, sets, reps, checkboxGroup);
        formLayout.setColspan(checkboxGroup, 3);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));

        this.container.add(formLayout);

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
