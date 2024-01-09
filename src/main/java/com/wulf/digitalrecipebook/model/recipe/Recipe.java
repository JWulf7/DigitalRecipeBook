package com.wulf.digitalrecipebook.model.recipe;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

//import javax.persistence.Id;


@Entity
//@Component
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	// changed this since ran the program last..comment out below line if issues occur
	@Column(nullable = false)
	private String name;
	
	private String description;
	
	private double version;
	
	@JsonManagedReference
	@ManyToMany
	private Set<Ingredient> ingredients; 
	
	private List<String> method;
	
	private int servings;
	
	private LocalDateTime prepTime;
	
	private LocalDateTime activeTime;
	
	private LocalDateTime totalTime;
	
	//@Enumerated(EnumType.STRING)
	@OneToMany
	private List<Equipment> equipment;
	
	@ManyToMany
	private Set<RecipeName> pairings;

	/**
	//@OneToMany
	//@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@ElementCollection // 1
    @CollectionTable(name = "recipe_notes", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "notes") // 3
    */
	private List<String> notes;
	
	private int rating;
	
	private String author;
	
	//@Enumerated(EnumType.STRING)
	private FoodorDrink foodOrDrink; 
	
	private List<Blob> pictures;
	
	@ManyToMany
	private List<RecipeName> oftenMadeAlongside; // in the same meal/ dish/ within 7 days of that recipe/ etc
	
	//@Enumerated(EnumType.STRING)
	private Seasonality seasonality;
	
	private List<String> tags;

	@ManyToMany
	private List<RecipeName> pairsWith;
	
	private Boolean notesInPlaceCollapse;
	
	private String origin;
	
	//@Enumerated(EnumType.STRING)
	private EaseLevel easeLevel;
	
	//@Enumerated(EnumType.STRING)
	private Meal meal;
	
	private String category;
	
	// Leftovers
	
	private String howToStore;
	
	private String howToReheat;
	
	private String howToFreeze;
	
	private List<String> howToUseRepurposeLeftoversIdeas;
	
	@ManyToMany
	private List<RecipeName> dishesThatAlsoUseLeftoverIngredients;
	
	@ManyToMany
	private List<RecipeName> mealAffinities; // this goes really well w/ _ meals in the week 
	
	// date/time
	
	private LocalDateTime lastCooked;
	
	private LocalDateTime created;
	
	private List<LocalDateTime> allDatesCooked;
	
	private List<LocalDateTime> allDatesUpdated;



	public Recipe() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Recipe(String name) {
		super();
		this.name = name;
		//this.created = LocalDateTime.now();
		this.version = 1.0;
	}
	
	public Recipe(String name, String description)  {
		super();
		this.name = name;
		this.description = description;
		this.version = 1.0;
	}
	

	

	public Recipe(int id, String name, String description, double version, Set<Ingredient> ingredients,
			List<String> method, int servings, LocalDateTime prepTime, LocalDateTime activeTime,
			LocalDateTime totalTime, List<Equipment> equipment, Set<RecipeName> pairings, List<String> notes, int rating,
			String author, FoodorDrink foodOrDrink, List<Blob> pictures, List<RecipeName> oftenMadeAlongside,
			Seasonality seasonality, List<String> tags, List<RecipeName> pairsWith, Boolean notesInPlaceCollapse, String origin,
			EaseLevel easeLevel, Meal meal, String category, String howToStore, String howToReheat, String howToFreeze,
			List<String> howToUseRepurposeLeftoversIdeas, List<RecipeName> dishesThatAlsoUseLeftoverIngredients, List<RecipeName> mealAffinities,
			LocalDateTime lastCooked, LocalDateTime created, List<LocalDateTime> allDatesCooked,
			List<LocalDateTime> allDatesUpdated) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.version = version;
		this.ingredients = ingredients;
		this.method = method;
		this.servings = servings;
		this.prepTime = prepTime;
		this.activeTime = activeTime;
		this.totalTime = totalTime;
		this.equipment = equipment;
		this.pairings = pairings;
		this.notes = notes;
		this.rating = rating;
		this.author = author;
		this.foodOrDrink = foodOrDrink;
		this.pictures = pictures;
		this.oftenMadeAlongside = oftenMadeAlongside;
		this.seasonality = seasonality;
		this.tags = tags;
		this.pairsWith = pairsWith;
		this.notesInPlaceCollapse = notesInPlaceCollapse;
		this.origin = origin;
		this.easeLevel = easeLevel;
		this.meal = meal;
		this.category = category;
		this.howToStore = howToStore;
		this.howToReheat = howToReheat;
		this.howToFreeze = howToFreeze;
		this.howToUseRepurposeLeftoversIdeas = howToUseRepurposeLeftoversIdeas;
		this.dishesThatAlsoUseLeftoverIngredients = dishesThatAlsoUseLeftoverIngredients;
		this.mealAffinities = mealAffinities;
		this.lastCooked = lastCooked;
		this.created = created;
		this.allDatesCooked = allDatesCooked;
		this.allDatesUpdated = allDatesUpdated;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}
	
	

	

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	/***/
	public List<String> getMethod() {
		return method;
	}

	public void setMethod(List<String> method) {
		this.method = method;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public LocalDateTime getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(LocalDateTime prepTime) {
		this.prepTime = prepTime;
	}

	public LocalDateTime getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(LocalDateTime activeTime) {
		this.activeTime = activeTime;
	}

	public LocalDateTime getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(LocalDateTime totalTime) {
		this.totalTime = totalTime;
	}

	public List<Equipment> getEquipment() {
		return equipment;
	}

	public void setEquipment(List<Equipment> equipment) {
		this.equipment = equipment;
	}

	public Set<RecipeName> getPairings() {
		return pairings;
	}

	public void setPairings(Set<RecipeName> pairings) {
		this.pairings = pairings;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public FoodorDrink getFoodOrDrink() {
		return foodOrDrink;
	}

	public void setFoodOrDrink(FoodorDrink foodOrDrink) {
		this.foodOrDrink = foodOrDrink;
	}

	public List<Blob> getPictures() {
		return pictures;
	}

	public void setPictures(List<Blob> pictures) {
		this.pictures = pictures;
	}

	public List<RecipeName> getOftenMadeAlongside() {
		return oftenMadeAlongside;
	}

	public void setOftenMadeAlongside(List<RecipeName> oftenMadeAlongside) {
		this.oftenMadeAlongside = oftenMadeAlongside;
	}

	public Seasonality getSeasonality() {
		return seasonality;
	}

	public void setSeasonality(Seasonality seasonality) {
		this.seasonality = seasonality;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<RecipeName> getPairsWith() {
		return pairsWith;
	}

	public void setPairsWith(List<RecipeName> pairsWith) {
		this.pairsWith = pairsWith;
	}

	public Boolean getNotesInPlaceCollapse() {
		return notesInPlaceCollapse;
	}

	public void setNotesInPlaceCollapse(Boolean notesInPlaceCollapse) {
		this.notesInPlaceCollapse = notesInPlaceCollapse;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public EaseLevel getEaseLevel() {
		return easeLevel;
	}

	public void setEaseLevel(EaseLevel easeLevel) {
		this.easeLevel = easeLevel;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getHowToStore() {
		return howToStore;
	}

	public void setHowToStore(String howToStore) {
		this.howToStore = howToStore;
	}

	public String getHowToReheat() {
		return howToReheat;
	}

	public void setHowToReheat(String howToReheat) {
		this.howToReheat = howToReheat;
	}

	public String getHowToFreeze() {
		return howToFreeze;
	}

	public void setHowToFreeze(String howToFreeze) {
		this.howToFreeze = howToFreeze;
	}

	public List<String> getHowToUseRepurposeLeftoversIdeas() {
		return howToUseRepurposeLeftoversIdeas;
	}

	public void setHowToUseRepurposeLeftoversIdeas(List<String> howToUseRepurposeLeftoversIdeas) {
		this.howToUseRepurposeLeftoversIdeas = howToUseRepurposeLeftoversIdeas;
	}

	public List<RecipeName> getDishesThatAlsoUseLeftoverIngredients() {
		return dishesThatAlsoUseLeftoverIngredients;
	}

	public void setDishesThatAlsoUseLeftoverIngredients(List<RecipeName> dishesThatAlsoUseLeftoverIngredients) {
		this.dishesThatAlsoUseLeftoverIngredients = dishesThatAlsoUseLeftoverIngredients;
	}

	public List<RecipeName> getMealAffinities() {
		return mealAffinities;
	}

	public void setMealAffinities(List<RecipeName> mealAffinities) {
		this.mealAffinities = mealAffinities;
	}

	public LocalDateTime getLastCooked() {
		return lastCooked;
	}

	public void setLastCooked(LocalDateTime lastCooked) {
		this.lastCooked = lastCooked;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public List<LocalDateTime> getAllDatesCooked() {
		return allDatesCooked;
	}

	public void setAllDatesCooked(List<LocalDateTime> allDatesCooked) {
		this.allDatesCooked = allDatesCooked;
	}

	public List<LocalDateTime> getAllDatesUpdated() {
		return allDatesUpdated;
	}

	public void setAllDatesUpdated(List<LocalDateTime> allDatesUpdated) {
		this.allDatesUpdated = allDatesUpdated;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", description=" + description + ", version=" + version
				+ ", ingredients=" + ingredients + ", method=" + method + ", servings=" + servings + ", prepTime="
				+ prepTime + ", activeTime=" + activeTime + ", totalTime=" + totalTime + ", equipment=" + equipment
				+ ", pairings=" + pairings + ", notes=" + notes + ", rating=" + rating + ", author=" + author
				+ ", foodOrDrink=" + foodOrDrink + ", pictures=" + pictures + ", oftenMadeAlongside="
				+ oftenMadeAlongside + ", seasonality=" + seasonality + ", tags=" + tags + ", pairsWith=" + pairsWith
				+ ", notesInPlaceCollapse=" + notesInPlaceCollapse + ", origin=" + origin + ", easeLevel=" + easeLevel
				+ ", meal=" + meal + ", category=" + category + ", howToStore=" + howToStore + ", howToReheat="
				+ howToReheat + ", howToFreeze=" + howToFreeze + ", howToUseRepurposeLeftoversIdeas="
				+ howToUseRepurposeLeftoversIdeas + ", dishesThatAlsoUseLeftoverIngredients="
				+ dishesThatAlsoUseLeftoverIngredients + ", mealAffinities=" + mealAffinities + ", lastCooked="
				+ lastCooked + ", created=" + created + ", allDatesCooked=" + allDatesCooked + ", allDatesUpdated="
				+ allDatesUpdated + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(activeTime, allDatesCooked, allDatesUpdated, author, category, created, description,
				dishesThatAlsoUseLeftoverIngredients, easeLevel, equipment, foodOrDrink, howToFreeze, howToReheat,
				howToStore, howToUseRepurposeLeftoversIdeas, id, ingredients, lastCooked, meal, mealAffinities, method,
				name, notes, notesInPlaceCollapse, oftenMadeAlongside, origin, pairings, pairsWith, pictures, prepTime,
				rating, seasonality, servings, tags, totalTime, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		return Objects.equals(activeTime, other.activeTime) && Objects.equals(allDatesCooked, other.allDatesCooked)
				&& Objects.equals(allDatesUpdated, other.allDatesUpdated) && Objects.equals(author, other.author)
				&& Objects.equals(category, other.category) && Objects.equals(created, other.created)
				&& Objects.equals(description, other.description)
				&& Objects.equals(dishesThatAlsoUseLeftoverIngredients, other.dishesThatAlsoUseLeftoverIngredients)
				&& easeLevel == other.easeLevel && Objects.equals(equipment, other.equipment)
				&& foodOrDrink == other.foodOrDrink && Objects.equals(howToFreeze, other.howToFreeze)
				&& Objects.equals(howToReheat, other.howToReheat) && Objects.equals(howToStore, other.howToStore)
				&& Objects.equals(howToUseRepurposeLeftoversIdeas, other.howToUseRepurposeLeftoversIdeas)
				&& id == other.id && Objects.equals(ingredients, other.ingredients)
				&& Objects.equals(lastCooked, other.lastCooked) && meal == other.meal
				&& Objects.equals(mealAffinities, other.mealAffinities) && Objects.equals(method, other.method)
				&& Objects.equals(name, other.name) && Objects.equals(notes, other.notes)
				&& Objects.equals(notesInPlaceCollapse, other.notesInPlaceCollapse)
				&& Objects.equals(oftenMadeAlongside, other.oftenMadeAlongside) && Objects.equals(origin, other.origin)
				&& Objects.equals(pairings, other.pairings) && Objects.equals(pairsWith, other.pairsWith)
				&& Objects.equals(pictures, other.pictures) && Objects.equals(prepTime, other.prepTime)
				&& rating == other.rating && seasonality == other.seasonality && servings == other.servings
				&& Objects.equals(tags, other.tags) && Objects.equals(totalTime, other.totalTime)
				&& Double.doubleToLongBits(version) == Double.doubleToLongBits(other.version);
	}
	
	/***/
	
	
}
