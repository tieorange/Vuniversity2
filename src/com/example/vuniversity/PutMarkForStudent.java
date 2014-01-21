package com.example.vuniversity;

import java.util.ArrayList;

import classes.Subject;
import classes.TestAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PutMarkForStudent extends MainActivity implements
		OnItemSelectedListener {
	Spinner spinnerSubject, spinnerMark;
	Button buttonPutMark;
	ArrayList<Subject> listSubjects;
	String studentId, groupId;

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

	private void loadSpinnerData() {
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
		spinnerSubject.setAdapter(adapterSubjects);

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
