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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    private ArrayList<Tasks> tasks;
    int clickNum = 1;
    Button addTask;

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
        tasks = new ArrayList<>();
        final String id = user.getUid();
        myRef = database.getReference("Tasks");


        final TasksAdapter adapter = new TasksAdapter(ListTasks.this, tasks);
        final ListView ListUserTasks = findViewById(R.id.lvTasks);
        ListUserTasks.setAdapter(adapter);

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
                    Log.v(TAG, "task array " + tasks);

                    myRef.child(id).child("task" + clickNum).setValue(task);
                    adapter.notifyDataSetChanged();
                    clickNum += 1;
                    ETTask.setText("");
                    ETdate.setText("");
                    ETdate.requestFocus();

                }
            }
        });

        ValueEventListener taskListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Tasks task = dataSnapshot.getValue(Tasks.class);

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



    }


    private void showData(DataSnapshot dataSnapshot) {
        int i = 0;
        ArrayList<Tasks> tasks  = new ArrayList<>();
        String id = user.getUid()
       for (DataSnapshot ds : dataSnapshot.getChildren()){

           tasks.add(new Tasks(ds.child(id).child(i+1).getValue(Tasks.class).getmTask()),ds.child(id).child(i+1).getValue(Tasks.class).getmDate());



       }


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
