package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.utsa.cs3443.cs3443project_fvk718.MainActivity;
import edu.utsa.cs3443.cs3443project_fvk718.R;
import edu.utsa.cs3443.cs3443project_fvk718.WorkoutActivity;

// List adapter for the routine list
public class RoutineListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] routineNames;
    private final Routines routines;

    public RoutineListAdapter (Activity context, String[] routineNames, Routines routines) {
        super(context, R.layout.routinelistitem, routineNames);
        this.context = context;
        this.routineNames = routineNames;
        this.routines = routines;
    }

    // Method called when list item is created, used to set assets and functionality
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.routinelistitem, null, true);

        TextView workoutNameText = rowView.findViewById(R.id.workoutNameTextView);
        Button routineButton = rowView.findViewById(R.id.routineButton);
        Button startWorkoutButton = rowView.findViewById(R.id.startWorkoutButton);
        ImageView deleteWorkoutImage = rowView.findViewById(R.id.deleteWorkoutImage);

        workoutNameText.setText(routineNames[position]);
        routineButton.setText("");

        // Start workout functionality, this is implemented in a fairly messy way if i'm being honest
        // This was early in development where I was still learning but it works so...
        // DO NOT TOUCH WILL BREAK ENTIRE PROJECT
        Intent startWorkoutIntent = new Intent(context, WorkoutActivity.class);
        View.OnClickListener startWorkoutListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWorkoutIntent.putExtra("Workout", new Workout(routines.getWorkouts().get(position).getName(), (MainActivity) context));
                context.startActivity(startWorkoutIntent);
            }
        };

        startWorkoutButton.setOnClickListener(startWorkoutListener);
        routineButton.setOnClickListener(startWorkoutListener);

        // Delete workout functionality
        deleteWorkoutImage.setOnClickListener(view -> {
            routines.deleteWorkout(routines.getWorkouts().get(position), (MainActivity) context);
            context.recreate();
        });

        return rowView;
    }
}
