package classes;

public class TeacherGroup {
	private String Id;
	private String TeacherId;
	private String SubjectId;
	private String GroupId;
	private String GroupName;
	private String SubjectName;	
	private String TeacherName;
	private String TeacherSurname;
	
	
	public TeacherGroup() {
		super();
	}
	public TeacherGroup(String teacherId, String subjectId, String groupId) {
		super();
		TeacherId = teacherId;
		SubjectId = subjectId;
		GroupId = groupId;
	}
	public TeacherGroup(String id, String teacherId, String subjectId,
			String groupId) {
		super();
		Id = id;
		TeacherId = teacherId;
		SubjectId = subjectId;
		GroupId = groupId;
	}
	public TeacherGroup(String teacherId, String subjectId, String groupId,
			String subjectName, String groupName) {
		super();
		TeacherId = teacherId;
		SubjectId = subjectId;
		GroupId = groupId;
		SubjectName = subjectName;
		GroupName = groupName;
	}
	public TeacherGroup(String id, String teacherId, String subjectId,
			String groupId, String subjectName, String groupName) {
		super();
		Id = id;
		TeacherId = teacherId;
		SubjectId = subjectId;
		GroupId = groupId;
		SubjectName = subjectName;
		GroupName = groupName;
	}
	public String getGroupId() {
		return GroupId;
	}
	public String getGroupName() {
		return GroupName;
	}
	public String getId() {
		return Id;
	}
	public String getSubjectId() {
		return SubjectId;
	}
	public String getSubjectName() {
		return SubjectName;
	}
	public String getTeacherId() {
		return TeacherId;
	}
	public String getTeacherName() {
		return TeacherName;
	}
	public String getTeacherSurname() {
		return TeacherSurname;
	}
	public void setGroupId(String groupId) {
		GroupId = groupId;
	}
	public void setGroupName(String groupName) {
		GroupName = groupName;
	}
	public void setId(String id) {
		Id = id;
	}
	public void setSubjectId(String subjectId) {
		SubjectId = subjectId;
	}
	public void setSubjectName(String subjectName) {
		SubjectName = subjectName;
	}
	public void setTeacherId(String teacherId) {
		TeacherId = teacherId;
	}
	public void setTeacherName(String teacherName) {
		TeacherName = teacherName;
	}
	public void setTeacherSurname(String teacherSurname) {
		TeacherSurname = teacherSurname;
	}
	@Override
	public String toString() {
		return getSubjectName() + "\n" + getGroupName();
	}
	

}
