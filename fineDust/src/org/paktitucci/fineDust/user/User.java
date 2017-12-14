package org.paktitucci.fineDust.user;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private List<String> locationNames;
	
	public User(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.locationNames = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getLocationNames() {
		return locationNames;
	}

	public void setLocationNames(List<String> locationNames) {
		this.locationNames = locationNames;
	}
	
	public boolean addLocationName(String locationName) {
		boolean isLocationNameExist = false;
		
		for(String name : locationNames) {
			if(name.equals(locationName)) {
				isLocationNameExist = true;
				break;
			}
		}
		
		if(!isLocationNameExist) this.locationNames.add(locationName);
		
		return isLocationNameExist;
	}
	
}
