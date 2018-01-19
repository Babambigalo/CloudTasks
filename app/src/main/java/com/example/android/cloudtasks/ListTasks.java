package com.example.android.cloudtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListTasks extends AppCompatActivity {

    private ListView ListUserTasks;
    private static final String TAG = "ListTasks";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);


        Toolbar myToolbar =  findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        database = FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        Log.v(TAG,"userId = " + id);

        myRef = database.getReference();
        myRef.setValue("0","ЙО");

        ListUserTasks = findViewById(R.id.listTasks);









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==item.getItemId()){
            Log.v(TAG,"USER = " +FirebaseAuth.getInstance().getCurrentUser());
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ListTasks.this,EmailPasswordActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
