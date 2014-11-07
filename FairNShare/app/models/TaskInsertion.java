package models;
import java.util.Timer;  
import java.util.TimerTask;  

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;



public class TaskInsertion extends TimerTask	 {

	@Override
	public void run() {
		System.out.println("This is being printed every 1 sec."); 
				
	}

	
	 public static void main(String[] args) {  
	        // Create an instance of our task/job for execution  
	        TaskInsertion task = new TaskInsertion();  
	          
	        // We use a class java.util.Timer to   
	        // schedule our task/job for execution  
	        Timer timer = new Timer();  
	          
	        // Let's schedule our task/job to be executed every 1 second  
	        timer.scheduleAtFixedRate(task, 0, 10000);  
	        // First parameter: task - the job logic we   
	        // created in run() method above.  
	        // Second parameter: 0 - means that the task is   
	        // executed in 0 millisecond after the program runs.  
	        // Third parameter: 1000 - means that the task is   
	        // repeated every 1000 milliseconds  
	          
	    }  
	  
	}  
	/*System.out.println("Recurring task");
	@message, style = "Java";
	/*public static void recurrinTask(){
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                // your code here
		            	//return ok(toJson("Recurring Task"));
		            	System.out.println("Recurring task");
		            }
		        }, 
		        5000 
		);
		//return null;
		
	}
}
*/