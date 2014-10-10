package controllers;

import models.Person;
import play.*;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import views.html.*;
import static play.libs.Json.toJson;

public class Application extends Controller {
	
    public static Result index() {
        return ok(index.render("Testing"));
    }
    
    
    public static Result addPerson() {
    	
    	
    		Person person=Form.form(Person.class).bindFromRequest().get();
    		person.save();
    		return redirect(routes.Application.index());
    	
    	
    }
    
    
    public static Result checkPerson() {
    	Login loginInfo=Form.form(Login.class).bindFromRequest().get();
     	Person existingPerson = (Person) new Model.Finder(String.class,Person.class).byId(loginInfo.getEmail());
    	 if(existingPerson!=null && existingPerson.getPassword().equals(loginInfo.getPassword()))
    	{
    		//return badRequest(views.html.index.render(filledPersonForm));
    		 return ok(toJson(existingPerson));
    	}
    	else
    	{
    		return redirect(routes.Application.index());
    	} 
    	
    }
    
    public static class Login {

        private String email;
        private String password;
        
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

    }
    

}
