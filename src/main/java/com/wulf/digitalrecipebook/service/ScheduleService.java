package com.wulf.digitalrecipebook.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wulf.digitalrecipebook.dto.RecipeDto;
import com.wulf.digitalrecipebook.model.calendar.Schedule;
import com.wulf.digitalrecipebook.model.recipe.Equipment;
import com.wulf.digitalrecipebook.model.recipe.PlannedMeal;
import com.wulf.digitalrecipebook.model.recipe.RecipeName;
import com.wulf.digitalrecipebook.repository.PlannedMealRepository;
import com.wulf.digitalrecipebook.repository.RecipeNameRepository;
import com.wulf.digitalrecipebook.repository.ScheduleRepository;

@Service
public class ScheduleService {
// begin implementations
	
	private static LocalDate today = LocalDate.now();
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	PlannedMealRepository plannedMealRepository;
	
	@Autowired
	RecipeNameRepository recipeNameRepository;
	
	@Autowired
	RecipeService recipeService;
	
	public Schedule getScheduleToday() {
		
		Schedule dbToday = scheduleRepository.findById(today).orElse(new Schedule(today));
		return dbToday;
	}
	
	public Schedule getScheduleofDate(LocalDate date) {
		
		Schedule dbDate = scheduleRepository.findById(date).orElse(new Schedule(date));
		return dbDate;
	}
	
	public Schedule updateSchedule(Schedule schedule) {
		plannedMealCheck(schedule);
		Schedule updatedSchedule = scheduleRepository.save(schedule);
		// might need to think about cascading/updating the plannedMeals.allDatesCooked here?... or maybe when transfer ovre to
		return updatedSchedule;
	}
	
	public List<Schedule> getScheduleNextSevenDays() {
		// create list
		List<Schedule> nextSevenDays = new ArrayList<>();
		// while list is less than 7 days
		while(nextSevenDays.size() < 7) {
			
			// date of the day to be retrieved, starting w/ today
			LocalDate date = today.minusDays(nextSevenDays.size());
			// the schedule for that day, either if saved in DB or created now
			Schedule nextDay = scheduleRepository.findById(date).orElse(new Schedule(date));
			// add that schedule to arraylist
			nextSevenDays.add(nextDay);
		}
		return nextSevenDays;
	}
	
	public List<Schedule> getAllFutureSchedule() {
		return null;
	}
	
	public List<Schedule> getAllPastSchedule() {
		return null;
	}
	
	public List<Schedule> getAllPastandFutureSchedule() {
		return null;
	}
	
	public void plannedMealCheck(Schedule schedule) {
		System.out.println("MYLOGGER : ScheduleService Class.plannedMealCheck(schedule) : starting plannedMealCheck for the schedule = -> " + schedule);
		for (PlannedMeal plannedMeal : (List<PlannedMeal>)safeList(schedule.getTodaysPlannedMeals())) {
			System.out.println("MYLOGGER : ScheduleService Class.plannedMealCheck(schedule) : iterating over plannedMeal for todaysPlannedMeals; plannedMeal = -> " + plannedMeal);
			if(null == plannedMealRepository.findByMenuIn(plannedMeal.getMenu())) {	// can also try to do || find by id..?.. but id might not be good?... 
				System.out.println("MYLOGGER : ScheduleService Class.plannedMealCheck(schedule) : findMenuById was null for plannedMeal.getMenu() = -> " + plannedMeal.getMenu());
				plannedMeal = recipeNameCheckandSet(plannedMeal);	// now setting this = ...
				System.out.println("MYLOGGER : ScheduleService Class.plannedMealCheck(schedule) : findMenuById was null for plannedMeal.getMenu(); after recipeNameCheckandSet() plannedMeal = -> " + plannedMeal);
				
				// this might be where i need to return a PlannedMeal from recipeNameCheckandSet and set it before i save....
				plannedMealRepository.save(plannedMeal);
			}
		}
		for (PlannedMeal plannedMeal : (List<PlannedMeal>)safeList(schedule.getTomorrowsPlannedMeals())) {
			System.out.println("MYLOGGER : ScheduleService Class.plannedMealCheck(schedule) : iterating over plannedMeal for tomorrowsPlannedMeals; plannedMeal= -> " + plannedMeal);
			if(null == plannedMealRepository.findByMenuIn(plannedMeal.getMenu())) {	// can also try to do || find by id..?.. but id might not be good?... 
				recipeNameCheckandSet(plannedMeal);
				plannedMealRepository.save(plannedMeal);
			}
		}
	}
	
	public PlannedMeal recipeNameCheckandSet(PlannedMeal plannedMeal) {
		System.out.println("MYLOGGER : ScheduleService Class.recipeNameCheck(plannedMeal) : starting recipeNameCheck for the plannedMeal = -> " + plannedMeal);
		
		List<RecipeName> updatedListFromDB = new ArrayList<>();
		
		// iterate over all recipe's in the plannedMeal's menu
		for (RecipeName recipeName : (List<RecipeName>)safeList(plannedMeal.getMenu())) {
			System.out.println("MYLOGGER : ScheduleService Class.recipeNameCheck(plannedMeal) : iterating for recipeName = -> " + recipeName); 
			// if recipe cannot be found by name
			if(null == recipeNameRepository.findByName(recipeName.getName())) {	
				System.out.println("MYLOGGER : ScheduleService Class.recipeNameCheck(plannedMeal) : recipeName was null in DB, about to save using recipeService -> "); 
				// save recipe to db using recipeService
				RecipeDto savedRecipe = recipeService.addRecipe(recipeName.getName());
				System.out.println("MYLOGGER : ScheduleService Class.recipeNameCheck(plannedMeal) : recipeName was null in DB, saved new Recipe to DB using recipeService; savedRecipe = -> " + savedRecipe);
				// set the recipe in the menu to the saved recipe in DB
				recipeName = recipeNameRepository.findByName(savedRecipe.getName());
				//plannedMealRepository.save(plannedMeal);
			} else {
				System.out.println("MYLOGGER : ScheduleService Class.recipeNameCheck(plannedMeal) : recipeName was not null in DB"); 
				// set the recipe in the menu to the recipe in DB
				recipeName = recipeNameRepository.findByName(recipeName.getName());
				System.out.println("MYLOGGER : ScheduleService Class.recipeNameCheck(plannedMeal) : recipeName was not in DB, saved set recipeName to instance from DB; recipeName = -> " + recipeName);
				System.out.println("MYLOGGER : ScheduleService Class.recipeNameCheck(plannedMeal) : recipeName was not in DB, saved set recipeName to instance from DB; recipeName.getId() = -> " + recipeName.getId());
				
			}
			updatedListFromDB.add(recipeName);
		}
		
		plannedMeal.setMenu(updatedListFromDB);
		System.out.println("MYLOGGER : ScheduleService Class.recipeNameCheck(plannedMeal) : exiting recipeNameCheck for the plannedMeal = -> " + plannedMeal);
		System.out.println("MYLOGGER : ScheduleService Class.recipeNameCheck(plannedMeal) : exiting recipeNameCheck for the plannedMeal = -> " + plannedMeal);
		return plannedMeal;
	}
	
	
	
	public static List safeList(List other) {
	    return other == null ? Collections.EMPTY_LIST : other;
	}
	
	public static Set safeSet(Set other) {
		return other == null ? Collections.EMPTY_SET : other;
	}
	
}
