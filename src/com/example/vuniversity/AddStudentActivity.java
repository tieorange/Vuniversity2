package com.example.vuniversity;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import classes.Group;
import classes.Student;
import classes.TestAdapter;
import classes.Utility;
import com.tieorange.vuniversity.R;

public class AddStudentActivity extends MainActivity implements
		OnItemSelectedListener {
	Button buttonSubmitNew;
	EditText editTextName, editTextSurname, editTextEska;
	Spinner spinnerGroups;
	String groupId;
	ArrayList<Group> listItems;
	int lastStudentId;
	SharedPreferences preferences;

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

	public void onClickAdd(View view) {
		if (editTextName.getText().length() <= 0
				|| editTextSurname.getText().length() <= 0
				|| editTextEska.getText().length() <= 0) {
			Utility.ShowMessageBox(view.getContext(),
					"Fill all the fields please..");
			return;
		}
		String name = editTextName.getText().toString();
		String surname = editTextSurname.getText().toString();
		String eska = editTextEska.getText().toString();

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		if (mDbHelper.isStudentExist(new Student(eska))) {
			Utility.ShowMessageBox(view.getContext(),
					"Such student already exists");
			return;
		}

		if (mDbHelper.AddStudent(name, surname, eska, groupId)) {
			SharedPreferences.Editor editor = preferences.edit();
			editor.putInt("lastStudentId", lastStudentId);
			editor.commit();
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
			} else {
				groupId = extras.getString("id");
			}
		} else {
			groupId = (String) savedInstanceState.getSerializable("id");
		}

		buttonSubmitNew = (Button) findViewById(R.id.buttonSubmitNewStudent);
		editTextName = (EditText) findViewById(R.id.editTextStudentName);
		editTextSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		editTextEska = (EditText) findViewById(R.id.editTextStudentEska);
		spinnerGroups = (Spinner) findViewById(R.id.spinnerGroups);
		// Spinner click listener
		spinnerGroups.setOnItemSelectedListener(this);
		loadSpinnerData();
		selectSpinnerItemByValue(spinnerGroups, groupId);

		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		lastStudentId = preferences.getInt("lastStudentId", -1);
		getLastStudentEska();
		editTextEska.setText(Integer.toString(lastStudentId));

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item
		Group item = (Group) parent.getItemAtPosition(position);
		groupId = item.getId();

	}

	public void getLastStudentEska() {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		lastStudentId = mDbHelper.IsStudentEskeExists(lastStudentId);
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
