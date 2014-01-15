package com.example.vuniversity;

public class Group {
	private int Id;
	private String Name;
	public Group() {
		super();
	}
	public Group(String name) {
		super();
		Name = name;
	}
	public Group(int id, String name) {
		super();
		Id = id;
		Name = name;
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
	@Override
	public String toString() {
		return Name;
	}

}
