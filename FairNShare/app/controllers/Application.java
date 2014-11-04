package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Person;
import models.TaskInfo;
import play.*;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.Context;
import play.mvc.Http.Session;
import views.html.*;
import static play.libs.Json.toJson;

public class Application extends Controller {

	public static Result index() {

		return ok(index.render(""));
	}


	public static Result addPerson() {

		Person person=Form.form(Person.class).bindFromRequest().get();
		person.save();
		//return redirect(routes.Application.index());
		return ok(index.render("User Registered"));
	}


	public static Result createTask() {

		TaskInfo newTask=Form.form(TaskInfo.class).bindFromRequest().get();

		Person assignedToEmailFound = (Person)new Model.Finder(String.class,Person.class).byId(newTask.getEmailAssignedTo());

		if(assignedToEmailFound!=null && assignedToEmailFound.getEmail().equals(newTask.getEmailAssignedTo()))
		{
			newTask.save();
			//return ok(toJson(newTask));
		}			

		return ok(views.html.dashboard.render(""));

	}



	public static Result getPointsToComplete()  {
		Person currentUser= (Person) new Model.Finder(String.class,Person.class).byId(session("connectedmail"));
		double earnedPoints = currentUser.getScore();
		double pointsToComplete=0;

		List<Person> otherUsers= new Model.Finder(String.class,Person.class).all(); 

		otherUsers.remove(currentUser);

		double scoreOfOtherUsers=0;
		for(Person user : otherUsers)
			scoreOfOtherUsers += user.getScore();
		if(otherUsers.size()>0)
			pointsToComplete=scoreOfOtherUsers/otherUsers.size()-earnedPoints;

		if(pointsToComplete<0)
			pointsToComplete=0;

		ObjectNode userPoints=Json.newObject();
		userPoints.put("PointsToComplete", pointsToComplete);
		userPoints.put("EarnedPoints", earnedPoints);
		return ok(userPoints);

	}


	public static Result showTasks() {
		List<TaskInfo> tasks = new Model.Finder(String.class,TaskInfo.class).all();
		return ok();

	}

	public static Result showFriends() {
		List<Person> persons = new Model.Finder(String.class,Person.class).all();
		return ok(toJson(persons));
	}


	public static Result checkPerson() {
		Login loginInfo=Form.form(Login.class).bindFromRequest().get();
		Person existingPerson = (Person) new Model.Finder(String.class,Person.class).byId(loginInfo.getEmail());
		if(existingPerson!=null && existingPerson.getPassword().equals(loginInfo.getPassword()))
		{	
			String username=existingPerson.getFname(); 			//Get the First name by using the primary key email
			String usermail=existingPerson.getEmail(); 			//Get the email id of the user & set in the session variable to use for other activities
			session("connected", username);						//Assign it to the session variable
			session("connectedmail", usermail);	
			String user = session("connected");
			if(user != null) ;	

			//return ok("Welcome " + user + usermail);			//Display the username - testing
			return ok(views.html.dashboard.render("Welcome " + user));
		}

		return redirect(routes.Application.index());


	}

	public static Result endSession() 
	{                      
		session().clear();										//Ends user session and redirects to index page
		String user = session("connected");
		return redirect(routes.Application.index());	
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
