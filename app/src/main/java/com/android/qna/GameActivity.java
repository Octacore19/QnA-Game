package com.android.qna;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.qna.data.GameDbHelper;
import com.android.qna.data.QnAContract.QnAEntry;

public class GameActivity extends AppCompatActivity  {

    private GameDbHelper mDbHelper;

    private TextView mDisplay;

    public TextView mQuestionTextView;

    public EditText mAnswerTextView;

    String currentQuestion;
    int currentAnswer;
    int qColumnIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);

        mDbHelper = new GameDbHelper(this);
        mDisplay = findViewById(R.id.display);
        mQuestionTextView = findViewById(R.id.question_text);
        mAnswerTextView = findViewById(R.id.answers_text);

        displayDatabaseInfo();
    }

    private void displayDatabaseInfo(){

        String[] project = {
                QnAEntry._ID,
                QnAEntry.COLUMN_QUESTIONS,
                QnAEntry.COLUMN_ANSWERS
        };

        Cursor cursor = getContentResolver().query(
                QnAEntry.CONTENT_URI,
                project,
                null,
                null,
                null);
        try{

            mDisplay.setText("Number of rows in database is " + cursor.getCount()+"\n\n");

            cursor.moveToFirst();
            qColumnIndex = cursor.getColumnIndex(QnAEntry.COLUMN_QUESTIONS);

            currentQuestion = cursor.getString(qColumnIndex);
            mQuestionTextView.setText(currentQuestion);

            Button nextBtn = findViewById(R.id.move);
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(GameActivity.this,"This button works",Toast.LENGTH_SHORT).show();
                }
            });
        }
        finally {
            cursor.close();
        }
    }
}