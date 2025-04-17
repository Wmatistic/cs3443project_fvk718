package edu.utsa.cs3443.cs3443project_fvk718.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import edu.utsa.cs3443.cs3443project_fvk718.R;

public class SetListAdapter extends RecyclerView.Adapter<SetListAdapter.MyViewHolder> {
    private final Activity context;

    private final ArrayList<String> setNames;
    private final ArrayList<Integer> setNumbers;
    private final ArrayList<Integer> weights;
    private final ArrayList<Integer> reps;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView setNumber;
        public TextInputLayout setWeight;
        public TextInputLayout setReps;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            setNumber = itemView.findViewById(R.id.setNumber);
            setWeight = itemView.findViewById(R.id.weightInput);
            setReps = itemView.findViewById(R.id.repsInput);
        }
    }

    public SetListAdapter(Activity context, ArrayList<String> setNames, ArrayList<Integer> setNumbers, ArrayList<Integer> weights, ArrayList<Integer> reps) {
        this.context = context;
        this.setNames = setNames;
        this.setNumbers = setNumbers;
        this.weights = weights;
        this.reps = reps;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.setlistitem, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.setNumber.setText(String.valueOf(setNumbers.get(position)));
        myViewHolder.setWeight.setHint(String.valueOf(weights.get(position)));
        myViewHolder.setReps.setHint(String.valueOf(reps.get(position)));
    }

    @Override
    public int getItemCount() {
        return setNumbers.size();
    }
}
