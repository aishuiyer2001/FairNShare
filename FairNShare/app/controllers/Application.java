package controllers;
import views.html.*;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.JButton;

import org.h2.engine.User;

import ch.qos.logback.core.joran.action.ActionUtil.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.java.swing.plaf.windows.WindowsBorders.DashedBorder;

import models.Person;
import models.TaskInfo;
import play.*;
import play.api.data.validation.ValidationError;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.Context;
import play.mvc.Http.Session;
import static play.libs.Json.toJson;

@SuppressWarnings("unused")
	public class Application extends Controller {
	
	public static Result index() {

		return ok(index.render(""));
	}

	
	public static Result loginFail() {
		 
		 		return ok(index.render("Login Fail"));
		 	}

	public static Result redirectDashBoardURL() {
		String user = session("connected");
		
		if(user==null)
			return redirect(routes.Application.index());
		return ok(views.html.dashboard.render("Welcome " + user));
	}
	
	public static Result calendar()
	{
		//return ok();
		return ok(calendar.render("CALENDAR"));
	}
	
	public static Result changeCalendar() throws ParseException{
		DynamicForm requestData = Form.form().bindFromRequest(); 
		String date = requestData.get("calendar");
		session("fairdate", date);
		String datenew= session("fairdate");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date newdate = dateFormat.parse(datenew); 
		return ok(views.html.dashboard.render("WELCOME"));
		//redirectDashBoardURL();
	}
	
	public static Result addPerson() {

		Person person=Form.form(Person.class).bindFromRequest().get();
		person.save();
		return ok(index.render("User Registered"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Result createTask() throws Exception {

		TaskInfo newTask = Form.form(TaskInfo.class).bindFromRequest().get();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DynamicForm requestData = Form.form().bindFromRequest();
		String recurring_type = requestData.get("recurring_type");
		String newpoints=requestData.get("newPoints");
		double points = Double.parseDouble(newpoints);
		int recurring_countNumber;
		int days=0;
		String recurring_count = requestData.get("taskCount");
		String ending_in = requestData.get("enddays");
		if(ending_in.equals("")){
		days = 0;
		}
		else{
			days = Integer.parseInt(ending_in);
		}

		Date start_date = dateFormat.parse(newTask.getStartDate());  //get the starting date
		Calendar c = Calendar.getInstance();
		c.setTime(start_date);
		String end=null;
		if(newTask.getEndDate()==null && days>0)		//if the number of days are specified
		{
			c.add(Calendar.DATE, days);		//add the number of days to the start date of the task
			end = dateFormat.format(c.getTime());
			newTask.setEndDate(end);
			//set the added date as the end date for the task
			newTask.setOldPoints(points);
			newTask.save();
			
		}  
		else
		{
			end=newTask.getEndDate();
			newTask.setOldPoints(points);
			newTask.save();
		}

		if(recurring_count.equals("")){

			recurring_count="1";
			recurring_countNumber = Integer.parseInt(recurring_count);

		}
		else{
			recurring_countNumber = Integer.parseInt(recurring_count);

		}


		Person assignedToEmailFound = (Person) new Model.Finder(String.class,
				Person.class).byId(newTask.getEmailAssignedTo());

		String usermail = session("connectedmail");					//Get the user mail from session and set it to the created field in db
 		newTask.setCreatedBy(usermail);
 		newTask.setOldPoints(points);
 		newTask.save();


		 //Check if the task is recurring and add it into db based on weekly or monthly basis with number of tasks to be added given by user


		if (newTask.isRecurring_status()) {

			if (recurring_type.equals("weekly")) {									//To add weekly Task

				int j = 7;
				int type_recurring = 0;

				for (int i = 1; i < recurring_countNumber; i++) {

					Application.dbinsertREcurringTask(i, j, type_recurring, end,points);		//Call dbinsertREcurringTask to insert into db
				}
			}
			if (recurring_type.equals("monthly")) {									//To add weekly Task

				int j = 30;
				int type_recurring = 0;

				for (int i = 1; i < recurring_countNumber; i++) {

					Application.dbinsertREcurringTask(i, j, type_recurring,end,points);		//Call dbinsertREcurringTask to insert into db
				}
			}

		}
		if (assignedToEmailFound != null
				&& assignedToEmailFound.getEmail().equals(
						newTask.getEmailAssignedTo())) {
			newTask.setOldPoints(points);
			newTask.save();
		}



		return ok(views.html.dashboard.render(""));

	}


	public static void dbinsertREcurringTask(int i,int j,int type_recurring, String end, double points){

		 		TaskInfo newTask=Form.form(TaskInfo.class).bindFromRequest().get();
		 		String usermail = session("connectedmail");					//Get the user mail from session and set it to the created field in db
		 		newTask.setCreatedBy(usermail);
		 		int n=j*i;													//generate value of recurring tasks using the loop to add i number of times in db
		 		String startdate= newTask.getStartDate();					//Get the start and end date from the db
		 		String enddate= end;
		 		System.out.println(enddate);
		 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	//Formatting from String to Date data type

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
			 			newTask.setOldPoints(points);

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
			 			newTask.setOldPoints(points);

		 			}

		 		} catch (ParseException e) {

		 		e.printStackTrace();
		 		}

		 		newTask.setOldPoints(points);
		 		newTask.save();
 		
		 	}


	



	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Result showTasks() {												//method to show all tasks in the system
		List<TaskInfo> tasks = new Model.Finder(String.class,TaskInfo.class).all(); //extracting all tasks from the database
		return ok(toJson(tasks));  													//passing the tasks as a json object
	}
	
	

	
	public static Result showMyRecurringTasks(){
		List<TaskInfo> Tasks = new Model.Finder(String.class,TaskInfo.class).all();  		//list with all the tasks
		List<TaskInfo> myRecurringTasks = new ArrayList<TaskInfo>();  					 //empty list taken as array for purpose
		for(TaskInfo eachTask : Tasks)  																//for each task in the list of all tasks,
		if(eachTask.isRecurring_status() &&  eachTask.getEmailAssignedTo().equalsIgnoreCase(session("connectedmail")))			//if the status of the task is not done,
			myRecurringTasks.add(eachTask);											    	//that task is added to the incompleteTasks list
		return ok(toJson(myRecurringTasks));										//the incompleteTasks list with all the tasks in a list is sent as Json Object

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	
	
	
	
	public static Result showMyTasks(){  													//method to show only user tasks in the system, i.e, only tasks assigned to the particular user
		List<TaskInfo> tasks = new Model.Finder(String.class,TaskInfo.class).all();			//extracting all tasks from the ddatabase into a list
		Person currentUser= (Person) new Model.Finder(String.class,Person.class).byId(session("connectedmail"));
		List<TaskInfo> myTasks = new ArrayList<TaskInfo>();									//creating an empty list of the type object TaskInfo
		String usermail = session("connectedmail");											//getting the current session email of the current user
		for(TaskInfo eachTask:tasks)														//for each task in the list of tasks
		{	
			if(eachTask.getEmailAssignedTo().equalsIgnoreCase(currentUser.getEmail()))		//the email to which the task is assigned is checked with the current session email
				myTasks.add(eachTask);														//if they are equal, the task is added to the mist myTasks
		}
		return ok(toJson(myTasks));															//list myTasks is sent as a json object
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Result showMyIncompleteTasks(){  													//method to show only user tasks in the system, i.e, only tasks assigned to the particular user
		List<TaskInfo> tasks = new Model.Finder(String.class,TaskInfo.class).all();			//extracting all tasks from the ddatabase into a list
		Person currentUser= (Person) new Model.Finder(String.class,Person.class).byId(session("connectedmail"));
		List<TaskInfo> myTasks = new ArrayList<TaskInfo>();									//creating an empty list of the type object TaskInfo
		String usermail = session("connectedmail");											//getting the current session email of the current user
		for(TaskInfo eachTask:tasks)														//for each task in the list of tasks
		{	
			if(eachTask.getEmailAssignedTo().equalsIgnoreCase(currentUser.getEmail()) && eachTask.getDone()==false)		//the email to which the task is assigned is checked with the current session email
				myTasks.add(eachTask);														//if they are equal, the task is added to the mist myTasks
		}
		return ok(toJson(myTasks));															//list myTasks is sent as a json object
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	public static Result taskUpdate(String taskID) {										//method to update task which is incomplete, to assign it to the user himself
		TaskInfo existingTask = (TaskInfo) new Model.Finder(String.class,TaskInfo.class).byId(Integer.parseInt(taskID));
		TaskInfo allTasks = (TaskInfo) new Model.Finder(String.class,TaskInfo.class).byId(Integer.parseInt(taskID));
		String usermail = session("connectedmail"); 										//getting the current email session of the user
		double points=existingTask.getnewPoints();
		
		List<TaskInfo> tasks = new Model.Finder(String.class,TaskInfo.class).all();			//get all the tasks in the table TaskInfo
		int count=0;																		//Check if the task is not assigned to the user & check if the flag assigned to false to get the
																							//get the count of tasks that has to be assigned to someone
		
		for(TaskInfo t: tasks)																
		{  
			if(t.getAssigned()==false)
			{
				if(usermail.contentEquals(t.getEmailAssignedTo()))
				{
				}
				else
				{
					count++;
				}
			}
		}
																					//if there is only 1 task as incomplete and to be taken, then don't alter the points
		if(count<=1)
		{
			existingTask.setEmailAssignedTo(usermail);								//changing the email task assigned to, to the current user
			existingTask.setOldPoints(points);
			existingTask.save();													//Save the only task to to the user who selects without altering the points as it is the last task
			return ok(views.html.dashboard.render(""));
		}
	
		existingTask.setAssigned(true);												//Set the 'assigned' flag to true to check the task and alter the points (reduce for selected task) 
		existingTask.save();
		double sumOfAllUnassignedTasks = 0;
		int noOfTasks= tasks.size();
		
		for(TaskInfo t: tasks)														//calculate the sum of all UnAssigned tasks for the formula
		{   
			if(t.getAssigned())
			{
			}
			else
			{
			sumOfAllUnassignedTasks = sumOfAllUnassignedTasks + t.getnewPoints();
			}
		}
		
		existingTask.setEmailAssignedTo(usermail);									//changing the email task assigned to, to the current user
		double chosenTaskPoints = existingTask.getnewPoints();
		existingTask.setOldPoints(points);											//copy the new points to old points table to assign the selected points for the user & not the altered
		double sumOfUnchosenTasks = sumOfAllUnassignedTasks - chosenTaskPoints;
		double totalDelta=(double) (chosenTaskPoints * 0.2);
		double individualDeltaForTaskX =totalDelta/(sumOfUnchosenTasks);
		double newPointValueofChosenTask = chosenTaskPoints-totalDelta;
		existingTask.setNewPoints(newPointValueofChosenTask);						//set the new points of the current task by reducing it by 20% (changeable)
		existingTask.save();	
		for(TaskInfo t: tasks)
		{	
			long idOfList= t.getTaskID();
			int idOfList1 = (int)idOfList;
			long idOfselectedTask=Integer.parseInt(taskID);
			if(idOfList1 != idOfselectedTask)										//to alter points for the unselected incomplete tasks
			{
				TaskInfo cTask= (TaskInfo) new Model.Finder(String.class,TaskInfo.class).byId(idOfList1);
				if(cTask.getAssigned())
				{
				}
				else
				{
					double x=cTask.getnewPoints();
					double newPointValueofUnchosenTask=(x/sumOfUnchosenTasks)*totalDelta;
					double y=newPointValueofUnchosenTask+x;							//Alter the points based on the auto adjust & increase by the percentage based on the formula 
					cTask.setNewPoints(y);
					cTask.save();	
				}
			}
		}
		return ok(views.html.dashboard.render(""));	
}

	
	/*
	 * Gives the points needed by a user to be doing fair share of work
	 * 
	 * Implementation :
	 * Current score of the user is pulled from the database
	 * Current scores of other users are pulled from the database and its average is computed
	 * If the given user's current score is less than the average of points earned by other users,
	 * then the difference in points is shown as the points he needs to achieve in order to be contributing fairly.
	 * 
	 * If the difference is negative (i.e. he has earned more points than the average points earned by others,
	 * then the difference is rounded to 0 and displayed (indicating that he has done fair share of work)
	 */ 
	
	public static Result getPointsToComplete()  {
		Person currentUser= (Person) new Model.Finder(String.class,Person.class).byId(session("connectedmail"));	// get current user from session
		double earnedPoints = currentUser.getScore();					//get points earned by him until now
		double pointsToComplete=0;										

		// get a list of other roommates by getting all the roommates and removing the currently logged in user from the list
		List<Person> otherUsers= new Model.Finder(String.class,Person.class).all(); 	
		otherUsers.remove(currentUser);				

		// get average score of other users
		double scoreOfOtherUsers=0;
		for(Person user : otherUsers)
			scoreOfOtherUsers += user.getScore();
		
		/* Here, Exception handling is done.
		* Case: if currently logged in user is the only user in the database & none of his roommates are registered,
		* then otherUsers will be 0. So, when finding average using the formula : (sum of scores)/No.Of.Users,
		* the denominator would throw a divideByZero Exception or NumberFormatException.
		* But the case has been handled by performing division only if there are other users in the database. ( as shown below)
		*/
		if(otherUsers.size()>0)    
			pointsToComplete=scoreOfOtherUsers/otherUsers.size()-earnedPoints;

		/* If current user's earned points are above the average of other's points, 
		*then the negative difference is rounded to zero. 
		*/
		if(pointsToComplete<0)
			pointsToComplete=0;

		// User points are packaged in a JSON object and sent to the UI
		ObjectNode userPoints=Json.newObject();
		userPoints.put("PointsToComplete", pointsToComplete);
		userPoints.put("EarnedPoints", earnedPoints);
		return ok(userPoints);

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	public static Result showFriends() {											//method to show friends
		List<Person> persons = new Model.Finder(String.class,Person.class).all(); 	//extracting all the persons in the database
		return ok(toJson(persons));													//passing persons as Json object for displaying
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	
	public static Result personUpdate(String taskID){											//method to update the score of a person, takes task as the parameter
		 
		//TaskIDRetrieval currentTask = Form.form(TaskIDRetrieval.class).bindFromRequest().get();		
		TaskInfo existingTask = (TaskInfo) new Model.Finder(String.class,TaskInfo.class).byId(Integer.parseInt(taskID));
		System.out.println("title : "+existingTask.getTitle()+ existingTask.getDone());
		existingTask.setDone(true);
		System.out.println("title : "+existingTask.getTitle()+ existingTask.getDone());
		existingTask.save();
		//for that particular task, we change in the database that the task is done
		//we get the user email id who is assigned to the task
		Person currentUser= (Person) new Model.Finder(String.class,Person.class).byId(session("connectedmail"));		//we extract the user based on the email id obtained above
		double currentscore = currentUser.getScore();						//the already present score of the user the taken into currentscore by using getter
		currentUser.setScore(existingTask.getOldPoints()+currentscore);	//now, the score of user is set to current score+ points of the task which he has done
		currentUser.save();
		return ok(views.html.dashboard.render(""));
		}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	
	public static Result reusetaskUpdate(String taskID){											

		//Method incomplete and left for further implementation as it was decided to take up by the other person earlier
		
		
		//TaskIDRetrieval currentTask = Form.form(TaskIDRetrieval.class).bindFromRequest().get();		
		TaskInfo existingTask = (TaskInfo) new Model.Finder(String.class,TaskInfo.class).byId(Integer.parseInt(taskID));
		System.out.println("title : "+existingTask.getTitle()+ existingTask.getDone());
		//existingTask.setDone(true);
		System.out.println("title : "+existingTask.getTitle()+ existingTask.getDone());
		
		return ok(toJson("existingTask"));	
		
		/*
		existingTask.save();
		//for that particular task, we change in the database that the task is done
		//we get the user email id who is assigned to the task
		Person currentUser= (Person) new Model.Finder(String.class,Person.class).byId(session("connectedmail"));		//we extract the user based on the email id obtained above
		int currentscore = currentUser.getScore();						//the already present score of the user the taken into currentscore by using getter
		currentUser.setScore(existingTask.getnewPoints()+currentscore);	//now, the score of user is set to current score+ points of the task which he has done
		currentUser.save();
		return ok(views.html.dashboard.render(""));
		*/
		}
	

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	
	public static Result showIncompleteTasks()	{    									//to show incomplete tasks out of the whole list of tasks
		List<TaskInfo> Tasks = new Model.Finder(String.class,TaskInfo.class).all();  		//list with all the tasks
		List<TaskInfo> incompleteTasks = new ArrayList<TaskInfo>();  					 //empty list taken as array for purpose
		for(TaskInfo eachTask : Tasks)  																//for each task in the list of all tasks,
		if(eachTask.getDone()==false &&  !eachTask.getEmailAssignedTo().equalsIgnoreCase(session("connectedmail")))			//if the status of the task is not done,
			incompleteTasks.add(eachTask);											    	//that task is added to the incompleteTasks list
		return ok(toJson(incompleteTasks));										//the incompleteTasks list with all the tasks in a list is sent as Json Object

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	
	public static Result showReUsableTasks()	{    									//to show incomplete tasks out of the whole list of tasks
		List<TaskInfo> Tasks = new Model.Finder(String.class,TaskInfo.class).all();  		//list with all the tasks
		List<TaskInfo> reUsableTasks = new ArrayList<TaskInfo>();  					 //empty list taken as array for purpose
		for(TaskInfo eachTask : Tasks)  																//for each task in the list of all tasks,
		//if(!eachTask.getDone() &&  !eachTask.getEmailAssignedTo().equalsIgnoreCase(session("connectedmail")))			//if the status of the task is not done,
		reUsableTasks.add(eachTask);											    	//that task is added to the incompleteTasks list
		return ok(toJson(reUsableTasks));										//the incompleteTasks list with all the tasks in a list is sent as Json Object
		//return ok(toJson("Tasks"));										//the incompleteTasks list with all the tasks in a list is sent as Json Object

	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })

	public static Result showAllOverdueTasks() throws ParseException	{
		List<TaskInfo> Tasks = new Model.Finder(String.class,TaskInfo.class).all();			//taking list of all existing tasks
		List<TaskInfo> overdueTasks = new ArrayList<TaskInfo>();		//a new list to add each overdue task to
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		//specifying the date format to be followed
		String date1=session("fairdate");
		Date date_change = dateFormat.parse(date1);
		//a new Date object gives us the current system time
		String enddate_string;	//string to take end date in
		for(TaskInfo eachTask : Tasks){			//for each task in the list of tasks
			enddate_string = eachTask.getEndDate();	//getting the end date of the task
			Date date2 = dateFormat.parse(enddate_string);		//converting the end date string into required date format
			if(date_change.after(date2) && eachTask.getDone()==false)			//if currrent date is after the end date of tasks
				overdueTasks.add(eachTask);		//add that task to list of overdue tasks
		}
		return ok(toJson(overdueTasks));		//return overdue tasks */
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Result showMyOverdueTasks() throws ParseException	{
		List<TaskInfo> Tasks = new Model.Finder(String.class,TaskInfo.class).all();			//taking list of all existing tasks
		List<TaskInfo> overdueTasks = new ArrayList<TaskInfo>();		//a new list to add each overdue task to
		Person currentUser= (Person) new Model.Finder(String.class,Person.class).byId(session("connectedmail"));
		String usermail = session("connectedmail");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		//specifying the date format to be followed
		String date1=session("fairdate");
		Date date_change = dateFormat.parse(date1);
		//a new Date object gives us the current system time
		String enddate_string;	//string to take end date in
		for(TaskInfo eachTask : Tasks){			//for each task in the list of tasks
			enddate_string = eachTask.getEndDate();		//getting the end date of the task
			Date date2 = dateFormat.parse(enddate_string);		//converting the end date string into required date format
			if(date_change.after(date2) && eachTask.getEmailAssignedTo().equalsIgnoreCase(currentUser.getEmail())  && eachTask.getDone()==false)			//if currrent date is after the end date of tasks
				overdueTasks.add(eachTask);		//add that task to list of overdue tasks
				//the email to which the task is assigned is checked with the current session email
				//if they are equal, the task is added to the list of the user's overdue tasks
		}
		return ok(toJson(overdueTasks));		//return overdue tasks
	}
	
	@SuppressWarnings("unchecked")
	
			
	public static Result checkPerson() {																//check if the person exists in the database or not
		Login loginInfo=Form.form(Login.class).bindFromRequest().get();		
		@SuppressWarnings("rawtypes")
		Person existingPerson = (Person) new Model.Finder(String.class,Person.class).byId(loginInfo.getEmail());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateObj = new Date();
		String date = (String) df.format(dateObj);
		if(existingPerson!=null && existingPerson.getPassword().equals(loginInfo.getPassword()))		//if there exists a person, and the password matches,
		{	

			String username=existingPerson.getFname(); 			//Get the First name by using the primary key email
			String usermail=existingPerson.getEmail(); 			//Get the email id of the user & set in the session variable to use for other activities
			session("connected", username);						//Assign it to the session variable
			session("connectedmail", usermail);
			session("fairdate", date);
			String user = session("connected");
			return ok(views.html.dashboard.render("Welcome " + user));
		}

		return ok(index.render("Invalid username or password!"));

	}

	public static Result endSession() 
	{                      
		session().clear();										//Ends user session and redirects to index page
		String user = session("connected");
		System.out.println("user "+user);
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
	
	public static class TaskIDRetrieval{
		private String taskID;
		public String getTaskID()
		{
			return taskID;
		}
		public void setTaskID(String taskID) {
			this.taskID = taskID;
		}
	
}}
