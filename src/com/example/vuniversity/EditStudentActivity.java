package com.example.vuniversity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import classes.Student;
import classes.TestAdapter;
import classes.Utility;

public class EditStudentActivity extends MainActivity {
	Button buttonEdit;
	EditText editTextStudentName, editTextStudentSurname, editTextStudentEska;
	String groupId, studentId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_student);
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				groupId = null;
				studentId = null;
			} else {
				groupId = extras.getString("groupId");
				studentId = extras.getString("studentId");
			}
		} else {
			groupId = (String) savedInstanceState.getSerializable("groupId");
			studentId = (String) savedInstanceState
					.getSerializable("studentId");
		}

		buttonEdit = (Button) findViewById(R.id.buttonSubmitNewStudent);
		editTextStudentName = (EditText) findViewById(R.id.editTextStudentName);
		editTextStudentSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		editTextStudentEska = (EditText) findViewById(R.id.editTextStudentEska);

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		editTextStudentName.setText((CharSequence) mDbHelper
				.getStudentById(studentId));

	}

	// public void onClickAdd(View view) {
	// if (editTextStudentName.getText().length() <= 0
	// || editTextStudentSurname.getText().length() <= 0
	// || editTextStudentEska.getText().length() <= 0) {
	// Utility.ShowMessageBox(view.getContext(),
	// "Fill all the fields please..");
	// return;
	// }
	//
	// String name = editTextStudentName.getText().toString();
	// String surname = editTextStudentSurname.getText().toString();
	// String eska = editTextStudentEska.getText().toString();
	//
	// Student student = new Student(studentId, name,
	// surname, eska, groupId);
	//
	// TestAdapter mDbHelper = new TestAdapter(this);
	// mDbHelper.createDatabase();
	// mDbHelper.open();
	// if (mDbHelper.EditStudent(student, studentId)) {
	// Utility.ShowMessageBox(this, "Student edited");
	// finish();
	// } else {
	// Utility.ShowMessageBox(this, "OOPS try again!");
	// }
	//
	// }
}
