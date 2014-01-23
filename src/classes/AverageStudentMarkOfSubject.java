package classes;

public class AverageStudentMarkOfSubject {
	private String averageMark;
	private String name;
	private String surname;
	private String groupName;

	public AverageStudentMarkOfSubject(String averageMark, String name,
			String surname, String groupName) {
		super();
		this.averageMark = averageMark;
		this.name = name;
		this.surname = surname;
		this.groupName = groupName;
	}

	public AverageStudentMarkOfSubject() {
		super();
	}

	public String getAverageMark() {
		return averageMark;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public void setAverageMark(String averageMark) {
		this.averageMark = averageMark;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return getAverageMark() + "\n " + getSurname() + " " + getName() + " "
				+ getGroupName();
	}

}
