package com.wulf.digitalrecipebook.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wulf.digitalrecipebook.dto.RecipeDto;
import com.wulf.digitalrecipebook.exception.ResourceNotFoundException;
import com.wulf.digitalrecipebook.mapper.RecipeMapper;
import com.wulf.digitalrecipebook.model.recipe.Ingredient;
import com.wulf.digitalrecipebook.model.recipe.Recipe;
import com.wulf.digitalrecipebook.model.recipe.RecipeName;
import com.wulf.digitalrecipebook.repository.RecipeNameRepository;
import com.wulf.digitalrecipebook.repository.RecipeRepository;

@Service
public class RecipeService {

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	RecipeNameRepository recipeNameRepository;

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

		recipe1.setIngredients(new HashSet<Ingredient>(Arrays.asList(ingredientService.findIngredientByName("banana"),
				ingredientService.findIngredientByName("flour"), ingredientService.findIngredientByName("brown sugar"),
				ingredientService.findIngredientByName("baking powder"))));

		// Ingredient ingredient1= ingredientService.findIngredientByName("banana");

		// recipe1.setIngredients(ingredient1)

		recipes.add(recipe1);

		// recipeRepository.save(recipe1);

		// pizza dough
		Recipe recipe2 = createNewRecipe("Pizza Dough");
		recipe2.setDescription("Neopolitanish pizza dough leavened with sourdough starter");
		recipe2.setIngredients(new HashSet<Ingredient>(Arrays.asList(
				ingredientService.findIngredientByName("sourdough starter"),
				ingredientService.findIngredientByName("flour"), ingredientService.findIngredientByName("water"),
				ingredientService.findIngredientByName("honey"))));
		recipes.add(recipe2);
		// recipeRepository.save(recipe2);

		// Pork Ragu
		Recipe recipe3 = createNewRecipe("Pork Ragu");
		recipe3.setDescription("Italian Ragu of pork sausage");
		recipe3.setIngredients(
				new HashSet<Ingredient>(Arrays.asList(ingredientService.findIngredientByName("sweet Italian salsiccia"),
						ingredientService.findIngredientByName("crushed tomatoes"),
						ingredientService.findIngredientByName("yellow onion"),
						ingredientService.findIngredientByName("garlic"))));
		recipes.add(recipe3);
		return recipeRepository.saveAll(recipes);
		// return recipes;
	}

	public RecipeDto addRecipe(String name) {
		Recipe recipe = recipeRepository.save(createNewRecipe(name));
		if (null != recipe) {
			recipeNameRepository.save(new RecipeName(recipe.getId(), recipe.getName()));
		}
		RecipeDto recipeDto = RecipeMapper.mapToRecipeDto(recipe);
		return recipeDto;
	}

	public RecipeDto getRecipeByName(String name) {
		// get Recipe obj from Repo by name
		Recipe recipe = recipeRepository.findByName(name);
		// check if obj exists, (null check)
		if (recipe != null) {
			// if obj exists, map to Dto obj and return to controller
			return RecipeMapper.mapToRecipeDto(recipe);
		}
		// else, if obj is null (doesn't exist), throw exception
		throw new ResourceNotFoundException("Recipe not found with name: '" + name + "'");
	}

	public List<RecipeDto> getRecipes() {
		// initialize Lists
		List<RecipeDto> allRecipeDto = new ArrayList<>();
		List<Recipe> allRecipes = recipeRepository.findAll();
		// map all Recipes from DB to RecipeDto's, add to Dto List
		for (Recipe recipe : allRecipes) {
			RecipeDto dto = RecipeMapper.mapToRecipeDto(recipe);
			allRecipeDto.add(dto);
		}
		// return Dto List to controller
		return allRecipeDto;

	}

	
	public RecipeDto updateRecipe(RecipeDto recipeDto) {
		System.out.println("MYLOGGER : started updateRecipe() method in RecipeService"); 
  		// map Dto to Recipe 
		Recipe recipe = RecipeMapper.mapToRecipe(recipeDto);
		System.out.println("MYLOGGER : mapped incoming RecipeDto to Recipe classes");
		// increment version 
	  	Recipe oldrecipe = recipeRepository.findByName(recipe.getName());
	  	recipe.setVersion((oldrecipe.getVersion()+1)); 
	  	// check ingredients in DB before saving recipe 
	  	ingredientCheck(recipe); 
	  	// update recipe in DB 
	  	Recipe updatedRecipe = recipeRepository.save(recipe); 
	  	// convert to and return Dto to controller 
	  	RecipeDto updatedRecipeDto = RecipeMapper.mapToRecipeDto(updatedRecipe); return updatedRecipeDto; 
  	}
	 

	/*
	 * public Recipe updateRecipe(Recipe recipe) { System.out.
	 * println("MYLOGGER : started updateRecipe() method in RecipeService"); // map
	 * Dto to Recipe //Recipe recipe = RecipeMapper.mapToRecipe(recipeDto);
	 * //System.out.println("MYLOGGER : mapped incoming RecipeDto to Recipe classes"
	 * ); // increment version Recipe oldrecipe =
	 * recipeRepository.findByName(recipe.getName()); System.out.
	 * println("MYLOGGER : in RecipeService Class.updateRecipe() : found existing DB recipe = "
	 * + oldrecipe); recipe.setVersion((oldrecipe.getVersion()+1)); System.out.
	 * println("MYLOGGER : in RecipeService Class : set new recipe version = " +
	 * recipe.getVersion()); System.out.
	 * println("MYLOGGER : in RecipeService Class : about to run ingredientCheck()"
	 * ); // check ingredients in DB before saving recipe ingredientCheck(recipe);
	 * // update recipe in DB System.out.
	 * println("MYLOGGER : in RecipeService Class : after running ingredientCheck()"
	 * ); Recipe updatedRecipe = recipeRepository.save(recipe); // convert to and
	 * return Dto to controller //RecipeDto updatedRecipeDto =
	 * RecipeMapper.mapToRecipeDto(updatedRecipe); return updatedRecipe; }
	 */

	public void ingredientCheck(Recipe recipe) {
		System.out.println("MYLOGGER : in RecipeService Class.ingredientCheck() : started ingredientCheck()");
		for (Ingredient ingredient : recipe.getIngredients()) {
			System.out.println("MYLOGGER : in RecipeService Class.ingredientCheck() : started for loop");
			if (null == ingredientService.findIngredientByName(ingredient.getName())) {
				ingredientService.saveIngredient(ingredient.getName());
			}
		}
	}

}
