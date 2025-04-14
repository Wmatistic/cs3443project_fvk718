package edu.utsa.cs3443.cs3443project_fvk718.model;

import java.io.Serializable;
import java.util.ArrayList;

import edu.utsa.cs3443.cs3443project_fvk718.MainActivity;

public class Routines implements Serializable {

    private ArrayList<Workout> workouts;

    private MainActivity activity;

    public Routines (MainActivity activity) {
        this.activity = activity;

        workouts = loadWorkouts();
    }

    private ArrayList<Workout> loadWorkouts() {
        ArrayList<Workout> test = new ArrayList<>();
        test.add(new Workout("Snooble"));
        test.add(new Workout("Legs and Abs"));
        test.add(new Workout("Legs and Abs"));
        test.add(new Workout("Legs and Abs"));
        test.add(new Workout("Legs and Abs"));
        test.add(new Workout("Legs and Abs"));
        return test;
    }

    public int getRoutineCount() {
        return workouts.size();
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }
}
