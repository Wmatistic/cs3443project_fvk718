package edu.utsa.cs3443.cs3443project_fvk718.model;

import java.io.Serializable;
import java.util.ArrayList;

// Exercise class representing an exercise in a workout, stores all relevant info
public class Exercise implements Serializable {

    private String name;
    private int restTimer;
    private String equipment;
    private String muscleGroup;
    private String type;

    // The storage of sets works in an interesting way to allow the use of only one array
    //      Both the reps and weight are stored in the array list together one after the other
    //      The weight is stored first and then the reps are stored immediately after
    //      When looping through the array list we loop by 2 and then take the weight and reps at i and i+1
    private ArrayList<Integer> sets;

    public Exercise (String name, int restTimer, String equipment, String muscleGroup, String type, ArrayList<Integer> sets) {
        this.name = name;
        this.restTimer = restTimer;
        this.equipment = equipment;
        this.muscleGroup = muscleGroup;
        this.type = type;
        this.sets = sets;
    }

    public Exercise (String name, int restTimer, String equipment, String muscleGroup, String type) {
        this.name = name;
        this.restTimer = restTimer;
        this.equipment = equipment;
        this.muscleGroup = muscleGroup;
        this.type = type;
        this.sets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRestTimer() {
        return restTimer;
    }

    public void setRestTimer(int restTimer) {
        this.restTimer = restTimer;
    }

    public ArrayList<Integer> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Integer> sets) {
        this.sets = sets;
    }
}
