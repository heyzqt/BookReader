package com.heyzqt.bookreader;

import android.content.ContentUris;
import android.content.ContentValues;
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

//		queryAllBook();
//		queryBookByUriId(4);
//		queryBookByUriName("The Sorrows of Young Werther");

//		queryAllBook();
//		insertOneBook();
//		queryAllBook();

		//deleteBookById(4);
		//deleteBookByUriId(5);
		//deleteBookByUriName("Book zzz");

		queryAllBook();
		updateOneDate();
		updateBookByUriId(16);
		queryAllBook();
	}

	private void updateBookByUriId(int id) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BookConstract.Name.NAME, "Sing in the Heaven");

		Uri newUri = ContentUris.withAppendedId(BookConstract.Book.CONTENT_URI, id);
		int result = getContentResolver().update(newUri,
				contentValues,
				null,
				null);
		Log.i(TAG, "updateBookByUriId: result = " + result);
	}

	private void updateOneDate() {
		ContentValues cv = new ContentValues();
		cv.put(BookConstract.Name.NAME, "Book X");
		int result = getContentResolver().update(BookConstract.Book.CONTENT_URI,
				cv,
				BookConstract.Name.NAME + " = ?",
				new String[]{"Harry Porter"});
		Log.i(TAG, "updateOneDate: result = " + result);
	}

	private void deleteBookByUriName(String str) {
		Uri uri = Uri.withAppendedPath(BookConstract.Name.CONTENT_URI, str);
		int deleteId = getContentResolver().delete(uri,
				null,
				null);
		if (deleteId > 0) {
			Log.i(TAG, "deleteBookById: delete book " + deleteId);
			queryAllBook();
		}
	}

	private void deleteBookByUriId(int id) {
		Uri newUri = Uri.withAppendedPath(BookConstract.Book.CONTENT_URI, String.valueOf(id));
		int deleteId = getContentResolver().delete(newUri,
				"",
				null);
		if (deleteId > 0) {
			Log.i(TAG, "deleteBookById: delete book " + deleteId);
			queryAllBook();
		}
	}

	private void deleteBookById(int id) {
		int deleteId = getContentResolver().delete(BookConstract.Book.CONTENT_URI,
				BookConstract.Book._ID + " = ?",
				new String[]{String.valueOf(id)});
		if (deleteId > 0) {
			Log.i(TAG, "deleteBookById: delete book " + deleteId);
			queryAllBook();
		}
	}

	private void insertOneBook() {
		ContentValues cv = new ContentValues();
		cv.put(BookConstract.Name.NAME, "The City Of Star");
		cv.put(BookConstract.Type.TYPE, "TYPEC003");
		cv.put(BookConstract.Book.MIMETYPE_ID, "2");
		Uri uri = getContentResolver().insert(BookConstract.Book.CONTENT_URI, cv);
		Log.i(TAG, "insertOneBook: uri = " + uri);
	}

	private void queryBookByUriName(String name) {
		Uri newUri = Uri.withAppendedPath(BookConstract.Name.CONTENT_URI, name);
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
