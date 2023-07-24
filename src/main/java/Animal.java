

public class Animal {
    public int id;
    public String name;
    public String type;


    //Create non endangered animal constant
    public static final String ANIMAL_TYPE = "Non-endangered";

    public Animal(String name){
        if (name.equals("")){
            //throw exception if no name is entered
            throw new IllegalArgumentException("Please enter an animal name.");
        }
        this.name = name;

        type = ANIMAL_TYPE;
    }

    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    //Set Animal Name

    public void setName(String name) {
        this.name = name;
    }

    //Override Animal
    @Override
    public boolean equals(Object otherAnimal) {
        if (otherAnimal instanceof Animal) {
            Animal newAnimal = (Animal) otherAnimal;
            return (this.getName().equals(newAnimal.getName()));
        }

        return false;
    }

    //Save new name by type and name
    public void save() {
        String sql = "INSERT INTO animals (name, type) VALUES (:name, :type)";
        try(Connection con = DB.sql2o.open()) {
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", name)
                    .addParameter("type", type)
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getKey();
        }
    }

    //Delete animal and sighting by Id and error handling
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE from animals WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
            String sql2 = "DELETE from sightings WHERE animal_id = :id";
            con.createQuery(sql2)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }

    //List all animals from animal table
    public static List<Animal> all() {
        String sql = "SELECT * FROM animals;";

        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animal.class);
        }
    }
      //finding animalusing id && error handling
      public static Animal find(int id) {
          String sql = "SELECT * FROM animals WHERE id = :id;";

          try (Connection con = DB.sql2o.open()) {
              return con.createQuery(sql)
                      .addParameter("id", id)
                      .throwOnMappingFailure(false)
                      .executeAndFetchFirst(Animal.class);
          }
      }







    public static void main(String[] args) {
        //

    }
}
