package com.wulf.digitalrecipebook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wulf.digitalrecipebook.model.recipe.Recipe;
import com.wulf.digitalrecipebook.service.RecipeService;

@RestController
public class RecipeController {
	
	@Autowired
	RecipeService recipeService;
	
	
	@GetMapping(value="/start")
	public List<Recipe> fillRecipes() {
		return recipeService.recipeBuilder();
	}
	
	
	@GetMapping(value="/getRecipes")
	public List<Recipe> getRecipes() {
		return recipeService.getRecipes();
	}
	
	//might want to change this to /getRecipe/(name) and use @PathVariable 
	@GetMapping(value="/getRecipe")
	public Recipe getRecipe(@RequestParam("name") String name) {
		return recipeService.getRecipe(name); 
	}
	
	@GetMapping(value="/getRecipe/{name}")
	public Recipe getRecipeSpecific(@PathVariable("name") String name) {
		return recipeService.getRecipe(name); 
	}
	
	@PostMapping(value="/addRecipe")
	//@RequestParam(name = "name")
	// if query param sent is named same as @RequestParam("value") then don't need to specify the ("value") part
	public Recipe addRecipe(@RequestParam("name")String name) {
		recipeService.addRecipe(name);
		return recipeService.getRecipe(name);
	}
	
	@PutMapping(value="/update")
	public Recipe updateRecipe(@RequestBody Recipe recipe) {
		return recipeService.updateRecipe(recipe);
	}
	

}
