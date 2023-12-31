import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {

    public static void  main (String[] args){
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);

        //retrieve index page

        get ("/", (request, response) -> {
            Map<String, Object> model = new  HashMap<String, Object>();
            model.put("template", "templates/animalForm.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        }

             //CREATE OBJECTS
    // Retrieve New Animal Form
             get("/animals/new", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/animalForm.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


        //Animal Details Post form
        post("/animals", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        String name = request.queryParams("name");
        try {
            Animal animal = new Animal(name);
            animal.save();
        } catch (IllegalArgumentException exception) {
            System.out.println("Please enter an animal name.");
        }
        response.redirect("/animals");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Retrieve all Animals
    get("/animals", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("animals", Animal.all());
        model.put("template", "templates/animals.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Retrieve Endangered Animal Form
    get("/endangered/new", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/endangeredAnimalForm.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Form to Post Endangered Animal Details
    post("/endangered/new", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        String name = request.queryParams("name");
        String health = request.queryParams("health");
        String age = request.queryParams("age");
        try {
            Endangered endangered = new Endangered(name, health, age);
            endangered.save();
        } catch (IllegalArgumentException exception) {
            System.out.println("Please enter all input fields.");
        }
        response.redirect("/animals");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Form to retrieving sighting
    get("/sightings/new", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("animals", Animal.all());
        model.put("template", "templates/SightingForm.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Form to post sighting form
    post("/sightings", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        int animal_id = Integer.parseInt(request.queryParams("animal"));
        String location = request.queryParams("location");
        String ranger_name = request.queryParams("rangerName");

        try {
            Sighting sighting = new Sighting(animal_id, location, ranger_name);
        } catch (IllegalArgumentException exception) {
            System.out.println("Please enter Ranger name.");
        }
        response.redirect("/sightings");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Retrieve all sightings
    get("/sightings", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("sightings", Sighting.all());
        model.put("Animal", Animal.class);
        model.put("template", "templates/sightings.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Retrieve animals by id
    get("/animals/:id", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("animal", Animal.find(Integer.parseInt(request.params(":id"))));
        model.put("endangered", Endangered.find(Integer.parseInt(request.params(":id"))));
        model.put("Sighting", Sighting.class);
        model.put("template", "templates/animal.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Update Objects
    //Form to edit retrieving animals
    get("/animals/:id/edit", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("animal", Animal.find(Integer.parseInt(request.params(":id"))));
        model.put("endangered", Endangered.find(Integer.parseInt(request.params(":id"))));
        model.put("template", "templates/animalEditForm.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Form to edit post animal details
    post("/animals/:id/edit", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params(":id"));
        String name = request.queryParams("name");
        String health = request.queryParams("health");
        String age = request.queryParams("age");
        Animal animal = Animal.find(id);
        animal.setName(name);
        animal.update();
        if(animal.getType().equals("Endangered")) {
            Endangered endangered = Endangered.find(id);
            endangered.setHealth(health);
            endangered.setAge(age);
            endangered.update();
        }
        response.redirect("/animals/" + id);
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Form to edit sightings retrieving
    get("/sightings/:id/edit", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("sighting", Sighting.find(Integer.parseInt(request.params(":id"))));
        model.put("Animal", Animal.class);
        model.put("template", "templates/SightingForm.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Update Sighting instance
    post("/sightings/:id/edit", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params(":id"));
        String location = request.queryParams("location");
        String rangerName = request.queryParams("rangerName");
        Sighting sighting = Sighting.find(id);
        sighting.setLocation(location);
        sighting.setRangerName(rangerName);
        sighting.update();
        response.redirect("/sightings");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //deleting a sighting
    get("/sightings/:id/delete", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        Sighting.find(Integer.parseInt(request.params(":id"))).delete();
        response.redirect("/sightings");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Delete Animal Object

    get("/animals/:id/delete", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        Animal.find(Integer.parseInt(request.params(":id"))).delete();
        response.redirect("/animals");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
}
}
