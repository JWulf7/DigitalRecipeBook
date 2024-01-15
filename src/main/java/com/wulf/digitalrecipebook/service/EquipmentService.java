package com.wulf.digitalrecipebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wulf.digitalrecipebook.model.recipe.Equipment;
import com.wulf.digitalrecipebook.repository.EquipmentRepository;

@Service
public class EquipmentService {

	@Autowired
	EquipmentRepository equipmentRepository;
	
	public Equipment findEquipmentByName(String name) {
		return equipmentRepository.findById(name).orElse(null);
	}
	
	public Equipment saveEquipment(String equipmentName) {
		return equipmentRepository.save(new Equipment(equipmentName));
	}
	
}
