package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import edu.utsa.cs3443.cs3443project_fvk718.R;

public class SetListAdapter extends ArrayAdapter<String> {
    private final Activity context;

    private final ArrayList<String> setNames;
    private final ArrayList<Integer> setNumbers;
    private final ArrayList<Integer> weights;
    private final ArrayList<Integer> reps;

    public SetListAdapter(Activity context, ArrayList<String> setNames, ArrayList<Integer> setNumbers, ArrayList<Integer> weights, ArrayList<Integer> reps) {
        super(context, R.layout.setlistitem, setNames);
        for(String i : setNames) {
            System.out.println(i);
        }
        this.context = context;
        this.setNames = setNames;
        this.setNumbers = setNumbers;
        this.weights = weights;
        this.reps = reps;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.setlistitem, null, true);

        TextView setNumber = rowView.findViewById(R.id.setNumber);
        TextInputLayout setWeight = rowView.findViewById(R.id.weightInput);
        TextInputLayout reps = rowView.findViewById(R.id.repsInput);

        setNumber.setText(setNumbers.get(position));

        return rowView;
    }
}
