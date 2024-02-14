package com.wulf.digitalrecipebook.model.recipe;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RecipeName {

	@Id
	@JsonIgnore
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	// changed this since ran the program last..comment out below line if issues occur
	@Column(nullable = false)
	private String name;

	public RecipeName(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public RecipeName() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		// messing w/ this 
		System.out.println("MYLOGGER : in RecipeName Class.equals() : this -> " + this);
		System.out.println("MYLOGGER : in RecipeName Class.equals() : obj -> " + obj);
		if ((this.getId() == 0) && (this.getName().equals(obj)))
		//
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		System.out.println("MYLOGGER : in RecipeName Class.equals() : after most if statements");
		RecipeName other = (RecipeName) obj;
		return id == other.id && Objects.equals(name, other.name);
	}

	// messed w/ this
	@Override
	public String toString() {
		return "RecipeName [id=" + id + ", name=" + name + "]";
		//return name;
	}
	
	
}
