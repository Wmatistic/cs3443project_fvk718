package edu.utsa.cs3443.cs3443project_fvk718;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

import edu.utsa.cs3443.cs3443project_fvk718.model.FileUtils;
import edu.utsa.cs3443.cs3443project_fvk718.model.RoutineListAdapter;
import edu.utsa.cs3443.cs3443project_fvk718.model.Routines;
import edu.utsa.cs3443.cs3443project_fvk718.model.Workout;

// Main Screen
public class MainActivity extends AppCompatActivity {

    private Routines routines;
    private Intent workoutIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Copy .csv files in asset folder to phones internal storage
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            FileUtils.copyAssetsFolderToInternalStorage(this, "");

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        }

        // Load the routines, workouts, and exercises from the .csv files
        loadRoutines();

        // Workout list initialization / functionality
        workoutIntent = new Intent(MainActivity.this, WorkoutActivity.class);

        Button emptyWorkout = findViewById(R.id.emptyWorkoutButton);
        emptyWorkout.setOnClickListener(view -> {
            workoutIntent.putExtra("Workout", new Workout());
            startActivity(workoutIntent);
        });

        ListView listView = findViewById(R.id.routineList);

        String[] routineNames = new String[routines.getRoutineCount()];

        for (int i = 0; i < routines.getRoutineCount(); i++) {
            routineNames[i] = routines.getWorkouts().get(i).getName();
        }

        RoutineListAdapter adapter = new RoutineListAdapter(this, routineNames, routines);
        listView.setAdapter(adapter);
    }

    // Method used to initialize routines, workouts, and exercises
    private void loadRoutines() {
        routines = new Routines(this);
    }
}