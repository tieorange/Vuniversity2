package classes;

public class Student {

	public Student(String eska) {
		super();
		Eska = eska;
	}

	private String Id;
	private String Name;
	private String Surname;
	private String Eska;
	private String GroupId;

	public Student() {
		Id = "-1";
		Name = null;
		Surname = null;
		Eska = null;
		GroupId = null;
	}

	public Student(String name, String surname) {
		super();
		Name = name;
		Surname = surname;
	}

	public Student(String id, String name, String surname) {
		super();
		Id = id;
		Name = name;
		Surname = surname;
	}

	public Student(String id, String name, String surname, String eska,
			String groupId) {
		super();
		Id = id;
		Name = name;
		Surname = surname;
		Eska = eska;
		GroupId = groupId;
	}

	public String getEska() {
		return Eska;
	}

	public String getGroupId() {
		return GroupId;
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

	public void setEska(String eska) {
		Eska = eska;
	}

	public void setGroupId(String groupId) {
		GroupId = groupId;
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
		return Surname + " " + Name + "\n S" + Eska;
	}

}
