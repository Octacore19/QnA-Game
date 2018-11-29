package com.android.qna;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.qna.model.Game;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;

import static com.android.qna.Constants.REALM_BASE_URL;

public class GameActivity extends AppCompatActivity {

    private Realm realm;

    public EditText mAnswerTextView;

    private TextView mDisplay;

    String currentQuestion, currentAnswer, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);

        setUpRealm();
        RealmResults<Game> games = setUpRealm();

        final GameRecyclerAdapter gameRecyclerAdapter = new GameRecyclerAdapter(games);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(gameRecyclerAdapter);

        mAnswerTextView = findViewById(R.id.answers_text);

//        mDisplay = findViewById(R.id.display);
//        currentQuestion = games.first().getQuestions();
//        mDisplay.setText(String.valueOf(currentQuestion));
//        findViewById(R.id.move).setOnClickListener(this);
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
            case R.id.add_data:
                startActivity(new Intent(this, EditorActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}