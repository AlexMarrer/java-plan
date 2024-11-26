package com.example.application.component;

import com.example.application.utilities.Exercise;
import com.example.application.utilities.I18NProvider;
import com.example.application.utilities.TableView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.component.select.Select;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.time.LocalDateTime;
import java.util.*;

public class CalendarTable {

    private Select<TableView> tableView = new Select<>();
    private String standardViewValue = "WEEKLY";
    private Button addExerciseButton;

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
        try {
            WebStorage.getItem("standardViewValue").thenAccept(value -> {
                UI ui = UI.getCurrent();

                ui.access(() -> {
                    this.standardViewValue = value == null ? "WEEKLY" : value;

                    createTableViewSelection();
                });
            });
        } catch (Exception e) {
            this.standardViewValue = "WEEKLY";
            createTableViewSelection();
        }
        createExerciseAddButton();

        updateTexts();
        setCurrentDate();

        setupCrossDayDragAndDrop();
        setupInDayReordering();

        return this.dayGrids;
    }

    public Select<TableView> getTableView() {
        return this.tableView;
    }

    public Button getAddExerciseButton() {
        return this.addExerciseButton;
    }

    private void createTableViewSelection() {
        this.tableView.setItems(
                new TableView(I18NProvider.getTranslation("TABLE.WEEKLY"), "WEEKLY"),
                new TableView(I18NProvider.getTranslation("TABLE.THREE-DAYS"), "THREE-DAYS"),
                new TableView(I18NProvider.getTranslation("TABLE.DAY"), "DAY")
        );
        this.tableView.setValue(new TableView(I18NProvider.getTranslation("TABLE."+this.standardViewValue), this.standardViewValue));
        this.tableView.setItemLabelGenerator(TableView::label);
        this.tableView.setItemEnabledProvider(TableView::enabled);
        this.tableView.setLabel(I18NProvider.getTranslation("TABLE.VIEW-HEADER"));

        this.tableView.addValueChangeListener(event -> {
            if(event.getValue() != null) {
                this.standardViewValue = event.getValue().value();
                WebStorage.setItem("standardViewValue", this.standardViewValue);
            }
        });
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

    private void createExerciseAddButton() {
        this.addExerciseButton = new Button();
        var buttonIcon = LineAwesomeIcon.PLUS_CIRCLE_SOLID.create();
        buttonIcon.setSize("2.5rem");
        this.addExerciseButton.addClickListener(clickEvent -> {
        });

        this.addExerciseButton.setIcon(buttonIcon);
        this.addExerciseButton.addClassNames("table__add-button");

        this.addExerciseButton.addClickListener(this::showAddExerciseView);
    }

    private void showAddExerciseView(ClickEvent<Button> clickEvent) {
        
    }

    public void updateTexts() {
        createTableViewSelection();

        for (Grid<Exercise> dayGrid : this.dayGrids) {
            dayGrid.getColumns().forEach( header -> {
                if(Arrays.asList(days).contains(header.getId().orElse("none"))) {
                    header.setHeader(I18NProvider.getTranslation("TABLE." + header.getId().orElse("none")));
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
