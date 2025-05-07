package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Array;
import java.util.ArrayList;

import edu.utsa.cs3443.cs3443project_fvk718.AddExerciseActivity;
import edu.utsa.cs3443.cs3443project_fvk718.MainActivity;
import edu.utsa.cs3443.cs3443project_fvk718.R;
import edu.utsa.cs3443.cs3443project_fvk718.WorkoutActivity;

// List adapter for the exercise list
public class ExerciseListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final Workout workout;

    private final ArrayList<String> exerciseNames;
    private final ArrayList<Integer> restTimers;

    // Because there are multiple set lists nested in the exercise list items they are stored in an array to be easily addressed based on the position of the exercise list item
    private RecyclerView[] setList;
    private SetListAdapter adapter;

    public ExerciseListAdapter(Activity context, Workout workout, ArrayList<String> exerciseNames, ArrayList<Integer> restTimers) {
        super(context, R.layout.exerciselistitem, exerciseNames);
        this.context = context;
        this.workout = workout;
        this.exerciseNames = exerciseNames;
        this.restTimers = restTimers;

        this.setList = new RecyclerView[workout.getExercises().size()];
    }

    // Method called when list item is created, used to set assets and functionality
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate exercise list item
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.exerciselistitem, null, true);

        TextView nameText = rowView.findViewById(R.id.exerciseName);
        TextView restTimer = rowView.findViewById(R.id.restTimerText);
        ImageView deleteExerciseImage = rowView.findViewById(R.id.deleteExerciseImage);
        Button editExerciseButton = rowView.findViewById(R.id.editExerciseButton);

        // Set the set list in the list of set lists
        setList[position] = rowView.findViewById(R.id.setList);

        // Refresh the list with the new / initial data
        refreshList(workout.getExercises().get(position), position);

        // Add set functionality
        Button addSetButton = rowView.findViewById(R.id.addSetButton);
        addSetButton.setOnClickListener(view -> {
            workout.getExercises().get(position).getSets().add(0);
            workout.getExercises().get(position).getSets().add(0);
            refreshList(workout.getExercises().get(position), position);
        });

        nameText.setText(exerciseNames.get(position));
        restTimer.setText("Rest Timer: " + restTimers.get(position) + " seconds");

        // Delete set functionality
        deleteExerciseImage.setOnClickListener(view -> {
            workout.deleteExercise(workout.getExercises().get(position));
            context.recreate();
        });

        // Edit set functionality
        Intent editIntent = new Intent(context, AddExerciseActivity.class);
        editExerciseButton.setOnClickListener(view -> {
            editIntent.putExtra("Workout", workout);
            editIntent.putExtra("Editing Exercise", true);
            editIntent.putExtra("Exercise Index", position);
            context.startActivity(editIntent);
        });

        return rowView;
    }

    // Refresh the set list at the given position using the set list adapter class
    public void refreshList(Exercise exercise, int position) {
        adapter = new SetListAdapter(this.context, exercise, (OnCheckboxCheckedListener) context);
        setList[position].setAdapter(adapter);
        setList[position].setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}
