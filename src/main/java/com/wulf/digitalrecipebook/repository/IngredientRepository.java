package com.wulf.digitalrecipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wulf.digitalrecipebook.model.recipe.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

}
