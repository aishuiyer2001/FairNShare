package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Person extends Model {

	@Id
	@Email
	private String email;

	@Required(message = "validation.required.emphasis")
	private String fname;

	@Required(message = "validation.required.emphasis")
	private String lname;


	@Required
	private int score;
	
	@DateTime(pattern = "mm/dd/yyyy")
	private String dob;
	

	private String ph_no;
	private Character gender;

	@Required(message = "validation.required.emphasis")
	private String password;

	public Person() {
		this.score=0;
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getPh_no() {
		return ph_no;
	}

	public void setPh_no(String ph_no) {
		this.ph_no = ph_no;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}