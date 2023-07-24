

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

    //






    public static void main(String[] args) {
        //

    }
}
