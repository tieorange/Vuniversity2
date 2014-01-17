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
import android.widget.ListView;
import classes.Student;
import classes.TestAdapter;
import classes.Utility;

public class StudentsActivity extends MainActivity {
	private ArrayList<Student> listItems;
	private ListView listView;
	private String groupId;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				groupId = null;
			} else {
				groupId = extras.getString("groupId");
			}
		} else {
			groupId = (String) savedInstanceState.getSerializable("groupId");
		}

		setContentView(R.layout.list);
		listView = (ListView) findViewById(R.id.listView);
		registerForContextMenu(listView);
		loadList();

		listView.setClickable(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				Student selectedItem = (Student) adapter.getAdapter().getItem(
						position);
				Utility.ShowMessageBox(view.getContext(),
						selectedItem.getName() + " is clicked");
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
		if (v.getId() == R.id.listView) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadList();
	}

	public void loadList() {

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listItems = mDbHelper.getAllStudents(groupId);
		ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this,
				android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(adapter);

		Utility.ShowMessageBox(this, "Students loaded");
		mDbHelper.close();
	}

	public void onClickAddNew(View view) {
		Intent intent = new Intent(view.getContext(), AddStudentActivity.class);
		intent.putExtra("id", groupId);
		startActivity(intent);

	}

	private Adapter getListAdapter() {
		return new ArrayAdapter<Student>(this,
				android.R.layout.simple_list_item_1, listItems);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Student Item = (Student) getListAdapter().getItem(info.position);
		switch (item.getItemId()) {
		case R.id.contextMenuDeleteItem: {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			mDbHelper.RemoveStudentById(Item.getId());
			mDbHelper.close();
			loadList();
			break;
		}
		case R.id.contextMenuEditItem: {
			Intent intent = new Intent(this, EditStudentActivity.class);
			intent.putExtra("studentId", Item.getId());
			intent.putExtra("groupId", Item.getGroupId());
			startActivity(intent);
			break;
		}

		}
		return true;
	}

}
