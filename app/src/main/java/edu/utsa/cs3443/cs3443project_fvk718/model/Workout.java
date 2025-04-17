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

public class Workout implements Serializable {

    private String name;
    private ArrayList<Exercise> exercises;

    public Workout () {

        this.name = "New Workout";
        this.exercises = new ArrayList<>();
    }

    public Workout (String name, MainActivity activity) {

        this.name = name;
        this.exercises = new ArrayList<>();

        loadExercises(activity);
    }

    public void deleteExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    public void saveWorkout(WorkoutActivity activity) {
        String workoutFilename = name.replaceAll("\\s+", "") + ".csv";

        try {
            FileOutputStream out = activity.openFileOutput(workoutFilename, Context.MODE_PRIVATE);
            out.write((name + "\n\n").getBytes(StandardCharsets.UTF_8));

            for (Exercise e : exercises) {
                out.write((e.getName() + ", " + e.getRestTimer() + ", " + e.getEquipment() + ", " + e.getMuscleGroup() + ", " + e.getType() + "\n").getBytes(StandardCharsets.UTF_8));

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

    private void loadExercises(MainActivity activity) {
        Scanner sc = null;
        String workoutFilename = name.replaceAll("\\s+", "") + ".csv";

        try {
            InputStream file = activity.openFileInput(workoutFilename);
            sc = new Scanner(file);

            sc.nextLine();
            sc.nextLine();

            Exercise exercise = null;

            while (sc.hasNextLine()) {
                String[] exerciseInfo = sc.nextLine().split(",");

                if (exerciseInfo.length == 5) {
                    exercise = new Exercise(exerciseInfo[0].trim(), Integer.parseInt(exerciseInfo[1].trim()), exerciseInfo[2].trim(), exerciseInfo[3].trim(), exerciseInfo[4].trim());
                    addExercise(exercise);
                } else if (exerciseInfo.length == 3) {
                    if (exercise != null) {
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
