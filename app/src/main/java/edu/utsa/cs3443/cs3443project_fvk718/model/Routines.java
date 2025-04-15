package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.content.res.AssetManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import edu.utsa.cs3443.cs3443project_fvk718.MainActivity;

public class Routines implements Serializable {

    private ArrayList<Workout> workouts;

    public Routines (MainActivity activity) {

        workouts = new ArrayList<>();

        loadWorkouts(activity);
    }

    private void addWorkout(Workout workout){
        workouts.add(workout);
    }

    public void deleteWorkout(Workout workout, MainActivity activity) {
        workouts.remove(workout);

        File dir = activity.getFilesDir();
        System.out.println(workout.getName().trim() + ".csv");
        File deletedWorkout = new File(dir, workout.getName().replaceAll("\\s+", "") + ".csv");
        deletedWorkout.delete();
    }

    private void loadWorkouts(MainActivity activity) {
        Scanner sc = null;

        try {
            String[] fileNames = activity.getFilesDir().list();

            if (fileNames != null) {
                for (String f : fileNames) {
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
