package com.example.kpinfo.bm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.firebase.database.FirebaseDatabase;

import static com.example.kpinfo.bm.R.string.un;

public class childLogin extends AppCompatActivity {
    EditText userName,userEmail,userPass,userCPass;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_login);
        userName=(EditText) findViewById(R.id.uName);
        userEmail=(EditText) findViewById(R.id.uEmail);
        userPass=(EditText) findViewById(R.id.uPassword);
        userCPass=(EditText) findViewById(R.id.uConfirmPassword);
        progressBar =(ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
            Toast.makeText(this, "Already Logged in", Toast.LENGTH_LONG).show();
            this.finish();
            Intent intent = new Intent(childLogin.this, WelcomeChild.class);
            startActivity(intent);
        }
    }

    public void registerUser(View view)
    {
        if(isOnline(this)) {
            final String name, email;
            String pass, cpass;
            name = userName.getText().toString().trim();
            email = userEmail.getText().toString().trim();
            pass = userPass.getText().toString().trim();
            cpass = userCPass.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(childLogin.this, "Name required", Toast.LENGTH_LONG).show();
                userName.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                Toast.makeText(childLogin.this, "Email required", Toast.LENGTH_LONG).show();
                userEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(childLogin.this, "Enter a valid Email", Toast.LENGTH_LONG).show();
                userEmail.requestFocus();
                return;
            }
            if (pass.isEmpty()) {
                Toast.makeText(childLogin.this, "Enter Password!!!", Toast.LENGTH_LONG).show();
                userPass.requestFocus();
                return;
            }
            if (pass.length() < 6) {
                Toast.makeText(childLogin.this, "Password must be atleast 6 characters long!!!", Toast.LENGTH_LONG).show();
                userPass.requestFocus();
                return;
            }
            if (cpass.isEmpty()) {
                Toast.makeText(childLogin.this, "Please enter the Confirmation Password", Toast.LENGTH_LONG).show();
                userCPass.requestFocus();
                return;
            }
            if (!cpass.equals(pass)) {
                Toast.makeText(childLogin.this, "Password dosent match,please re-enter", Toast.LENGTH_LONG).show();
                userPass.setText("");
                userCPass.setText("");
                userPass.requestFocus();
            }


            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                User user = new User(name, email);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            Toast.makeText(childLogin.this, "registration_success", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(childLogin.this, WelcomeChild.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(childLogin.this, "Failed", Toast.LENGTH_LONG).show();
                                            Toast.makeText(childLogin.this,
                                                    "Login unsuccessful: " + task.getException().getMessage(), //ADD THIS
                                                    Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(childLogin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
        else{
            Toast.makeText(this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }

    }

    protected boolean isOnline(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            int networkType = activeNetwork.getType();
            return networkType == ConnectivityManager.TYPE_WIFI || networkType == ConnectivityManager.TYPE_MOBILE;
        } else {
            return false;
        }

    }
}
