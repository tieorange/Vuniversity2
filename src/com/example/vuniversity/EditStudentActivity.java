package com.example.vuniversity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import classes.TestAdapter;
import classes.Utility;

public class EditStudentActivity extends MainActivity {
	Button buttonEdit;
	EditText editTextStudentName, editTextStudentSurname, editTextStudentEska;
	String groupId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_student);
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				groupId = null;
			} else {
				groupId = extras.getString("id");
			}
		} else {
			groupId = (String) savedInstanceState.getSerializable("id");
		}

		buttonEdit = (Button) findViewById(R.id.buttonSubmitNewStudent);
		editTextStudentName = (EditText) findViewById(R.id.editTextStudentName);
		editTextStudentSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		editTextStudentEska = (EditText) findViewById(R.id.editTextStudentEska);

	}

	public void onClickAdd(View view) {
		if (editTextStudentName.getText().length() <= 0
				|| editTextStudentSurname.getText().length() <= 0
				|| editTextStudentEska.getText().length() <= 0) {
			Utility.ShowMessageBox(view.getContext(),
					"Fill all the fields please..");
			return;
		}
//student new 
		String name = editTextStudentName.getText().toString();
		String surname = editTextStudentSurname.getText().toString();
		String eska = editTextStudentEska.getText().toString();

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
//USER EDIT STUDENT
		if (mDbHelper.AddGroup(name)) {
			Utility.ShowMessageBox(this, "Group added");
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}

}
