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
	
	RecipeName blankName = new RecipeName(0,"");

	// temporarty method just for initial testing/setup.... maybe not just initial....
	// This method is called from addRecipe(name) method... 
	// This method instantiates a new Recipe class object only w/ the name from the parameter, and sets the Recipe's created attribute, then returns that Recipe class object
	public Recipe createNewRecipeInitial(String name) {
		Recipe recipe = new Recipe(name);
		recipe.setCreated(LocalDateTime.now());
		return recipe;

	}
	
	// This method is called from RecipeController
	// This method handles the whole operation of creating a new recipe from front-end's Add New Recipe functionality
	public RecipeDto createNewRecipe(RecipeDto dto) {
		System.out.println("MYLOGGER : starting RecipeService Class.createNewRecipe(recipeDto)"); 
		// save new recipe entry to DB w/ name
		System.out.println("MYLOGGER : RecipeService Class.createNewRecipe() : about to call RecipeService.addRecipe(dto.getName())"); 
		RecipeDto dbDto = addRecipe(dto.getName());
		// set the incoming dto's version, id, and date created to the values just created in DB
		//dto.setVersion(dbDto.getVersion());  // stopping this.. b/c recipe version gets +1'd during the updateRecipe() phase of this method anyways...might just set it to zero???
		dto.setVersion(0); 
		dto.setId(dbDto.getId());
		dto.setCreated(dbDto.getCreated());
		// may want to get rid of null values here??? for the alldates etc. arrays...
		System.out.println("MYLOGGER : RecipeService Class.createNewRecipe() : about to call RecipeService.updateRecipe(dto) :: current dto -> " + dto); 
		// update the new DB entry w/ remainder of incoming dto's info and return
		return updateRecipe(dto);
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

	// this method creates a new Recipe class object, sets created attribute, persists recipe to DB, if succesfully persisted, adds a matching value in recipeName table of the ID and name, 
	// returns the new Recipe class object (w/ only ID, name, and created attributes), as a converted RecipeDto class object.
	public RecipeDto addRecipe(String name) {
		System.out.println("MYLOGGER : starting RecipeService Class.addRecipe(name) "); 
		System.out.println("MYLOGGER : RecipeService Class.addRecipe(name) : calling recipe = recipeRepository.save(createNewRecipeInitial(name)) "); 
		// creates a new Recipe class object, sets the name and created attributes
		// then persists that new Recipe class object to the DB
		Recipe recipe = recipeRepository.save(createNewRecipeInitial(name));
		System.out.println("MYLOGGER : RecipeService Class.addRecipe(name) : recipe = " + recipe);
		if (null != recipe) {
			// if Recipe was successfully created in the DB, then recipeName table also updated w/ the same ID + name as Recipe
			recipeNameRepository.save(new RecipeName(recipe.getId(), recipe.getName()));
		}
		// converts the newly created Recipe class object (w/ only an ID, name, and created attributes) to a RecipeDto class object, and returns it
		RecipeDto recipeDto = RecipeMapper.mapToRecipeDto(recipe);
		return recipeDto;
	}

	public RecipeDto getRecipeByName(String name) {
		// get Recipe obj from Repo by name
		Recipe recipe = recipeRepository.findByName(name);
		System.out.println("MYLOGGER : RecipeService Class.getRecipeByName(name) : recipe = " + recipe);
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

	
	// this method can be called by the addRecipe() method.. I believe by Controller class as well for a straightforward update... 
	/** 
	 * may want to decouple the functionality of addRecipe from the update??... IDK...
	 * **/
	public RecipeDto updateRecipe(RecipeDto recipeDto) {
		System.out.println("MYLOGGER : started updateRecipe() method in RecipeService"); 
  		// map Dto to Recipe 
		Recipe recipe = RecipeMapper.mapToRecipe(recipeDto);
		System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : mapped incoming RecipeDto to Recipe classes");
		// increment version
	  	Recipe oldrecipe = recipeRepository.findByName(recipe.getName());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : oldrecipe -> " + oldrecipe);
	  	if (recipeDto.getVersion() != 0) {
	  		// increment version if dto version is not 0
	  		recipe.setVersion((oldrecipe.getVersion()+1));
	  		// might set the recipe ID here too???
	  		recipe.setId(oldrecipe.getId());
	  	} else {
	  		// if newly created recipe.. dto version should have been set to 0, now setting it to 1
	  		recipe.setVersion(1);
	  	}
	  	
	  	// keep old created date
	  	recipe.setCreated(oldrecipe.getCreated());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : status of recipe object before the ingredient/equip/recipeName checks... : recipe -> " + recipe);
	  	// check ingredients in DB before saving recipe 
	  	ingredientCheck(recipe);
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : status of recipe object after ingredientCheck... : recipe -> " + recipe);
	  	equipmentCheck(recipe);
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : status of recipe object after equipmentCheck... : recipe -> " + recipe);
	  	recipe = recipeNameCheck(recipe);
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : have just set recipe = return value of recipe = recipeNameCheck(recipe)... : now recipe -> " + recipe);
	  	// add value to recipe.allDatesUpdated
	  	recipe.setAllDatesUpdated(oldrecipe.getAllDatesUpdated());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : started right before instantiating arrayList");
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : current, before update - recipe.getAllDatesUpdated() -> " + recipe.getAllDatesUpdated().toString());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : current, before update - recipe -> " + recipe);
	  	ArrayList<LocalDateTime> previousUpdates = new ArrayList<>();
	  	previousUpdates = (ArrayList<LocalDateTime>) recipe.getAllDatesUpdated();
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : ArrayList previousUpdates -> " + previousUpdates);
	  	
	  	// might want to do a version check here... and not update all dates updated if only version 0/1???
	  	// error / exception getting close to here... just a litttle further...
	  	
	  	if (recipe.getVersion() != 1.0) {
	  		// not a new recipe... add a new update to allDatesUpdated
	  		if(previousUpdates.add(LocalDateTime.now())) {
		  		System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : updating allDatesUpdated, in if statement (it was true)");
		  		recipe.setAllDatesUpdated(previousUpdates);
		  		System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : updating allDatesUpdated, in if statement, after recipe should now have full set of updated list");
		  		System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : updating allDatesUpdated, in if statement, after recipe should now have full set of updated list; recipe.getAllDatesUpdated() -> " + recipe.getAllDatesUpdated());
		  	}
	  		
	  	}
	  	
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : about to save updated recipe to DB : status : recipe.getPairings().size() -> " + recipe.getPairings().size());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : about to save updated recipe to DB : status : recipe.getPairings() -> " + recipe.getPairings().toString());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : about to save updated recipe to DB : status : recipe.getPairsWith().size() -> " + recipe.getPairsWith().size());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : about to save updated recipe to DB : status : recipe.getPairsWith().size() -> " + recipe.getPairsWith().size());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : about to save updated recipe to DB : status : recipe.getOftenMadeAlongside().size() -> " + recipe.getOftenMadeAlongside().size());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : about to save updated recipe to DB : status : recipe.getOftenMadeAlongside().size() -> " + recipe.getOftenMadeAlongside().size());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : about to save updated recipe to DB : status : recipe.getDishesThatAlsoUseLeftoverIngredients().size() -> " + recipe.getDishesThatAlsoUseLeftoverIngredients().size());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : about to save updated recipe to DB : status : recipe.getMealAffinities().size() -> " + recipe.getMealAffinities().size());
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : about to save updated recipe to DB : recipe -> " + recipe);
	  	// update recipe in DB 
	  	Recipe updatedRecipe = recipeRepository.save(recipe); 
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : saved updated recipe in DB... updatedRecipe -> " + updatedRecipe);
	  	// convert to and return Dto to controller 
	  	RecipeDto updatedRecipeDto = RecipeMapper.mapToRecipeDto(updatedRecipe); 
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : converting to recipeDto -> " + updatedRecipeDto);
	  	System.out.println("MYLOGGER : in RecipeService Class.updateRecipe() : returning updatedRecipeDto");
	  	return updatedRecipeDto; 
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

	// this method is called by updateRecipe(dto) method
	// it loops through all the ingredient names in the ingredients list, and adds the ingredients to DB in ingredients table if they do not exist
	public void ingredientCheck(Recipe recipe) {
		System.out.println("MYLOGGER : in RecipeService Class.ingredientCheck() : started ingredientCheck()");
		System.out.println("MYLOGGER : in RecipeService Class.ingredientCheck() : recipe.getIngredients().keySet() -> " + recipe.getIngredients().keySet());
		for (Ingredient ingredient : recipe.getIngredients().keySet()) {
			System.out.println("MYLOGGER : in RecipeService Class.ingredientCheck() : started for loop");
			System.out.println("MYLOGGER : in RecipeService Class.ingredientCheck() : for(loop) for - Ingredient : "+ ingredient);
			if (null == ingredientService.findIngredientByName(ingredient.getName())) {
				System.out.println("MYLOGGER : in RecipeService Class.ingredientCheck() : inside if conditioanal; (it was true)");
				ingredientService.saveIngredient(ingredient.getName());
			}
		}
	}
	
	// this method is called from updateRecipe(dto) method
	// it loops through all the equipment names in the equipment list, and adds the equipment to DB in equipment table if they do not exist
	public void equipmentCheck(Recipe recipe) {
		System.out.println("MYLOGGER : in RecipeService Class.equipmentCheck() : started equipmentCheck()");
		System.out.println("MYLOGGER : in RecipeService Class.equipmentCheck() : (List<Equipment>)safeList(recipe.getEquipment()) -> " + (List<Equipment>)safeList(recipe.getEquipment()));
		for (Equipment equipment : (List<Equipment>)safeList(recipe.getEquipment())) {
			System.out.println("MYLOGGER : in RecipeService Class.equipmentCheck() : started for loop");
			System.out.println("MYLOGGER : in RecipeService Class.equipmentCheck() : for loop() - Equipment : " + equipment);

			if (null == equipmentService.findEquipmentByName(equipment.getName())) {
				System.out.println("MYLOGGER : in RecipeService Class.equipmentCheck() : inside if conditional; (it was true)");

				equipmentService.saveEquipment(equipment.getName());
			}
		}
	}
	
	// this method is called from updateRecipe(dto) method
	// it checks the RecipeName table in db for all values in the lists of pairings etc... if there is a pairing/etc. not found in db.. it adds that recipe to db as well....
	public Recipe recipeNameCheck(Recipe recipe) {
		System.out.println("MYLOGGER : Starting RecipeService Class.recipeNamecheck()");
		//Recipe.pairings
		Set<RecipeName> pairings = new HashSet<>();
		System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairings");
		System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : starting for loop for all RecipeName in recipe.getPairings()");
		for (RecipeName recipeName : (Set<RecipeName>)safeSet(recipe.getPairings())) {
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getPairings() : recipeName -> " + recipeName);   // could potentiall add an additional helper method check here... if recpieName = '' (blank) then make it an empty list or skip or something??
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getPairings() : trying to solve blank recipe");
			// if current iteration recipeName == blank recipeName (id=0, name='').. don't add to db
			if (blankName.equals(recipeName)) {
				System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getPairings() : in if conditional for blankName.equals(recipeName), it was true.. about to continue");
				continue;
			}
			// if current iteration recipeName is not in db, add it to db
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getPairings() : if conditional was true, recipeName.getName() -> " + recipeName.getName());
				System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getPairings() : if conditional was true, about to call addRecipe(recipeName.getName()) method on -> " + recipeName.getName());
				addRecipe(recipeName.getName());
			}
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getPairings() : after if conditional, about to add recipeName (either newly added or already existed) to previously instantiated pairings HashSet: recipeName.getName() -> " + recipeName.getName());
			// add current iteration recipeName to temporary set instantiated pairings
			pairings.add(recipeNameRepository.findByName(recipeName.getName()));
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getPairings() : after if conditional, after added recipeName (either newly added or already existed) to previously instantiated pairings HashSet, about to set recipe.pairings to pairings: pairings -> " + pairings);
			// set recipe.pairings to temporary set instantiated pairings
			recipe.setPairings(pairings);
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getPairings() : after if conditional, after set recipe.pairings : current recipe -> " + recipe);
		}
		
		System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairings, after for loop... trying to solve pairings issue, about to hit if condition " );


		System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairings, after for loop... trying to solve pairings issue, recipe.getPairings().toString() -> " + recipe.getPairings().toString() );
		
		// if recipe.pairings contains only blank recipeName (id=0, name='')..set pairings to empty 
		if ((recipe.getPairings().size() == 1) && (recipe.getPairings().contains(blankName))) {
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairings, after for loop... trying to solve pairings issue, inside 2nd if condition, it was true" );
			Set<RecipeName> pairingsNull = new HashSet<>();
			recipe.setPairings(pairingsNull);
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairings, after for loop... trying to solve pairings issue, inside 2nd if condition, it was true, set recipe pairings to null" );
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairings, after for loop... trying to solve pairings issue, inside 2nd if condition : recipe.getPairings.toString() -> " + recipe.getPairings().toString() );
			
		}
		
		
		
		System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairings, after for loop... recipe.getPairings().toString() -> " + recipe.getPairings().toString());
		//Recipe.pairsWith
		List<RecipeName> pairsWith = new ArrayList<>();
		System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith ");
		System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith, recipe.getPairsWith() -> " + recipe.getPairsWith());
		for (RecipeName recipeName : (List<RecipeName>)safeList(recipe.getPairsWith())) {
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith .. in for loop");
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : recipeName -> " + recipeName);
			if (blankName.equals(recipeName)) {
				System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getPairsWith() : in if conditional for blankName.equals(recipeName), it was true.. about to continue");
				continue;
			}
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				addRecipe(recipeName.getName());
				System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith.. in for loop.. recipeNameRepo.findByName was null, added recipeName to db ");
				System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : called addRecipe(recipeName.getName()");
			}
			pairsWith.add(recipeNameRepository.findByName(recipeName.getName()));
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith, added recipeName to new pairsWith list");
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith.. pairsWith => " + pairsWith);
			recipe.setPairsWith(new ArrayList<>(pairsWith));
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith.. set pairsWith to recipe.setPairsWith().. recipe.getPairsWith -> " + recipe.getPairsWith());
		}
		
		if ((recipe.getPairsWith().size() == 1) && (recipe.getPairsWith().contains(blankName))) {
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith, after for loop... trying to solve pairsWith issue, inside 2nd if condition, it was true" );
			List<RecipeName> pairsWithNull = new ArrayList<>();
			recipe.setPairsWith(pairsWithNull);
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith, after for loop... trying to solve pairsWith issue, inside 2nd if condition, it was true, set recipe pairsWith to null" );
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().pairsWith, after for loop... trying to solve pairWith issue, inside 2nd if condition : recipe.getPairsWith.toString() -> " + recipe.getPairsWith().toString() );
			
		}
		
		
		//Recipe.oftenMadeAlongside
		pairsWith.clear();
		for (RecipeName recipeName : (List<RecipeName>)safeList(recipe.getOftenMadeAlongside())) {
			if (blankName.equals(recipeName)) {
				System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getOftenMadeAlongside() : in if conditional for blankName.equals(recipeName), it was true.. about to continue");
				continue;
			}
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				addRecipe(recipeName.getName());
			}
			pairsWith.add(recipeNameRepository.findByName(recipeName.getName()));
			recipe.setOftenMadeAlongside(new ArrayList<>(pairsWith));
		}
		
		if ((recipe.getOftenMadeAlongside().size() == 1) && (recipe.getOftenMadeAlongside().contains(blankName))) {
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().getOftenMadeAlongside, after for loop... trying to solve OftenMadeAlongside() issue, inside 2nd if condition, it was true" );
			List<RecipeName> pairsWithNull = new ArrayList<>();
			recipe.setOftenMadeAlongside(pairsWithNull);
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().getOftenMadeAlongside, after for loop... trying to solve OftenMadeAlongside() issue, inside 2nd if condition, it was true, set recipe OftenMadeAlongside() to null" );
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().getOftenMadeAlongside, after for loop... trying to solve OftenMadeAlongside() issue, inside 2nd if condition : recipe.getOftenMadeAlongside().toString() -> " + recipe.getOftenMadeAlongside().toString() );
			
		}
		
		
		//Recipe.dishesThatAlsoUseLeftoverIngredients
		pairsWith.clear();
		for (RecipeName recipeName : (List<RecipeName>)safeList(recipe.getDishesThatAlsoUseLeftoverIngredients())) {
			if (blankName.equals(recipeName)) {
				System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getDishesThatAlsoUseLeftoverIngredients() : in if conditional for blankName.equals(recipeName), it was true.. about to continue");
				continue;
			}
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				addRecipe(recipeName.getName());
			}
			pairsWith.add(recipeNameRepository.findByName(recipeName.getName()));
			recipe.setDishesThatAlsoUseLeftoverIngredients(new ArrayList<>(pairsWith));
		}
		
		if ((recipe.getDishesThatAlsoUseLeftoverIngredients().size() == 1) && (recipe.getDishesThatAlsoUseLeftoverIngredients().contains(blankName))) {
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().setDishesThatAlsoUseLeftoverIngredients, after for loop... trying to solve setDishesThatAlsoUseLeftoverIngredients() issue, inside 2nd if condition, it was true" );
			List<RecipeName> pairsWithNull = new ArrayList<>();
			recipe.setDishesThatAlsoUseLeftoverIngredients(pairsWithNull);
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().setDishesThatAlsoUseLeftoverIngredients, after for loop... trying to solve setDishesThatAlsoUseLeftoverIngredients() issue, inside 2nd if condition, it was true, set recipe setDishesThatAlsoUseLeftoverIngredients() to null" );
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().setDishesThatAlsoUseLeftoverIngredients, after for loop... trying to solve setDishesThatAlsoUseLeftoverIngredients() issue, inside 2nd if condition : recipe.setDishesThatAlsoUseLeftoverIngredients().toString() -> " + recipe.getDishesThatAlsoUseLeftoverIngredients().toString() );
			
		}
		
		//Recipe.mealAffinities
		pairsWith.clear();
		for (RecipeName recipeName : (List<RecipeName>)safeList(recipe.getMealAffinities())) {
			if (blankName.equals(recipeName)) {
				System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : in for loop for RecipeName in recipe.getMealAffinities() : in if conditional for blankName.equals(recipeName), it was true.. about to continue");
				continue;
			}
			if (null == recipeNameRepository.findByName(recipeName.getName())) {
				addRecipe(recipeName.getName());
			}
			pairsWith.add(recipeNameRepository.findByName(recipeName.getName()));
			recipe.setMealAffinities(new ArrayList<>(pairsWith));
		}
		
		if ((recipe.getMealAffinities().size() == 1) && (recipe.getMealAffinities().contains(blankName))) {
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().mealaffinities, after for loop... trying to solve mealaffinities() issue, inside 2nd if condition, it was true" );
			List<RecipeName> pairsWithNull = new ArrayList<>();
			recipe.setMealAffinities(pairsWithNull);
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().mealaffinities, after for loop... trying to solve mealaffinities() issue, inside 2nd if condition, it was true, set recipe mealaffinities() to null" );
			System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : started getRecipeName().mealaffinities, after for loop... trying to solve mealaffinities() issue, inside 2nd if condition : recipe.mealaffinities().toString() -> " + recipe.getMealAffinities().toString() );
			
		}
		
		
		
		System.out.println("MYLOGGER : in RecipeService Class.recipeNamecheck() : finishing recipeNameCheck() method functionality.. : about to return recipe -> " + recipe);
		return recipe;
	}
	
	
	public static List safeList(List other) {
	    return other == null ? Collections.EMPTY_LIST : other;
	}
	
	public static Set safeSet(Set other) {
		return other == null ? Collections.EMPTY_SET : other;
	}

}
