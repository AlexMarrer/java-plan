package com.example.application.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;

public class CreateExercise extends Div {
    private Div container;
    private Div background;
    private TextField exerciseName;
    private TextField muscleGroup;

    public CreateExercise() {
        addClassName("create-exercise-wrapper");

        this.background = new Div();

        this.background.addClassNames("create-exercise-background");
        this.background.setHeight("100%");
        this.background.setWidth("100%");

        this.container = new Div();
        this.container.addClassNames("create-exercise");

        var header = new Header();
        header.add(new H2("Create Exercise"));
        header.addClassName("create-exercise__header");

        Div selectContainer = createSelectContainer();

        Div exerciseAttributesContainer = createExerciseAttributesContainer();

        Div createContainer = createSliderContainer();

        this.container.add(header, selectContainer, exerciseAttributesContainer, createContainer);

        add(this.background, this.container);
    }

    private Div createExerciseAttributesContainer() {
        var exerciseAttributesContainer = new Div();

        muscleGroup = new TextField();
        muscleGroup.setLabel("Muscle Group");
        muscleGroup.setRequiredIndicatorVisible(true);

        exerciseAttributesContainer.add(muscleGroup);
        exerciseAttributesContainer.addClassNames("exercise-attributes-container");

        return exerciseAttributesContainer;
    }

    private Div createSelectContainer() {
        var selectContainer = new Div();

        exerciseName = new TextField();
        exerciseName.setLabel("Exercise");
        exerciseName.setRequiredIndicatorVisible(true);

        selectContainer.add(exerciseName);
        selectContainer.addClassNames("select-container");

        return selectContainer;
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (exerciseName.getValue() == null || exerciseName.getValue().trim().isEmpty()) {
            exerciseName.setInvalid(true);
            exerciseName.setErrorMessage("Exercise name is required");
            isValid = false;
        } else {
            exerciseName.setInvalid(false);
        }

        if (muscleGroup.getValue() == null || muscleGroup.getValue().trim().isEmpty()) {
            muscleGroup.setInvalid(true);
            muscleGroup.setErrorMessage("Muscle group is required");
            isValid = false;
        } else {
            muscleGroup.setInvalid(false);
        }

        return isValid;
    }

    private Div createSliderContainer() {
        var sliderContainer = new Div();

        var submitButton = new Button("Create");

        sliderContainer.add(submitButton);
        sliderContainer.addClassNames("create-container");

        submitButton.addClickListener(event -> {
            if (validateInputs()) {
                Notification notification = Notification.show("Application submitted!");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.setDuration(1000);

                // Reset fields after successful submission
                exerciseName.clear();
                muscleGroup.clear();
            } else {
                Notification notification = Notification.show("Please fill in all required fields!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.setDuration(2000);
            }
        });

        return sliderContainer;
    }
}