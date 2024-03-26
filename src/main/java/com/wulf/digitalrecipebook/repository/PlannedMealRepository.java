package com.wulf.digitalrecipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wulf.digitalrecipebook.model.recipe.PlannedMeal;

public interface PlannedMealRepository extends JpaRepository<PlannedMeal, Integer> {

	
	PlannedMeal findByName(String name);
	
	// implement method to get specific planned meal based on a single date from allDatesCooked
}
