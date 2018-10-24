/*package com.java.dto;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
@Entity
public class Standard {
	@Id
	@GeneratedValue
	private int id;
	@ManyToMany
	Map<Integer, Integer> students= new HashMap<>();

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<Student, Integer> getStudents() {
		return students;
	}

	public void setStudents(Map<Student, Integer> students) {
		this.students = students;
	}

}
*/