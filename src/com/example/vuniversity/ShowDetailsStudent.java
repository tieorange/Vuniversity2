package com.example.vuniversity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import classes.Group;
import classes.Student;
import classes.TestAdapter;
import classes.Utility;

public class ShowDetailsStudent extends MainActivity {
	Button buttonEdit;
	EditText editTextName, editTextSurname, editTextEska;
	Spinner spinnerGroups;
	String groupId, studentId;
	ArrayList<Group> listItems;
	ArrayList<Mark> listMarks;
	ListView listViewStudentMarks;

	public void loadSpinnerData() {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listItems = mDbHelper.getAllGroups();
		ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(this,
				android.R.layout.simple_spinner_item, listItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnerGroups.setAdapter(adapter);
	}

	public void loadList() {

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listMarks = mDbHelper.getMarksOfStudent(studentId);
		ArrayAdapter<Mark> adapter = new ArrayAdapter<Mark>(this,
				android.R.layout.simple_list_item_1, listMarks);
		listViewStudentMarks.setAdapter(adapter);

		Utility.ShowMessageBox(this, "Students loaded");
		mDbHelper.close();
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

		listViewStudentMarks = (ListView) findViewById(R.id.listViewStudentMarks);
		buttonEdit = (Button) findViewById(R.id.buttonSubmitNewStudent);
		buttonEdit.setVisibility(View.GONE);
		editTextName = (EditText) findViewById(R.id.editTextStudentName);
		editTextName.setFocusable(false);
		editTextSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		editTextSurname.setFocusable(false);
		editTextEska = (EditText) findViewById(R.id.editTextStudentEska);
		editTextEska.setFocusable(false);
		spinnerGroups = (Spinner) findViewById(R.id.spinnerGroups);
		spinnerGroups.setFocusable(false);

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// set data to fields
		Student item = mDbHelper.getStudentById(studentId);
		editTextName.setText((CharSequence) item.getName());
		editTextSurname.setText((CharSequence) item.getSurname());
		editTextEska.setText((CharSequence) item.getEska());

		loadSpinnerData();
		loadList();

		// marks
		listViewStudentMarks.setClickable(true);
		listViewStudentMarks.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				Mark selectedItem = (Mark) adapter.getAdapter().getItem(
						position);
				Utility.ShowMessageBox(adapter.getContext(),
						"Teacher: " + selectedItem.getTeacherName()
								+ selectedItem.getTeacherSurname());
			}
		});

	}

}
