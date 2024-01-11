package com.wulf.digitalrecipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wulf.digitalrecipebook.model.recipe.RecipeName;

@Repository
public interface RecipeNameRepository extends JpaRepository<RecipeName, Integer> {

	RecipeName findByName(String name);
}
