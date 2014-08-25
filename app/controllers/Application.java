package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        //return ok("It's ALIVE!!");
        return ok(index.render());
    }
    
    public static Result submit() {
        return ok("We got: " + "nothing");
    }
    
    public static Result paste(String pasteId) {
        return ok("Fetching paste with Id: " + pasteId);
    }
    
    public static Result passPaste(String pasteId, String secKey) {
        return ok("Fetching paste with Id '" + pasteId + "' using key '" +
            secKey + "'");
    }

}
