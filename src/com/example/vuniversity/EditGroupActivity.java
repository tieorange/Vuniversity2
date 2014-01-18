package com.example.vuniversity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import classes.Group;
import classes.TestAdapter;
import classes.Utility;

public class EditGroupActivity extends MainActivity {
	Button buttonEdit;
	EditText editTextName;
	String groupId;

	public void onClickAdd(View view) {
		if (editTextName.getText().length() <= 0) {
			Utility.ShowMessageBox(view.getContext(),
					"Fill all the fields please..");
			return;
		}

		// get data from fields
		String name = editTextName.getText().toString();

		Group item = new Group(groupId, name);

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		if (mDbHelper.EditGroup(item, groupId)) {
			Utility.ShowMessageBox(this, "Group edited");
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);
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

		buttonEdit = (Button) findViewById(R.id.buttonSubmitNewGroup);
		editTextName = (EditText) findViewById(R.id.editTextGroupName);

		buttonEdit.setText("Save");

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// set data to fields
		editTextName.setText((CharSequence) mDbHelper.getGroupById(groupId)
				.getName());

	}
}
