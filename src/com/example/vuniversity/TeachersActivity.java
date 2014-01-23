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
import classes.Teacher;
import classes.TestAdapter;
import classes.Utility;

public class TeachersActivity extends MainActivity {
	private ArrayList<Teacher> listItems;
	private ListView listView;

	private Adapter getListAdapter() {
		return new ArrayAdapter<Teacher>(this,
				android.R.layout.simple_list_item_1, listItems);
	}

	public void loadList() {

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listItems = mDbHelper.getAllTeachers();
		ArrayAdapter<Teacher> adapter = new ArrayAdapter<Teacher>(this,
				android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(adapter);

		Utility.ShowMessageBox(this, "loaded");
		mDbHelper.close();
	}

	public void onClickAddNew(View view) {
		Intent intent = new Intent(view.getContext(), AddTeacherActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Teacher Item = (Teacher) getListAdapter().getItem(info.position);
		switch (item.getItemId()) {
		case R.id.contextMenuDeleteItem: {
			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			mDbHelper.RemoveTeacherById(Item.getId());
			mDbHelper.close();
			loadList();
			break;
		}
		case R.id.contextMenuEditItem: {
			Intent intent = new Intent(this, EditTeacherActivity.class);
			intent.putExtra("teacherId", Item.getId());
			startActivity(intent);
			break;
		}

		}
		return true;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		listView = (ListView) findViewById(R.id.list);
		registerForContextMenu(listView);
		loadList();

		listView.setClickable(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				Teacher selectedItem = (Teacher) adapter.getAdapter().getItem(
						position);

				Intent intent = new Intent(view.getContext(),
						ShowDetailsTeacher.class);
				intent.putExtra("teacherId", selectedItem.getId());
				startActivity(intent);
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		String header = listItems.get(info.position).toString();
		menu.setHeaderTitle(header);
		if (v.getId() == R.id.list) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadList();
	}

}
