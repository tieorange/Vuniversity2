package classes;

public class Teacher {
	private String Id;
	private String Name;
	private String Surname;

	public Teacher() {
		super();
	}

	public Teacher(String name, String surname) {
		super();
		Name = name;
		Surname = surname;
	}

	public Teacher(String id, String name, String surname) {
		super();
		Id = id;
		Name = name;
		Surname = surname;
	}

	public String getId() {
		return Id;
	}

	public String getName() {
		return Name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setId(String id) {
		Id = id;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	@Override
	public String toString() {
		return getName()+ " " + getSurname();
	}
}
