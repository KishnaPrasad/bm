package com.example.kpinfo.bm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class parentLogin extends AppCompatActivity {
    EditText username,password;
    String user,pass;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);
        username = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
       progressBar =(ProgressBar) findViewById(R.id.pBar);
       progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
            Toast.makeText(this, "Already Logged in", Toast.LENGTH_LONG).show();
            this.finish();
            Intent intent = new Intent(parentLogin.this, welcomeParent.class);
            startActivity(intent);
        }
    }
    public void login(View view) {
        user = username.getText().toString().trim();
        pass = password.getText().toString().trim();

        if (user.isEmpty()) {
            Toast.makeText(parentLogin.this, "Email required", Toast.LENGTH_LONG).show();
            username.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            Toast.makeText(parentLogin.this, "Enter a valid Email", Toast.LENGTH_LONG).show();
            username.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            Toast.makeText(parentLogin.this, "Enter Password!!!", Toast.LENGTH_LONG).show();
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                                finish();
                                Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),welcomeParent.class);
                                startActivity(i);
                    //checkEmail();
                } else {
                    mAuth.signOut();
                    username.setText("");
                    password.setText("");
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(parentLogin.this, "Invalid User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*private void checkEmail()
    {

        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();

        Boolean emailverify = firebaseUser.isEmailVerified(); //it return either true or false

        if(emailverify)
        {
            finish();
            Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,welcomeParent.class));
        }
        else {
            mAuth.signOut();
            username.setText("");
            password.setText("");
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this,"Verify your Email",Toast.LENGTH_SHORT).show();
        }


    }*/
}





