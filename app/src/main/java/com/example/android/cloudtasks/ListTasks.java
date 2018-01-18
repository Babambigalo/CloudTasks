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

public class ListTasks extends AppCompatActivity {

    ListView ListUserTasks;

    private static final String TAG = "ListTasks";
    //private MenuItem signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);


        Toolbar myToolbar =  findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        ListUserTasks = findViewById(R.id.discr_for_task);


//        signOut = findViewById(R.id.action_sign_out);
//        signOut.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                FirebaseAuth.getInstance().signOut();
//                return false;
//            }
//        });














//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("TAG", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });






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
