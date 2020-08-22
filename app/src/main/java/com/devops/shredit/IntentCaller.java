package com.devops.shredit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.aditya.filebrowser.Constants;
import com.aditya.filebrowser.FileChooser;
import com.aditya.filebrowser.FolderChooser;

import java.util.ArrayList;

public class IntentCaller {

    private static final int FILE_PICKER_REQUEST_CODE = 1001;
    private static final int DIRECTORY_REQUEST_CODE = 1002;

    protected void CallAbout(Context context, Class<?> go) {
        Intent intent = new Intent(context, go);
        context.startActivity(intent);
    }

    protected void CallHelp(Context context, Class<?> go) {
        Intent intent = new Intent(context, go);
        context.startActivity(intent);
    }

    protected void CallSupport(Context context, Class<?> go) {
        Intent intent = new Intent(context, go);
        context.startActivity(intent);
    }

    protected void CallHome(Context context, Class<?> go) {
        Intent intent = new Intent(context, go);
        context.startActivity(intent);
    }

    protected void pickFiles(Context context, Activity activity) {
        /*------------------Multiple File chooser-----------------------*/
        Intent intent = new Intent(context, FileChooser.class);
        intent.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
        activity.startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
    }

    protected void pickDir(Context context, Activity activity) {
        Intent dir_intent = new Intent(context, FolderChooser.class);
        dir_intent.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
        activity.startActivityForResult(dir_intent, DIRECTORY_REQUEST_CODE);
    }

    protected void callWipeAlgorithms(Context context, ArrayList<Uri> file, Class<?> go, boolean Internal_Free) {
        Intent intent = new Intent(context, go);
        intent.putParcelableArrayListExtra("URIs", file);
        intent.putExtra("Internal_Free", Internal_Free);
        context.startActivity(intent);
    }

}
