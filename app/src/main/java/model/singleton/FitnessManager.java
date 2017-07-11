package model.singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.gyms.Exercise;
import model.gyms.Gym;

/**
 * Created by dkirilova on 7/6/2017.
 */

public class FitnessManager {
    private static final FitnessManager ourInstance = new FitnessManager();

    public static FitnessManager getInstance() {
        return ourInstance;
    }

    private HashMap<Boolean, HashSet<Gym>> allGyms;
    private HashSet<Exercise> exercises;

    private FitnessManager() {
        this.allGyms = new HashMap<>();
        this.allGyms.put(true, new HashSet<Gym>());
        this.allGyms.put(false, new HashSet<Gym>());
        this.exercises = new HashSet<>();
    }

    public List<Gym> getAllGyms() {

        List<Gym> list = new ArrayList<>();
        list.addAll(allGyms.get(true));
        list.addAll(allGyms.get(false));
        return Collections.unmodifiableList(list);
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercisesList = new ArrayList<>();
        exercisesList.addAll(this.exercises);
        return Collections.unmodifiableList(exercisesList);
    }


    public void delete(Gym gym) {
        if (gym != null && allGyms.containsKey(gym.isFavourite())) {
            this.allGyms.get(gym.isFavourite()).remove(gym);
        }
    }

    public void delete(Exercise exercise) {
        exercises.remove(exercise);

        for(Gym gym : allGyms.get(true)){
            gym.removeExercise(exercise);
        }
        for(Gym gym : allGyms.get(false)){
            gym.removeExercise(exercise);
        }
    }

    public void add(Gym gym) {
        if ((gym) != null) {
            this.allGyms.get(gym.isFavourite()).add(gym);
        }
    }

    public void add(Exercise exercise) {
        exercises.add(exercise);
    }

    public List<Gym> getFavourites() {
        List<Gym> favourites = new ArrayList<>();
        favourites.addAll(this.allGyms.get(true));
        return favourites;
    }

    public void addExercises(ArrayList<Exercise> exercises) {
        for (Exercise exercise : exercises) {
            this.exercises.add(exercise);
        }
    }

    public List<Gym> getGyms(Exercise exercise) {

        List<Gym> gyms = new ArrayList<>();
        for(Gym gym : allGyms.get(true)){
            if(gym.getExercises().contains(exercise)){
                gyms.add(gym);
            }
        }

        for(Gym gym : allGyms.get(false)){
            if(gym.getExercises().contains(exercise)){
                gyms.add(gym);
            }
        }
        return gyms;
    }
}
