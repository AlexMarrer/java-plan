package com.example.application.component;

import com.example.application.utilities.Exercise;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import org.vaadin.addons.componentfactory.PaperSlider;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.List;

public class AddExercise extends Div {
    private Div container;
    private Button background;
    private boolean isAddExerciseOpen = false;
    private Button addExerciseButton;

    public AddExercise() {
        addClassName("add-exercise-wrapper");

        this.background = new Button();

        this.background.addClassNames("add-exercise-background");
        this.background.setHeight("100%");
        this.background.setWidth("100%");

        this.container = new Div();
        this.container.addClassNames("add-exercise");

        createExerciseAddButton();


        var selectContainer = new Div();
        Select<Exercise> select = new Select<>();
        select.setOverlayWidth("350px");

        List<Exercise> exercises = Exercise.getExercises();
        select.setLabel("Exercise");
        select.setItems(exercises);
        select.setItemLabelGenerator(Exercise::getName);
        select.setRequiredIndicatorVisible(true);

        selectContainer.add(select);
        selectContainer.addClassNames("select-container");

        var exerciseAttributesContainer = new Div();
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

        exerciseAttributesContainer.add(sets, reps);
        exerciseAttributesContainer.addClassNames("exercise-attributes-container");

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Workout days");
        checkboxGroup.setItems("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday");
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        var sliderContainer = new Div();
        PaperSlider slider = new PaperSlider();
        slider.setMin(0);
        slider.setMax(10);
        slider.setValue(5);
        slider.setPinned(true);
        slider.setSnaps(true);
        slider.setMaxMarkers(1);
        slider.addClassName("add-exercise__slider");

        var submitButton = new Button("Save");

        sliderContainer.add(slider, submitButton);
        sliderContainer.addClassNames("slider-container");

        this.container.add(selectContainer, exerciseAttributesContainer, checkboxGroup, sliderContainer);

        this.background.addClickListener(event -> deactivateAddExercise());
        add(this.background, this.container, this.addExerciseButton);
    }

    public Div getContainer() {
        return this.container;
    }

    public Button getBackground() {
        return this.background;
    }

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

    private void createExerciseAddButton() {
        this.addExerciseButton = new Button();
        var buttonIcon = LineAwesomeIcon.PLUS_CIRCLE_SOLID.create();
        buttonIcon.setSize("2.5rem");

        this.addExerciseButton.setIcon(buttonIcon);
        this.addExerciseButton.addClassNames("table__add-button");

        this.addExerciseButton.addClickListener(this::showAddExerciseView);
    }

    private void showAddExerciseView(ClickEvent<Button> clickEvent) {
        if (this.isAddExerciseOpen) {
            deactivateAddExercise();
        } else {
            activateAddExercise();
        }
    }
}
