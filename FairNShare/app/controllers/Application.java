package controllers;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;  
import java.util.TimerTask; 






























import org.h2.engine.User;

import ch.qos.logback.core.joran.action.ActionUtil.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.java.swing.plaf.windows.WindowsBorders.DashedBorder;

import models.Person;
import models.TaskInfo;
import models.TaskInsertion;
import play.*;
import play.api.data.validation.ValidationError;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.Context;
import play.mvc.Http.Session;
import views.html.*;
import static play.libs.Json.toJson;

@SuppressWarnings("unused")
public class Application extends Controller {

	public static Result index() {

		return ok(index.render(""));
	}

	public static Result loginFail() {

		return ok(index.render("Login Fail"));
	}
	

	public static Result addPerson() {

		Person person=Form.form(Person.class).bindFromRequest().get();
		person.save();
		//return redirect(routes.Application.index());
		return ok(index.render("User Registered"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Result createTask() {

		TaskInfo newTask = Form.form(TaskInfo.class).bindFromRequest().get();
	

		DynamicForm requestData = Form.form().bindFromRequest();
		String recurring_type = requestData.get("recurring_type");
		int recurring_countNumber;
		String recurring_count = requestData.get("taskCount");
	
		
		if(recurring_count.equals("")){
	
			recurring_count="1";
			recurring_countNumber = Integer.parseInt(recurring_count);
	
		}
		else{
			recurring_countNumber = Integer.parseInt(recurring_count);
		
		}
	
		
		Person assignedToEmailFound = (Person) new Model.Finder(String.class,
				Person.class).byId(newTask.getEmailAssignedTo());

		if (assignedToEmailFound != null
				&& assignedToEmailFound.getEmail().equals(
						newTask.getEmailAssignedTo())) {
			String usermail = session("connectedmail"); // Get the user mail from session and set it to the created field in db
			newTask.setCreatedBy(usermail);
			newTask.save();

		}

		
		 //Check if the task is recurring and add it into db based on weekly or monthly basis with number of tasks to be added given by user
		 
		
		if (newTask.isRecurring_status()) {

			if (recurring_type.equals("weekly")) {									//To add weekly Task

				int j = 7;
				int type_recurring = 0;

				for (int i = 1; i < recurring_countNumber; i++) {

					Application.dbinsertREcurringTask(i, j, type_recurring);		//Call dbinsertREcurringTask to insert into db
				}
			}
			if (recurring_type.equals("monthly")) {									//To add weekly Task

				int j = 30;
				int type_recurring = 0;

				for (int i = 1; i < recurring_countNumber; i++) {

					Application.dbinsertREcurringTask(i, j, type_recurring);		//Call dbinsertREcurringTask to insert into db
				}
			}

		}
		if (assignedToEmailFound != null
				&& assignedToEmailFound.getEmail().equals(
						newTask.getEmailAssignedTo())) {
			newTask.save();
		}

		return ok(views.html.dashboard.render(""));

	}
	
	
	public static void dbinsertREcurringTask(int i,int j,int type_recurring){
		 		
		 		TaskInfo newTask=Form.form(TaskInfo.class).bindFromRequest().get();
		 		String usermail = session("connectedmail");					//Get the user mail from session and set it to the created field in db
		 		newTask.setCreatedBy(usermail);
		 		int n=j*i;													//generate value of recurring tasks using the loop to add i number of times in db
		 		String startdate= newTask.getStartDate();					//Get the start and end date from the db
		 		String enddate= newTask.getEndDate();
		 		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	//Formatting from String to Date data type
		 		
		 		String dateInString_StartDate=startdate;
		 		String dateInString_EndDate=enddate;
		 		
		 		
		 		try {
		 			Date date1 = sdf.parse(dateInString_StartDate);
		 			Date date2 = sdf.parse(dateInString_EndDate);
		 			if(type_recurring==0){
		 				Calendar c1 = Calendar.getInstance();
			 			c1.setTime(date1); 									// Now use the retrieved start date.
			 			c1.add(Calendar.DATE, n); 							// Adding n = 7 days if weekly or or n=30*no of times the user has requested to insert
			 			String output = sdf.format(c1.getTime());
			 			newTask.setStartDate(output);
			 			
			 			Calendar c2 = Calendar.getInstance();
			 			c2.setTime(date2); 									// Now use the retrieved end date.
			 			c2.add(Calendar.DATE, n); 							// Adding n=7 days
			 			String output2 = sdf.format(c2.getTime());
			 			newTask.setEndDate(output2);
		 				
		 			}
		 			if(type_recurring==1){
		 				Calendar c1 = Calendar.getInstance();
			 			c1.setTime(date1); 									// Now use the retrieved start date.
			 			c1.add(Calendar.MONTH, i); 							// Adding 1 month to the ith recurring task
			 			String output = sdf.format(c1.getTime());
			 			newTask.setStartDate(output);
			 			
			 			Calendar c2 = Calendar.getInstance();
			 			c2.setTime(date2); 									// Now use the retrieved end date.
			 			c2.add(Calendar.MONTH, i); 							// Adding 1 month to the ith recurring task
			 			String output2 = sdf.format(c2.getTime());
			 			newTask.setEndDate(output2);
		 				
		 			}
	
		 		} catch (ParseException e) {
		 			
		 		e.printStackTrace();
		 		}
		 			
		 		
		 		newTask.save();
 		
		 	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Result showTasks() {
		List<TaskInfo> tasks = new Model.Finder(String.class,TaskInfo.class).all();
		return ok(toJson(tasks));
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Result showFriends() {
		List<Person> persons = new Model.Finder(String.class,Person.class).all();
		return ok(toJson(persons));
	}

	public static Result getPointsToComplete()  {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Person currentUser= (Person) new Model.Finder(String.class,Person.class).byId(session("connectedmail"));
		double earnedPoints = currentUser.getScore();
		double pointsToComplete=0;

		@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	public static Result showIncompleteTasks()	{
		//extract all tasks
		List<TaskInfo> Tasks = new Model.Finder(String.class,TaskInfo.class).all();		//extract incomplete tasks
		List<TaskInfo> incompleteTasks = null;
		for(TaskInfo newTask : Tasks)
		{
			if(newTask.done==false)
			{	
				
				
				//incompleteTasks.add(newTask);
			}
		}
		//while displaying, place a button, edit task, at the end of task name
		//edit task will open the add task type of page, where it gives the user, option to assign to himself
		//TaskInfo task= (TaskInfo) new Model.Finder(String.class,TaskInfo.class).byId(session("connectedmail"));
		//return ok(toJson(incompleteTasks));
		return ok(toJson(incompleteTasks));
	}
	
	@SuppressWarnings("unchecked")
	public static Result checkPerson() {
		Login loginInfo=Form.form(Login.class).bindFromRequest().get();
		@SuppressWarnings("rawtypes")
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
		return ok(index.render("Invalid username or password!"));
		//return redirect(routes.Application.index());
		
		/* New approach to display the invalid login message 
		Application l= new Application();
		l.loginFail();
		*/


	}
	
	public static Result endSession() {
		session().clear();										//Ends user session and redirects to index page
		String user = session("connected");
		return redirect(routes.Application.index());	
	}

	public static Result taskUpdate() {
		
		return ok(toJson("Success"));
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
