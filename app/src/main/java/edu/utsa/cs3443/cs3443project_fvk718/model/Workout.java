package edu.utsa.cs3443.cs3443project_fvk718.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Workout implements Serializable {

    private String name;
    private ArrayList<Exercise> exercises;

    public Workout (String name) {
        this.name = name;
        this.exercises = loadExercises();
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public Exercise getExercise(String name) {
        for (Exercise e : exercises) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    private ArrayList<Exercise> loadExercises() {
        return new ArrayList<>();
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
