package com.example.vuniversity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import classes.Subject;
import classes.TestAdapter;
import classes.Utility;

public class AddSubjectActivity extends MainActivity {

	Button buttonSubmitNew;
	EditText editTextName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);

		buttonSubmitNew = (Button) findViewById(R.id.buttonSubmitNewGroup);
		editTextName = (EditText) findViewById(R.id.editTextGroupName);
	}

	public void onClickAdd(View view) {
		if (editTextName.getText().length() <= 0) {
			Utility.ShowMessageBox(view.getContext(),
					"Enter the name please..");
			return;
		}

		String name = editTextName.getText().toString();

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		
		if (mDbHelper.isSubjectExist(new Subject(name))) {
			Utility.ShowMessageBox(view.getContext(),
					"Such subject already exists");
			return;
		}

		if (mDbHelper.AddSubject(name)) {
			Utility.ShowMessageBox(this, "added");
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}

}
