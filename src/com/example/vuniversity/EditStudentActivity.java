package com.example.vuniversity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import classes.Student;
import classes.TestAdapter;
import classes.Utility;

public class EditStudentActivity extends MainActivity {
	Button buttonEdit;
	EditText editTextName, editTextSurname, editTextEska;
	String groupId, studentId;

	// finish editing
	public void onClickAdd(View view) {
		if (editTextName.getText().length() <= 0
				|| editTextSurname.getText().length() <= 0
				|| editTextEska.getText().length() <= 0) {
			Utility.ShowMessageBox(view.getContext(),
					"Fill all the fields please..");
			return;
		}

		// get data from fields
		String name = editTextName.getText().toString();
		String surname = editTextSurname.getText().toString();
		String eska = editTextEska.getText().toString();

		Student item = new Student(studentId, name, surname, eska, groupId);

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		if (mDbHelper.EditStudent(item, studentId)) {
			Utility.ShowMessageBox(this, "edited");
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}

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
		editTextName = (EditText) findViewById(R.id.editTextStudentName);
		editTextSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		editTextEska = (EditText) findViewById(R.id.editTextStudentEska);
		buttonEdit.setText("Save");

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// set data to fields
		Student item = mDbHelper.getStudentById(studentId);
		editTextName.setText((CharSequence) item.getName());
		editTextSurname.setText((CharSequence) item.getSurname());
		editTextEska.setText((CharSequence) item.getEska());

	}
}
