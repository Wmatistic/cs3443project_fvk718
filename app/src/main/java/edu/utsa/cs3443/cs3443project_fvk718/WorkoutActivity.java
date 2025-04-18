package edu.utsa.cs3443.cs3443project_fvk718;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import edu.utsa.cs3443.cs3443project_fvk718.model.Exercise;
import edu.utsa.cs3443.cs3443project_fvk718.model.ExerciseListAdapter;
import edu.utsa.cs3443.cs3443project_fvk718.model.Workout;

public class WorkoutActivity extends AppCompatActivity {

    Workout workout;
    ListView exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputLayout workoutNameInput = findViewById(R.id.workoutNameInput);
        Button saveWorkoutButton = findViewById(R.id.saveWorkoutButton);

        saveWorkoutButton.setVisibility(View.GONE);
        workoutNameInput.setVisibility(View.GONE);

        Intent callingIntent = getIntent();
        workout = (Workout) callingIntent.getSerializableExtra("Workout");

        TextView workoutNameText = findViewById(R.id.workoutName);
        workoutNameText.setText(workout.getName());

        exerciseList = findViewById(R.id.exerciseList);

        refreshList();

        Intent discardIntent = new Intent(WorkoutActivity.this, MainActivity.class);

        Button discardButton = findViewById(R.id.discardWorkoutButton);
        discardButton.setOnClickListener(view -> {
            startActivity(discardIntent);
        });

        TextView cancelButton = findViewById(R.id.cancelWorkoutText);
        cancelButton.setOnClickListener(view -> {
            startActivity(discardIntent);
        });

        Intent addExerciseIntent = new Intent(WorkoutActivity.this, AddExerciseActivity.class);

        Button addExerciseButton = findViewById(R.id.addExerciseButton);
        addExerciseButton.setOnClickListener(view -> {
            addExerciseIntent.putExtra("Workout", workout);
            startActivity(addExerciseIntent);
        });

        Button finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(view -> {
            exerciseList.setVisibility(View.INVISIBLE);

            saveWorkoutButton.setVisibility(View.VISIBLE);
            workoutNameInput.setVisibility(View.VISIBLE);
        });

        Intent saveIntent = new Intent(WorkoutActivity.this, MainActivity.class);

        saveWorkoutButton.setOnClickListener(view -> {
            if(!workoutNameInput.getEditText().getText().toString().isEmpty()) {
                workout.setName(Objects.requireNonNull(workoutNameInput.getEditText()).getText().toString());
            }
            workout.saveWorkout(this);
            startActivity(saveIntent);
        });
    }

    public static void exampleMethod() {

    }

    public void refreshList() {
        ArrayList<String> exerciseNames = new ArrayList<>();
        ArrayList<Integer> restTimers = new ArrayList<>();

        for (int i = 0; i < workout.getExercises().size(); i++) {
            exerciseNames.add(workout.getExercises().get(i).getName());
            restTimers.add(workout.getExercises().get(i).getRestTimer());
        }

        ExerciseListAdapter adapter = new ExerciseListAdapter(this, workout, exerciseNames, restTimers);
        exerciseList.setAdapter(adapter);
    }
}