package classes;

import android.content.Context;
import android.database.Cursor;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Utility {

	public static final int MENU_DELETE_ITEM = 1;
	public static final int MENU_EDIT_ITEM = 2;
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String ESKA = "eska";
	public static final String ID = "id";

	public static String GetColumnValue(Cursor cur, String ColumnName) {
		try {
			return cur.getString(cur.getColumnIndex(ColumnName));
		} catch (Exception ex) {
			return "";
		}
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	// public static String GetString(EditText source) {
	// try {
	// return GetString(source.getText().toString());
	// } catch (Exception ex) {
	// return "";
	// }
	// }
	//
	// public static String GetString(TextView source) {
	// try {
	// return GetString(source.getText().toString());
	// } catch (Exception ex) {
	// return "";
	// }
	// }
	//
	// public static String GetString(Object source) {
	// try {
	// return GetString(source.toString());
	// } catch (Exception ex) {
	// return "";
	// }
	// }

	public static void ShowMessageBox(Context cont, String msg) {
		Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
		// toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
		toast.show();
	}
}
