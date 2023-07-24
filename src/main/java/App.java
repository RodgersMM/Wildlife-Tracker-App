

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
        //Animal Details Post form
        post("/animals", (request, response) ->

    {
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








    )




    }


}
