package com.example.android.cloudtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String TAG = "EmailPasswordActivity";
    EditText ETemail;
    EditText ETpassword;
    Button bReg;
    Button bSign;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);
        mAuth = FirebaseAuth.getInstance();
        bReg = findViewById(R.id.btn_registration);
        bSign = findViewById(R.id.btn_sign_in);
        ETemail = findViewById(R.id.et_email);
        ETpassword = findViewById(R.id.et_password);

        Toolbar myToolbar =  findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);




        bSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ETemail.getText().toString().length()!= 0 & ETpassword.getText().toString().length() != 0 & mAuth.getCurrentUser() != null ){
                    signIn(ETemail.getText().toString(),ETpassword.getText().toString());
                    Intent intent = new Intent(EmailPasswordActivity.this,ListTasks.class);
                    startActivity(intent);

                }else if (ETemail.getText().toString().length()!= 0 & ETpassword.getText().toString().length() != 0 & mAuth.getCurrentUser() == null){
                    Log.v(TAG,"user = " + mAuth.getCurrentUser());
                    Toast.makeText(EmailPasswordActivity.this,"User not found",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EmailPasswordActivity.this,"fill all fields ",Toast.LENGTH_SHORT).show();
                }


            }
        });
        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration(ETemail.getText().toString(),ETpassword.getText().toString());
            }
        });

        //mAuth.addAuthStateListener(mAuthStateListener);





//        mAuth = FirebaseAuth.getInstance();
//
//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//                if (firebaseUser != null) {
//                    Intent intent = new Intent(EmailPasswordActivity.this, ListTasks.class);
//                    startActivity(intent);
//
//                    // Do whatever you want with the UserId by firebaseUser.getUid()
//                    //Log.d("TAG", "onAuthStateChanged:signed_in:" + firebaseUser.getUid());
//                } else {
//                    Log.d("TAG", "onAuthStateChanged:signed_out");
//                }
//            }
//        };
//
//        ETemail = findViewById(R.id.et_email);
//        ETpassword = findViewById(R.id.et_password);
//
//
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            //Intent intent = new Intent(EmailPasswordActivity.this,ListTasks.class);
//            //startActivity(intent);
//            Log.d("TAG", "onAuthStateChanged:signed_out");
//
//        }
//        findViewById(R.id.btn_sign_in).setOnClickListener(this);
//        findViewById(R.id.btn_registration).setOnClickListener(this);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btn_sign_in) {
//            signin(ETemail.getText().toString(), ETpassword.getText().toString());
//        } else if (v.getId() == R.id.btn_registration) {
//            registration(ETemail.getText().toString(), ETpassword.getText().toString());
//
//        }
//    }
//
//    public void signin(String email, String password) {
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(EmailPasswordActivity.this, "авторизация успешна", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(EmailPasswordActivity.this, "авторизация провалена", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }
//
//    public void registration(String email, String password) {
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                Log.d("TAG", "Successful?  " + task.getException());
//                if (task.isSuccessful()) {
//                    Toast.makeText(EmailPasswordActivity.this, "регистрация успешна", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(EmailPasswordActivity.this, "регистрация провалена", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    public void signIn(String email, String password){
        ETemail = findViewById(R.id.et_email);
        ETpassword = findViewById(R.id.et_password);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }

        });
    }

    public void registration(String email,String password){
        ETemail = findViewById(R.id.et_email);
        ETpassword = findViewById(R.id.et_password);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }


    public void updateUI(FirebaseUser user) {
        if (mAuth.getCurrentUser() != null){
            Intent intent = new Intent(EmailPasswordActivity.this,ListTasks.class);
            startActivity(intent);
        }else {
            Log.v(TAG,"User not authenticate  " + mAuth.getCurrentUser());
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        FirebaseAuth.getInstance().signOut();
//    }

}





