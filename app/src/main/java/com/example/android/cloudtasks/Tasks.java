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



    public String getmDate() {
        return mDate;
    }

    public String getmTask() {
        return mTask;
    }

    public void setmTask(String task) {
        this.mTask = task;
    }

    public void setmDate(String date) {
        this.mDate = date;
    }
}
