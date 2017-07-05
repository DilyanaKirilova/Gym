package model;

import com.example.dkirilova.gym.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class GymManager {
    private static final GymManager ourInstance = new GymManager();

    public static GymManager getInstance() {
        return ourInstance;
    }

    private ArrayList<Gym> allGyms;

    private GymManager() {
        this.allGyms = new ArrayList<Gym>();

        ArrayList<Availability> arr = new ArrayList<>();
        arr.add(new Availability(1, 25, "dayname2"));
        arr.add(new Availability(1, 25, "dayname2"));
        arr.add(new Availability(1, 25, "dayname3"));

        ArrayList<Exercise> arr1 = new ArrayList<>();
        arr1.add(new Exercise(5, 4, "name1", "instr", "descr"));

        Gym gym0 = new Gym(R.drawable.gym, 5, 4, 1, 1, "Gym1", "Descr1", "Address1", new Contact("Addr1", "phone", "email1", "person1"), arr, arr1);
        Gym gym1 = new Gym(R.drawable.gym, 5, 4, 1, 1, "Gym2", "Descr1", "Address1", new Contact("Addr1", "phone", "email1", "person1"), arr, arr1);
        Gym gym2 = new Gym(R.drawable.gym, 5, 4, 1, 1, "Gym3", "Descr1", "Address1", new Contact("Addr1", "phone", "email1", "person1"), arr, arr1);
        Gym gym3 = new Gym(R.drawable.gym, 5, 4, 1, 1, "Gym4", "Descr1", "Address1", new Contact("Addr1", "phone", "email1", "person1"), arr, arr1);

        allGyms.add(gym0);
        allGyms.add(gym1);
        allGyms.add(gym2);
        allGyms.add(gym3);
    }

    public List<Gym> getAllGyms() {
        return Collections.unmodifiableList(allGyms);
    }
}
