package com.wulf.digitalrecipebook.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wulf.digitalrecipebook.model.recipe.*;
import com.wulf.digitalrecipebook.repository.RecipeRepository;

@Service
public class RecipeService {
	
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	IngredientService ingredientService;
	
	
	List<Recipe> recipes = new ArrayList();

	// temporarty method just for initial testing/setup
	public Recipe createNewRecipe(String name) {
		Recipe recipe = new Recipe(name);
		return recipe;
		
	}
	
	
	
	
	// temporary method to fillout a few recipes
	public List<Recipe> recipeBuilder() {
		
		
		
		// banana bread
		Recipe recipe1 = createNewRecipe("Banana Bread");
		recipe1.setDescription("Quick bread of ripe bananas baked in a loaf pan");
		
		  recipe1.setIngredients(new HashSet<Ingredient>(Arrays.asList(
				  ingredientService.findIngredientByName("banana"), 
				  ingredientService.findIngredientByName("flour"), 
				  ingredientService.findIngredientByName("brown sugar"), 
				  ingredientService.findIngredientByName("baking powder"))));
		 
		//Ingredient ingredient1= ingredientService.findIngredientByName("banana");
		
		//recipe1.setIngredients(ingredient1)
		
		
		recipes.add(recipe1);
		 
	
		//recipeRepository.save(recipe1);
		
		// pizza dough
		Recipe recipe2 = createNewRecipe("Pizza Dough");
		recipe2.setDescription("Neopolitanish pizza dough leavened with sourdough starter");
		recipe2.setIngredients(new HashSet<Ingredient>(Arrays.asList(
				ingredientService.findIngredientByName("sourdough starter"), 
				ingredientService.findIngredientByName("flour"), 
				ingredientService.findIngredientByName("water"), 
				ingredientService.findIngredientByName("honey"))));
		recipes.add(recipe2);
		//recipeRepository.save(recipe2);
		
		// Pork Ragu
		Recipe recipe3 = createNewRecipe("Pork Ragu");
		recipe3.setDescription("Italian Ragu of pork sausage");
		recipe3.setIngredients(new HashSet<Ingredient>(Arrays.asList(
				ingredientService.findIngredientByName("sweet Italian salsiccia"), 
				ingredientService.findIngredientByName("crushed tomatoes"), 
				ingredientService.findIngredientByName("yellow onion"), 
				ingredientService.findIngredientByName("garlic"))));
		recipes.add(recipe3);
		return recipeRepository.saveAll(recipes);
		//return recipes;
	}
	
	
	
	public Recipe addRecipe(String name) {
		return recipeRepository.save(createNewRecipe(name));
		//recipes.add(createNewRecipe(name));
		//return getRecipe(name);
	}
	
	public Recipe getRecipe(String name) {
//		for(Recipe recipe : recipes) {
//			if (recipe.getName().equalsIgnoreCase(name)) {
//				return recipe;
//			}
//		}
		return recipeRepository.findByName(name);
		//return null;
	}

	public List<Recipe> getRecipes() {
		return recipeRepository.findAll();
		//return recipes;
	}
	
	public Recipe updateRecipe(Recipe recipe) {
		/**Recipe oldRecipe = getRecipe(recipe.getName());
		//oldRecipe.
		int index = recipes.indexOf(oldRecipe);
		double version = oldRecipe.getVersion();
		recipe.setVersion((oldRecipe.getVersion()+1));
		recipes.set(index, recipe);
		//recipes.
		return getRecipe(recipe.getName());
		*/
		Recipe oldrecipe = recipeRepository.findByName(recipe.getName());
		recipe.setVersion((oldrecipe.getVersion()+1));
		return recipeRepository.save(recipe);
		
	}
	
}
