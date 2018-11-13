package com.example.kpinfo.bm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;


public class welcomeParent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_parent);



        mToolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.parentDrawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_app:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        new appFragment()).commit();
                break;
            case R.id.nav_call:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        new callsFragment()).commit();
                break;
            case R.id.nav_sms:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        new smsFragment()).commit();
                break;
            case R.id.nav_gallery:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        new  galleryFragment()).commit();
                break;
            case R.id.nav_gps:
                /*it is in WelcomeChild
                Intent serviceIntent = new Intent(this, TrackerService.class);
                startService(serviceIntent);*/
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        new gpsFragment()).commit();

                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        new helpFragment()).commit();
                break;
            case R.id.nav_logout:
                final AlertDialog.Builder builder = new AlertDialog.Builder((this));
                builder.setMessage("Are you sure ,you want to Logout?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        welcomeParent.this.finish();
                        mAuth.signOut();
                        startActivity(new Intent(welcomeParent.this,user_identify.class));
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        mAuth.signOut();
        startActivity(new Intent(this,user_identify.class));
        return super.onOptionsItemSelected(item);
    }
*/
}
