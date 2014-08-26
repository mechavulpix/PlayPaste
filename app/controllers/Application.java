package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

import java.util.Map;

public class Application extends Controller {

    public static Result index() {
        response().setContentType("text/html");
        return ok(index.render());
    }
    
    public static Result error() {
        response().setContentType("text/html");
        return notFound(error.render());
    }
    
    public static Result submit() {
        Result pageContent = null;
        
        // Grab our POST data
        Map<String, String[]> postData = request().body().asFormUrlEncoded();
        
        // grab our secure box and if it's checked, flag that this paste isSecure
        boolean isSecure = (postData.containsKey("secure"));
        
        // Create a new Paste from it
        Paste submittedPaste = new Paste(postData.get("paste")[0], isSecure);
        
        // If this was secure, redirect to the secure paste
        if ( isSecure ) {
            pageContent = redirect(controllers.routes.Application.showPaste(
                submittedPaste.id, submittedPaste.key));
        }
        
        // Otherwise, redirect to the normal paste
        else {
            pageContent = redirect(controllers.routes.Application.paste(
                submittedPaste.id));
        }
        
        return pageContent;
    }
    
    public static Result paste(String pasteId) {
        // Pass these off to the showPaste route, but with no secure key
        return showPaste(pasteId, null);
    }
    
    public static Result showPaste(String pasteId, String secKey) {
        Result pageContent = null;
        
        // Grab the paste we were given
        Paste aPaste = Paste.getPaste(pasteId, secKey);
        
        if ( aPaste == null )
            {pageContent = redirect(controllers.routes.Application.index());}
        else
            {pageContent = ok(paste.render(aPaste.pasteData));}
        
        // Return the content we ended up with
        response().setContentType("text/html");
        return pageContent;
    }

}
