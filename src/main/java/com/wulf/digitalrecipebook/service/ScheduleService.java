package com.wulf.digitalrecipebook.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wulf.digitalrecipebook.model.calendar.Schedule;
import com.wulf.digitalrecipebook.repository.ScheduleRepository;

@Service
public class ScheduleService {
// begin implementations
	
	private static LocalDate today = LocalDate.now();
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	public Schedule getScheduleToday() {
		
		Schedule dbToday = scheduleRepository.findById(today).orElse(new Schedule(today));
		return dbToday;
	}
	
	public Schedule getScheduleofDate(LocalDate date) {
		
		Schedule dbDate = scheduleRepository.findById(date).orElse(new Schedule(date));
		return dbDate;
	}
	
	public Schedule updateSchedule(Schedule schedule) {
		
		Schedule updatedSchedule = scheduleRepository.save(schedule);
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
	
}
