package com.example.android.cloudtasks;

/**
 * Created by nmatveev on 19.01.2018.
 */

public class Tasks {
    private String mTask;
    private String mDate;


    public Tasks(String task,String date){
        mTask = task;
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }

    public String getmTask() {
        return mTask;
    }
}
