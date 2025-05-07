package edu.utsa.cs3443.cs3443project_fvk718;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.cs3443.cs3443project_fvk718.model.Workout;

// Settings screen
public class SettingsActivity extends AppCompatActivity {

    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Getting current workout
        Intent callingIntent = getIntent();
        workout = (Workout) callingIntent.getSerializableExtra("Workout");

        // Close settings page functionality
        TextView doneText = findViewById(R.id.doneSettingsText);

        Intent doneIntent = new Intent(SettingsActivity.this, WorkoutActivity.class);
        doneText.setOnClickListener(view -> {
            doneIntent.putExtra("Workout", workout);
            startActivity(doneIntent);
        });

        // Show previous workout values setting functionality, using shared preferences to have it carry over through restarts
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        Switch showPrevValuesSwitch = findViewById(R.id.showPrevValuesSwitch);
        boolean showPrevValues = prefs.getBoolean("showPrevValues", true);
        showPrevValuesSwitch.setChecked(showPrevValues);
        showPrevValuesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("showPrevValues", b);
                editor.apply();
            }
        });

        // Default number of sets generated setting functionality, using shared preferences to have it carry over through restarts
        EditText defaultSetTextInput = findViewById(R.id.defaultSetTextInput);
        int defaultSetNumber = prefs.getInt("defaultSetsNumber", 0);
        defaultSetTextInput.setText(String.valueOf(defaultSetNumber));
        defaultSetTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("defaultSetsNumber", Integer.parseInt(String.valueOf(defaultSetTextInput.getText())));
                    editor.apply();
                }
            }
        });
    }
}