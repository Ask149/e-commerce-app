package com.learnadroid.myfirstapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.hitomi.cmlibrary.CircleMenu;

import org.w3c.dom.Text;

import static android.R.anim.slide_in_left;
import static android.R.anim.slide_out_right;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static public boolean flag;
    static public int fragment_no;
    static public int cat_id;
    static public int prod_id;
    static public int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cat_id=1;
        prod_id=1;
        fragment_no=1;
        flag=false;
        setContentView(R.layout.activity_home);
        Fragment fragment = new CategoryActivity();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_home,fragment);
        ft.commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);
            if (circleMenu.isOpened())
                    circleMenu.closeMenu();
            else
                finish();
        }
        else {
//
            Fragment fragment = null;
            if(fragment_no==2){
                fragment = new Fragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_home,fragment);
                ft.commit();
            }
            else if(fragment_no==3)
            {
                fragment = new CategoryActivity();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_home,fragment);
                ft.commit();
            }
            else if(fragment_no==4)
            {
                fragment = new ListActivity();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_home,fragment);
                ft.commit();
            }
            else if(fragment_no==5)
            {
                fragment = new DetailActivity();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_home,fragment);
                ft.commit();

            }
            else
            {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        TextView t = (TextView) findViewById(R.id.home_username);
        t.setText(MainActivity.user_name);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_orders) {
            Fragment fragment = new OrderListActivity();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_home,fragment);
            ft.commit();
            flag=false;
            return true;
        }
        else if (id == R.id.action_logout)
        {
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            cat_id=0;
            fragment = new CategoryActivity();
        } else if (id == R.id.nav_mobile) {
            cat_id=1;
            fragment = new ListActivity();
        } else if (id == R.id.nav_laptops) {
            cat_id=2;
            fragment = new ListActivity();
        } else if (id == R.id.nav_washingmachine) {
            cat_id=3;
            fragment = new ListActivity();
        } else if (id == R.id.nav_refridgerators) {
            cat_id=4;
            fragment = new ListActivity();
        } else if (id == R.id.nav_tv) {
            cat_id=5;
            fragment = new ListActivity();
        }
        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_home,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}