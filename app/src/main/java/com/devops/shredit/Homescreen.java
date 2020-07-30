package com.devops.shredit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.KeyguardManager;
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
    private static final int INTENT_AUTHENTICATE = 004;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private Shredder Shredder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppAuthenticate();
    }

    private void AppAuthenticate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if (km.isKeyguardSecure()) {
                Intent authIntent = km.createConfirmDeviceCredentialIntent(getString(R.string.dialog_title_auth), getString(R.string.dialog_msg_auth));
                startActivityForResult(authIntent, INTENT_AUTHENTICATE);
            }
        }
    }

    private void setContentsViews() {
        /*-------------Requesting Storage permission-------------*/
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, WRITE_PERMISSION);
        }


        /*------------------Attachments----------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        /*----------------------------------------------------*/

        /*----------------Using custom toolbar as actionbar-----------------*/
        setSupportActionBar(toolbar);

        /*--------------------------Drawer Toggle------------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.exit:
                ExitDialouge();
                break;
        }

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
        if (requestCode == INTENT_AUTHENTICATE) {
            if (resultCode == RESULT_OK) {
                setContentView(R.layout.activity_homescreen);
                setContentsViews();
            } else {
                AppExitWithoutDialouge();
            }
        }

    }

    private void ExitDialouge() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void AppExitWithoutDialouge() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //as there can be many requests for permission so evaluation based on request code using switch
        switch (requestCode) {
            case WRITE_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
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