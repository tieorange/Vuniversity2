package com.example.vuniversity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import classes.Subject;
import classes.TestAdapter;
import classes.Utility;
import com.tieorange.vuniversity.R;

public class EditSubjectActivity extends MainActivity {
	Button buttonEdit;
	EditText editTextName;
	String subjectId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				subjectId = null;
			} else {
				subjectId = extras.getString("subjectId");
			}
		} else {
			subjectId = (String) savedInstanceState.getSerializable("subjectId");
		}

		buttonEdit = (Button) findViewById(R.id.buttonSubmitNewGroup);
		editTextName = (EditText) findViewById(R.id.editTextGroupName);

		buttonEdit.setText("Save");

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		// set data to fields
		editTextName.setText((CharSequence) mDbHelper.getSubjectById(subjectId)
				.getName());

	}

	public void onClickAdd(View view) {
		if (editTextName.getText().length() <= 0) {
			Utility.ShowMessageBox(view.getContext(),
					"Fill all the fields please..");
			return;
		}

		// get data from fields
		String name = editTextName.getText().toString();

		Subject item = new Subject(subjectId, name);

		TestAdapter mDbHelper = new TestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		if (mDbHelper.EditSubject(item, subjectId)) {
			Utility.ShowMessageBox(this, "edited");
			finish();
		} else {
			Utility.ShowMessageBox(this, "OOPS try again!");
		}

	}
}
