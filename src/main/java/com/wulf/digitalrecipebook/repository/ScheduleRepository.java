package com.wulf.digitalrecipebook.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wulf.digitalrecipebook.model.calendar.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, LocalDate> {

}
