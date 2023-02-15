package com.example.fdb.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.fdb.DBHandler;
import com.example.fdb.R;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity {
    //Navigation menu parameters
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }//public boolean onOptionsItemSelected(@NonNull MenuItem item)


    public void addContentView(int layoutId) {
        //Allows other activities to add their content to this activity
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutId, null, false);
        drawerLayout.addView(contentView, 0);
    }//    public void addContentView(int layoutId)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setupControls();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actor: {
                        Intent intent = new Intent(getBaseContext(), ActorActivity_Main.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.filmography: {
                        Intent intent = new Intent(getBaseContext(), FilmsActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.search: {
                        Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.reset: {
                        //calling the reset method
                        reset();
                        break;
                    }
                    case R.id.help: {
                        Intent intent = new Intent(getBaseContext(), HelpActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.about: {
                        Intent intent = new Intent(getBaseContext(), AboutActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case R.id.exit: {
                        //exiting the application
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

    }//protected void onCreate(Bundle savedInstanceState)

    private void setupControls() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //to enable display of the drawer menu on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    } //private void setupControls()

    private void reset() {
        //initialising parameters and setting up controls for the rest
        DBHandler fdbH = new DBHandler(this);
        SQLiteDatabase fdb = fdbH.getWritableDatabase();
        Dialog resetDialog = new Dialog(this);
        resetDialog.setContentView(R.layout.reset_confirm_dialog);
        resetDialog.show();
        Button resetConfirmBtn;
        Button resetBackBtn;
        resetConfirmBtn = resetDialog.findViewById(R.id.resetConfirmButton);
        resetBackBtn = resetDialog.findViewById(R.id.resetBackButton);

        resetConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // reset the database and go back to main activity(actor)
                fdbH.onUpgrade(fdb, 1, 1);
                resetDialog.dismiss();
                startActivity(new Intent(getBaseContext(), ActorActivity_Main.class));
            }

        });//resetConfirmBtn.setOnClickListener(new View.OnClickListener()

        resetBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetDialog.dismiss();
            }
        }); //resetBackBtn.setOnClickListener(new View.OnClickListener()
    }//private void reset()

    @Override
    public void onBackPressed() { // overriding back press when the drawer is open to close it
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}//ublic void onBackPressed()