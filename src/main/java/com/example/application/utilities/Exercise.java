package com.example.application.utilities;

import java.util.ArrayList;
import java.util.List;

public class Exercise {
    private String Day;
    private String name;
    private  int sets;
    private int reps;
    private double rpe;

    public Exercise(String name, int sets, int reps, double rpe) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.rpe = rpe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getRpe() {
        return rpe;
    }

    public void setRpe(double rpe) {
        this.rpe = rpe;
    }

    public static List<Exercise> getExercises() {
        var exercises = new ArrayList<Exercise>();
        exercises.add(new Exercise("Bench", 5, 5, 7.5));
        exercises.add(new Exercise("Squat", 5, 5, 7.5));
        exercises.add(new Exercise("Chest Incline", 5, 5, 7.5));
        exercises.add(new Exercise("Triceps Pulldown", 5, 5, 7.5));
        exercises.add(new Exercise("Legcurl", 5, 5, 7.5));

        return exercises;
    }
}
