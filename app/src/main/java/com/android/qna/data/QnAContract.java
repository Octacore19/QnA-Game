package com.android.qna.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class QnAContract {

    public static final String CONTENT_AUTHORITY = "com.android.qna";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH = "game";

    private QnAContract(){}

    public static final class QnAEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        public final static String TABLE_NAME = "game";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_QUESTIONS = "questions";
        public final static String COLUMN_ANSWERS = "answers";
    }
}
