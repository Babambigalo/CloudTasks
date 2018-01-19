package com.example.android.cloudtasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nmatveev on 19.01.2018.
 */

public class TasksAdapter extends ArrayAdapter<Tasks> {


    public TasksAdapter(@NonNull Context context, ArrayList<Tasks> tasks) {
        super(context, 0, tasks);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tasks task = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);
        }
        TextView textTask = convertView.findViewById(R.id.text1);
        TextView textDate = convertView.findViewById(R.id.text2);

        textDate.setText(task.getDate());
        textTask.setText(task.getmTask());

        return convertView;
    }
}
