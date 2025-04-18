package edu.utsa.cs3443.cs3443project_fvk718;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import edu.utsa.cs3443.cs3443project_fvk718.model.Exercise;
import edu.utsa.cs3443.cs3443project_fvk718.model.Workout;

public class AddExerciseActivity extends AppCompatActivity {

    private String equipment = "";
    private String muscleGroup = "";
    private String exerciseType = "";

    private Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent callingIntent = getIntent();
        workout = (Workout) callingIntent.getSerializableExtra("Workout");

        TextInputLayout exerciseName = findViewById(R.id.exerciseNameInput);

        TextInputLayout restTimer = findViewById(R.id.restTimerInput);

        Button equipmentButton = findViewById(R.id.equipmentButton);
        equipmentButton.setText("");
        TextView equipmentSelectedText = findViewById(R.id.equipmentSelectedText);

        Button muscleGroupButton = findViewById(R.id.muscleGroupButton);
        muscleGroupButton.setText("");
        TextView muscleGroupSelectedText = findViewById(R.id.muscleGroupSelectedText);

        Button exerciseTypeButton = findViewById(R.id.exerciseTypeButton);
        exerciseTypeButton.setText("");
        TextView exerciseSelectedText = findViewById(R.id.exerciseSelectedText);

        Button saveButton = findViewById(R.id.saveButton);

        TextView cancelExerciseText = findViewById(R.id.cancelExerciseText);

        Intent cancelIntent = new Intent(AddExerciseActivity.this, WorkoutActivity.class);
        cancelExerciseText.setOnClickListener(view -> {
            cancelIntent.putExtra("Workout", workout);
            startActivity(cancelIntent);
        });

        equipmentButton.setOnClickListener(view -> {
            equipmentSelectedText.setError(null);

            equipment = "Barbell";

            equipmentSelectedText.setText(equipment);
        });

        muscleGroupButton.setOnClickListener(view -> {
            muscleGroupSelectedText.setError(null);

            muscleGroup = "Legs";

            muscleGroupSelectedText.setText(muscleGroup);
        });

        exerciseTypeButton.setOnClickListener(view -> {
            exerciseSelectedText.setError(null);

            exerciseType = "Weight And Reps";

            exerciseSelectedText.setText(exerciseType);
        });

        Intent intent = new Intent(AddExerciseActivity.this, WorkoutActivity.class);
        saveButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(exerciseName.getEditText()).getText().toString().isEmpty()) {
                exerciseName.setError("Exercise Name");
            }
            if (Objects.requireNonNull(restTimer.getEditText()).getText().toString().isEmpty()) {
                restTimer.setError("Rest Timer");
            }
            if (equipment.isEmpty()) {
                equipmentSelectedText.setError("Equipment");
            }
            if (muscleGroup.isEmpty()) {
                muscleGroupSelectedText.setError("Muscle Group");
            }
            if (exerciseType.isEmpty()) {
                exerciseSelectedText.setError("Exercise Type");
            }

            try {
                if (!(Objects.requireNonNull(exerciseName.getEditText()).getText().toString().isEmpty() && Objects.requireNonNull(restTimer.getEditText()).getText().toString().isEmpty() && equipment.isEmpty() && muscleGroup.isEmpty() && exerciseType.isEmpty())) {
                    workout.addExercise(new Exercise(Objects.requireNonNull(exerciseName.getEditText()).getText().toString(), Integer.parseInt(Objects.requireNonNull(restTimer.getEditText()).getText().toString()), equipment, muscleGroup, exerciseType));

                    intent.putExtra("Workout", workout);
                    startActivity(intent);
                }
            }catch(NumberFormatException ignored){
                restTimer.setError("Rest Timer");
            }
        });
    }
}