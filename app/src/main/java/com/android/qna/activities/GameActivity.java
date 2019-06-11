package com.android.qna.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.android.qna.R;
import com.android.qna.databinding.GameMainBinding;
import com.android.qna.model.Options;
import com.android.qna.model.Questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.android.qna.utilities.QuestionsDataUtil.getListAnswers;
import static com.android.qna.utilities.QuestionsDataUtil.getListOptions;
import static com.android.qna.utilities.QuestionsDataUtil.getListQuestions;

public class GameActivity extends AppCompatActivity{

    private static final String LOG_TAG = GameActivity.class.getSimpleName();

    private GameMainBinding gameMainBinding;
    private Realm realm;
    private int gameID;
    private List<String> storedAnswers = new ArrayList<>();
    private List<String> answerList = new ArrayList<>();
    private int mSuccessCount, mFailureCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameMainBinding = DataBindingUtil.setContentView(this, R.layout.game_main);
        realm = Realm.getDefaultInstance();
        insertQuestionAndOptions();
        gameID = 1;
        queryData(gameID);
        answerClick();
    }

    private void queryData(int id) {
        Questions questions = realm.where(Questions.class).equalTo("id", id).findFirst();
        if (questions != null){
            RealmList<Options> options = questions.getOptions();
            RealmResults<Options> optionsRealmResults = options.where().findAll();
            List<String> list = new ArrayList<>();
            list.add(optionsRealmResults.get(0).getOption());
            list.add(optionsRealmResults.get(1).getOption());
            list.add(optionsRealmResults.get(2).getOption());
            list.add(optionsRealmResults.get(3).getOption());

            int[] ind = {0, 1, 2, 3};
            int[] index = shuffle(ind);
            gameMainBinding.tvQuestion.setText(questions.getQuestion());
            gameMainBinding.tvOption1.setText(list.get(index[0]));
            gameMainBinding.tvOption2.setText(list.get(index[1]));
            gameMainBinding.tvOption3.setText(list.get(index[2]));
            gameMainBinding.tvOption4.setText(list.get(index[3]));
        }
        else {
            calculate();
            int total = mSuccessCount + mFailureCount;
            String display = mSuccessCount + "/" + total;
            new AlertDialog.Builder(this)
                    .setTitle(R.string.correct_score)
                    .setMessage(display)
                    .setPositiveButton("Yes, I want to start over", (dialogInterface, i) -> {
                        storedAnswers.clear();
                        answerList.clear();
                        mSuccessCount = 0;
                        mFailureCount = 0;
                        gameID = 1;
                        queryData(gameID);
//                        answerClick();
                    })
                    .setNegativeButton("No, I'm done", (dialogInterface, i) -> finish())
                    .show();
        }
    }

    public void insertQuestionAndOptions() {
        realm.executeTransaction(realm -> {
            Integer id = 1;
            int j = 0;
            for (int i = 0; i < getListQuestions().size(); i++) {
                Options option1 = new Options(id, getListOptions().get(j));
                j++;
                Options option2 = new Options(id, getListOptions().get(j));
                j++;
                Options option3 = new Options(id, getListOptions().get(j));
                j++;
                Options option4 = new Options(id, getListOptions().get(j));
                j++;
                realm.insertOrUpdate(option1);
                realm.insertOrUpdate(option2);
                realm.insertOrUpdate(option3);
                realm.insertOrUpdate(option4);

                Questions question = new Questions(id, getListQuestions().get(i), getListAnswers().get(i));
                RealmList<Options> list = question.getOptions();
                list.add(option1);
                list.add(option2);
                list.add(option3);
                list.add(option4);
                realm.insertOrUpdate(question);
                id++;
            }
        });
    }

    private void calculate() {
        RealmResults<Questions> answers = realm.where(Questions.class).findAll();
        for (int i = 0; i < answers.size(); i++){
            answerList.add(answers.get(i).getAnswer());
        }
        for (int i = 0; i < storedAnswers.size(); i++){
            if (storedAnswers.get(i).equals(answerList.get(i))){
                mSuccessCount++;
            }else {
                mFailureCount++;
            }
        }
    }

    private void setAnswer(String answer){
        storedAnswers.add(answer);
        gameID++;
        queryData(gameID);
    }

    private void answerClick(){
        gameMainBinding.tvOption1.setOnClickListener(view -> {
            String answer = gameMainBinding.tvOption1.getText().toString();
            setAnswer(answer);
        });
        gameMainBinding.tvOption2.setOnClickListener(view -> {
            String answer = gameMainBinding.tvOption2.getText().toString();
            setAnswer(answer);
        });
        gameMainBinding.tvOption3.setOnClickListener(view -> {
            String answer = gameMainBinding.tvOption3.getText().toString();
            setAnswer(answer);
        });
        gameMainBinding.tvOption4.setOnClickListener(view -> {
            String answer = gameMainBinding.tvOption4.getText().toString();
            setAnswer(answer);
        });
    }

    private int[] shuffle(int[] arr){
        Random rnd = new Random();
        for (int i = 0; i < arr.length; i++){
            int randomPosition = rnd.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[randomPosition];
            arr[randomPosition] = temp;
        }
        return arr;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}