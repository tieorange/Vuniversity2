package com.example.vuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import classes.Teacher;
import classes.TestAdapter;
import classes.Utility;

public class EditTeacherActivity extends MainActivity {
	Button buttonEdit;
	EditText editTextName, editTextSurname;
	String teacherId;

	// finish editing
	public void onClickAdd(View view) {
		if (editTextName.getText().length() <= 0
				|| editTextSurname.getText().length() <= 0) {
			Utility.ShowMessageBox(view.getContext(),
					"Fill all the fields please..");
			return;
		}

		// get data from fields
		String name = editTextName.getText().toString();
		String surname = editTextSurname.getText().toString();

		Teacher item = new Teacher(teacherId, name, surname);

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		if (mDbHelper.EditTeacher(item, teacherId)) {
			Utility.ShowMessageBox(this, "edited");
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}
	public void onClickAddNewSubject(View view) {
		Intent intent = new Intent(view.getContext(),
				AddSubjectForTeacher.class);
		intent.putExtra("teacherId", teacherId);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_teacher);
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				teacherId = null;
			} else {
				teacherId = extras.getString("teacherId");
			}
		} else {
			teacherId = (String) savedInstanceState
					.getSerializable("teacherId");
		}

		buttonEdit = (Button) findViewById(R.id.buttonSubmitNewStudent);
		editTextName = (EditText) findViewById(R.id.editTextStudentName);
		editTextSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		buttonEdit.setText("Save");

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// set data to fields
		Teacher item = mDbHelper.getTeacherById(teacherId);
		editTextName.setText((CharSequence) item.getName());
		editTextSurname.setText((CharSequence) item.getSurname());

	}
}
