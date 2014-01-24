package com.example.vuniversity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import classes.Group;
import classes.TestAdapter;
import classes.Utility;
import com.tieorange.vuniversity.R;

public class GroupsActivity extends MainActivity {
	private ArrayList<Group> listItems;
	private ListView listView;
	private ArrayAdapter<Group> adapter;
	private EditText searchField;
	private String ORDER_BY, ASC_DESC;

	private Adapter getListAdapter() {
		return new ArrayAdapter<Group>(this,
				android.R.layout.simple_list_item_1, listItems);
	}

	public void loadList() {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listItems = mDbHelper.getAllGroups(ORDER_BY, ASC_DESC);
		adapter = new ArrayAdapter<Group>(this,
				android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(adapter);
		mDbHelper.close();
	}

	public void onClickAddNew(View view) {
		Intent intent = new Intent(view.getContext(), AddGroupActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Group Item = (Group) getListAdapter().getItem(info.position);
		switch (item.getItemId()) {
		case R.id.contextMenuDeleteItem: {

			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			mDbHelper.RemoveGroupById(Item.getId());
			mDbHelper.close();
			loadList();
			break;
		}
		case R.id.contextMenuEditItem: {
			Intent intent = new Intent(this, EditGroupActivity.class);
			intent.putExtra("groupId", Item.getId());
			startActivity(intent);
			break;
		}

		}

		return true;
	}

	@Override
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
				Group selectedItem = (Group) adapter.getAdapter().getItem(
						position);
				Intent intent = new Intent(view.getContext(),
						StudentsActivity.class);
				intent.putExtra("groupId", selectedItem.getId());
				startActivity(intent);

			}
		});
		searchField = (EditText) findViewById(R.id.search_field);
		searchField.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				adapter.getFilter().filter(cs);
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
			// menu.add(Menu.NONE, 1, 1, "Remove him from this list");
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
			// super.onCreateContextMenu(menu, v, menuInfo);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.groups_subjects, menu);
		return true;// return true so to menu pop up is opens
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menuByNameASC:
			ORDER_BY = Utility.NAME;
			ASC_DESC = Utility.ASC;
			loadList();
			return true;
		case R.id.menuByNameDESC:
			ORDER_BY = Utility.NAME;
			ASC_DESC = Utility.DESC;
			loadList();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadList();
	}
}
