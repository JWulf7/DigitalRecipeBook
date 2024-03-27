package com.wulf.digitalrecipebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wulf.digitalrecipebook.model.recipe.PlannedMeal;
import com.wulf.digitalrecipebook.model.recipe.RecipeName;

public interface PlannedMealRepository extends JpaRepository<PlannedMeal, Integer> {

	
	PlannedMeal findByName(String name);
	
	PlannedMeal findByMenuIn(List<RecipeName> menu);
	
	// implement method to get specific planned meal based on a single date from allDatesCooked
}
