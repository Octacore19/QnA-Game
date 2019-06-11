package com.android.qna.utilities;

import java.util.ArrayList;
import java.util.List;

public class QuestionsDataUtil {

    private static final List<String> listQuestions = new ArrayList<String>() {
        {
            add("What is the root of 4?");
            add("What is the cube of 3?");
            add("What does UPS stand for?");
            add("How many Champions League trophy has Liverpool won");
            add("Club that has won the most Champions League till date is");
        }
    };

    private static final List<String> listAnswers = new ArrayList<String>() {
        {
            add("2");
            add("27");
            add("Uninterrupted Power Supply");
            add("6");
            add("Real Madrid");
        }
    };

    private static final List<String> listOptions = new ArrayList<String>(){
        {
            add("3"); add("8"); add("1"); add("2");
            add("9"); add("18"); add("28"); add("27");
            add("United Power Supply"); add("Uninterrupted Power Supply"); add("Unified Policy Supplement"); add("Union Power Super");
            add("6"); add("12"); add("1"); add("10");
            add("Arsenal"); add("Barcelona"); add("Chelsea"); add("Real Madrid");
        }
    };

    public static List<String> getListQuestions() {
        return listQuestions;
    }

    public static List<String> getListAnswers() {
        return listAnswers;
    }

    public static List<String> getListOptions() {
        return listOptions;
    }
}
