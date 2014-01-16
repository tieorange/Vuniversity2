package com.example.vuniversity;

import classes.TestAdapter;
import classes.Utility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGroupActivity extends MainActivity {

	Button buttonSubmitNewGroup;
	EditText editTextGroupName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);

		buttonSubmitNewGroup = (Button) findViewById(R.id.buttonSubmitNewGroup);
		editTextGroupName = (EditText) findViewById(R.id.editTextGroupName);

	}

	public void onClickAdd(View view) {
		if (editTextGroupName.getText().length() <= 0) {
			Utility.ShowMessageBox(view.getContext(),
					"Enter the name of the group please..");
			return;
		}

		String name = editTextGroupName.getText().toString();

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		if (mDbHelper.AddGroup(name)) {
			Utility.ShowMessageBox(this, "Group added");
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}

}
