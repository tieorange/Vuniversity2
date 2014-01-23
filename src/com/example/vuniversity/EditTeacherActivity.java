package com.example.vuniversity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import classes.Teacher;
import classes.TeacherGroup;
import classes.TestAdapter;
import classes.Utility;

public class EditTeacherActivity extends MainActivity {
	Button buttonEdit;
	EditText editTextName, editTextSurname;
	ListView listView;
	String teacherId;
	ArrayList<TeacherGroup> listItems;

	private Adapter getListAdapter() {
		return new ArrayAdapter<TeacherGroup>(this,
				android.R.layout.simple_list_item_1, listItems);
	}

	public void loadList() {

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listItems = mDbHelper.getAllTeacherGroups(teacherId);
		ArrayAdapter<TeacherGroup> adapter = new ArrayAdapter<TeacherGroup>(
				this, android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(adapter);

		Utility.ShowMessageBox(this, "loaded");
		mDbHelper.close();
	}

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

	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		TeacherGroup Item = (TeacherGroup) getListAdapter().getItem(
				info.position);
		switch (item.getItemId()) {
		case R.id.contextMenuDeleteItem: {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			mDbHelper.RemoveTeacherGroupById(Item.getId());
			mDbHelper.close();
			loadList();
			break;
		}
		}
		return true;
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
		listView = (ListView) findViewById(R.id.listViewTeacherSubjects);
		editTextSurname = (EditText) findViewById(R.id.editTextStudentSurname);
		buttonEdit.setText("Save");
		registerForContextMenu(listView);

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// set data to fields
		Teacher item = mDbHelper.getTeacherById(teacherId);
		editTextName.setText((CharSequence) item.getName());
		editTextSurname.setText((CharSequence) item.getSurname());

		loadList();
		listView.setClickable(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				TeacherGroup selectedItem = (TeacherGroup) adapter.getAdapter()
						.getItem(position);
				Utility.ShowMessageBox(view.getContext(),
						selectedItem.getSubjectName() + " is clicked");
				// Intent intent = new Intent(view.getContext().)
			}
		});
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
