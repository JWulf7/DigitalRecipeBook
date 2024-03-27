package com.wulf.digitalrecipebook.model.recipe;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class PlannedMeal implements Serializable{
//public class PlannedMeal {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String name = "";
	
	@Enumerated(EnumType.STRING)
	private Meal meal = Meal.ANY;
	
	@ManyToMany
	private List<RecipeName> menu = new ArrayList<>();
	
//	@ManyToMany
	private List<LocalDate> allDatesCooked = new ArrayList<>();
	
	private List<String> notes = new ArrayList<>();

	
	public PlannedMeal() {
		super();
	}
	
	
	public PlannedMeal(int id) {
		super();
		this.id = id;
	}



	public PlannedMeal(int id, String name, Meal meal, List<RecipeName> menu, List<LocalDate> allDatesCooked,
			List<String> notes) {
		super();
		this.id = id;
		this.name = name;
		this.meal = meal;
		this.menu = menu;
		this.allDatesCooked = allDatesCooked;
		this.notes = notes;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Meal getMeal() {
		return meal;
	}


	public void setMeal(Meal meal) {
		this.meal = meal;
	}


	public List<RecipeName> getMenu() {
		return menu;
	}


	public void setMenu(List<RecipeName> menu) {
		this.menu = menu;
	}


	public List<LocalDate> getAllDatesCooked() {
		return allDatesCooked;
	}


	public void setAllDatesCooked(List<LocalDate> allDatesCooked) {
		this.allDatesCooked = allDatesCooked;
	}


	public List<String> getNotes() {
		return notes;
	}


	public void setNotes(List<String> notes) {
		this.notes = notes;
	}


	@Override
	public int hashCode() {
		return Objects.hash(allDatesCooked, id, meal, menu, name, notes);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlannedMeal other = (PlannedMeal) obj;
		return Objects.equals(allDatesCooked, other.allDatesCooked) && id == other.id && meal == other.meal
				&& Objects.equals(menu, other.menu) && Objects.equals(name, other.name)
				&& Objects.equals(notes, other.notes);
	}


	@Override
	public String toString() {
		return "PlannedMeal [id=" + id + ", name=" + name + ", meal=" + meal + ", menu=" + menu + ", allDatesCooked="
				+ allDatesCooked + ", notes=" + notes + "]";
	}

	
	
}
