package com.android.qna.model;

import io.realm.RealmObject;

public class Options extends RealmObject {

    public Integer optionID;

    private String option;

    public Options(){}

    public Options(Integer optionID, String option){ this.optionID = optionID; this.option = option; }

    public String getOption() {
        return option;
    }
}
