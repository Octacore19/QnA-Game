package com.android.qna;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.qna.model.Game;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;

import static com.android.qna.Constants.REALM_BASE_URL;

public class GameActivity extends AppCompatActivity  {

    private Realm realm;

    public TextView mQuestionTextView;

    public EditText mAnswerTextView;

    RealmResults<Game> game;

    String currentQuestion, currentAnswer, answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);

        mQuestionTextView = findViewById(R.id.question_text);
        mAnswerTextView = findViewById(R.id.answers_text);

        game = setUpRealm();
        final int[] count = {0};
        currentQuestion = game.get(count[0]).getQuestions();
        currentAnswer = game.get(count[0]).getAnswers();
        mQuestionTextView.setText(currentQuestion);
        answers = mAnswerTextView.getText().toString().trim();

        Button btnNext = findViewById(R.id.move);
        btnNext.setOnClickListener(view -> {
            if (currentAnswer.equals(answers)){
                count[0]++;
                currentQuestion = "" + game.get(count[0]).getQuestions();
                currentAnswer = "" + game.get(count[0]).getAnswers();
                mQuestionTextView.setText(currentQuestion);
            }
            else
                Toast.makeText(GameActivity.this,"You have inputted a wrong answer",Toast.LENGTH_SHORT).show();
        });
    }

    private RealmResults<Game> setUpRealm() {
        SyncConfiguration configuration = SyncUser.current()
                .createConfiguration(REALM_BASE_URL + "/default")
                .build();
        realm = Realm.getInstance(configuration);

        return realm
                .where(Game.class)
                .sort("id", Sort.ASCENDING)
                .findAllAsync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                SyncUser syncUser = SyncUser.current();
                if (syncUser != null) {
                    syncUser.logOut();
                    Intent intent = new Intent(this, WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}