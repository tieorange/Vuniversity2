package classes;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestAdapter {
	protected static final String TAG = "DataAdapter";

	private final Context mContext;
	private SQLiteDatabase mDb;
	private DataBaseHelper mDbHelper;

	public TestAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
	}

	public TestAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.close();
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public TestAdapter open() throws SQLException {

		try {
			mDbHelper.close();
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	// *********STUDENTS ********
	public void RemoveStudentById(String id) {
		mDb.delete("student", "id=?", new String[] { (id) });
	}

	public Student getStudentById(String id) {
		try {
			String sql = "SELECT * FROM student WHERE id = " + id;
			Student student = new Student();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					student.setId(mCur.getString(0));
					student.setName(mCur.getString(1));
					student.setSurname(mCur.getString(2));
					student.setEska(mCur.getString(3));
					student.setGroupId(mCur.getString(4));
				}
			}
			return student;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getData >>" + mSQLException.toString());
			throw mSQLException;
		}

	}

	public boolean AddStudent(String name, String surname, String eska,
			String groupId) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			cv.put("surname", surname);
			cv.put("Eska", eska);
			cv.put("groupId", groupId);

			mDb.insert("student", null, cv);

			Log.d(name + " " + surname + "  ADDED", "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d("Add error", ex.toString());
			return false;
		}
	}

	public boolean EditStudent(Student item, String id) {
		try {
			String strFilter = "id=" + id;
			ContentValues args = new ContentValues();
			args.put("name", item.getName());
			args.put("surname", item.getSurname());
			args.put("Eska", item.getEska());
			args.put("groupId", item.getGroupId());
			mDb.update("student", args, strFilter, null);
			return true;
		} catch (Exception ex) {
			Log.d("Edit error", ex.toString());
			return false;
		}
	}

	public ArrayList<Student> getAllStudents() {
		try {
			String sql = "SELECT * FROM student ORDER BY Eska ASC";
			ArrayList<Student> studentList = new ArrayList<Student>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						Student student = new Student();
						student.setId(mCur.getString(0));
						student.setName(mCur.getString(1));
						student.setSurname(mCur.getString(2));
						student.setEska(mCur.getString(3));
						student.setGroupId(mCur.getString(4));
						// Adding contact to list
						studentList.add(student);
					} while (mCur.moveToNext());
				}
			}
			return studentList;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}

	}

	public ArrayList<Student> getAllStudents(String id) {
		return getAllStudents(id, "id", "ASC");

	}

	public int IsStudentEskeExists(int lastStudentId) {
		ArrayList<Student> students = getAllStudents();
		for (Student student : students) {
			if (student.getEska().equals(Integer.toString(lastStudentId))) {
				do {
					lastStudentId++;
				} while (student.getEska().equals(
						Integer.toString(lastStudentId)));
			}
		}
		return lastStudentId;
	}

	public ArrayList<Student> getAllStudents(String id, String ORDER_BY,
			String ASC_DESC) {
		try {
			if (ORDER_BY == null)
				ORDER_BY = "id";
			if (ASC_DESC == null)
				ASC_DESC = "ASC";

			String sql = "SELECT * FROM student WHERE groupId = " + id
					+ " ORDER BY " + ORDER_BY + " " + ASC_DESC;
			ArrayList<Student> studentList = new ArrayList<Student>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						Student student = new Student();
						student.setId(mCur.getString(0));
						student.setName(mCur.getString(1));
						student.setSurname(mCur.getString(2));
						student.setEska(mCur.getString(3));
						student.setGroupId(mCur.getString(4));
						// Adding contact to list
						studentList.add(student);
					} while (mCur.moveToNext());
				}
			}
			return studentList;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}

	}

	public boolean isStudentExist(Student student) {
		try {
			String sql = "SELECT * FROM student WHERE Eska = " + "'"
					+ student.getEska() + "'";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur.getCount() > 0)
				return true;
			else
				return false;

		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	// ******** GROUPS *********
	public ArrayList<Group> getAllGroups() {
		return getAllGroups(Utility.ID, Utility.ASC);
	}

	public ArrayList<Group> getAllGroups(String ORDER_BY, String ASC_DESC) {
		try {
			if (ORDER_BY == null)
				ORDER_BY = "id";
			if (ASC_DESC == null)
				ASC_DESC = "ASC";
			String sql = "SELECT * FROM \"group\"" + " ORDER BY " + ORDER_BY
					+ " " + ASC_DESC;
			ArrayList<Group> groupList = new ArrayList<Group>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						Group group = new Group();
						group.setId(mCur.getString(0));
						group.setName(mCur.getString(1));
						// Adding contact to list
						groupList.add(group);
					} while (mCur.moveToNext());
				}
			}
			return groupList;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public boolean AddGroup(String name) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			mDb.insert("\"group\"", null, cv);

			Log.d(name + " ADDED", "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d("Add error", ex.toString());
			return false;
		}
	}

	public boolean EditGroup(Group item, String id) {
		try {
			String strFilter = "id=" + id;
			ContentValues args = new ContentValues();
			args.put("name", item.getName());
			mDb.update("\"group\"", args, strFilter, null);
			return true;
		} catch (Exception ex) {
			Log.d("Edit error", ex.toString());
			return false;
		}
	}

	public boolean isGroupExist(Group group) {
		try {
			String sql = "SELECT * FROM \"group\" WHERE name = " + "'"
					+ group.getName() + "'" + " COLLATE NOCASE ";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur.getCount() > 0)
				return true;
			else
				return false;

		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Group getGroupById(String id) {
		try {
			String sql = "SELECT * FROM \"group\" WHERE id = " + id;
			Group group = new Group();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					group.setId(mCur.getString(0));
					group.setName(mCur.getString(1));
				}
			}
			return group;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getData >>" + mSQLException.toString());
			throw mSQLException;
		}

	}

	public void RemoveGroupById(String id) {
		mDb.delete("\"group\"", "id=?", new String[] { (id) });
	}

	// ****** SUBJECT *****
	public ArrayList<Subject> getAllSubjects() {
		return getAllSubjects(Utility.ID, Utility.ASC);
	}

	public ArrayList<Subject> getAllSubjects(String ORDER_BY, String ASC_DESC) {
		try {
			if (ORDER_BY == null)
				ORDER_BY = "id";
			if (ASC_DESC == null)
				ASC_DESC = "ASC";
			String sql = "SELECT * FROM subject" + " ORDER BY " + ORDER_BY
					+ " " + ASC_DESC;
			ArrayList<Subject> subjectList = new ArrayList<Subject>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						Subject subject = new Subject();
						subject.setId(mCur.getString(0));
						subject.setName(mCur.getString(1));

						// Adding contact to list
						subjectList.add(subject);
					} while (mCur.moveToNext());
				}
			}
			return subjectList;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public ArrayList<Subject> getAllSubjectsWithMarks(String ORDER_BY,
			String ASC_DESC) {
		try {
			if (ORDER_BY == null)
				ORDER_BY = "id";
			if (ASC_DESC == null)
				ASC_DESC = "ASC";

			String sql = "SELECT s.id, s.name " + "FROM subject s "
					+ "INNER JOIN mark m ON s.id = m.subjectId "
					+ "GROUP BY s.id" + " ORDER BY " + "s." + ORDER_BY + " "
					+ ASC_DESC;
			ArrayList<Subject> subjectList = new ArrayList<Subject>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						Subject subject = new Subject();
						subject.setId(mCur.getString(0));
						subject.setName(mCur.getString(1));

						// Adding contact to list
						subjectList.add(subject);
					} while (mCur.moveToNext());
				}
			}
			return subjectList;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public ArrayList<Subject> getSubjectsOfGroup(String groupId) {
		try {
			String sql = "SELECT s.id, s.name" + " FROM teacher_groups tg"
					+ " INNER JOIN subject s ON tg.subject_id = s.id"
					+ " WHERE group_id = " + groupId;
			ArrayList<Subject> subjectList = new ArrayList<Subject>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						Subject subject = new Subject();
						subject.setId(mCur.getString(0));
						subject.setName(mCur.getString(1));

						// Adding contact to list
						subjectList.add(subject);
					} while (mCur.moveToNext());
				}
			}
			return subjectList;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public boolean isSubjectExist(Subject subject) {
		try {
			String sql = "SELECT * FROM subject WHERE name = " + "'"
					+ subject.getName() + "'" + " COLLATE NOCASE ";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur.getCount() > 0)
				return true;
			else
				return false;

		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public void RemoveSubjectById(String id) {
		mDb.delete("subject", "id=?", new String[] { (id) });

	}

	public boolean AddSubject(String name) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			mDb.insert("subject", null, cv);

			Log.d(name + " ADDED", "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d("Add group error", ex.toString());
			return false;
		}
	}

	public Subject getSubjectById(String id) {
		try {
			String sql = "SELECT * FROM subject WHERE id = " + id;
			Subject subject = new Subject();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					subject.setId(mCur.getString(0));
					subject.setName(mCur.getString(1));
				}
			}
			return subject;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public boolean EditSubject(Subject item, String id) {
		try {
			String strFilter = "id=" + id;
			ContentValues args = new ContentValues();
			args.put("name", item.getName());
			mDb.update("subject", args, strFilter, null);
			return true;
		} catch (Exception ex) {
			Log.d("Edit error", ex.toString());
			return false;
		}
	}

	// ***** TEACHERS &*****
	public ArrayList<Teacher> getAllTeachers() {
		return getAllTeachers(Utility.ID, Utility.ASC);
	}

	public ArrayList<Teacher> getAllTeachers(String ORDER_BY, String ASC_DESC) {
		try {
			if (ORDER_BY == null)
				ORDER_BY = "id";
			if (ASC_DESC == null)
				ASC_DESC = "ASC";

			String sql = "SELECT * FROM teacher" + " ORDER BY " + ORDER_BY
					+ " " + ASC_DESC;
			ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						Teacher teacher = new Teacher();
						teacher.setId(mCur.getString(0));
						teacher.setName(mCur.getString(1));
						teacher.setSurname(mCur.getString(2));
						// Adding contact to list
						teacherList.add(teacher);
					} while (mCur.moveToNext());
				}
			}
			return teacherList;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Teacher getTeacherByGroupAndSubject(String groupId, String subjectId) {
		try {
			String sql = "SELECT t.id, t.name, t.surname FROM teacher t "
					+ "INNER JOIN teacher_groups tg ON t.id = tg.teacher_id"
					+ " WHERE tg.subject_id = " + subjectId
					+ " AND tg.group_id = " + groupId;

			Teacher teacher = new Teacher();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					teacher = new Teacher();
					teacher.setId(mCur.getString(0));
					teacher.setName(mCur.getString(1));
					teacher.setSurname(mCur.getString(2));
				}
			}
			return teacher;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public void RemoveTeacherById(String id) {
		mDb.delete("teacher", "id=?", new String[] { (id) });

	}

	public Teacher getTeacherById(String id) {
		try {
			String sql = "SELECT * FROM teacher WHERE id = " + id;
			Teacher teacher = new Teacher();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					teacher.setId(mCur.getString(0));
					teacher.setName(mCur.getString(1));
					teacher.setSurname(mCur.getString(2));
				}
			}
			return teacher;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public boolean isTeacherExist(Teacher item) {
		try {
			String sql = "SELECT * FROM teacher WHERE name = " + "'"
					+ item.getName() + "'" + " COLLATE NOCASE"
					+ " AND surname = " + "'" + item.getSurname() + "'"
					+ " COLLATE NOCASE ";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur.getCount() > 0)
				return true;
			else
				return false;

		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public boolean EditTeacher(Teacher item, String id) {
		try {
			String strFilter = "id=" + id;
			ContentValues args = new ContentValues();
			args.put("name", item.getName());
			args.put("surname", item.getSurname());
			mDb.update("teacher", args, strFilter, null);
			return true;
		} catch (Exception ex) {
			Log.d("Edit error", ex.toString());
			return false;
		}
	}

	public boolean AddTeacher(String name, String surname) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			cv.put("surname", surname);

			mDb.insert("teacher", null, cv);

			Log.d(name + " " + surname + "  ADDED", "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d("Add error", ex.toString());
			return false;
		}
	}

	// **** TEACHER SUBJECT ******
	public boolean AddTeacherGroup(String teacherId, String subjectId,
			String groupId) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("teacher_id", teacherId);
			cv.put("subject_id", subjectId);
			cv.put("group_id", groupId);

			mDb.insert("teacher_groups", null, cv);

			Log.d("  ADDED", "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d("Add error", ex.toString());
			return false;
		}
	}

	public boolean IsSubjectInGroupExists(String subjectId, String groupId) {
		try {
			String sql = "SELECT * FROM teacher_groups WHERE group_id = "
					+ groupId + " AND subject_id = " + subjectId;
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur.getCount() > 0)
				return true;
			else
				return false;

		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public ArrayList<TeacherGroup> getAllTeacherGroups() {
		try {

			String sql = "SELECT	tg.id, t.id,	tg.subject_id, tg.group_id,g.name, s.name, t.name, t.surname"
					+ " FROM teacher t"
					+ " INNER JOIN teacher_groups tg ON t.id = tg.teacher_id"
					+ " INNER JOIN subject s ON tg.subject_id = s.id"
					+ " INNER JOIN \"group\" g ON tg.group_id = g.id";
			ArrayList<TeacherGroup> teacherGroupList = new ArrayList<TeacherGroup>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						TeacherGroup item = new TeacherGroup();
						item.setId(mCur.getString(0));
						item.setTeacherId(mCur.getString(1));
						item.setSubjectId(mCur.getString(2));
						item.setGroupId(mCur.getString(3));
						item.setGroupName(mCur.getString(4));
						item.setSubjectName(mCur.getString(5));
						item.setTeacherName(mCur.getString(6));
						item.setTeacherSurname(mCur.getString(7));
						// Adding contact to list
						teacherGroupList.add(item);
					} while (mCur.moveToNext());
				}
			}
			return teacherGroupList;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public void RemoveTeacherGroupById(String id) {
		mDb.delete("teacher_groups", "id=?", new String[] { (id) });
	}

	public ArrayList<TeacherGroup> getAllTeacherGroups(String id) {
		try {

			String sql = "SELECT	tg.id, t.id,	tg.subject_id, tg.group_id,g.name, s.name, t.name, t.surname"
					+ " FROM teacher t"
					+ " INNER JOIN teacher_groups tg ON t.id = tg.teacher_id"
					+ " INNER JOIN subject s ON tg.subject_id = s.id"
					+ " INNER JOIN \"group\" g ON tg.group_id = g.id"
					+ " WHERE t.id = " + id;
			ArrayList<TeacherGroup> teacherGroupList = new ArrayList<TeacherGroup>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						TeacherGroup item = new TeacherGroup();
						item.setId(mCur.getString(0));
						item.setTeacherId(mCur.getString(1));
						item.setSubjectId(mCur.getString(2));
						item.setGroupId(mCur.getString(3));
						item.setGroupName(mCur.getString(4));
						item.setSubjectName(mCur.getString(5));
						item.setTeacherName(mCur.getString(6));
						item.setTeacherSurname(mCur.getString(7));
						// Adding contact to list
						teacherGroupList.add(item);
					} while (mCur.moveToNext());
				}
			}
			return teacherGroupList;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	// *****MARKS *******
	public boolean AddMark(String studentId, String subjectId, String groupId,
			String mark) {
		try {
			ContentValues cv = new ContentValues();
			Teacher teacher = getTeacherByGroupAndSubject(groupId, subjectId);
			cv.put("studentId", studentId);
			cv.put("subjectId", subjectId);
			cv.put("teacherId", teacher.getId());
			cv.put("mark", mark);

			mDb.insert("mark", null, cv);

			Log.d(" ADDED", "informationsaved");
			return true;

		} catch (Exception ex) {
			Log.d("Add error", ex.toString());
			return false;
		}
	}

	public ArrayList<Mark> getMarksOfStudent(String studentId) {
		try {
			String sql = "SELECT m.id, m.studentId, m.subjectId, m.teacherId, m.mark, s.name, t.name, t.surname"
					+ " FROM mark m"
					+ " INNER JOIN teacher t ON t.id = m.teacherId"
					+ " INNER JOIN subject s ON s.id = m.subjectId"
					+ " WHERE m.studentId = " + studentId;
			ArrayList<Mark> list = new ArrayList<Mark>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						Mark item = new Mark();
						item.setId(mCur.getString(0));
						item.setStudentId(mCur.getString(1));
						item.setSubjectId(mCur.getString(2));
						item.setTeacherId(mCur.getString(3));
						item.setMark(mCur.getString(4));
						item.setSubjectName(mCur.getString(5));
						item.setTeacherName(mCur.getString(6));
						item.setTeacherSurname(mCur.getString(7));
						// Adding contact to list
						list.add(item);
					} while (mCur.moveToNext());
				}
			}
			return list;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public void RemoveMarkById(String id) {
		mDb.delete("mark", "id=?", new String[] { (id) });
	}

	// *******AVERAGE MARKS ***********
	public ArrayList<AverageStudentMarkOfSubject> getAverageMarksOfStudents(
			String subjectId, String ORDER_BY, String ASC_DESC) {
		try {
			if (ORDER_BY == null)
				ORDER_BY = Utility.AVERAGE_MARK;
			if (ASC_DESC == null)
				ASC_DESC = Utility.DESC;

			String sql = "SELECT avg(m.mark), s.name, s.surname, g.name"
					+ " FROM mark m"
					+ " INNER JOIN student s ON s.id = m.studentId"
					+ " INNER JOIN \"group\" g ON g.id = s.groupId"
					+ " WHERE m.subjectId = " + subjectId
					+ " GROUP BY studentId" + " ORDER BY " + ORDER_BY + " "
					+ ASC_DESC;
			ArrayList<AverageStudentMarkOfSubject> list = new ArrayList<AverageStudentMarkOfSubject>();
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				if (mCur.moveToFirst()) {
					do {
						AverageStudentMarkOfSubject item = new AverageStudentMarkOfSubject();
						item.setAverageMark(mCur.getString(0));
						item.setName(mCur.getString(1));
						item.setSurname(mCur.getString(2));
						item.setGroupName(mCur.getString(3));
						// Adding contact to list
						list.add(item);
					} while (mCur.moveToNext());
				}
			}
			return list;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getList >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

}
