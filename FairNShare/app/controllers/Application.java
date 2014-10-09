package controllers;

<<<<<<< HEAD
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
public class Application extends Controller {
	public static Result index() {
        return ok(index.render("Manasa"));
    }
           
    }


=======
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
>>>>>>> 565084110e3ade1909c471a66463d552f9b82294
