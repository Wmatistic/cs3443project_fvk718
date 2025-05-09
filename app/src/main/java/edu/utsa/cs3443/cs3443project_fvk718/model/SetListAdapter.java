package edu.utsa.cs3443.cs3443project_fvk718.model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import edu.utsa.cs3443.cs3443project_fvk718.R;

// List adapter for set list
public class SetListAdapter extends RecyclerView.Adapter<SetListAdapter.MyViewHolder> {
    private final Activity context;

    private final Exercise exercise;
    private final ArrayList<String> setNames;
    private final ArrayList<Integer> setNumbers;
    private final ArrayList<Integer> weights;
    private final ArrayList<Integer> reps;
    private final int restTimer;
    private final OnCheckboxCheckedListener listener;

    // View holder that contains each list items assets
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView setNumber;
        public TextInputLayout setWeight;
        public EditText weightEditText;
        public TextInputLayout setReps;
        public EditText repsEditText;
        public ImageView deleteSetImage;
        public CheckBox finishSetCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            setNumber = itemView.findViewById(R.id.setNumber);
            setWeight = itemView.findViewById(R.id.weightInput);
            weightEditText = itemView.findViewById(R.id.weightEditText);
            setReps = itemView.findViewById(R.id.repsInput);
            repsEditText = itemView.findViewById(R.id.repsEditText);
            deleteSetImage = itemView.findViewById(R.id.deleteSetImage);
            finishSetCheckBox = itemView.findViewById(R.id.finishSetCheckBox);
        }
    }

    // Constructor that initializes assets / lists of all the set list contents
    public SetListAdapter(Activity context, Exercise exercise, OnCheckboxCheckedListener listener) {
        this.context = context;
        this.exercise = exercise;
        this.setNames = new ArrayList<>();
        this.setNumbers = new ArrayList<>();
        this.weights = new ArrayList<>();
        this.reps = new ArrayList<>();
        this.restTimer = exercise.getRestTimer();
        this.listener = listener;

        for (int i = 0; i < exercise.getSets().size()/2; i++) {
            setNames.add("set" + (i+1));
            setNumbers.add(i+1);
        }

        for (int i = 0; i < exercise.getSets().size(); i+=2) {
            weights.add(exercise.getSets().get(i));
            reps.add(exercise.getSets().get(i+1));
        }
    }

    // Method that uses the setlistitem.xml layout file to create the view holder for each list item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.setlistitem, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    // Method that is called once the list item is bound to the list, position is the position of the list item and used to change the assets
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.setNumber.setText(String.valueOf(setNumbers.get(position)));

        // Get shared preference boolean for show previous values setting
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean showPrevValues = prefs.getBoolean("showPrevValues", true);

        // Set weights and reps hint text based on previous values if the setting is on
        if (weights.get(position) == 0 || !showPrevValues) {
            myViewHolder.setWeight.setHint("Weight");
        } else {
            myViewHolder.setWeight.setHint(String.valueOf(weights.get(position)));
        }

        if (reps.get(position) == 0 || !showPrevValues) {
            myViewHolder.setReps.setHint("Reps");
        } else {
            myViewHolder.setReps.setHint(String.valueOf(reps.get(position)));
        }

        // If checked box is checked call onCheckboxChecked() method in WorkoutActivity
        myViewHolder.finishSetCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && listener != null) {
                    listener.onCheckboxChecked(restTimer * 1000);
                }
            }
        });

        // Logic for removing set and refreshing list
        myViewHolder.deleteSetImage.setOnClickListener(view -> {
            exercise.getSets().remove((myViewHolder.getAdapterPosition()*2) + 1);
            exercise.getSets().remove(myViewHolder.getAdapterPosition()*2);
            context.recreate();
        });

        // Functionality to set the set weight / reps to the inputted text
        myViewHolder.weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    try {
                        exercise.getSets().set(myViewHolder.getAdapterPosition() * 2, Integer.parseInt(editable.toString()));
                    } catch(NumberFormatException ignored){}
                }
            }
        });

        myViewHolder.repsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    try {
                        exercise.getSets().set((myViewHolder.getAdapterPosition() * 2) + 1, Integer.parseInt(editable.toString()));
                    } catch(NumberFormatException ignored) {}
                }
            }
        });
    }

    // Get the number of sets in the list
    @Override
    public int getItemCount() {
        return setNumbers.size();
    }
}
