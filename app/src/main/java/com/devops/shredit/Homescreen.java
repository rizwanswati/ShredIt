package com.devops.shredit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aditya.filebrowser.Constants;
import com.aditya.filebrowser.FileChooser;
import com.aditya.filebrowser.FolderChooser;
import com.google.android.material.navigation.NavigationView;

public class Homescreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "PATH";
    public static final int FILE_PICKER_REQUEST_CODE = 1001;
    public static final int DIRECTORY_REQUEST_CODE = 1002;
    public static final int WRITE_PERMISSION = 001;
    public static final int READ_PERMISSION = 002;
    public static final int REMOVABLE_STORAGE_REQUEST = 003;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private Shredder Shredder;


    /*Singleton Pattern Implementation*/
    ObjectGenerator objectGenerator = new ObjectGenerator();

    Security security = objectGenerator.SecurityObj();
    Permissions permission = objectGenerator.PermissionObj();
    DesignedViews designedViews = objectGenerator.DesignViewObj();
    Generalhelper generalhelper = objectGenerator.GeneralHelperObj();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*App authentications*/
        security.AppAuthenticate(this, this);
        /*Permissions*/
        permission.AppPermissions(this, this);
        /*NavigationItemSelectedListener*/
    }

//    private void AppAuthenticate() {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//            if (km.isKeyguardSecure()) {
//                Intent authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
//                startActivityForResult(authIntent, INTENT_AUTHENTICATE);
//            }
//        }
//    }


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

        generalhelper.ItemSelected(item.getItemId(), this, this);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void pickFile(View view) {

        /*------------------Multiple File chooser-----------------------*/
        Intent intent = new Intent(this, FileChooser.class);
        intent.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
    }

    public void pickDir(View view) {
        /*
         * Single or multiple directories selection for shredding
         * */
        Intent dir_intent = new Intent(this, FolderChooser.class);
        dir_intent.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
        startActivityForResult(dir_intent, DIRECTORY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        TextView textView = findViewById(R.id.file_path);
//        Shredder = new Shredder();
//        int i=0;
//        String path=null;
//        /**
//         * For Multiple files selected
//         * **/
//        if (requestCode == FILE_PICKER_REQUEST_CODE && data != null) {
//            if (resultCode == RESULT_OK) {
//                ArrayList<Uri> files = data.getParcelableArrayListExtra(Constants.SELECTED_ITEMS);
//                for (Uri file: files) { // Datatype var FOR EACH ArrayData var
//                    path = file.getPath();
//                    try {
//                        Shredder.wipeVSITR(path,true);
//                        i++;
//                        textView.setText(path+"\n");
//                        Toast.makeText(this,"Success"+i,Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        /**
//         * For Multiple Directories Selected
//         * **/
//        if (requestCode == DIRECTORY_REQUEST_CODE && data != null) {
//            if (resultCode == RESULT_OK){
//                ArrayList<Uri> dirs = data.getParcelableArrayListExtra(Constants.SELECTED_ITEMS);
//                for (Uri dir: dirs) {
//                    path = dir.getPath();
//                    try {
//                        Shredder.wipeVSITR(path,false);
//                        i++;
//                        textView.setText(path+"\n");
//                        Toast.makeText(this,"Success"+path,Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        if (requestCode==REMOVABLE_STORAGE_REQUEST && resultCode==RESULT_OK)
//        {
//            Uri treeUri = data.getData();
//            grantUriPermission(getPackageName(), treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            getContentResolver().takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//            Shredder = new Shredder();
//
//            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, treeUri);
//            DocumentFile[] files = pickedDir.listFiles();
//
//            String sd_path = FileHelper.getRemoveableMedia(this,true);
//            //String sd_path = treeUri.getEncodedPath();
//            try {
//                Shredder.wipeDoD(sd_path, false, false);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//
//
////            preference = getSharedPreferences("MyShared" , MODE_PRIVATE) ;
////            SharedPreferences.Editor editor = preferences.edit() ;
////            editor.putString("Dir" , treeUri.toString());
////            editor.commit() ;
//
//        }
//
        if (requestCode == Security.INTENT_AUTHENTICATE) {
            if (security.AuthenticateReqResult(resultCode)) {
                designedViews.setHomeScreen(this);
                navigationView = designedViews.setNavigationContents(this, this);
                navigationView.setNavigationItemSelectedListener(this);

            } else {
                AppExitWithoutDialouge();
            }
        }

    }

    private void AppExitWithoutDialouge() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //as there can be many requests for permission so evaluation based on request code using switch
        permission.PermissionReqResult(this, requestCode, permissions, grantResults);
    }

    public void Go(View view) {

        Intent intent = new Intent(Homescreen.this, AlgoShreddingScreen.class);
        startActivity(intent);
    }

//    public void space(View view) {
//        TextView textView = findViewById(R.id.file_path);
//        Shredder = new Shredder();
//        int i = 1;
//        int number_of_files = 5000;
//        long space_in_bytes = FileHelper.ReturnFreeSpace();
//        long File_size =  space_in_bytes/number_of_files;
//        String Folder_path = FileHelper.CreateDir_File(false,null,0);
//        FileHelper.CreateFiles(Folder_path,number_of_files,File_size);
//        try {
//            if(Shredder.wipeDoD(Folder_path,true,false)){
//                textView.setText("Free Space Shredded Successfully.Checking Remaining Space");
//                double remaining_space = FileHelper.ReturnFreeSpace();
//                if(remaining_space>0){
//                    Toast.makeText(this,"remaining Space Available="+remaining_space,Toast.LENGTH_SHORT).show();
//                    String Rm_Folder_path = FileHelper.CreateDir_File(false,null,0);
//                    File_size = (long) (remaining_space/number_of_files);
//                    FileHelper.CreateFiles(Rm_Folder_path,number_of_files,File_size);
//                    Shredder.wipeDoD(Rm_Folder_path,false,false);
//                    FileUtils.deleteDirectory(new File(Folder_path));
//                    textView.setText("Remaining Space Shredded Successfully");
//                }else {
//                    textView.setText("No Remaining Space.Shredding Successful");
//                }
//            }else{
//                textView.setText("Files Shredding has failed");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void spaceCheck(View view) {
//        double remaining_space = FileHelper.ReturnFreeSpace();
//        TextView textView = findViewById(R.id.file_path);
//        textView.setText(String.valueOf(remaining_space));
//    }
//
//    public void shredInternal(View view) {
//        String path = FileHelper.ReturnStoragePath();
//        Shredder = new Shredder();
//        try {
//            Shredder.wipeDoD(path,true,false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        TextView textView = findViewById(R.id.file_path);
//        textView.setText(path);
//    }
//
//    public void shredRemovable(View view) {
//
//        startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), REMOVABLE_STORAGE_REQUEST);
//
//    }
}