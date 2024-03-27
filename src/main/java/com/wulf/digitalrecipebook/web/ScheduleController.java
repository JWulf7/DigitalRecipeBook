package com.wulf.digitalrecipebook.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping(value="/{date}")
	public ResponseEntity<Schedule> getScheduleonDate(@PathVariable("date") LocalDate date) {
		System.out.println("In ScheduleController... 'date' getting from pathVariable = " + date); // Remove after debug
		Schedule schedule = scheduleService.getScheduleofDate(date);
		return new ResponseEntity<>(schedule, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value="/nextSeven")
	public ResponseEntity<List<Schedule>> getNextSevenDays() {
		List<Schedule> sevenDays = scheduleService.getScheduleNextSevenDays();
		return new ResponseEntity<>(sevenDays, HttpStatus.OK);
	}
	
	@GetMapping(value="/allHistory")
	public ResponseEntity<List<Schedule>> getallHistory() {
		List<Schedule> allHistory = scheduleService.getAllPastandFutureSchedule();
		return new ResponseEntity<>(allHistory, HttpStatus.OK);
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule) {
		System.out.println("MYLOGGER : started updateSchedule() method at '/update' endpoint in ScheduleController");
		Schedule updatedSchedule = scheduleService.updateSchedule(schedule);
		return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
	}
	
	
	

}
