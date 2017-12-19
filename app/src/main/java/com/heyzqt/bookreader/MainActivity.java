package com.heyzqt.bookreader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.heyzqt.booklibrary.BookConstract;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		queryAllBook();
		queryBookByUriId(4);
	}

	private void queryBookByUriId(int id) {
		Uri newUri = Uri.withAppendedPath(BookConstract.Book.CONTENT_URI, String.valueOf(id));
		Cursor cursor = getContentResolver().query(newUri,
				null,
				null,
				null,
				null);
		if (cursor != null) {
			showAllData(cursor);
			cursor.close();
		}
	}

	private void queryAllBook() {
		Cursor cursor = getContentResolver().query(BookConstract.Book.CONTENT_URI,
				null,
				null,
				null,
				null);
		if (cursor != null) {
			showAllData(cursor);
			cursor.close();
		}
	}

	private void showAllData(Cursor cursor) {
		try {
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < cursor.getColumnCount(); i++) {
						sb.append(cursor.getString(i) + ",");
					}
					Log.i(TAG, "" + sb);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			Log.i(TAG, "queryBookById: NullPointerException");
		}
	}
}
