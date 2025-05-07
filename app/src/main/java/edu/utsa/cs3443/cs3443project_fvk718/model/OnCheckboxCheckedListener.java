package edu.utsa.cs3443.cs3443project_fvk718.model;

// Interface used by the sets and workout activity to reach the workout activity class when a check box is pressed, activates the rest timer
// SEE WorkoutActivity.java AND SetListAdapter.java
public interface OnCheckboxCheckedListener {
    void onCheckboxChecked(int restTimer);
}
