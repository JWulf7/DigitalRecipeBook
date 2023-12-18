package com.wulf.digitalrecipebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wulf.digitalrecipebook.model.recipe.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	
//	public Recipe findByName(String name) {
//		
//	}
	Recipe findByName(String name);
}
