package com.android.qna;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.android.qna.model.Game;

import java.util.UUID;

import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;

import static com.android.qna.Constants.REALM_BASE_URL;

public class EditorActivity extends AppCompatActivity {
    private Realm realm;

    public EditText mQuestions;

    public EditText mAnswers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_activity);

        setUpRealm();

        mQuestions = findViewById(R.id.question_entry);
        mAnswers = findViewById(R.id.answers_entry);

        findViewById(R.id.save).setOnClickListener(view -> realm.executeTransactionAsync(realm -> {
                    Game game = new Game();
                    game.setId(UUID.randomUUID().toString());
                    game.setQuestions(mQuestions.getText().toString());
                    game.setAnswers(mAnswers.getText().toString());
                    realm.insert(game);
                }
        ));
    }

    private void setUpRealm() {
        SyncConfiguration configuration = SyncUser.current()
                .createConfiguration(REALM_BASE_URL + "/default")
                .build();
        realm = Realm.getInstance(configuration);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}