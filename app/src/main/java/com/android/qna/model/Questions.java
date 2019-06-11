package com.android.qna.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Questions extends RealmObject{

    @Required
    @PrimaryKey
    public Integer id;

    private String question;

    private String answer;

    private RealmList<Options> options = new RealmList<>();

    public Questions(){}

    public Questions(Integer id,String question, String answer){
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() { return answer; }

    public RealmList<Options> getOptions() { return options; }
}
