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
import classes.Subject;
import classes.TestAdapter;
import classes.Utility;

public class SubjectsActivity extends MainActivity {
	private ArrayList<Subject> listItems;
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
				Subject selectedItem = (Subject) adapter.getAdapter().getItem(
						position);
				// Intent intent = new Intent(view.getContext(),
				// SubjectDetailsActivity.class);
				// intent.putExtra("subjectId", selectedItem.getId());
				// startActivity(intent);

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

	public void onClickAddNew(View view) {
		Intent intent = new Intent(view.getContext(), AddSubjectActivity.class);
		startActivity(intent);
	}

	public void loadList() {
		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		listItems = mDbHelper.getAllSubjects();
		ArrayAdapter<Subject> adapter = new ArrayAdapter<Subject>(this,
				android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(adapter);

		Utility.ShowMessageBox(this, "Subjects loaded");
		mDbHelper.close();
	}

	private Adapter getListAdapter() {
		return new ArrayAdapter<Subject>(this,
				android.R.layout.simple_list_item_1, listItems);
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
			// Intent intent = new Intent(this, EditSubjectActivity.class);
			// intent.putExtra("subjectId", Item.getId());
			// startActivity(intent);
			// break;
		}

		}

		return true;
	}
}
