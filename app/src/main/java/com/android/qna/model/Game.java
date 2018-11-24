package com.android.qna.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Game extends RealmObject {
    @Required
    @PrimaryKey
    private String id;

    @Required
    private String questions;

    @Required
    private String answers;

    public Game(){
        this.id = UUID.randomUUID().toString();
        this.questions = "";
        this.answers = "";
    }

    public String getId(){ return id; }

    public void setId(String id){ this.id = id; }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
