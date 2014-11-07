package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
@SuppressWarnings("serial")
@Entity
public class TaskInfo extends Model{

	@Id
	private long taskID;
	
	@Required(message = "validation.required.emphasis")
	private String title;
	
	private String description;
	
	private String createdBy;
	
	@ManyToOne
	@Email
	@Required(message = "validation.required.emphasis")
	@JoinColumn(name = "emailAssignedTo", referencedColumnName = "email")
	private String emailAssignedTo;
	
	@Required
	private boolean done;
	
	private boolean recurring_status;
	
	
	public boolean isRecurring_status() {
		return recurring_status;
	}

	public void setRecurring_status(boolean recurring_status) {
		this.recurring_status = recurring_status;
	}

	public TaskInfo()
	{
	this.done=false;
	this.points=0;
	}
	
	
	
	 public long getTaskID() {
		return taskID;
	}

	public void setTaskID(long taskID) {
		this.taskID = taskID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmailAssignedTo() {
		return emailAssignedTo;
	}

	public void setEmailAssignedTo(String emailAssignedTo) {
		this.emailAssignedTo = emailAssignedTo;
	}

	public boolean getDone() {
		return done;
	}

	public void setDone(boolean status) {
		this.done = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Required(message = "validation.required.emphasis")
	//@DateTime(pattern = "mm/dd/yyyy hh:mm")
	private String startDate;
	
	@Required(message = "validation.required.emphasis")
	//@DateTime(pattern = "mm/dd/yyyy hh:mm")
	private String endDate;
	
	@Required
	private int points;
}
