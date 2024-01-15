package com.wulf.digitalrecipebook.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wulf.digitalrecipebook.dto.RecipeDto;
import com.wulf.digitalrecipebook.exception.ResourceNotFoundException;
import com.wulf.digitalrecipebook.mapper.RecipeMapper;
import com.wulf.digitalrecipebook.model.recipe.Equipment;
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
	
	@Autowired
	EquipmentService equipmentService;

	List<Recipe> recipes = new ArrayList();

	// temporarty method just for initial testing/setup
	public Recipe createNewRecipe(String name) {
		Recipe recipe = new Recipe(name);
		recipe.setCreated(LocalDateTime.now());
		return recipe;

	}

	// temporary method to fillout a few recipes
	/*
	 * public List<Recipe> recipeBuilder() {
	 * 
	 * // banana bread Recipe recipe1 = createNewRecipe("Banana Bread");
	 * recipe1.setDescription("Quick bread of ripe bananas baked in a loaf pan");
	 * 
	 * recipe1.setIngredients(new
	 * HashSet<Ingredient>(Arrays.asList(ingredientService.findIngredientByName(
	 * "banana"), ingredientService.findIngredientByName("flour"),
	 * ingredientService.findIngredientByName("brown sugar"),
	 * ingredientService.findIngredientByName("baking powder"))));
	 * 
	 * // Ingredient ingredient1= ingredientService.findIngredientByName("banana");
	 * 
	 * // recipe1.setIngredients(ingredient1)
	 * 
	 * recipes.add(recipe1);
	 * 
	 * // recipeRepository.save(recipe1);
	 * 
	 * // pizza dough Recipe recipe2 = createNewRecipe("Pizza Dough"); recipe2.
	 * setDescription("Neopolitanish pizza dough leavened with sourdough starter");
	 * recipe2.setIngredients(new HashSet<Ingredient>(Arrays.asList(
	 * ingredientService.findIngredientByName("sourdough starter"),
	 * ingredientService.findIngredientByName("flour"),
	 * ingredientService.findIngredientByName("water"),
	 * ingredientService.findIngredientByName("honey")))); recipes.add(recipe2); //
	 * recipeRepository.save(recipe2);
	 * 
	 * // Pork Ragu Recipe recipe3 = createNewRecipe("Pork Ragu");
	 * recipe3.setDescription("Italian Ragu of pork sausage");
	 * recipe3.setIngredients( new
	 * HashSet<Ingredient>(Arrays.asList(ingredientService.
	 * findIngredientByName("sweet Italian salsiccia"),
	 * ingredientService.findIngredientByName("crushed tomatoes"),
	 * ingredientService.findIngredientByName("yellow onion"),
	 * ingredientService.findIngredientByName("garlic")))); recipes.add(recipe3);
	 * return recipeRepository.saveAll(recipes); // return recipes; }
	 */

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
	  	equipmentCheck(recipe);
	  	recipe = recipeNameCheck(recipe);
	  	// add value to recipe.allDatesUpdated
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : started right before instantiating arrayList");
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : current, before update - recipe.getAllDatesUpdated() -> " + recipe.getAllDatesUpdated().toString());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : current, before update - recipe -> " + recipe);
	  	ArrayList<LocalDateTime> previousUpdates = new ArrayList<>();
	  	previousUpdates = (ArrayList<LocalDateTime>) recipe.getAllDatesUpdated();
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : ArrayList previousUpdates -> " + previousUpdates);
	  	if(previousUpdates.add(LocalDateTime.now())) {
	  		recipe.setAllDatesUpdated(previousUpdates);
	  	}
	  	
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
		for (Ingredient ingredient : recipe.getIngredients().keySet()) {
			System.out.println("MYLOGGER : in RecipeService Class.ingredientCheck() : started for loop");
			if (null == ingredientService.findIngredientByName(ingredient.getName())) {
				ingredientService.saveIngredient(ingredient.getName());
			}
		}
	}
	
	public void equipmentCheck(Recipe recipe) {
		System.out.println("MYLOGGER : in RecipeService Class.equipmentCheck() : started equipmentCheck()");
		for (Equipment equipment : (List<Equipment>)safeList(recipe.getEquipment())) {
			System.out.println("MYLOGGER : in RecipeService Class.equipmentCheck() : started for loop");
			if (null == equipmentService.findEquipmentByName(equipment.getName())) {
				equipmentService.saveEquipment(equipment.getName());
			}
		}
	}
	
	public Recipe recipeNameCheck(Recipe recipe) {
		System.out.println("MYLOGGER : in RecipeService Class.getRecipeName() : started getRecipeName().pairings");
		//Recipe.pairings
		Set<RecipeName> pairings = new HashSet<>();
		for (RecipeName recipeName : (Set<RecipeName>)safeSet(recipe.getPairings())) {
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				addRecipe(recipeName.getName());
			}
			pairings.add(recipeNameRepository.findByName(recipeName.getName()));
			recipe.setPairings(pairings);
		}
		//Recipe.pairsWith
		List<RecipeName> pairsWith = new ArrayList<>();
		System.out.println("MYLOGGER : in RecipeService Class.getRecipeName() : started getRecipeName().pairsWith ");
		System.out.println("MYLOGGER : in RecipeService Class.getRecipeName() : started getRecipeName().pairsWith, recipe.getPairsWith() -> " + recipe.getPairsWith());
		for (RecipeName recipeName : (List<RecipeName>)safeList(recipe.getPairsWith())) {
			System.out.println("MYLOGGER : in RecipeService Class.getRecipeName() : started getRecipeName().pairsWith .. in for loop");
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				addRecipe(recipeName.getName());
				System.out.println("MYLOGGER : in RecipeService Class.getRecipeName() : started getRecipeName().pairsWith.. in for loop.. recipeNameRepo.findByName was null, added recipeName to db ");
			}
			pairsWith.add(recipeNameRepository.findByName(recipeName.getName()));
			System.out.println("MYLOGGER : in RecipeService Class.getRecipeName() : started getRecipeName().pairsWith, added recipeName to new pairsWith list");
			System.out.println("MYLOGGER : in RecipeService Class.getRecipeName() : started getRecipeName().pairsWith.. pairsWith => " + pairsWith);
			recipe.setPairsWith(new ArrayList<>(pairsWith));
			System.out.println("MYLOGGER : in RecipeService Class.getRecipeName() : started getRecipeName().pairsWith.. set pairsWith to recipe.setPairsWith().. recipe.getPairsWith -> " + recipe.getPairsWith());
		}
		//Recipe.oftenMadeAlongside
		pairsWith.clear();
		for (RecipeName recipeName : (List<RecipeName>)safeList(recipe.getOftenMadeAlongside())) {
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				addRecipe(recipeName.getName());
			}
			pairsWith.add(recipeNameRepository.findByName(recipeName.getName()));
			recipe.setOftenMadeAlongside(new ArrayList<>(pairsWith));
		}
		//Recipe.dishesThatAlsoUseLeftoverIngredients
		pairsWith.clear();
		for (RecipeName recipeName : (List<RecipeName>)safeList(recipe.getDishesThatAlsoUseLeftoverIngredients())) {
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				addRecipe(recipeName.getName());
			}
			pairsWith.add(recipeNameRepository.findByName(recipeName.getName()));
			recipe.setDishesThatAlsoUseLeftoverIngredients(new ArrayList<>(pairsWith));
		}
		//Recipe.mealAffinities
		pairsWith.clear();
		for (RecipeName recipeName : (List<RecipeName>)safeList(recipe.getMealAffinities())) {
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				addRecipe(recipeName.getName());
			}
			pairsWith.add(recipeNameRepository.findByName(recipeName.getName()));
			recipe.setMealAffinities(new ArrayList<>(pairsWith));
		}
		return recipe;
	}
	
	public static List safeList(List other) {
	    return other == null ? Collections.EMPTY_LIST : other;
	}
	
	public static Set safeSet(Set other) {
		return other == null ? Collections.EMPTY_SET : other;
	}

}
