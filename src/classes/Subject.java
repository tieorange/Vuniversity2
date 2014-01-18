package classes;

public class Subject {
	private String Id;
	private String Name;

	public Subject() {
		Id = "-1";
		Name = "null";
	}

	public Subject(String name) {
		super();
		Name = name;
	}

	public Subject(String id, String name) {
		super();
		Id = id;
		Name = name;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
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
		return getName();
	}
}
