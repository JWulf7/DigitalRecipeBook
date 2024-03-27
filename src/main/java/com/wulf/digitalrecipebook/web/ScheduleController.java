package com.wulf.digitalrecipebook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wulf.digitalrecipebook.dto.RecipeDto;
import com.wulf.digitalrecipebook.model.calendar.Schedule;
import com.wulf.digitalrecipebook.service.ScheduleService;

@CrossOrigin("*") // allowing all incoming clients
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	@GetMapping(value="/today")
	public ResponseEntity<Schedule> getToday() {
		Schedule today = scheduleService.getScheduleToday();
		return new ResponseEntity<>(today, HttpStatus.OK);
	}
	
	@GetMapping(value="/nextSeven")
	public ResponseEntity<List<Schedule>> getNextSevenDays() {
		List<Schedule> sevenDays = scheduleService.getScheduleNextSevenDays();
		return new ResponseEntity<>(sevenDays, HttpStatus.OK);
	}
	
}
