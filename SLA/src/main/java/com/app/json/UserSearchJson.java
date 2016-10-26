package com.app.json;
/**
 * @author Jinesh George
 */
import java.util.ArrayList;



public class UserSearchJson {
    
	private String person_id;
	private ArrayList<String> emails;
	private ArrayList<String> phone_numbers;
	
	public String getPerson_id() {
		return person_id;
	}
	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}
	public ArrayList<String> getEmails() {
		return emails;
	}
	public void setEmails(ArrayList<String> emails) {
		this.emails = emails;
	}
	public ArrayList<String> getPhone_numbers() {
		return phone_numbers;
	}
	public void setPhone_numbers(ArrayList<String> phone_numbers) {
		this.phone_numbers = phone_numbers;
	}
	
   
	
	
	
}
