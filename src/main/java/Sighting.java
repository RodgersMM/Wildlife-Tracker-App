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

    //Get Methods

    public int getId(){
        return id;
    }

    public int getAnimalId(){
        return animal_id;
    }

    public String getLocation(){
        return location;
    }

    public String getRangerName(){
        return ranger_name;
    }

    public String getTimeSeen(){
        return String.format("%1$TD %1$TR", timestamp);
    }


    //set methods for Sightings
    public void setLocation(String location) {
        this.location = location;
    }

    public void setRangerName(String rangerName) {
        this.ranger_name = rangerName;
    }

    // Override save and implement method save() from dB Management
    @Override
    public void save() {
        String sql = "INSERT INTO sightings (animal_id, location, ranger_name, timestamp) VALUES (:animal_id, :location, :ranger_name, now());";
        System.out.println("INSERT INTO sightings (animal_id, location, ranger_name, timestamp) VALUES (:animal_id, :location, :ranger_name, now());");
        try (Connection con = DB.sql2o.open()) {
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("animal_id", this.animal_id)
                    .addParameter("location", this.location)
                    .addParameter("ranger_name", this.ranger_name)
                    .executeUpdate()
                    .getKey();
        }
    }






