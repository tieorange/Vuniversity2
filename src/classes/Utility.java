package classes;

import android.content.Context;
import android.database.Cursor;
import android.view.Gravity;
import android.widget.Toast;

public class Utility {

 	public static String GetColumnValue(Cursor cur, String ColumnName) {
		try {
			return cur.getString(cur.getColumnIndex(ColumnName));
		} catch (Exception ex) {
			return "";
		}
	}
 	
//	public static String GetString(EditText source) {
//		try {
//			return GetString(source.getText().toString());
//		} catch (Exception ex) {
//			return "";
//		}
//	}
//
//	public static String GetString(TextView source) {
//		try {
//			return GetString(source.getText().toString());
//		} catch (Exception ex) {
//			return "";
//		}
//	}
//
//	public static String GetString(Object source) {
//		try {
//			return GetString(source.toString());
//		} catch (Exception ex) {
//			return "";
//		}
//	}
	
	public static void ShowMessageBox(Context cont, String msg) {
		Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
		// toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
		toast.show();
	}
}
