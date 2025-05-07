package edu.utsa.cs3443.cs3443project_fvk718;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import edu.utsa.cs3443.cs3443project_fvk718.model.ExerciseListAdapter;
import edu.utsa.cs3443.cs3443project_fvk718.model.OnCheckboxCheckedListener;
import edu.utsa.cs3443.cs3443project_fvk718.model.Workout;

// Workout Screen
public class WorkoutActivity extends AppCompatActivity implements OnCheckboxCheckedListener {

    Workout workout;
    ListView exerciseList;
    TextView restTimerTextView;
    ProgressBar restProgressBar;
    CountDownTimer activeRestTimer;

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

        // Workout save assets initialization
        TextInputLayout workoutNameInput = findViewById(R.id.workoutNameInput);
        Button saveWorkoutButton = findViewById(R.id.saveWorkoutButton);

        saveWorkoutButton.setVisibility(View.GONE);
        workoutNameInput.setVisibility(View.GONE);

        // Getting current workout
        Intent callingIntent = getIntent();
        workout = (Workout) callingIntent.getSerializableExtra("Workout");

        TextView workoutNameText = findViewById(R.id.workoutName);
        workoutNameText.setText(workout.getName());

        restTimerTextView = findViewById(R.id.restTimerTextView);
        restProgressBar = findViewById(R.id.restProgressBar);

        exerciseList = findViewById(R.id.exerciseList);

        // Loading list with old / new info
        refreshList();

        // Canceling workout functionality
        Intent discardIntent = new Intent(WorkoutActivity.this, MainActivity.class);

        Button discardButton = findViewById(R.id.discardWorkoutButton2);
        discardButton.setOnClickListener(view -> {
            startActivity(discardIntent);
        });

        TextView cancelButton = findViewById(R.id.cancelWorkoutText);
        cancelButton.setOnClickListener(view -> {
            startActivity(discardIntent);
        });

        // Settings page asset functionality
        Intent settingsIntent = new Intent(WorkoutActivity.this, SettingsActivity.class);

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(view -> {
            settingsIntent.putExtra("Workout", workout);
            startActivity(settingsIntent);
        });

        // Add exercise asset functionality
        Intent addExerciseIntent = new Intent(WorkoutActivity.this, AddExerciseActivity.class);

        Button addExerciseButton = findViewById(R.id.addExerciseButton);
        addExerciseButton.setOnClickListener(view -> {
            addExerciseIntent.putExtra("Workout", workout);
            startActivity(addExerciseIntent);
        });

        // Finish / save exercise asset functionality
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

    // Method to start rest timer when check box is checked
    @Override
    public void onCheckboxChecked(int restTimer) {
        if (activeRestTimer != null) {
            activeRestTimer.cancel();
        }

        int timerDuration = restTimer;

        activeRestTimer = new CountDownTimer(timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (((double)(timerDuration - millisUntilFinished) / timerDuration) * 1000);
                restProgressBar.setProgress(progress);

                restTimerTextView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                restProgressBar.setProgress(1000);

                restTimerTextView.setText("Rest Timer Over!");
            }
        }.start();
    }

    // method to load list with old / new info
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