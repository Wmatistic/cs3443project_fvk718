package edu.utsa.cs3443.cs3443project_fvk718;

import android.content.Intent;
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

import edu.utsa.cs3443.cs3443project_fvk718.model.RoutineListAdapter;
import edu.utsa.cs3443.cs3443project_fvk718.model.Routines;
import edu.utsa.cs3443.cs3443project_fvk718.model.Workout;

public class MainActivity extends AppCompatActivity {

    private Routines routines;
    private Intent workoutIntent;
    private int workoutPosition;

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

        loadRoutines();

        workoutIntent = new Intent(MainActivity.this, WorkoutActivity.class);

        Button emptyWorkout = findViewById(R.id.emptyWorkoutButton);
        emptyWorkout.setOnClickListener(view -> {
            workoutIntent.putExtra("Workout", new Workout(MainActivity.this));
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

    private void loadRoutines() {
        routines = new Routines(this);
    }
}