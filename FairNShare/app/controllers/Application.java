package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.Date;
import java.util.TimerTask;

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

		if (assignedToEmailFound != null
				&& assignedToEmailFound.getEmail().equals(
						newTask.getEmailAssignedTo())) {
			String usermail = session("connectedmail"); // Get the user mail
														// from session and set
														// it to the created
														// field in db
			newTask.setCreatedBy(usermail);
			
			newTask.save();
			
			/*
			 * 
			 * If Recurring Task, then add n(2 recurring for simplicity) number
			 * of tasks to db
			 */

			if (newTask.isRecurring_status()) {

				for (int i = 1; i < 3; i++) {

					Application.dbinsertREcurringTask(i);

				}

			}

		}
			
		return ok(views.html.dashboard.render(""));

	}
	

	public static void dbinsertREcurringTask(int i){
		
		TaskInfo newTask=Form.form(TaskInfo.class).bindFromRequest().get();
		String usermail = session("connectedmail");				//Get the user mail from session and set it to the created field in db
		newTask.setCreatedBy(usermail);
		int n=7*i;
		
		

		String startdate= newTask.getStartDate();							//Get the start and end date from the db
		String enddate= newTask.getEndDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");			//Formatting from String to Date data type
		
		String dateInString_StartDate=startdate;
		String dateInString_EndDate=enddate;
		
		
		try {
			Date date1 = sdf.parse(dateInString_StartDate);
			Date date2 = sdf.parse(dateInString_EndDate);
			
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date1); 									// Now use the retrieved start date.
			c1.add(Calendar.DATE, n); 							// Adding n=7 days
			String output = sdf.format(c1.getTime());
			newTask.setStartDate(output);
			
			Calendar c2 = Calendar.getInstance();
			c2.setTime(date2); 									// Now use the retrieved end date.
			c2.add(Calendar.DATE, n); 							// Adding n=7 days
			String output2 = sdf.format(c2.getTime());
			newTask.setEndDate(output2);
			
			//System.out.println(output);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		newTask.save();
		//return ok(toJson(newTask));
		
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
		
		return ok(toJson(tasks));

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
