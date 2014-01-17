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
import classes.Group;
import classes.TestAdapter;
import classes.Utility;

public class GroupsActivity extends MainActivity {
	private ArrayList<Group> listItems;
	private ListView listView;

	@Override
	protected void onResume() {
		super.onResume();
		loadList();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		listView = (ListView) findViewById(R.id.listView);
		registerForContextMenu(listView);
		loadList();

		listView.setClickable(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				Group selectedItem = (Group) adapter.getAdapter().getItem(
						position);
				Intent intent = new Intent(view.getContext(),
						StudentsActivity.class);
				intent.putExtra("id", Integer.toString(selectedItem.getId()));
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
		if (v.getId() == R.id.listView) {
			// menu.add(Menu.NONE, 1, 1, "Remove him from this list");
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
			// super.onCreateContextMenu(menu, v, menuInfo);
		}

	}

	public void onClickAddNew(View view) {
		Intent intent = new Intent(view.getContext(), AddGroupActivity.class);
		startActivity(intent);
	}

	public void loadList() {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listItems = mDbHelper.getAllGroups();
		ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(this,
				android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(adapter);

		Utility.ShowMessageBox(this, "Groups loaded");
		mDbHelper.close();
	}

	private Adapter getListAdapter() {
		return new ArrayAdapter<Group>(this,
				android.R.layout.simple_list_item_1, listItems);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.contextMenuDeleteItem: {
			Group Item = (Group) getListAdapter().getItem(info.position);

			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			mDbHelper.RemoveGroupById(Item.getId());
			mDbHelper.close();
			loadList();
			break;
		}
		case R.id.contextMenuEditItem: {
			// edit
		}

		}

		return true;
	}
}
