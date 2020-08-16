package com.devops.shredit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class Generalhelper {

    ObjectGenerator objectGenerator = new ObjectGenerator();
    IntentCaller intentCaller = objectGenerator.IntentCallerObj();

    public void ItemSelected(int id, Context context, Activity activity) {
        switch (id) {
            case R.id.exit:
                ExitDialouge(context, activity);
                break;
            case R.id.about:
                intentCaller.CallAbout(context, About.class);
                break;
            case R.id.help:
                intentCaller.CallHelp(context, Help.class);
                break;
            case R.id.support:
                intentCaller.CallSupport(context, Support.class);
                break;
            case R.id.homescr:
                intentCaller.CallHome(context, Homescreen.class);
                break;

        }
    }

    private void ExitDialouge(Context context, Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                activity.moveTaskToBack(true);
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
}
