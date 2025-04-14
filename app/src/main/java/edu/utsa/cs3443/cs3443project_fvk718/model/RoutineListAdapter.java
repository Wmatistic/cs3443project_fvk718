package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.utsa.cs3443.cs3443project_fvk718.R;

public class RoutineListAdapter extends ArrayAdapter<String> {
    private final Activity context;

    private final String[] routineNames;

    public RoutineListAdapter (Activity context, String[] routineNames) {
        super(context, R.layout.routinelistitem, routineNames);
        this.context = context;
        this.routineNames = routineNames;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertViet, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.routinelistitem, null, true);

        TextView workoutNameText = rowView.findViewById(R.id.workoutNameTextView);
        Button routineButton = rowView.findViewById(R.id.routineButton);
        Button startWorkoutButton = rowView.findViewById(R.id.startWorkoutButton);

        workoutNameText.setText(routineNames[position]);
        routineButton.setText("");

        return rowView;
    }
}
