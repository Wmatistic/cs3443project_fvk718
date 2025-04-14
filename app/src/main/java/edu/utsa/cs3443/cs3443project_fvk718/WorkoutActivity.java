package edu.utsa.cs3443.cs3443project_fvk718;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

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

        Intent addExerciseIntent = new Intent(WorkoutActivity.this, AddExerciseActivity.class);

        Button addExerciseButton = findViewById(R.id.addExerciseButton);
        addExerciseButton.setOnClickListener(view -> {
            addExerciseIntent.putExtra("Workout", workout);
            startActivity(addExerciseIntent);
//            workout.addExercise(new Exercise("Bench Press", 120, "Barbell", "Biceps", "Weight & Reps"));
//            refreshList();
        });
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