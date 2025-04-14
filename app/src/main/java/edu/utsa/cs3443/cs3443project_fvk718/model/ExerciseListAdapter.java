package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Array;
import java.util.ArrayList;

import edu.utsa.cs3443.cs3443project_fvk718.MainActivity;
import edu.utsa.cs3443.cs3443project_fvk718.R;
import edu.utsa.cs3443.cs3443project_fvk718.WorkoutActivity;

public class ExerciseListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final Workout workout;

    private final ArrayList<String> exerciseNames;
    private final ArrayList<Integer> restTimers;
    private ListView setList;

    public ExerciseListAdapter(Activity context, Workout workout, ArrayList<String> exerciseNames, ArrayList<Integer> restTimers) {
        super(context, R.layout.exerciselistitem, exerciseNames);
        this.context = context;
        this.workout = workout;
        this.exerciseNames = exerciseNames;
        this.restTimers = restTimers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.exerciselistitem, null, true);

        TextView nameText = rowView.findViewById(R.id.exerciseName);
        TextView restTimer = rowView.findViewById(R.id.restTimerText);

        setList = rowView.findViewById(R.id.setList);

        refreshList(workout.getExercises().get(position));

        Button addSetButton = rowView.findViewById(R.id.addSetButton);
        addSetButton.setOnClickListener(view -> {
            workout.getExercises().get(position).getSets().add(0);
            workout.getExercises().get(position).getSets().add(0);
            for (int i = 0; i < workout.getExercises().get(position).getSets().size(); i++) {
                System.out.println(workout.getExercises().get(position).getSets().get(i));
            }
            refreshList(workout.getExercises().get(position));
        });

        nameText.setText(exerciseNames.get(position));
        restTimer.setText("Rest Timer: " + restTimers.get(position));

        return rowView;
    }

    public void refreshList(Exercise exercise) {
        ArrayList<String> setNames = new ArrayList<>();
        ArrayList<Integer> setNumbers = new ArrayList<>();
        ArrayList<Integer> weights = new ArrayList<>();
        ArrayList<Integer> reps = new ArrayList<>();

        for (int i = 0; i < exercise.getSets().size()/2; i++) {
            setNames.add("set" + (i+1));
            setNumbers.add(i+1);
            weights.add(exercise.getSets().get(i));
            reps.add(exercise.getSets().get(i+1));
        }

        SetListAdapter adapter = new SetListAdapter(this.context, setNames, setNumbers, weights, reps);
        setList.setAdapter(adapter);
    }
}
