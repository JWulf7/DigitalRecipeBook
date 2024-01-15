package com.wulf.digitalrecipebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wulf.digitalrecipebook.model.recipe.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {

}
