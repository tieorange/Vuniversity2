package com.example.vuniversity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import classes.Group;
import classes.Mark;
import classes.Student;
import classes.TeacherGroup;
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
		registerForContextMenu(listViewStudentMarks);
		buttonEdit = (Button) findViewById(R.id.buttonSubmitNewStudent);
		buttonEdit.setVisibility(View.GONE);
		editTextName = (EditText) findViewById(R.id.editTextStudentName);
		editTextName.setKeyListener(null);
		editTextSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		editTextSurname.setKeyListener(null);
		editTextEska = (EditText) findViewById(R.id.editTextStudentEska);
		editTextEska.setKeyListener(null);
		spinnerGroups = (Spinner) findViewById(R.id.spinnerGroups);
		spinnerGroups.setEnabled(false);

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
		Utility.setListViewHeightBasedOnChildren(listViewStudentMarks);

		// marks
		listViewStudentMarks.setClickable(true);
		listViewStudentMarks.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				Mark selectedItem = (Mark) adapter.getAdapter().getItem(
						position);
				Utility.ShowMessageBox(adapter.getContext(),
						"Teacher: " + selectedItem.getTeacherName() + " "
								+ selectedItem.getTeacherSurname());
			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		String header = listMarks.get(info.position).toString();
		menu.setHeaderTitle(header);
		if (v.getId() == R.id.listViewStudentMarks) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.remove_only_context_menu, menu);
		}
	}

	private Adapter getListAdapter() {
		return new ArrayAdapter<Mark>(this,
				android.R.layout.simple_list_item_1, listMarks);
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Mark Item = (Mark) getListAdapter().getItem(info.position);
		switch (item.getItemId()) {
		case R.id.contextMenuDeleteItem: {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			mDbHelper.RemoveMarkById(Item.getId());
			mDbHelper.close();
			loadList();
			break;
		}
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadList();
	}

}
