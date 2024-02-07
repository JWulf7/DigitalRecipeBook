package com.wulf.digitalrecipebook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wulf.digitalrecipebook.dto.RecipeDto;
import com.wulf.digitalrecipebook.service.RecipeService;

@CrossOrigin("*") // allowing all incoming clients
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
	
	@Autowired
	RecipeService recipeService;
	
	// deprecated once DB is established
	/*
	 * @GetMapping(value="/start") public List<Recipe> fillRecipes() { return
	 * recipeService.recipeBuilder(); }
	 */
	
	
	@GetMapping(value="/getRecipes")
	public ResponseEntity<List<RecipeDto>> getRecipes() {
		List<RecipeDto> allRecipesDto = recipeService.getRecipes();
		return new ResponseEntity<>(allRecipesDto, HttpStatus.OK);
	}
	
	//might want to change this to /getRecipe/(name) and use @PathVariable 
	// deprecated once /getRecipe/{name} is created for better REST organization
	@GetMapping(value="/getRecipe")
	public RecipeDto getRecipe(@RequestParam("name") String name) {
		return recipeService.getRecipeByName(name); 
	}
	
	// 
	@GetMapping(value="/getRecipe/{name}")
	public ResponseEntity<RecipeDto> getRecipeSpecific(@PathVariable("name") String name) {
		System.out.println("In controller... 'name' getting from pathVariable = " + name); // Remove after debug
		RecipeDto recipeDto = recipeService.getRecipeByName(name); 
		return new ResponseEntity<>(recipeDto, HttpStatus.OK);
	}
	
	// this adds a new blank recipe based on only the recipe name
	@PostMapping(value="/addRecipe")
	//@RequestParam(name = "name")
	// if query param sent is named same as @RequestParam("value") then don't need to specify the ("value") part
	public ResponseEntity<RecipeDto> addRecipe(@RequestParam("name")String name) {
		RecipeDto recipeDto = recipeService.addRecipe(name);
		return new ResponseEntity<>(recipeDto, HttpStatus.CREATED);
	}
	
	// this adds a new recipe w/ most of recipe member attributes
	@PostMapping(value="/createNewRecipe")
	// if query param sent is named same as @RequestParam("value") then don't need to specify the ("value") part
	public ResponseEntity<RecipeDto> createNewRecipe(@RequestBody RecipeDto recipeDto) {
		System.out.println("MYLOGGER : started RecipeController Class.createNewRecipe()"); 
		System.out.println("MYLOGGER : RecipeController Class.createNewRecipe() : incoming @RequestBody recipeDto : " + recipeDto); 
		RecipeDto persistedRecipeDto = recipeService.createNewRecipe(recipeDto);
		System.out.println("MYLOGGER : RecipeController Class.createNewRecipe() : persisted the newly created recipe in the db... persistedRecipeDto -> : " + persistedRecipeDto); 
		System.out.println("MYLOGGER : RecipeController Class.createNewRecipe() : backend functionality complete... attempting to return new ResponseEntity<persistedRecipeDto> for the post request"); 
		return new ResponseEntity<>(persistedRecipeDto, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value="/update") 
	public ResponseEntity<RecipeDto> updateRecipe(@RequestBody RecipeDto recipeDto) { 
		System.out.println("MYLOGGER : started updateRecipe() method at '/update' endpoint in RecipeController"); 
		RecipeDto updatedDto = recipeService.updateRecipe(recipeDto);
		return new ResponseEntity<>(updatedDto, HttpStatus.OK); 
	}
	 
	/*
	 * @PutMapping(value="/update") public ResponseEntity<Recipe>
	 * updateRecipe(@RequestBody Recipe recipeDto) { System.out.
	 * println("MYLOGGER : started updateRecipe() method at '/update' endpoint in RecipeController"
	 * ); Recipe updatedDto = recipeService.updateRecipe(recipeDto); return new
	 * ResponseEntity<>(updatedDto, HttpStatus.OK); }
	 */

}
