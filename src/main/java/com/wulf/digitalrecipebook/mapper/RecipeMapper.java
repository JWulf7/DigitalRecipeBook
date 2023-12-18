package com.wulf.digitalrecipebook.mapper;

import com.wulf.digitalrecipebook.dto.RecipeDto;
import com.wulf.digitalrecipebook.model.recipe.Recipe;

public class RecipeMapper {

	public static RecipeDto mapToRecipeDto(Recipe recipe) {
		return new RecipeDto(
				recipe.getId(),
				recipe.getName(),
				recipe.getDescription(),
				recipe.getVersion(),
				recipe.getIngredients(),
				recipe.getMethod(),
				recipe.getServings(),
				recipe.getPrepTime(),
				recipe.getActiveTime(),
				recipe.getTotalTime(),
				recipe.getEquipment(),
				recipe.getPairings(),
				recipe.getNotes(),
				recipe.getRating(),
				recipe.getAuthor(),
				recipe.getFoodOrDrink(),
				recipe.getPictures(),
				recipe.getOftenMadeAlongside(),
				recipe.getSeasonality(),
				recipe.getTags(),
				recipe.getPairsWith(),
				recipe.getNotesInPlaceCollapse(),
				recipe.getOrigin(),
				recipe.getEaseLevel(),
				recipe.getMeal(),
				recipe.getCategory(),
				recipe.getHowToStore(),
				recipe.getHowToReheat(),
				recipe.getHowToFreeze(),
				recipe.getHowToUseRepurposeLeftoversIdeas(),
				recipe.getDishesThatAlsoUseLeftoverIngredients(),
				recipe.getMealAffinities(),
				recipe.getLastCooked(),
				recipe.getCreated(),
				recipe.getAllDatesCooked(),
				recipe.getAllDatesUpdated()
				);
	}
	
	public static Recipe mapToRecipe(RecipeDto recipe) {
		return new Recipe(
				recipe.getId(),
				recipe.getName(),
				recipe.getDescription(),
				recipe.getVersion(),
				recipe.getIngredients(),
				recipe.getMethod(),
				recipe.getServings(),
				recipe.getPrepTime(),
				recipe.getActiveTime(),
				recipe.getTotalTime(),
				recipe.getEquipment(),
				recipe.getPairings(),
				recipe.getNotes(),
				recipe.getRating(),
				recipe.getAuthor(),
				recipe.getFoodOrDrink(),
				recipe.getPictures(),
				recipe.getOftenMadeAlongside(),
				recipe.getSeasonality(),
				recipe.getTags(),
				recipe.getPairsWith(),
				recipe.getNotesInPlaceCollapse(),
				recipe.getOrigin(),
				recipe.getEaseLevel(),
				recipe.getMeal(),
				recipe.getCategory(),
				recipe.getHowToStore(),
				recipe.getHowToReheat(),
				recipe.getHowToFreeze(),
				recipe.getHowToUseRepurposeLeftoversIdeas(),
				recipe.getDishesThatAlsoUseLeftoverIngredients(),
				recipe.getMealAffinities(),
				recipe.getLastCooked(),
				recipe.getCreated(),
				recipe.getAllDatesCooked(),
				recipe.getAllDatesUpdated()
				);
	}
	
}
