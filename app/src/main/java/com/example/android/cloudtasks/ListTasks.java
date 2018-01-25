package com.example.android.cloudtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListTasks extends AppCompatActivity {


    private static final String TAG = "ListTasks";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private EditText ETTask;
    private EditText ETdate;
    private ArrayList<Tasks> tasks = new ArrayList<>();
    ProgressBar progressBar;
    ListView ListUserTasks;
    Button addTask;
    Long taskNumber;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);





        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        database = FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();


        addTask = findViewById(R.id.addTask);
        ETTask = findViewById(R.id.edTask);
        ETdate = findViewById(R.id.edDate);
        myRef = database.getReference("Tasks").child(user.getUid());
        ListUserTasks = findViewById(R.id.lvTasks);
        progressBar = findViewById(R.id.progressBar2);
//        final String taskText = ETTask.getText().toString();
//        final String dateText = ETdate.getText().toString();


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = ETTask.getText().toString();
                String dateText = ETdate.getText().toString();
                if (TextUtils.isEmpty(taskText) || TextUtils.isEmpty(dateText)) {
                    Toast.makeText(ListTasks.this, "Field are empty",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Tasks task = new Tasks(taskText, dateText);
                    tasks.add(new Tasks(taskText, dateText));
                    Log.v(TAG, "task array " + tasks.size());


                    myRef.child("task" + taskNumber).setValue(task);
                    taskNumber+=1;
                    ETTask.setText("");
                    ETdate.setText("");
                    ETdate.requestFocus();

                }
            }
        });


        //DatabaseReference mChild =  myRef.child(id).child("task"+i);

        ValueEventListener taskListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                showData(dataSnapshot);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef.addValueEventListener(taskListener);



        ListUserTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListTasks.this,"Item is clicked",Toast.LENGTH_SHORT).show();
            }
        });




    }


    private void showData(DataSnapshot dataSnapshot) {
        taskNumber = dataSnapshot.getChildrenCount();
        if (tasks.size() == 0){
            for (int ii = 1; ii<=dataSnapshot.getChildrenCount();ii++) {
                Log.v(TAG, "ds = " + dataSnapshot + "    " + "dataSnapshot.getChildren()=  " + dataSnapshot.getChildrenCount() + "   " + "ii = " + ii);
                Tasks task1 = new Tasks(dataSnapshot.child("task" + ii).getValue(Tasks.class).getmTask(), dataSnapshot.child("task" + ii).getValue(Tasks.class).getmDate());
                tasks.add(task1);
                //Tasks task1 = new Tasks(dataSnapshot.child("task"+dataSnapshot.getChildrenCount()).getValue(Tasks.class).getmTask(),dataSnapshot.child("task"+dataSnapshot.getChildrenCount()).getValue(Tasks.class).getmDate());
            }
            taskNumber+=1;

        }else{
            Tasks task1 = new Tasks(dataSnapshot.child("task"+taskNumber).getValue(Tasks.class).getmTask(),dataSnapshot.child("task"+taskNumber).getValue(Tasks.class).getmDate());
            taskNumber+=1;
        }


        TasksAdapter adapter =new TasksAdapter(ListTasks.this,tasks);
        ListUserTasks.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == item.getItemId()) {
            Log.v(TAG, "USER = " + FirebaseAuth.getInstance().getCurrentUser());
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ListTasks.this, EmailPasswordActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
