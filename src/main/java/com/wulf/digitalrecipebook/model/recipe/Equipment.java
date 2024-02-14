package com.wulf.digitalrecipebook.model.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Equipment {

	@Id
	public String name; 
	
	public Equipment() {
		super();
	}
	
	public Equipment(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
