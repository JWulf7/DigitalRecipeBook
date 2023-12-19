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
	
	
	
	public RecipeDto addRecipe(String name) {
		Recipe recipe = recipeRepository.save(createNewRecipe(name));
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
		for(Recipe recipe : allRecipes) {
			RecipeDto dto = RecipeMapper.mapToRecipeDto(recipe);
			allRecipeDto.add(dto);
		}
		// return Dto List to controller
		return allRecipeDto;

	}
	
	public RecipeDto updateRecipe(RecipeDto recipeDto) {
		// map Dto to Recipe
		Recipe recipe = RecipeMapper.mapToRecipe(recipeDto);
		// increment version
		Recipe oldrecipe = recipeRepository.findByName(recipe.getName());
		recipe.setVersion((oldrecipe.getVersion()+1));
		// update recipe in DB
		Recipe updatedRecipe = recipeRepository.save(recipe);
		// convert to and return Dto to controller
		RecipeDto updatedRecipeDto = RecipeMapper.mapToRecipeDto(updatedRecipe);
		return updatedRecipeDto;
	}
	
}
