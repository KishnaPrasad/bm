package com.example.kpinfo.bm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class user_identify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_identify);
    }
    public void userLoginParent(View v){
        if(isOnline(this)) {
            Intent intent = new Intent(this, parentLogin.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(user_identify.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }
    public void userLoginChild(View v){
        if(isOnline(this)) {
            Intent intent = new Intent(this, childLogin.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(user_identify.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
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
