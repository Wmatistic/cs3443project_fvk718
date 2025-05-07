package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import edu.utsa.cs3443.cs3443project_fvk718.MainActivity;
import edu.utsa.cs3443.cs3443project_fvk718.WorkoutActivity;

// Workout class represents a workout and stores the exercises inside in a list
public class Workout implements Serializable {

    private String name;
    private ArrayList<Exercise> exercises;

    // Constructor to initialize blank workout
    public Workout () {
        this.name = "New Workout";
        this.exercises = new ArrayList<>();
    }

    // Constructor to initialize previous workout with name
    public Workout (String name, MainActivity activity) {

        this.name = name;
        this.exercises = new ArrayList<>();

        loadExercises(activity);
    }

    // Method to delete an exercise
    public void deleteExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    // Method to save workout and write changes to .csv file
    public void saveWorkout(WorkoutActivity activity) {
        String workoutFilename = name.replaceAll("\\s+", "") + ".csv";

        try {
            // Write name of workout at top of file
            FileOutputStream out = activity.openFileOutput(workoutFilename, Context.MODE_PRIVATE);
            out.write((name + "\n\n").getBytes(StandardCharsets.UTF_8));

            // Loop through Exercises
            for (Exercise e : exercises) {
                // Write Exercise info at top of exercise
                out.write((e.getName() + ", " + e.getRestTimer() + ", " + e.getEquipment() + ", " + e.getMuscleGroup() + ", " + e.getType() + "\n").getBytes(StandardCharsets.UTF_8));

                // Write sets under exercise info
                // SEE EXERCISE CLASS FOR THE LOGIC BEHIND THE SETS
                for (int i = 0; i < e.getSets().size(); i+=2) {
                    out.write((((i/2)+1) + ", " + e.getSets().get(i) + ", " + e.getSets().get(i+1) + "\n").getBytes(StandardCharsets.UTF_8));
                }

                out.write(("\n").getBytes(StandardCharsets.UTF_8));
            }

            out.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to add exercise
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    // Method to get exercise based on name
    public Exercise getExercise(String name) {
        for (Exercise e : exercises) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    // Method to load exercises from .csv file
    private void loadExercises(MainActivity activity) {
        Scanner sc = null;
        String workoutFilename = name.replaceAll("\\s+", "") + ".csv";

        try {
            InputStream file = activity.openFileInput(workoutFilename);
            sc = new Scanner(file);

            // Skip workout initial line and new line
            sc.nextLine();
            sc.nextLine();

            // Make blank exercise to fill and eventually add to list
            Exercise exercise = null;

            while (sc.hasNextLine()) {
                // Separate info from first line
                String[] exerciseInfo = sc.nextLine().split(",");

                // If statements that fill the exercise to allow looping only once
                //      if the length is 5 then we know it's the info on the exercise
                //      this will always prelude the sets so we can then add the sets to the same exercise
                //      if the length is 3 then we know it's a set and add the weight and reps of the set
                //      if the length is 0 then we know it's a new line representing the end of the exercise and we reset the exercise
                if (exerciseInfo.length == 5) {
                    exercise = new Exercise(exerciseInfo[0].trim(), Integer.parseInt(exerciseInfo[1].trim()), exerciseInfo[2].trim(), exerciseInfo[3].trim(), exerciseInfo[4].trim());
                    addExercise(exercise);
                } else if (exerciseInfo.length == 3) {
                    if (exercise != null) {
                        // SEE EXERCISE CLASS FOR THE LOGIC BEHIND THE SETS
                        exercise.getSets().add(Integer.parseInt(exerciseInfo[1].trim()));
                        exercise.getSets().add(Integer.parseInt(exerciseInfo[2].trim()));
                    }
                } else if (exerciseInfo.length == 0){
                    exercise = null;
                }
            }

            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
