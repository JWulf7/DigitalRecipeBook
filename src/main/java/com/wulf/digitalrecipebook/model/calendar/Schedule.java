package com.wulf.digitalrecipebook.model.calendar;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.wulf.digitalrecipebook.model.recipe.PlannedMeal;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Schedule implements Serializable{
//public class Schedule {
	
	@Id
	private LocalDate date;
	
	@OneToMany
	private List<PlannedMeal> todaysPlannedMeals = new ArrayList<>();
	
	@OneToMany
	private List<PlannedMeal> tomorrowsPlannedMeals = new ArrayList<>();
	
	private List<String> prepList = new ArrayList<>();
	
	@ElementCollection
	private Map<LocalDate, PlannedMeal> allMealsPlanned = new HashMap<>();
	
	private List<String> notes = new ArrayList<>();
	
	@ElementCollection
	private Map<LocalDate, PlannedMeal> actualMeals = new HashMap<>();	// this I think might want to change Types???

	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Schedule(LocalDate date, List<PlannedMeal> todaysPlannedMeals, List<PlannedMeal> tomorrowsPlannedMeals,
			List<String> prepList, Map<LocalDate, PlannedMeal> allMealsPlanned, List<String> notes,
			Map<LocalDate, PlannedMeal> actualMeals) {
		super();
		this.date = date;
		this.todaysPlannedMeals = todaysPlannedMeals;
		this.tomorrowsPlannedMeals = tomorrowsPlannedMeals;
		this.prepList = prepList;
		this.allMealsPlanned = allMealsPlanned;
		this.notes = notes;
		this.actualMeals = actualMeals;
	}

	public Schedule(LocalDate date) {
		super();
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<PlannedMeal> getTodaysPlannedMeals() {
		return todaysPlannedMeals;
	}

	public void setTodaysPlannedMeals(List<PlannedMeal> todaysPlannedMeals) {
		this.todaysPlannedMeals = todaysPlannedMeals;
	}

	public List<PlannedMeal> getTomorrowsPlannedMeals() {
		return tomorrowsPlannedMeals;
	}

	public void setTomorrowsPlannedMeals(List<PlannedMeal> tomorrowsPlannedMeals) {
		this.tomorrowsPlannedMeals = tomorrowsPlannedMeals;
	}

	public List<String> getPrepList() {
		return prepList;
	}

	public void setPrepList(List<String> prepList) {
		this.prepList = prepList;
	}

	public Map<LocalDate, PlannedMeal> getAllMealsPlanned() {
		return allMealsPlanned;
	}

	public void setAllMealsPlanned(Map<LocalDate, PlannedMeal> allMealsPlanned) {
		this.allMealsPlanned = allMealsPlanned;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	public Map<LocalDate, PlannedMeal> getActualMeals() {
		return actualMeals;
	}

	public void setActualMeals(Map<LocalDate, PlannedMeal> actualMeals) {
		this.actualMeals = actualMeals;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actualMeals, allMealsPlanned, date, notes, prepList, todaysPlannedMeals,
				tomorrowsPlannedMeals);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		return Objects.equals(actualMeals, other.actualMeals) && Objects.equals(allMealsPlanned, other.allMealsPlanned)
				&& Objects.equals(date, other.date) && Objects.equals(notes, other.notes)
				&& Objects.equals(prepList, other.prepList)
				&& Objects.equals(todaysPlannedMeals, other.todaysPlannedMeals)
				&& Objects.equals(tomorrowsPlannedMeals, other.tomorrowsPlannedMeals);
	}

	@Override
	public String toString() {
		return "Schedule [date=" + date + ", todaysPlannedMeals=" + todaysPlannedMeals + ", tomorrowsPlannedMeals="
				+ tomorrowsPlannedMeals + ", prepList=" + prepList + ", allMealsPlanned=" + allMealsPlanned + ", notes="
				+ notes + ", actualMeals=" + actualMeals + "]";
	}
	
	
	
	

}
