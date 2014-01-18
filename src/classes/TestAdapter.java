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
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public TestAdapter open() throws SQLException {
		try {
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
			String sql = "SELECT * FROM student";
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
		try {
			String sql = "SELECT * FROM student WHERE groupId = " + id;
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
			String sql = "SELECT * FROM student WHERE Eska = "
					+ student.getEska();
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
		try {
			String sql = "SELECT * FROM \"group\"";
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
			String sql = "SELECT * FROM \"group\" WHERE name = "
					+ group.getName();
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
		try {
			String sql = "SELECT * FROM subject";
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

	
	//***** TEACHERS &*****
	public ArrayList<Teacher> getAllTeachers() {
		try {
			String sql = "SELECT * FROM teacher";
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

	public void RemoveTeacherById(String id) {
		mDb.delete("teacher", "id=?", new String[] { (id) });
		
	}

}
