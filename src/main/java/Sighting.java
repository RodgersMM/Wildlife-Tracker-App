import java.sql.Timestamp;
import org.sql2o.*;
import java.util.List;


public class Sighting implements DatabaseManagement {

    private int id;
    private int animal_id;
    private String location;
    private String ranger_name;
    private Timestamp timestamp;

    //Constructor for sighting to implement abstract method save in Database management class

    public Sighting(int animal_id, String location, String ranger_name) {
        if (ranger_name.equals("")) {
            throw new IllegalArgumentException("Please enter Ranger name.");
        }
        this.animal_id = animal_id;
        this.location = location;
        this.ranger_name = ranger_name;

        this.save();
    }


