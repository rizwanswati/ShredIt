package com.devops.shredit;

import android.app.Activity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class DesignedViews {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    protected void setHomeScreen(Homescreen activity) {
        activity.setContentView(R.layout.activity_homescreen);
    }


    protected NavigationView setNavigationContents(AppCompatActivity homescreen, Activity activity, String Title) {

        /*------------------Attachments----------------------*/
        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        /*----------------------------------------------------*/

        /*----------------Using custom toolbar as actionbar-----------------*/
        homescreen.setSupportActionBar(toolbar);
        toolbar.setTitle(Title);

        /*--------------------------Drawer Toggle------------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        return navigationView;
    }

}
