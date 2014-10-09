package models;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

public class TaskInfo {

	@Id
	private long taskID;
	
	@Required(message = "validation.required.emphasis")
	private String title;
	
	private String description;
	
	@ManyToOne
	@Email
	@Required(message = "validation.required.emphasis")
	@JoinColumn(name = "emailAssignedTo", referencedColumnName = "email")
	private String emailAssignedTo;
	
	@Required
	private boolean done;
	
	
public TaskInfo() {
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Required(message = "validation.required.emphasis")
	@DateTime(pattern = "mm/dd/yyyy hh:mm")
	private Date startDate;
	
	@Required(message = "validation.required.emphasis")
	@DateTime(pattern = "mm/dd/yyyy hh:mm")
	private Date endDate;
	
	@Required
	private int points;
	
	
}
