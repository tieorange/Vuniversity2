package classes;

public class Group {
	private String Id;
	private String Name;
	public Group() {
		super();
	}
	public Group(String name) {
		super();
		Name = name;
	}
	public Group(String id, String name) {
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
		return Name;
	}

}
