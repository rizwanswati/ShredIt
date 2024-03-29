package com.devops.shredit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class About extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ObjectGenerator objectGenerator = new ObjectGenerator();
    DesignedViews designedViews = objectGenerator.DesignViewObj();
    Generalhelper generalhelper = objectGenerator.GeneralHelperObj();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView linked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        navigationView = designedViews.setNavigationContents(this, this, "About");
        navigationView.setNavigationItemSelectedListener(this);

        linked = findViewById(R.id.linkedin);
        linked.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shareIt:
                generalhelper.ShareApp(this);
                break;
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        generalhelper.ItemSelected(item.getItemId(), this, this);
        drawerLayout = findViewById(R.id.drawer_layout);
        if (id != R.id.nav_switch) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}