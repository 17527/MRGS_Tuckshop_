package com.example.mrgstuckshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class OrderPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//setting the variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    // gets full screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

//setting the custom toolbar as the toolbar for the app
        setSupportActionBar(toolbar);

//gets the navigation drawer and displays it when clicked on the button
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


    }


    // if back button is pressed while navigation drawer is open, then it closes it
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //this sets the navigation place for each of the buttons in naviagtion bar and once other activity is opened, nav drawer closes
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(OrderPage.this, HomePage.class);
                startActivity(intent);
                break;
            case R.id.nav_ordernow:
                break;
            case R.id.nav_help:
                intent = new Intent(OrderPage.this, Help.class);
                startActivity(intent);
                break;
            case R.id.nav_contact:
                intent = new Intent(OrderPage.this, ContactPage.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                Toast.makeText(OrderPage.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}