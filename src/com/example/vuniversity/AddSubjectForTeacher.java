package com.example.vuniversity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import classes.Group;
import classes.Subject;
import classes.TestAdapter;
import classes.Utility;

public class AddSubjectForTeacher extends MainActivity implements
		OnItemSelectedListener {
	Spinner spinnerSubjectForTeacher, spinnerGroupForTeacher;
	Button buttonAddSubjectForTeacher;

	ArrayList<Group> listGroups;
	ArrayList<Subject> listSubjects;
	String teacherId, selectedSubjectId, selectedGroupId;

	private Adapter getSpinnerGroupAdapter() {
		return new ArrayAdapter<Group>(this,
				android.R.layout.simple_spinner_item, listGroups);
	}

	private Adapter getSpinnerSubjectAdapter() {
		return new ArrayAdapter<Subject>(this,
				android.R.layout.simple_spinner_item, listSubjects);
	}

	public void loadSpinnerData() {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// ****subjects
		listSubjects = mDbHelper.getAllSubjects();
		ArrayAdapter<Subject> adapterSubjects = new ArrayAdapter<Subject>(this,
				android.R.layout.simple_spinner_item, listSubjects);
		adapterSubjects
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinnerSubjectForTeacher.setAdapter(adapterSubjects);

		// ****groups
		listGroups = mDbHelper.getAllGroups();
		ArrayAdapter<Group> adapterGroups = new ArrayAdapter<Group>(this,
				android.R.layout.simple_spinner_item, listGroups);
		adapterGroups
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnerGroupForTeacher.setAdapter(adapterGroups);
	}

	public void onClickAdd(View view) {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		if (mDbHelper.AddTeacherGroup(teacherId, selectedSubjectId,
				selectedGroupId)) {
			Utility.ShowMessageBox(this, "edited");
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_subject_for_teacher);
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

		spinnerSubjectForTeacher = (Spinner) findViewById(R.id.spinnerSubjectForTeacher);
		spinnerGroupForTeacher = (Spinner) findViewById(R.id.spinnerGroupForTeacher);
		spinnerGroupForTeacher.setOnItemSelectedListener(this);
		spinnerSubjectForTeacher.setOnItemSelectedListener(this);
		loadSpinnerData();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.spinnerGroupForTeacher: {
			Group item = (Group) parent.getItemAtPosition(position);
			selectedGroupId = item.getId();
			break;
		}
		case R.id.spinnerSubjectForTeacher: {
			Subject item = (Subject) parent.getItemAtPosition(position);
			selectedSubjectId = item.getId();
			break;
		}
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
