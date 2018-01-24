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
    private ArrayList<Tasks> tasks = new ArrayList<>();
    int clickNum = 1;
    int i = 1;
    Button addTask;
    //private ListView ListUserTasks;

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
        //tasks = new ArrayList<>();
        final String id = user.getUid();
        myRef = database.getReference("Tasks").child(id);



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

                    myRef.child("task" + clickNum).setValue(task);
                    adapter.notifyDataSetChanged();
                    clickNum += 1;
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



    }


    private void showData(DataSnapshot dataSnapshot) {
        //ArrayList<Tasks> tasks  = new ArrayList<>();
       // Log.v(TAG,"mTask = "+ dataSnapshot.getValue(Tasks.class).getmTask());

        for (int ii = 1; i<=dataSnapshot.getChildrenCount();i++){
            Log.v(TAG,"ds = " + dataSnapshot + "    " + "dataSnapshot.getChildren()=  " + dataSnapshot.getChildrenCount());
           Tasks task1 = new Tasks(dataSnapshot.child("task"+i).getValue(Tasks.class).getmTask(),dataSnapshot.child("task"+i).getValue(Tasks.class).getmDate());
            tasks.add(task1);
            ii+=1;
        }
//        for (DataSnapshot ds : dataSnapshot.getChildren()){
//            Log.v(TAG,"ds = " + ds + "    " + "dataSnapshot.getChildren()=  " + dataSnapshot.getChildren());
//            Tasks task1 = new Tasks(dataSnapshot.child("task"+i).getValue(Tasks.class).getmTask(),dataSnapshot.child("task"+i).getValue(Tasks.class).getmDate());
//            tasks.add(task1);
//            i+=1;
//        }







//       for (DataSnapshot ds : dataSnapshot.getChildren()){
//           if (ds.child(id).getChildren()!= null){
//               //tasks.add();
//               //tasks.add(new Tasks(ds.child(id).child("task" + i).getValue(Tasks.class).getmTask(),ds.child(id).child("task"+i).getValue(Tasks.class).getmDate()));
//               i+=1;
//               Log.v(TAG,"Key " + "task" + i+ "not found" +"  " +ds.child(id).getChildren());
//
//           }else{
//               Log.v(TAG,"Key " + "task" + i+ "not found" +"  " +ds.child(id).getChildren());
//               i+=1;
//           }
//       }
        i += 1;
        TasksAdapter adapter =new TasksAdapter(ListTasks.this,tasks);
        ListView lv = findViewById(R.id.lvTasks);
        lv.setAdapter(adapter);






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
