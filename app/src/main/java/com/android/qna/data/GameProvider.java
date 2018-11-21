package com.android.qna.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.android.qna.data.QnAContract.QnAEntry;

public class GameProvider extends ContentProvider {

    private static final int QNA = 100;

    private static final int QNA_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(QnAContract.CONTENT_AUTHORITY, QnAContract.PATH, QNA);
        sUriMatcher.addURI(QnAContract.CONTENT_AUTHORITY, QnAContract.PATH + "/#", QNA_ID);
    }

    private GameDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new GameDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case QNA:
                cursor = db.query(QnAEntry.TABLE_NAME, projection, selection, selectionArgs,null, null, sortOrder);
                break;
            case QNA_ID:
                selection = QnAEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(QnAEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert( Uri uri, ContentValues contentValues) {
        return null;
    }
    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
