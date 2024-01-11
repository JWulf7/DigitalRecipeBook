package com.wulf.digitalrecipebook.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wulf.digitalrecipebook.model.recipe.Ingredient;
import com.wulf.digitalrecipebook.repository.IngredientRepository;

@Service
public class IngredientService {

	@Autowired
	IngredientRepository ingredientRepository;
	
	/*
	 * public IngredientService() { super(); }
	 */
	
	public Ingredient findIngredientByName(String name) {
		return ingredientRepository.findById(name).orElse(null);
		//return ingredientRepository.findById(name).get();
	}
	
	public Ingredient saveIngredient(String ingredientName) {
		return ingredientRepository.save(new Ingredient(ingredientName));
	}
}
