package com.wulf.digitalrecipebook.model.recipe;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Ingredient {

	@Id
	public String name;
	
	@JsonBackReference
	@ManyToMany
	(mappedBy = "ingredients")
	public Set<Recipe> usedIn;
	
	public Ingredient() {
		super();
	}
	
	public Ingredient(String name) {
		super();
		this.setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
