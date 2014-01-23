package com.example.vuniversity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	// views and initialization
	Button buttonGroups;

	public void InitializeViews() {
		buttonGroups = (Button) findViewById(R.id.buttonGroups);
	}

	public void onClickButtons(View view) {
		Intent myIntent = null;
		switch (view.getId()) {
		case R.id.buttonGroups:
			myIntent = new Intent(view.getContext(), GroupsActivity.class);
			break;
		case R.id.buttonSubjects:
			myIntent = new Intent(view.getContext(), SubjectsActivity.class);
			break;
		case R.id.buttonTeachers:
			myIntent = new Intent(view.getContext(), TeachersActivity.class);
			break;
		case R.id.buttonMarks:
			myIntent = new Intent(view.getContext(), MarksActivity.class);
			break;
		}
		startActivity(myIntent);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
