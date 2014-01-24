package com.example.vuniversity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import classes.Group;
import classes.Student;
import classes.TestAdapter;
import classes.Utility;

public class EditStudentActivity extends MainActivity implements
		OnItemSelectedListener {
	Button buttonEdit;
	EditText editTextName, editTextSurname, editTextEska;
	Spinner spinnerGroups;
	String groupId, studentId;
	ArrayList<Group> listItems;
	ListView listViewStudentMarks;

	private Adapter getSpinnerAdapter() {
		return new ArrayAdapter<Group>(this,
				android.R.layout.simple_spinner_item, listItems);
	}

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
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		buttonEdit = (Button) findViewById(R.id.buttonSubmitNewStudent);
		editTextName = (EditText) findViewById(R.id.editTextStudentName);
		editTextSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		editTextEska = (EditText) findViewById(R.id.editTextStudentEska);
		spinnerGroups = (Spinner) findViewById(R.id.spinnerGroups);
		listViewStudentMarks = (ListView) findViewById(R.id.listViewStudentMarks);
		listViewStudentMarks.setVisibility(View.GONE);
		buttonEdit.setText("Save");
		

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// set data to fields
		Student item = mDbHelper.getStudentById(studentId);
		editTextName.setText((CharSequence) item.getName());
		editTextSurname.setText((CharSequence) item.getSurname());
		editTextEska.setText((CharSequence) item.getEska());

		// Spinner click listener
		spinnerGroups.setOnItemSelectedListener(this);
		loadSpinnerData();
		selectSpinnerItemByValue(spinnerGroups, groupId);

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item
		Group item = (Group) parent.getItemAtPosition(position);
		groupId = item.getId();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@SuppressWarnings("unchecked")
	public void selectSpinnerItemByValue(Spinner spnr, String value) {
		ArrayAdapter<Group> adapter = (ArrayAdapter<Group>) getSpinnerAdapter();
		for (int position = 0; position < adapter.getCount(); position++) {
			Group Item = (Group) adapter.getItem(position);
			if (Item.getId().equals(value)) {
				spnr.setSelection(position);
				return;
			}
		}
	}
}
