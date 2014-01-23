package com.example.vuniversity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import classes.Group;
import classes.TestAdapter;
import classes.Utility;

public class AddGroupActivity extends MainActivity {

	Button buttonSubmitNew;
	EditText editTextName;

	public void onClickAdd(View view) {
		if (editTextName.getText().length() <= 0) {
			Utility.ShowMessageBox(view.getContext(), "Enter the name please..");
			return;
		}

		String name = editTextName.getText().toString();

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		if (mDbHelper.isGroupExist(new Group(name))) {
			Utility.ShowMessageBox(view.getContext(),
					"Such group already exists");
			return;
		}

		if (mDbHelper.AddGroup(name)) {
			Utility.ShowMessageBox(this, "added");
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);

		buttonSubmitNew = (Button) findViewById(R.id.buttonSubmitNewGroup);
		editTextName = (EditText) findViewById(R.id.editTextGroupName);

	}

}
