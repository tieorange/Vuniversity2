package com.example.vuniversity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import classes.Student;
import classes.Subject;
import classes.TestAdapter;
import classes.Utility;

public class PutMarkForStudent extends MainActivity implements
		OnItemSelectedListener {
	Spinner spinnerSubject, spinnerMark;
	Button buttonPutMark;
	ArrayList<Subject> listSubjects;
	String[] listMarks = new String[] { "1", "2", "3", "4", "5", "6" };
	String studentId, groupId, selectedSubjectId, selectedMark;

	private void loadSpinnerData() {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// Set title
		Student currentStudent = mDbHelper.getStudentById(studentId);
		if (currentStudent != null)
			setTitle(currentStudent.toString());
		// ****subjects
		listSubjects = mDbHelper.getSubjectsOfGroup(groupId);
		ArrayAdapter<Subject> adapterSubjects = new ArrayAdapter<Subject>(this,
				android.R.layout.simple_spinner_item, listSubjects);
		adapterSubjects
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinnerSubject.setAdapter(adapterSubjects);

		// ****marks
		ArrayAdapter<String> adapterMarks = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listMarks);
		adapterMarks
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinnerMark.setAdapter(adapterMarks);

	}

	public void onClickAdd(View view) {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		if (selectedSubjectId == null || selectedMark == null)
		{
			Utility.ShowMessageBox(this, "OOPS you have no mark or group!");
			return;
		}
		if (mDbHelper.AddMark(studentId, selectedSubjectId, groupId,
				selectedMark)) {
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_mark);
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				studentId = null;
				groupId = null;
			} else {
				studentId = extras.getString("studentId");
				groupId = extras.getString("groupId");
			}
		} else {
			studentId = (String) savedInstanceState
					.getSerializable("studentId");
			groupId = (String) savedInstanceState.getSerializable("groupId");
		}

		spinnerSubject = (Spinner) findViewById(R.id.spinnerSubjectOfMark);
		spinnerMark = (Spinner) findViewById(R.id.spinnerMarkOfStudent);
		spinnerSubject.setOnItemSelectedListener(this);
		spinnerMark.setOnItemSelectedListener(this);
		loadSpinnerData();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.spinnerSubjectOfMark: {
			Subject item = (Subject) parent.getItemAtPosition(position);
			selectedSubjectId = item.getId();
			Utility.ShowMessageBox(parent.getContext(), selectedSubjectId);
			break;
		}
		case R.id.spinnerMarkOfStudent: {
			String item = (String) parent.getItemAtPosition(position);
			selectedMark = item;
			Utility.ShowMessageBox(parent.getContext(), selectedMark);
			break;
		}
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
