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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import classes.Subject;
import classes.TestAdapter;
import classes.Utility;
import com.tieorange.vuniversity.R;

public class SubjectsActivity extends MainActivity {
	private ArrayList<Subject> listItems;
	private ListView listView;
	private EditText searchField;
	private ArrayAdapter<Subject> adapter;
	private String ORDER_BY, ASC_DESC;

	private Adapter getListAdapter() {
		return new ArrayAdapter<Subject>(this,
				android.R.layout.simple_list_item_1, listItems);
	}

	public void loadList() {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listItems = mDbHelper.getAllSubjects(ORDER_BY, ASC_DESC);
		adapter = new ArrayAdapter<Subject>(this,
				android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(adapter);

		mDbHelper.close();
	}

	public void onClickAddNew(View view) {
		Intent intent = new Intent(view.getContext(), AddSubjectActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Subject Item = (Subject) getListAdapter().getItem(info.position);
		switch (item.getItemId()) {
		case R.id.contextMenuDeleteItem: {

			TestAdapter mDbHelper = new TestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
			mDbHelper.RemoveSubjectById(Item.getId());
			mDbHelper.close();
			loadList();
			break;
		}
		case R.id.contextMenuEditItem: {
			Intent intent = new Intent(this, EditSubjectActivity.class);
			intent.putExtra("subjectId", Item.getId());
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
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
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
