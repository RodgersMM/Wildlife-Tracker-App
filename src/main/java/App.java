

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


    )




    }


}
