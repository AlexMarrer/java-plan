package com.example.application.component;

import com.example.application.utilities.Exercise;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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

    private Select<Exercise> exerciseSelect;
    private IntegerField setsField;
    private IntegerField repsField;
    private CheckboxGroup<String> workoutDaysGroup;
    private PaperSlider intensitySlider;


    public AddExercise() {
        addClassName("add-exercise-wrapper");

        this.background = new Button();

        this.background.addClassNames("add-exercise-background");
        this.background.setHeight("100%");
        this.background.setWidth("100%");

        this.container = new Div();
        this.container.addClassNames("add-exercise");

        var header = new Header();
        header.add(new H2("Add Exercise"));
        header.addClassName("add-exercise__header");

        createExerciseAddButton();


        Div selectContainer = createSelectContainer();

        Div exerciseAttributesContainer = createExerciseAttributesContainer();

        CheckboxGroup<String> checkboxGroup = createCheckboxGroup();

        Div sliderContainer = createSliderContainer();

        this.container.add(header, selectContainer, exerciseAttributesContainer, checkboxGroup, sliderContainer);

        this.background.addClickListener(event -> deactivateAddExercise());
        add(this.background, this.container, this.addExerciseButton);
    }

    private void activateAddExercise() {
        this.container.addClassNames("add-exercise--active");
        this.background.addClassNames("add-exercise-background--active");
        addClassNames("add-exercise-wrapper--active");

        this.isAddExerciseOpen = true;
    }

    private void deactivateAddExercise() {
        this.container.removeClassNames("add-exercise--active");
        this.background.removeClassNames("add-exercise-background--active");
        removeClassNames("add-exercise-wrapper--active");

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

    private Div createExerciseAttributesContainer() {
        var exerciseAttributesContainer = new Div();

        setsField = new IntegerField();
        setsField.setLabel("Sets");
        setsField.setHelperText("Min 1 set");
        setsField.setRequiredIndicatorVisible(true);
        setsField.setMin(1);
        setsField.setValue(3);
        setsField.setStepButtonsVisible(true);

        setsField.setI18n(new IntegerField.IntegerFieldI18n()
                .setRequiredErrorMessage("Field is required")
                .setBadInputErrorMessage("Invalid number format")
                .setMinErrorMessage("Quantity must be at least 1"));

        repsField = new IntegerField();
        repsField.setLabel("Reps");
        repsField.setHelperText("Min 1 rep");
        repsField.setRequiredIndicatorVisible(true);
        repsField.setMin(1);
        repsField.setValue(12);
        repsField.setStepButtonsVisible(true);

        repsField.setI18n(new IntegerField.IntegerFieldI18n()
                .setRequiredErrorMessage("Field is required")
                .setBadInputErrorMessage("Invalid number format")
                .setMinErrorMessage("Quantity must be at least 1"));

        exerciseAttributesContainer.add(setsField, repsField);
        exerciseAttributesContainer.addClassNames("exercise-attributes-container");

        return exerciseAttributesContainer;
    }

    private Div createSelectContainer() {
        var selectContainer = new Div();

        exerciseSelect = new Select<>();
        exerciseSelect.setOverlayWidth("350px");

        List<Exercise> exercises = Exercise.getExercises();
        exerciseSelect.setLabel("Exercise");
        exerciseSelect.setItems(exercises);
        exerciseSelect.setItemLabelGenerator(Exercise::getName);
        exerciseSelect.setRequiredIndicatorVisible(true);

        selectContainer.add(exerciseSelect);
        selectContainer.addClassNames("select-container");

        return selectContainer;
    }

    private CheckboxGroup<String> createCheckboxGroup() {
        workoutDaysGroup = new CheckboxGroup<String>();

        workoutDaysGroup.setLabel("Workout days");
        workoutDaysGroup.setItems("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday");
        workoutDaysGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        return workoutDaysGroup;
    }

    private Div createSliderContainer() {
        var sliderContainer = new Div();

        intensitySlider = new PaperSlider();
        intensitySlider.setMin(0);
        intensitySlider.setMax(10);
        intensitySlider.setValue(5);
        intensitySlider.setPinned(true);
        intensitySlider.setSnaps(true);
        intensitySlider.setMaxMarkers(1);
        intensitySlider.setHelperText("RPE");
        intensitySlider.addClassName("add-exercise__slider");

        var submitButton = new Button("Save");
        submitButton.addClickListener(e -> {
            if (validateInputs()) {
                Notification notification = Notification.show("Application submitted!");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.setDuration(1000);

                // Felder zurücksetzen
                resetFields();

                showAddExerciseView(e);
            } else {
                Notification notification = Notification.show("Please fill in all required fields!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.setDuration(2000);
            }
        });

        sliderContainer.add(intensitySlider, submitButton);
        sliderContainer.addClassNames("slider-container");

        return sliderContainer;
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validiere Exercise Select
        if (exerciseSelect.getValue() == null) {
            exerciseSelect.setInvalid(true);
            isValid = false;
        } else {
            exerciseSelect.setInvalid(false);
        }

        // Validiere Sets
        if (setsField.getValue() == null || setsField.getValue() < 1) {
            setsField.setInvalid(true);
            isValid = false;
        } else {
            setsField.setInvalid(false);
        }

        // Validiere Reps
        if (repsField.getValue() == null || repsField.getValue() < 1) {
            repsField.setInvalid(true);
            isValid = false;
        } else {
            repsField.setInvalid(false);
        }

        // Validiere Workout Days (mindestens ein Tag ausgewählt)
        if (workoutDaysGroup.getValue().isEmpty()) {
            workoutDaysGroup.setInvalid(true);
            isValid = false;
        } else {
            workoutDaysGroup.setInvalid(false);
        }

        return isValid;
    }

    private void resetFields() {
        exerciseSelect.clear();
        setsField.setValue(3);
        repsField.setValue(12);
        workoutDaysGroup.clear();
        intensitySlider.setValue(5);
    }

    private void showAddExerciseView(ClickEvent<Button> clickEvent) {
        if (this.isAddExerciseOpen) {
            deactivateAddExercise();
        } else {
            activateAddExercise();
        }
    }
}
