package edu.utsa.cs3443.cs3443project_fvk718;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.cs3443.cs3443project_fvk718.model.Workout;

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

        Intent callingIntent = getIntent();
        workout = (Workout) callingIntent.getSerializableExtra("Workout");

        TextView doneText = findViewById(R.id.doneSettingsText);

        Intent doneIntent = new Intent(SettingsActivity.this, WorkoutActivity.class);
        doneText.setOnClickListener(view -> {
            doneIntent.putExtra("Workout", workout);
            startActivity(doneIntent);
        });

        Switch showPrevValuesSwitch = findViewById(R.id.showPrevValuesSwitch);
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
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
    }
}