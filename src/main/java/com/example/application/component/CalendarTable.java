package com.example.application.component;

import com.example.application.utilities.Exercise;
import com.example.application.utilities.I18NProvider;
import com.example.application.utilities.TableView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.component.select.Select;

import java.time.LocalDateTime;
import java.util.*;

public class CalendarTable extends Div {

    private Select<TableView> tableView;
    private String standardViewValue = "WEEKLY";
    private List<Grid<Exercise>> dayGrids = new ArrayList<>();
    private Exercise draggedExercise;
    private int currentDay;
    private final String[] days = {
            "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    };

    public CalendarTable() {
        addClassName("calendar-table");

        createTable();
        initializeTableView();
        setupLayout();

        updateTexts();
        setCurrentDate();

        setupCrossDayDragAndDrop();
        setupInDayReordering();
    }

    private void setupLayout() {
        Div tableWrapper = new Div();
        tableWrapper.addClassName("table-wrapper");

        // Add all grids to the wrapper
        dayGrids.forEach(tableWrapper::add);

        // Create layout for the table view and wrapper
        HorizontalLayout layout = new HorizontalLayout(tableWrapper, tableView);
        layout.setSizeFull();

        add(layout);
    }

    private void createTable() {
        for (String day : days) {
            Grid<Exercise> dayGrid = createDayGrid(day);
            dayGrids.add(dayGrid);
        }

        loadStoredViewValue();
    }

    private void loadStoredViewValue() {
        try {
            WebStorage.getItem("standardViewValue").thenAccept(value -> {
                UI ui = UI.getCurrent();
                ui.access(() -> {
                    standardViewValue = value == null ? "WEEKLY" : value;
                    updateViewSelection();
                });
            });
        } catch (Exception e) {
            standardViewValue = "WEEKLY";
            updateViewSelection();
        }
    }

    private void initializeTableView() {
        tableView = new Select<>();
        tableView.setItems(
                new TableView(I18NProvider.getTranslation("TABLE.WEEKLY"), "WEEKLY"),
                new TableView(I18NProvider.getTranslation("TABLE.THREE-DAYS"), "THREE-DAYS"),
                new TableView(I18NProvider.getTranslation("TABLE.DAY"), "DAY")
        );
        tableView.setItemLabelGenerator(TableView::label);
        tableView.setItemEnabledProvider(TableView::enabled);
        tableView.setLabel(I18NProvider.getTranslation("TABLE.VIEW-HEADER"));
        tableView.addClassNames("table__view-selector");

        setupTableViewListener();
    }

    private void setupTableViewListener() {
        tableView.addValueChangeListener(event -> {
            if(event.getValue() != null) {
                standardViewValue = event.getValue().value();
                WebStorage.setItem("standardViewValue", standardViewValue);
            }
            updateActiveColumns();
        });
    }

    private void updateActiveColumns() {
        dayGrids.forEach(x -> x.removeClassNames("table__column--active"));

        switch (standardViewValue) {
            case "WEEKLY":
                dayGrids.forEach(grid -> grid.addClassName("table__column--active"));
                break;
            case "THREE-DAYS":
                int startIndex = currentDay == 6 ? currentDay-2 : currentDay-1;
                for (int i = startIndex; i < startIndex+3; i++) {
                    dayGrids.get(i).addClassName("table__column--active");
                }
                break;
            case "DAY":
                dayGrids.get(currentDay).addClassName("table__column--active");
                break;
        }
    }

    public void updateTexts() {
        updateViewSelection();
        dayGrids.forEach(dayGrid ->
                dayGrid.getColumns().forEach(header -> {
                    if(Arrays.asList(days).contains(header.getId().orElse("none"))) {
                        header.setHeader(I18NProvider.getTranslation("TABLE." + header.getId().orElse("none")));
                    }
                })
        );
    }

    private void updateViewSelection() {
        tableView.setValue(new TableView(I18NProvider.getTranslation("TABLE."+standardViewValue), standardViewValue));
    }

    private void setCurrentDate() {
        var day = LocalDateTime.now().getDayOfWeek();
        currentDay = day.ordinal();
        dayGrids.forEach(dayGrid ->
                dayGrid.getColumns().forEach(header -> {
                    if(day.toString().equals(header.getId().orElse("none"))) {
                        header.addClassName("current-day");
                    }
                })
        );
    }

    private Grid<Exercise> createDayGrid(String day) {
        Grid<Exercise> grid = new Grid<>(Exercise.class);
        grid.setRowsDraggable(true);
        grid.removeAllColumns();
        grid.addColumn(Exercise::getName).setId(day);
        grid.setItems(generateExercisesForDay(day));
        return grid;
    }

    private void setupInDayReordering() {
        for (Grid<Exercise> grid : dayGrids) {
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
                    targetGrid.getListDataView().addItem(draggedExercise);
                }
            });

            sourceGrid.addDragEndListener(event -> {
                draggedExercise = null;
                for (Grid<Exercise> targetGrid : dayGrids) {
                    targetGrid.setDropMode(null);
                }
            });
        }
    }

    private List<Exercise> generateExercisesForDay(String day) {
        List<Exercise> exercises = new ArrayList<>();
        switch(day) {
            case "MONDAY":
                exercises.add(new Exercise("Squats", 3, 10, 4.5));
                exercises.add(new Exercise("Bench Press", 3, 8, 4.5));
                break;
            case "WEDNESDAY":
                exercises.add(new Exercise("Deadlifts", 3, 5, 4.5));
                exercises.add(new Exercise("Pull-ups", 3, 10, 4.5));
                break;
            default:
                exercises.add(new Exercise("Rest", 0, 0, 0.0));
                break;
        }
        return exercises;
    }
}