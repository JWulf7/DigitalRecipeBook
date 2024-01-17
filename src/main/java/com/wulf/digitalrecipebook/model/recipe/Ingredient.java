package com.wulf.digitalrecipebook.model.recipe;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
@Embeddable
public class Ingredient implements Serializable{

	@Id
	public String ingredientName;
	
//	@JsonBackReference(value = "recipe-ingredients")
//	@ManyToMany (mappedBy = "ingredients") 
//	public Set<Recipe> usedIn;
	 
	
	public Ingredient() {
		super();
	}
	
	public Ingredient(String name) {
		super();
		this.setName(name);
	}
	
	public void setName(String name) {
		this.ingredientName = name;
	}
	
	public String getName() {
		return this.ingredientName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ingredientName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		return Objects.equals(ingredientName, other.ingredientName);
	}

//	@Override
//	public String toString() {
//		return "Ingredient [ingredientName=" + ingredientName + "]";
//	}
	
	@Override
	public String toString() {
		return ingredientName;
	}
	
	
	
}
