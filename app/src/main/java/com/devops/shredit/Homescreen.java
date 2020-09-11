package com.devops.shredit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.Preference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.aditya.filebrowser.Constants;
import com.aditya.filebrowser.FileChooser;
import com.aditya.filebrowser.FolderChooser;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Homescreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "PATH";


    private static final int WRITE_PERMISSION = 001;
    private static final int READ_PERMISSION = 002;
    private static final int FILE_PICKER_REQUEST_CODE = 1001;
    private static final int DIRECTORY_REQUEST_CODE = 1002;
    private static final int REMOVABLE_STORAGE_REQUEST = 003;
    private static final String SEC_PREF = "APP_GUARD";
    private static boolean Check;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Switch passSwtich;
    SharedPreferences securityPref;


    /*Singleton Pattern Implementation*/
    ObjectGenerator objectGenerator = new ObjectGenerator();

    Security security = objectGenerator.SecurityObj();
    Permissions permission = objectGenerator.PermissionObj();
    DesignedViews designedViews = objectGenerator.DesignViewObj();
    Generalhelper generalhelper = objectGenerator.GeneralHelperObj();
    IntentCaller intentCaller = objectGenerator.IntentCallerObj();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        Check = generalhelper.appGuard(securityPref, this, SEC_PREF);

        if (Check) {
            security.AppAuthenticate(this, this);
        } else {
            designedViews.setHomeScreen(this);
            navigationView = designedViews.setNavigationContents(this, this, "Shred It");
            navigationView.setNavigationItemSelectedListener(this);
        }
        permission.AppPermissions(this, this);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        generalhelper.ItemSelected(item.getItemId(), this, this);
        drawerLayout = findViewById(R.id.drawer_layout);
        if (id != R.id.nav_switch) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
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

    public void pickFile(View view) {
        /**
         * @this : Context
         * @this : Activity
         * Single or Multiple File Selection
         * */
        intentCaller.pickFiles(this, this);
    }

    public void pickDir(View view) {
        /**
         * @this: Context
         * @this: Activity
         * Single or multiple directories selection for shredding
         * */
        intentCaller.pickDir(this, this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * For Multiple files selected
         * **/
        if (requestCode == FILE_PICKER_REQUEST_CODE && data != null) {
            if (resultCode == RESULT_OK) {
                ArrayList<Uri> files = data.getParcelableArrayListExtra(Constants.SELECTED_ITEMS);
                intentCaller.callWipeAlgorithms(this, files, AlgoShreddingScreen.class, false);
            }
        }

        /**
         * For Multiple Directories Selected
         * **/
        if (requestCode == DIRECTORY_REQUEST_CODE && data != null) {
            if (resultCode == RESULT_OK) {
                ArrayList<Uri> dirs = data.getParcelableArrayListExtra(Constants.SELECTED_ITEMS);
                intentCaller.callWipeAlgorithms(this, dirs, AlgoShreddingScreen.class, false);
            }
        }


        if (requestCode == REMOVABLE_STORAGE_REQUEST && resultCode == RESULT_OK) {
            Uri treeUri = data.getData();
            grantUriPermission(getPackageName(), treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            getContentResolver().takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, treeUri);
            //   String sd_path = FileHelper.getRemoveableMedia(this,true);


            Uri RM_uri = Uri.parse(String.valueOf(pickedDir.getUri()));
            String LastPathSegment = RM_uri.getLastPathSegment();

            Uri uri = Uri.parse(LastPathSegment);
            ArrayList<Uri> rootPath = new ArrayList<>();
            rootPath.add(uri);
            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
            //intentCaller.callWipeAlgorithms(this, rootPath, AlgoShreddingScreen.class, false);

//            preference = getSharedPreferences("MyShared" , MODE_PRIVATE) ;
//            SharedPreferences.Editor editor = preferences.edit() ;
//            editor.putString("Dir" , treeUri.toString());
//            editor.commit() ;

        }

        if (requestCode == Security.INTENT_AUTHENTICATE) {
            if (security.AuthenticateReqResult(resultCode)) {
                designedViews.setHomeScreen(this);
                navigationView = designedViews.setNavigationContents(this, this, "Shred It");
                navigationView.setNavigationItemSelectedListener(this);
                navigationView.getMenu().findItem(R.id.nav_switch)
                        .setActionView(new Switch(this));
                ((Switch) navigationView.getMenu().findItem(R.id.nav_switch).getActionView()).setChecked(Check);

            } else {
                generalhelper.AppExitWithoutDialouge(this);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permission.PermissionReqResult(this, requestCode, permissions, grantResults);
    }


    public void ShredInternalFreeSpace(View view) {
        int number_of_files = 5000;
        long space_in_bytes = FileHelper.ReturnFreeSpace();
        long File_size = space_in_bytes / number_of_files;
        String Folder_path = FileHelper.CreateDir_File(false, null, 0);
        FileHelper.CreateFiles(Folder_path, number_of_files, File_size);

        Uri uri = Uri.parse(Folder_path);
        ArrayList<Uri> FolderPath = new ArrayList<>();
        FolderPath.add(uri);
        intentCaller.callWipeAlgorithms(this, FolderPath, AlgoShreddingScreen.class, true);
    }

    public void spaceCheck(View view) {
        String remaining_space = String.valueOf(FileHelper.ReturnFreeSpace());
        Toast.makeText(this, "Remaining Free Space is: " + remaining_space, Toast.LENGTH_SHORT).show();
    }

    public void shredInternal(View view) {
        String path = FileHelper.ReturnStoragePath();
        Uri uri = Uri.parse(path);
        ArrayList<Uri> rootPath = new ArrayList<>();
        rootPath.add(uri);
        intentCaller.callWipeAlgorithms(this, rootPath, AlgoShreddingScreen.class, false);
    }

    public void shredRemovable(View view) {
//        Toast.makeText(this, "Under development", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), REMOVABLE_STORAGE_REQUEST);
    }

}