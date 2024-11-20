package com.example.application.component;

import com.example.application.utilities.Exercise;
import com.example.application.utilities.I18NProvider;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;

import java.time.LocalDateTime;
import java.util.*;

public class CalendarTable {

    private List<Grid<Exercise>> dayGrids = new ArrayList<>();
    private Exercise draggedExercise;
    private String[] days = {
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY",
            "SUNDAY"
    };

    public List<Grid<Exercise>> createTable() {
        for (String day : this.days) {
            Grid<Exercise> dayGrid = createDayGrid(day);
            this.dayGrids.add(dayGrid);
        }
        updateTexts();
        setCurrentDate();

        setupCrossDayDragAndDrop();
        setupInDayReordering();

        return this.dayGrids;
    }

    private void setCurrentDate() {
        var day = LocalDateTime.now().getDayOfWeek();
        for (Grid<Exercise> dayGrid : this.dayGrids) {
            dayGrid.getColumns().forEach( header -> {
                if(day.toString().equals(header.getId().orElse("none"))) {
                    header.addClassName("current-day");
                }
            });
        }
    }

    public void updateTexts() {
        Locale locale = UI.getCurrent().getLocale();

        for (Grid<Exercise> dayGrid : this.dayGrids) {
            dayGrid.getColumns().forEach( header -> {
                if(Arrays.asList(days).contains(header.getId().orElse("none"))) {
                    header.setHeader(I18NProvider.getTranslation("TABLE." + header.getId().orElse("none"), locale));
                }
            });
        }
    }

    private void setupInDayReordering() {
        for (Grid<Exercise> grid : this.dayGrids) {
            grid.setRowsDraggable(true);

            GridListDataView<Exercise> dataView = grid.getListDataView();

            grid.addDragStartListener(event -> {
                draggedExercise = event.getDraggedItems().get(0);
                grid.setDropMode(GridDropMode.BETWEEN);
                grid.getListDataView().removeItem(draggedExercise);
            });

            grid.addDropListener(event -> {
                Optional<Exercise> targetItem = event.getDropTargetItem();
                GridDropLocation dropLocation = event.getDropLocation();

                if (targetItem.isPresent() && draggedExercise != null) {
                    dataView.removeItem(draggedExercise);

                    if (dropLocation == GridDropLocation.BELOW) {
                        dataView.addItemAfter(draggedExercise, targetItem.get());
                    } else {
                        dataView.addItemBefore(draggedExercise, targetItem.get());
                    }
                }
            });

            grid.addDragEndListener(event -> {
                draggedExercise = null;
                grid.setDropMode(null);
            });
        }
    }

    private void setupCrossDayDragAndDrop() {
        for (Grid<Exercise> sourceGrid : dayGrids) {
            sourceGrid.addDragStartListener(event -> {
                // Speichere das gezogene Element
                draggedExercise = event.getDraggedItems().get(0);

                for (Grid<Exercise> targetGrid : dayGrids) {
                    if (targetGrid != sourceGrid) {
                        targetGrid.setDropMode(GridDropMode.ON_GRID);
                    }
                }
            });

            sourceGrid.addDropListener(event -> {
                if (draggedExercise != null) {
                    Grid<Exercise> targetGrid = (Grid<Exercise>) event.getSource();

                    GridListDataView<Exercise> targetDataView = targetGrid.getListDataView();

                    targetDataView.addItem(draggedExercise);
                }
            });

            sourceGrid.addDragEndListener(event -> {
                // Setze das gezogene Element zurück
                draggedExercise = null;

                for (Grid<Exercise> targetGrid : dayGrids) {
                    targetGrid.setDropMode(null);
                }
            });
        }
    }

    private Grid<Exercise> createDayGrid(String day) {
        Grid<Exercise> grid = new Grid<>(Exercise.class);
        grid.setRowsDraggable(true);
        grid.removeAllColumns();

        grid.addColumn(Exercise::getName).setId(day);

        List<Exercise> exercises = generateExercisesForDay(day);
        grid.setItems(exercises);

        return grid;
    }

    private List<Exercise> generateExercisesForDay(String day) {
        // Dummy-Daten
        List<Exercise> exercises = new ArrayList<>();
        switch(day) {
            case "montag":
                exercises.add(new Exercise("Squats", 3, 10, 4.5));
                exercises.add(new Exercise("Bench Press", 3, 8, 4.5));
                break;
            case "dienstag":
                exercises.add(new Exercise("Deadlifts", 3, 5, 4.5));
                exercises.add(new Exercise("Pull-ups", 3, 10, 4.5));
                break;
            default:
                exercises.add(new Exercise("Sigma", 3, 10, 4.5));
                exercises.add(new Exercise("Ligma Press", 3, 8, 4.5));
                break;
        }
        return exercises;
    }
}
