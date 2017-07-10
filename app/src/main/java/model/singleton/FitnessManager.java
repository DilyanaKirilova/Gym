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
    private ArrayList<Exercise> exercises;

    private FitnessManager() {
        this.allGyms = new HashMap<>();
        this.exercises = new ArrayList<Exercise>();
    }

    public List<Gym> getAllGyms() {

        List<Gym> list = new ArrayList<>();
        if (allGyms.get(true) != null) {
            list.addAll(allGyms.get(true));
        }
        if (allGyms.get(false) != null) {
            list.addAll(allGyms.get(false));
        }
        return Collections.unmodifiableList(list);
    }

    public List<Exercise> getAllExercises() {
        return Collections.unmodifiableList(exercises);
    }


    public void delete(Gym gym) {
        if (gym != null && this.allGyms.get(gym.isFavourite()) != null) {
            this.allGyms.get(gym.isFavourite()).remove(gym);
        }
    }

    public void delete(Exercise exercise) {
        exercises.remove(exercise);
    }

    public void add(Gym gym) {
        if (!this.allGyms.containsKey(gym.isFavourite())) {
            this.allGyms.put(gym.isFavourite(), new HashSet<Gym>());
        }
        this.allGyms.get(gym.isFavourite()).add(gym);
    }

    public void add(Exercise exercise) {
        exercises.add(exercise);
    }

    public List<Gym> getFavourites() {
        if (this.allGyms.containsKey(true)) {
            List<Gym> favourites = new ArrayList<>();

            favourites.addAll(this.allGyms.get(true));
            return favourites;
        }
        return null;
    }
}
