package com.projects.satyajit.projectbca;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton searchActivity;
    Button test;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Making bottom navigation functional
        bottomNavigationView= findViewById(R.id.botton_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);
        // setting the fragment the moment onCreate is Called.
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FragmentHome()).commit();
        setUpToolBar();
        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_about:
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FragmentHome()).commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_compare:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FragmentCompare()).commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_shoppinglist:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FragmentShoppingList()).commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FragmentProfile()).commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_health:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FragmentHealth()).commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_settings:
                        Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId())
                    {
                        case R.id.bottom_nav_home:
                            selectedFragment = new FragmentHome();
                            getSupportActionBar().setTitle("Dashboard");
                            break;
                        case R.id.bottom_nav_compare:
                            selectedFragment = new FragmentCompare();
                            getSupportActionBar().setTitle("Compare Items");
                            break;
                        case R.id.bottom_nav_shoppinglist:
                            selectedFragment = new FragmentShoppingList();
                            getSupportActionBar().setTitle("Shopping List");
                            break;
                        case R.id.bottom_nav_profile:
                            selectedFragment = new FragmentProfile();
                            getSupportActionBar().setTitle("Profile");
                            break;
                        case R.id.bottom_nav_health:
                            selectedFragment = new FragmentHealth();
                            getSupportActionBar().setTitle("Your Health");
                            break;



                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;// select the item, if false fragment will be shown but not slected.
                }
            };

    public void toSearchPage(View view) {
        Intent intent = new Intent(this, search_activity.class);
        startActivity(intent);
    }

    private void setUpToolBar(){
            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            toolbar = (Toolbar)findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.app_name, R.string.app_name);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
    }



}
