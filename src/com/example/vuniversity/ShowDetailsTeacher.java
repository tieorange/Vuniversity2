package com.example.vuniversity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import classes.Teacher;
import classes.TeacherGroup;
import classes.TestAdapter;
import classes.Utility;
import com.tieorange.vuniversity.R;

public class ShowDetailsTeacher extends MainActivity {
	Button buttonEdit, buttonAddSubject;
	EditText editTextName, editTextSurname;
	ListView listView;
	String teacherId;
	ArrayList<TeacherGroup> listItems;

	public void loadList() {

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listItems = mDbHelper.getAllTeacherGroups(teacherId);
		ArrayAdapter<TeacherGroup> adapter = new ArrayAdapter<TeacherGroup>(
				this, android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(adapter);

		mDbHelper.close();
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
		buttonEdit.setVisibility(View.GONE);
		buttonAddSubject = (Button) findViewById(R.id.buttonAddNewSubjectForTeacher);
		buttonAddSubject.setVisibility(View.GONE);
		editTextName = (EditText) findViewById(R.id.editTextStudentName);
		editTextName.setKeyListener(null);
		listView = (ListView) findViewById(R.id.listViewTeacherSubjects);
		editTextSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		editTextSurname.setKeyListener(null);

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// set data to fields
		Teacher item = mDbHelper.getTeacherById(teacherId);
		editTextName.setText((CharSequence) item.getName());
		editTextSurname.setText((CharSequence) item.getSurname());

		loadList();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		String header = listItems.get(info.position).toString();
		menu.setHeaderTitle(header);
		if (v.getId() == R.id.listViewTeacherSubjects) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.remove_only_context_menu, menu);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadList();
	}
}
