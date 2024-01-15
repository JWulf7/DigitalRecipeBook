/*
 * package com.wulf.digitalrecipebook.model.recipe;
 * 
 * import java.io.Serializable; import java.util.Map; import java.util.Objects;
 * import java.util.Set; import java.util.TreeMap;
 * 
 * import com.fasterxml.jackson.annotation.JsonBackReference; import
 * com.fasterxml.jackson.annotation.JsonIgnore;
 * 
 * import jakarta.persistence.Embeddable; import jakarta.persistence.Entity;
 * import jakarta.persistence.Id; import jakarta.persistence.ManyToMany;
 * 
 * @Entity
 * 
 * @Embeddable public class IngredientsList implements Serializable{
 * 
 * @Id public int recipeId;
 * 
 * //@ManyToMany private Map<Ingredient, String> ingredients = new
 * TreeMap<Ingredient, String>();
 * 
 * 
 * public IngredientsList() { super(); }
 * 
 * public IngredientsList(int recipeId) { super(); this.setRecipeId(recipeId); }
 * 
 * //getter and setters public void setRecipeId(int recipeId) { this.recipeId =
 * recipeId; }
 * 
 * public int getRecipeId() { return this.recipeId; }
 * 
 * public void setIngredients(Map<Ingredient, String> ingredientMap) {
 * this.ingredients = ingredientMap; }
 * 
 * public Map<Ingredient, String> getIngredients() { return this.ingredients; }
 * 
 * // toString
 * 
 * @Override public String toString() { return "IngredientsList [recipeId=" +
 * recipeId + ", ingredients=" + ingredients + "]"; }
 * 
 * @Override public int hashCode() { return Objects.hash(ingredients, recipeId);
 * }
 * 
 * @Override public boolean equals(Object obj) { if (this == obj) return true;
 * if (obj == null) return false; if (getClass() != obj.getClass()) return
 * false; IngredientsList other = (IngredientsList) obj; return
 * Objects.equals(ingredients, other.ingredients) && recipeId == other.recipeId;
 * }
 * 
 * 
 * 
 * }
 */