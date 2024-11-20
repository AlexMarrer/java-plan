package com.example.application.utilities;

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
}
