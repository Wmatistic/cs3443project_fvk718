package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.content.res.AssetManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import edu.utsa.cs3443.cs3443project_fvk718.MainActivity;

// Routines class represents all the workouts and is used to organize them in an accessible way
public class Routines implements Serializable {

    private ArrayList<Workout> workouts;

    // Called by MainActivity at startup and starts the cascade of initialization
    public Routines (MainActivity activity) {

        workouts = new ArrayList<>();

        loadWorkouts(activity);
    }

    // Method to add a workout to the list
    private void addWorkout(Workout workout){
        workouts.add(workout);
    }

    // Method to delete workout from list and the .csv file
    public void deleteWorkout(Workout workout, MainActivity activity) {
        workouts.remove(workout);

        File dir = activity.getFilesDir();
        File deletedWorkout = new File(dir, workout.getName().replaceAll("\\s+", "") + ".csv");
        deletedWorkout.delete();
    }

    // Method to load workouts from the .csv files in internal storage
    private void loadWorkouts(MainActivity activity) {
        Scanner sc = null;

        try {
            // get file names in the internals storage
            String[] fileNames = activity.getFilesDir().list();

            if (fileNames != null) {
                // loop through names
                for (String f : fileNames) {
                    // if it's a .csv file add new workout with the name of the file passed to the workout class which handles the exercises inside the file
                    if (f.endsWith(".csv")) {
                        InputStream file = activity.openFileInput(f);
                        sc = new Scanner(file);

                        String nextLine = sc.nextLine();

                        addWorkout(new Workout(nextLine, activity));
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
