package com.example.vuniversity;

public class Student {
	private int Id;
	private String Name;
	private String Surname;

	public Student() {
		Id = -1;
		Name = null;
		Surname = null;
	}
	
	public Student(int id, String name, String surname) {
		super();
		Id = id;
		Name = name;
		Surname = surname;
	}

	public Student(String name, String surname) {
		super();
		Name = name;
		Surname = surname;
	}

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	@Override
	public String toString() {
		return Name + " " + Surname;
	}

	

	
}
