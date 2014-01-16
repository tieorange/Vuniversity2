package com.example.vuniversity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import classes.Group;
import classes.TestAdapter;
import classes.Utility;

public class GroupsActivity extends MainActivity {
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LoadGroupList();
	}

	private ArrayList<Group> listGroups;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groups);
				LoadGroupList();
	}
	
	public void onClickAddNewGroup(View view)
	{
		Intent intent = new Intent(view.getContext(), AddGroupActivity.class);
		startActivity(intent);
	}
	public void LoadGroupList()
	{
		ListView listViewGroups = (ListView) findViewById(R.id.listViewGroups);
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		
		listGroups = mDbHelper.getAllGroups();
		ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(this,
				android.R.layout.simple_list_item_1, listGroups);
		listViewGroups.setAdapter(adapter);
		registerForContextMenu(listViewGroups);
		
		Utility.ShowMessageBox(this, "Groups loaded");
		mDbHelper.close();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		String header = listGroups.get(info.position).toString();
		menu.setHeaderTitle(header);
		if (v.getId() == R.id.listViewGroups) {
			menu.add(Menu.NONE, 1, 1, "Remove him from this list");
		}
	}

	private Adapter getListAdapter() {
		return new ArrayAdapter<Group>(this,
				android.R.layout.simple_list_item_1, listGroups);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		Group group = (Group) getListAdapter().getItem(info.position);
		
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		mDbHelper.RemoveGroupById(group.getId());
		mDbHelper.close();
		LoadGroupList();
		return true;
	}


}
