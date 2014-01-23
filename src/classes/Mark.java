package classes;

public class Mark {
	String id;
	String studentId;
	String subjectId;
	String teacherId;
	String mark;
	String subjectName;
	String teacherName;
	String teacherSurname;

	public Mark(String id, String studentId, String subjectId,
			String teacherId, String mark, String subjectName,
			String teacherName, String teacherSurname) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.teacherId = teacherId;
		this.mark = mark;
		this.subjectName = subjectName;
		this.teacherName = teacherName;
		this.teacherSurname = teacherSurname;
	}

	public Mark() {
		super();
	}

	public String getId() {
		return id;
	}

	public String getMark() {
		return mark;
	}

	public String getStudentId() {
		return studentId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public String getTeacherSurname() {
		return teacherSurname;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public void setTeacherSurname(String teacherSurname) {
		this.teacherSurname = teacherSurname;
	}

	@Override
	public String toString() {
		return getMark() + " - " + getSubjectName();
	}

}
