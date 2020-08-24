package com.devops.shredit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class AlgoShreddingScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener {


    ObjectGenerator objectGenerator = new ObjectGenerator();
    DesignedViews designedViews = objectGenerator.DesignViewObj();
    Generalhelper generalhelper = objectGenerator.GeneralHelperObj();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ArrayList<Uri> files;
    RadioButton one_byte, gutman, vister, schneire, dod, dod_ext;
    boolean Internal_Free = false;
    ProgressBar progressBar;
    TextView showProgress;
    int progress = 0;
    private int SelectedAlgo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algo_shredding_screen);
        navigationView = designedViews.setNavigationContents(this, this);
        navigationView.setNavigationItemSelectedListener(this);

        one_byte = findViewById(R.id.one_byte);
        one_byte.setOnCheckedChangeListener(this);
        gutman = findViewById(R.id.gutman);
        gutman.setOnCheckedChangeListener(this);
        schneire = findViewById(R.id.schneire);
        schneire.setOnCheckedChangeListener(this);
        vister = findViewById(R.id.vsitr);
        vister.setOnCheckedChangeListener(this);
        dod = findViewById(R.id.dod);
        dod.setOnCheckedChangeListener(this);
        dod_ext = findViewById(R.id.dod_ext);
        dod_ext.setOnCheckedChangeListener(this);

        Intent intent = getIntent();
        files = intent.getParcelableArrayListExtra("URIs");
        Internal_Free = intent.getBooleanExtra("Internal_Free", false);
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
        generalhelper.ItemSelected(item.getItemId(), this, this);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }


    public void BeginShredding(View view) {
        progressBar = findViewById(R.id.pbar);
        showProgress = findViewById(R.id.showProgr);

        if (SelectedAlgo != 0) {
            if (SelectedAlgo == R.id.dod) {
                generalhelper.CallDoD(files, Internal_Free, progressBar, showProgress);
            } else if (SelectedAlgo == R.id.dod_ext) {
                generalhelper.CallDoDExtended(files, Internal_Free, progressBar, showProgress);
            } else if (SelectedAlgo == R.id.vsitr) {
                generalhelper.CallVsiter(files, Internal_Free, progressBar, showProgress);
            } else if (SelectedAlgo == R.id.schneire) {
                generalhelper.CallSchneire(files, Internal_Free, progressBar, showProgress);
            } else if (SelectedAlgo == R.id.gutman) {
                generalhelper.CallGutman(files, Internal_Free, progressBar, showProgress);
            } else {
                generalhelper.CallOneByte(files, Internal_Free, progressBar, showProgress);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.one_byte:
                    SelectedAlgo = R.id.one_byte;
                    DelselectAllExcept(R.id.one_byte);
                    break;

                case R.id.gutman:
                    SelectedAlgo = R.id.gutman;
                    DelselectAllExcept(R.id.gutman);
                    break;

                case R.id.vsitr:
                    SelectedAlgo = R.id.vsitr;
                    DelselectAllExcept(R.id.vsitr);
                    break;

                case R.id.schneire:
                    SelectedAlgo = R.id.schneire;
                    DelselectAllExcept(R.id.schneire);
                    break;

                case R.id.dod:
                    SelectedAlgo = R.id.dod;
                    DelselectAllExcept(R.id.dod);
                    break;

                case R.id.dod_ext:
                    SelectedAlgo = R.id.dod_ext;
                    DelselectAllExcept(R.id.dod_ext);
                    break;

                default:
                    Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void DelselectAllExcept(int id) {
        if (id == R.id.one_byte) {
            gutman.setChecked(false);
            schneire.setChecked(false);
            vister.setChecked(false);
            dod.setChecked(false);
            dod_ext.setChecked(false);
        }
        if (id == R.id.gutman) {
            one_byte.setChecked(false);
            schneire.setChecked(false);
            vister.setChecked(false);
            dod.setChecked(false);
            dod_ext.setChecked(false);
        }
        if (id == R.id.schneire) {
            gutman.setChecked(false);
            one_byte.setChecked(false);
            vister.setChecked(false);
            dod.setChecked(false);
            dod_ext.setChecked(false);
        }

        if (id == R.id.vsitr) {
            gutman.setChecked(false);
            schneire.setChecked(false);
            one_byte.setChecked(false);
            dod.setChecked(false);
            dod_ext.setChecked(false);
        }
        if (id == R.id.dod) {
            gutman.setChecked(false);
            schneire.setChecked(false);
            vister.setChecked(false);
            one_byte.setChecked(false);
            dod_ext.setChecked(false);
        }
        if (id == R.id.dod_ext) {
            gutman.setChecked(false);
            schneire.setChecked(false);
            vister.setChecked(false);
            dod.setChecked(false);
            one_byte.setChecked(false);
        }
    }
}