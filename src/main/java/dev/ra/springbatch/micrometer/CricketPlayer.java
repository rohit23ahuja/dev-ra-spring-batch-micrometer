package dev.ra.springbatch.micrometer;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CricketPlayer implements Serializable {

	private String id;
	private String lastName;
	private String firstName;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CricketPlayer [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", type=" + type
				+ "]";
	}
	

}
