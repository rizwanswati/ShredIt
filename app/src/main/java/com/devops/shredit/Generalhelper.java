package com.devops.shredit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generalhelper {

    ObjectGenerator objectGenerator = new ObjectGenerator();
    IntentCaller intentCaller = objectGenerator.IntentCallerObj();
    public static final String LIST_SIZE = "LIST_SIZE";
    public static final String PROG = "PROG_UPDATE";
    Shredder shredder = objectGenerator.ShredderObj();
    private int progressBarStatus = 0;
    private int counter = 0;
    private String file_path;
    private File s_file;
    private String s_fileName;

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

    protected void AppExitWithoutDialouge(Activity activity) {
        activity.moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    protected void ShareApp(Context context) {
        ApplicationInfo api = context.getApplicationContext().getApplicationInfo();
        String apkpath = api.sourceDir;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
        context.startActivity(Intent.createChooser(intent, "Share App"));
    }

    public void CallDoD(ArrayList<Uri> files, boolean Internal_Free, Context context) {
        if (Internal_Free) {
            int number_of_files = 5000;
            String Folder_path = files.toString();
            try {
                if (shredder.wipeDoD(Folder_path, false, false, Internal_Free)) {
                    double remaining_space = FileHelper.ReturnFreeSpace();
                    Toast.makeText(context, "Internal Shredding Success.! Checking for remaining space.!", Toast.LENGTH_SHORT).show();
                    if (remaining_space > 0) {
                        Toast.makeText(context, "remaining Space Available=" + remaining_space, Toast.LENGTH_SHORT).show();
                        String Rm_Folder_path = FileHelper.CreateDir_File(false, null, 0);
                        long File_size = (long) (remaining_space / number_of_files);
                        FileHelper.CreateFiles(Rm_Folder_path, number_of_files, File_size);
                        shredder.wipeDoD(Folder_path, false, true, Internal_Free);
                        FileUtils.deleteDirectory(new File(Folder_path));
                        Toast.makeText(context, "Internal Storage Shredded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No more internal space ramaining for shredding", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Internal Shredding Failed..!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionDebugInfo(e);
            }
        } else {
            String path;
            for (Uri file : files) {
                path = file.getPath();
                try {
                    shredder.wipeDoD(path, false, false, Internal_Free);
                } catch (IOException e) {
                    e.printStackTrace();
                    ExceptionDebugInfo(e);
                }
            }
        }
    }

    public void CallDoDExtended(ArrayList<Uri> files, boolean Internal_Free, Context context) {

        if (Internal_Free) {
            int number_of_files = 5000;
            String Folder_path = files.toString();
            try {
                if (shredder.wipeDoD(Folder_path, false, true, Internal_Free)) {
                    double remaining_space = FileHelper.ReturnFreeSpace();
                    Toast.makeText(context, "Internal Shredding Success.! Checking for remaining space.!", Toast.LENGTH_SHORT).show();
                    if (remaining_space > 0) {
                        Toast.makeText(context, "remaining Space Available=" + remaining_space, Toast.LENGTH_SHORT).show();
                        String Rm_Folder_path = FileHelper.CreateDir_File(false, null, 0);
                        long File_size = (long) (remaining_space / number_of_files);
                        FileHelper.CreateFiles(Rm_Folder_path, number_of_files, File_size);
                        shredder.wipeDoD(Folder_path, false, true, Internal_Free);
                        FileUtils.deleteDirectory(new File(Folder_path));
                        Toast.makeText(context, "Internal Storage Shredded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No more internal space ramaining for shredding", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Internal Shredding Failed..!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionDebugInfo(e);
            }
        } else {
            String path;
            for (Uri file : files) {
                path = file.getPath();
                try {
                    shredder.wipeDoD(path, false, true, Internal_Free);
                } catch (IOException e) {
                    e.printStackTrace();
                    ExceptionDebugInfo(e);
                }
            }
        }
    }


    public void CallVsiter(ArrayList<Uri> files, boolean Internal_Free, Context context) {
        if (Internal_Free) {
            int number_of_files = 5000;
            String Folder_path = files.toString();
            try {
                if (shredder.wipeVSITR(Folder_path, false, Internal_Free)) {
                    double remaining_space = FileHelper.ReturnFreeSpace();
                    Toast.makeText(context, "Internal Shredding Success.! Checking for remaining space.!", Toast.LENGTH_SHORT).show();
                    if (remaining_space > 0) {
                        Toast.makeText(context, "remaining Space Available=" + remaining_space, Toast.LENGTH_SHORT).show();
                        String Rm_Folder_path = FileHelper.CreateDir_File(false, null, 0);
                        long File_size = (long) (remaining_space / number_of_files);
                        FileHelper.CreateFiles(Rm_Folder_path, number_of_files, File_size);
                        shredder.wipeVSITR(Rm_Folder_path, false, Internal_Free);
                        FileUtils.deleteDirectory(new File(Folder_path));
                        Toast.makeText(context, "Internal Storage Shredded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No more internal space ramaining for shredding", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Internal Shredding Failed..!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionDebugInfo(e);
            }
        } else {

            String path;
            for (Uri file : files) {
                path = file.getPath();
                try {
                    shredder.wipeVSITR(path, false, Internal_Free);
                } catch (IOException e) {
                    e.printStackTrace();
                    ExceptionDebugInfo(e);
                }
            }
        }
    }

    public void CallSchneire(ArrayList<Uri> files, boolean Internal_Free, Context context) {
        if (Internal_Free) {
            int number_of_files = 5000;
            String Folder_path = files.toString();
            try {
                if (shredder.wipeSchneier(Folder_path, false, Internal_Free)) {
                    double remaining_space = FileHelper.ReturnFreeSpace();
                    Toast.makeText(context, "Internal Shredding Success.! Checking for remaining space.!", Toast.LENGTH_SHORT).show();
                    if (remaining_space > 0) {
                        Toast.makeText(context, "remaining Space Available=" + remaining_space, Toast.LENGTH_SHORT).show();
                        String Rm_Folder_path = FileHelper.CreateDir_File(false, null, 0);
                        long File_size = (long) (remaining_space / number_of_files);
                        FileHelper.CreateFiles(Rm_Folder_path, number_of_files, File_size);
                        shredder.wipeSchneier(Rm_Folder_path, false, Internal_Free);
                        FileUtils.deleteDirectory(new File(Folder_path));
                        Toast.makeText(context, "Internal Storage Shredded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No more internal space ramaining for shredding", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Internal Shredding Failed..!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionDebugInfo(e);
            }
        } else {

            String path;
            for (Uri file : files) {
                path = file.getPath();
                try {
                    shredder.wipeSchneier(path, false, Internal_Free);
                } catch (IOException e) {
                    e.printStackTrace();
                    ExceptionDebugInfo(e);
                }
            }
        }
    }

    public void CallGutman(ArrayList<Uri> files, boolean Internal_Free, Context context) {
        if (Internal_Free) {
            int number_of_files = 5000;
            String Folder_path = files.toString();
            try {
                if (shredder.wipeGutmann(Folder_path, false, false, Internal_Free)) {
                    double remaining_space = FileHelper.ReturnFreeSpace();
                    Toast.makeText(context, "Internal Shredding Success.! Checking for remaining space.!", Toast.LENGTH_SHORT).show();
                    if (remaining_space > 0) {
                        Toast.makeText(context, "remaining Space Available=" + remaining_space, Toast.LENGTH_SHORT).show();
                        String Rm_Folder_path = FileHelper.CreateDir_File(false, null, 0);
                        long File_size = (long) (remaining_space / number_of_files);
                        FileHelper.CreateFiles(Rm_Folder_path, number_of_files, File_size);
                        shredder.wipeGutmann(Rm_Folder_path, false, false, Internal_Free);
                        FileUtils.deleteDirectory(new File(Folder_path));
                        Toast.makeText(context, "Internal Storage Shredded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No more internal space ramaining for shredding", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Internal Shredding Failed..!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionDebugInfo(e);
            }
        } else {
            String path;
            for (Uri file : files) {
                path = file.getPath();
                try {
                    shredder.wipeGutmann(path, false, false, Internal_Free);
                } catch (IOException e) {
                    e.printStackTrace();
                    ExceptionDebugInfo(e);
                }
            }
        }
    }

    public void CallOneByte(ArrayList<Uri> files, boolean Internal_Free, Context context, ProgressBar pbar, TextView showProg) {

        Handler progressbarHandler = new Handler();
        pbar.setProgress(0);


        if (Internal_Free) {
            int number_of_files = 5000;
            String Folder_path = files.toString();
            try {
                if (shredder.wipeRandom(Folder_path, false, 2, 1, Internal_Free)) {
                    double remaining_space = FileHelper.ReturnFreeSpace();
                    Toast.makeText(context, "Internal Shredding Success.! Checking for remaining space.!", Toast.LENGTH_SHORT).show();
                    if (remaining_space > 0) {
                        Toast.makeText(context, "remaining Space Available=" + remaining_space, Toast.LENGTH_SHORT).show();
                        String Rm_Folder_path = FileHelper.CreateDir_File(false, null, 0);
                        long File_size = (long) (remaining_space / number_of_files);
                        FileHelper.CreateFiles(Rm_Folder_path, number_of_files, File_size);
                        shredder.wipeRandom(Folder_path, false, 2, 1, Internal_Free);
                        FileUtils.deleteDirectory(new File(Folder_path));
                        Toast.makeText(context, "Internal Storage Shredded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No more internal space ramaining for shredding", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Internal Shredding Failed..!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionDebugInfo(e);
            }
        } else {


            /***
             * Assuming all inputs are files, not directories
             */
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (counter < total_files) {
                        String path;
                        for (Uri file : files) {

                            path = file.getPath();
                            s_fileName = path;
                            s_file = new File(s_fileName);

                            if (s_file.isDirectory()) {
                                counter++;
                                final FileList fileList = new FileList();
                                final List<String> ListofFiles = fileList.getFilePathList(path);
                                int listSize = ListofFiles.size();
                                Log.e(LIST_SIZE, "Size of Dir:=" + listSize);
                                pbar.setMax(listSize - 1);
                                for (int i = 0; i < listSize; i++) {
                                    path = ListofFiles.get(i);
                                    file_path = path;
                                    try {

                                        // 1 - Operation
                                        if (shredder.wipeRandom(path, false, 2, 1, Internal_Free)) {
                                            progressBarStatus = i;
                                            // 2 - sleep
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            // 3 - update
                                            progressbarHandler.post(new Runnable() {
                                                public void run() {
                                                    showProg.setText(file_path);
                                                    pbar.setProgress(progressBarStatus);
                                                    Log.w(PROG, "progressbarstatus:=" + progressBarStatus);
                                                }
                                            });
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
//
                            } else {
                                path = file.getPath();
                                file_path = path;
                                try {
                                    counter++;
                                    // 1 - operation
                                    if (shredder.wipeRandom(path, false, 2, 1, Internal_Free)) {
                                        progressBarStatus++;
                                        // 2 - sleep
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        // 3 - update
                                        progressbarHandler.post(new Runnable() {
                                            public void run() {
                                                showProg.setText(file_path);
                                                pbar.setProgress(progressBarStatus);
                                            }
                                        });
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                            }
                        }
                    }
                }
            });
            thread.start();
        }
    }

    protected void ExceptionDebugInfo(Exception e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        Log.e("Sys_Debug", "Class\t\tFile\t\t\tMethod\tLine");
        for (StackTraceElement element : stackTraceElements) {
            Log.e("Sys_Debug", "\t" + element.getClassName());
            Log.e("Sys_Debug", "\t" + element.getFileName());
            Log.e("Sys_Debug", "\t" + element.getMethodName());
            Log.e("Sys_Debug", "\t" + element.getLineNumber());
        }
    }
}
