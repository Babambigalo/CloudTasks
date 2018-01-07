package com.example.android.cloudtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private EditText ETemail;
    private EditText ETpassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Intent intent = new Intent(EmailPasswordActivity.this,ListTasks.class);
                    startActivity(intent);

                    // Do whatever you want with the UserId by firebaseUser.getUid()
                    //Log.d("TAG", "onAuthStateChanged:signed_in:" + firebaseUser.getUid());
                } else {
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
            }
        };

        ETemail = findViewById(R.id.et_email);
        ETpassword = findViewById(R.id.et_password);


        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(EmailPasswordActivity.this,ListTasks.class);
            startActivity(intent);

        }
        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_in) {
            signin(ETemail.getText().toString(), ETpassword.getText().toString());
        } else if (v.getId() == R.id.btn_registration) {
            registration(ETemail.getText().toString(), ETpassword.getText().toString());

        }
    }

    public void signin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EmailPasswordActivity.this, "авторизация успешна", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(EmailPasswordActivity.this, "авторизация провалена", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("TAG","Successful?  " + task.getException());
                if (task.isSuccessful()) {
                    Toast.makeText(EmailPasswordActivity.this, "регистрация успешна", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EmailPasswordActivity.this, "регистрация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//
//    private EditText ETemail;
//    private EditText ETpassword;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_email_password);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//
//                } else {
//                    // User is signed out
//
//                }
//
//            }
//        };
//
//        ETemail = (EditText) findViewById(R.id.et_email);
//        ETpassword = (EditText) findViewById(R.id.et_password);
//
//        findViewById(R.id.btn_sign_in).setOnClickListener(this);
//        findViewById(R.id.btn_registration).setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        if(view.getId() == R.id.btn_sign_in)
//        {
//            signin(ETemail.getText().toString(),ETpassword.getText().toString());
//        }else if (view.getId() == R.id.btn_registration)
//        {
//            registration(ETemail.getText().toString(),ETpassword.getText().toString());
//        }
//
//    }
//
//    public void signin(String email , String password)
//    {
//        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()) {
//                    Toast.makeText(EmailPasswordActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
//                }else
//                    Toast.makeText(EmailPasswordActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//    public void registration (String email , String password){
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                Log.d("TAG","Successful?  " + task.getException());
//                if(task.isSuccessful())
//                {
//                    Toast.makeText(EmailPasswordActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
//                }
//                else
//                    Toast.makeText(EmailPasswordActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}
