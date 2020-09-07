package com.devops.shredit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generalhelper {

    ObjectGenerator objectGenerator = new ObjectGenerator();
    IntentCaller intentCaller = objectGenerator.IntentCallerObj();
    Shredder shredder = objectGenerator.ShredderObj();

    private int progressBarStatus = 0;
    private int counter = 0;
    private String file_path;
    private File s_file;
    private String s_fileName;
    private boolean check = false;
    protected static boolean AppCheck = true;


    public void CallDoD(ArrayList<Uri> files, boolean Internal_Free, ProgressBar pbar, TextView showProg, Context context, View view, Activity activity) {
        view.setEnabled(false);
        Handler progressbarHandler = new Handler();
        pbar.setProgress(0);
        if (Internal_Free) {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {

                    Uri uri = files.get(0);
                    String Folder_path = uri.getPath();
                    final FileList fileList = new FileList();
                    final List<String> ListofFiles = fileList.getFilePathList(Folder_path);
                    int listSize = ListofFiles.size();
                    pbar.setMax(listSize);
                    for (String file : ListofFiles) {
                        counter++;
                        file_path = file;
                        try {
                            // 1 - Operation
                            if (shredder.wipeDoD(file, false, false, Internal_Free)) {
                                progressBarStatus++;
                            }
                            // 2 - sleep
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                            // 3 - update
                            progressbarHandler.post(() -> {
                                showProg.setText(file_path);
                                pbar.setProgress(progressBarStatus);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            ExceptionDebugInfo(e);
                        }
                    }

                    Uri Furi = files.get(0);
                    String RDFolder_path = Furi.getPath();
                    s_file = new File(RDFolder_path);
                    try {
                        FileUtils.cleanDirectory(s_file);
                        Thread.sleep(3000);
                        FileUtils.deleteDirectory(s_file);
                        if (s_file.exists()) {
                            check = s_file.delete();
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        ExceptionDebugInfo(e);
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        } else {

            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {
                    String path;
                    for (Uri file : files) {
                        counter++;
                        path = file.getPath();
                        s_fileName = path;
                        s_file = new File(s_fileName);

                        if (s_file.isDirectory()) {

                            final FileList fileList = new FileList();
                            final List<String> ListofFiles = fileList.getFilePathList(path);
                            int listSize = ListofFiles.size();
                            pbar.setMax(listSize);
                            for (int i = 0; i < listSize; i++) {
                                path = ListofFiles.get(i);
                                file_path = path;
                                try {

                                    // 1 - Operation

                                    if (shredder.wipeDoD(path, false, false, Internal_Free)) {
                                        progressBarStatus++;
                                    }
                                    // 2 - sleep
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        ExceptionDebugInfo(e);
                                    }
                                    // 3 - update
                                    progressbarHandler.post(() -> {
                                        showProg.setText(file_path);
                                        pbar.setProgress(progressBarStatus);
                                    });

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                            }
                        } else {
                            path = file.getPath();
                            file_path = path;
                            try {

                                // 1 - operation
                                if (shredder.wipeDoD(path, false, true, Internal_Free)) {
                                    progressBarStatus++;
                                }
                                // 2 - sleep
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                                // 3 - update
                                progressbarHandler.post(() -> {
                                    showProg.setText(file_path);
                                    pbar.setProgress(progressBarStatus);
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                        }
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        }
    }

    public void CallDoDExtended(ArrayList<Uri> files, boolean Internal_Free, ProgressBar pbar, TextView showProg, Context context, View view, Activity activity) {
        view.setEnabled(false);
        Handler progressbarHandler = new Handler();
        pbar.setProgress(0);
        if (Internal_Free) {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {

                    Uri uri = files.get(0);
                    String Folder_path = uri.getPath();
                    final FileList fileList = new FileList();
                    final List<String> ListofFiles = fileList.getFilePathList(Folder_path);
                    int listSize = ListofFiles.size();
                    pbar.setMax(listSize);
                    for (String file : ListofFiles) {
                        counter++;
                        file_path = file;
                        try {
                            // 1 - Operation
                            if (shredder.wipeDoD(file, false, true, Internal_Free)) {
                                progressBarStatus++;
                            }
                            // 2 - sleep
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                            // 3 - update
                            progressbarHandler.post(() -> {
                                showProg.setText(file_path);
                                pbar.setProgress(progressBarStatus);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            ExceptionDebugInfo(e);
                        }
                    }

                    Uri Furi = files.get(0);
                    String RDFolder_path = Furi.getPath();
                    s_file = new File(RDFolder_path);
                    try {
                        FileUtils.cleanDirectory(s_file);
                        Thread.sleep(3000);
                        FileUtils.deleteDirectory(s_file);
                        if (s_file.exists()) {
                            check = s_file.delete();
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        ExceptionDebugInfo(e);
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        } else {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {
                    String path;
                    for (Uri file : files) {
                        counter++;
                        path = file.getPath();
                        s_fileName = path;
                        s_file = new File(s_fileName);

                        if (s_file.isDirectory()) {

                            final FileList fileList = new FileList();
                            final List<String> ListofFiles = fileList.getFilePathList(path);
                            int listSize = ListofFiles.size();
                            pbar.setMax(listSize);
                            for (int i = 0; i < listSize; i++) {
                                path = ListofFiles.get(i);
                                file_path = path;
                                try {

                                    // 1 - Operation

                                    if (shredder.wipeDoD(path, false, true, Internal_Free)) {
                                        progressBarStatus++;
                                    }
                                    // 2 - sleep
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        ExceptionDebugInfo(e);
                                    }
                                    // 3 - update
                                    progressbarHandler.post(() -> {
                                        showProg.setText(file_path);
                                        pbar.setProgress(progressBarStatus);
                                    });

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                            }
                        } else {
                            path = file.getPath();
                            file_path = path;
                            try {

                                // 1 - operation
                                if (shredder.wipeDoD(path, false, true, Internal_Free)) {
                                    progressBarStatus++;
                                }
                                // 2 - sleep
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                                // 3 - update
                                progressbarHandler.post(() -> {
                                    showProg.setText(file_path);
                                    pbar.setProgress(progressBarStatus);
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                        }
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        }
    }

    public void CallVsiter(ArrayList<Uri> files, boolean Internal_Free, ProgressBar pbar, TextView showProg, Context context, View view, Activity activity) {
        view.setEnabled(false);
        Handler progressbarHandler = new Handler();
        pbar.setProgress(0);
        if (Internal_Free) {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {

                    Uri uri = files.get(0);
                    String Folder_path = uri.getPath();
                    final FileList fileList = new FileList();
                    final List<String> ListofFiles = fileList.getFilePathList(Folder_path);
                    int listSize = ListofFiles.size();
                    pbar.setMax(listSize);
                    for (String file : ListofFiles) {
                        counter++;
                        file_path = file;
                        try {
                            // 1 - Operation
                            if (shredder.wipeVSITR(file, false, Internal_Free)) {
                                progressBarStatus++;
                            }
                            // 2 - sleep
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                            // 3 - update
                            progressbarHandler.post(() -> {
                                showProg.setText(file_path);
                                pbar.setProgress(progressBarStatus);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            ExceptionDebugInfo(e);
                        }
                    }

                    Uri Furi = files.get(0);
                    String RDFolder_path = Furi.getPath();
                    s_file = new File(RDFolder_path);
                    try {
                        FileUtils.cleanDirectory(s_file);
                        Thread.sleep(3000);
                        FileUtils.deleteDirectory(s_file);
                        if (s_file.exists()) {
                            check = s_file.delete();
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        ExceptionDebugInfo(e);
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();

        } else {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {
                    String path;
                    for (Uri file : files) {
                        counter++;
                        path = file.getPath();
                        s_fileName = path;
                        s_file = new File(s_fileName);

                        if (s_file.isDirectory()) {

                            final FileList fileList = new FileList();
                            final List<String> ListofFiles = fileList.getFilePathList(path);
                            int listSize = ListofFiles.size();
                            pbar.setMax(listSize);
                            for (int i = 0; i < listSize; i++) {
                                path = ListofFiles.get(i);
                                file_path = path;
                                try {

                                    // 1 - Operation

                                    if (shredder.wipeVSITR(path, false, Internal_Free)) {
                                        progressBarStatus++;
                                    }
                                    // 2 - sleep
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        ExceptionDebugInfo(e);
                                    }
                                    // 3 - update
                                    progressbarHandler.post(() -> {
                                        showProg.setText(file_path);
                                        pbar.setProgress(progressBarStatus);
                                    });

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                            }
//
                        } else {
                            path = file.getPath();
                            file_path = path;
                            try {

                                // 1 - operation
                                if (shredder.wipeVSITR(path, false, Internal_Free)) {
                                    progressBarStatus++;
                                }
                                // 2 - sleep
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                                // 3 - update
                                progressbarHandler.post(() -> {
                                    showProg.setText(file_path);
                                    pbar.setProgress(progressBarStatus);
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                        }
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        }
    }

    public void CallSchneire(ArrayList<Uri> files, boolean Internal_Free, ProgressBar pbar, TextView showProg, Context context, View view, Activity activity) {
        view.setEnabled(false);
        Handler progressbarHandler = new Handler();
        pbar.setProgress(0);
        if (Internal_Free) {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {

                    Uri uri = files.get(0);
                    String Folder_path = uri.getPath();
                    final FileList fileList = new FileList();
                    final List<String> ListofFiles = fileList.getFilePathList(Folder_path);
                    int listSize = ListofFiles.size();
                    pbar.setMax(listSize);
                    for (String file : ListofFiles) {
                        counter++;
                        file_path = file;
                        try {
                            // 1 - Operation
                            if (shredder.wipeSchneier(file, false, Internal_Free)) {
                                progressBarStatus++;
                            }
                            // 2 - sleep
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                            // 3 - update
                            progressbarHandler.post(() -> {
                                showProg.setText(file_path);
                                pbar.setProgress(progressBarStatus);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            ExceptionDebugInfo(e);
                        }
                    }

                    Uri Furi = files.get(0);
                    String RDFolder_path = Furi.getPath();
                    s_file = new File(RDFolder_path);
                    try {
                        FileUtils.cleanDirectory(s_file);
                        Thread.sleep(3000);
                        FileUtils.deleteDirectory(s_file);
                        if (s_file.exists()) {
                            check = s_file.delete();
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        ExceptionDebugInfo(e);
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        } else {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {
                    String path;
                    for (Uri file : files) {
                        counter++;
                        path = file.getPath();
                        s_fileName = path;
                        s_file = new File(s_fileName);

                        if (s_file.isDirectory()) {

                            final FileList fileList = new FileList();
                            final List<String> ListofFiles = fileList.getFilePathList(path);
                            int listSize = ListofFiles.size();
                            pbar.setMax(listSize);
                            for (int i = 0; i < listSize; i++) {
                                path = ListofFiles.get(i);
                                file_path = path;
                                try {

                                    // 1 - Operation

                                    if (shredder.wipeSchneier(path, false, Internal_Free)) {
                                        progressBarStatus++;
                                    }
                                    // 2 - sleep
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        ExceptionDebugInfo(e);
                                    }
                                    // 3 - update
                                    progressbarHandler.post(() -> {
                                        showProg.setText(file_path);
                                        pbar.setProgress(progressBarStatus);
                                    });

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                            }
//
                        } else {
                            path = file.getPath();
                            file_path = path;
                            try {

                                // 1 - operation
                                if (shredder.wipeSchneier(path, false, Internal_Free)) {
                                    progressBarStatus++;
                                }
                                // 2 - sleep
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                                // 3 - update
                                progressbarHandler.post(() -> {
                                    showProg.setText(file_path);
                                    pbar.setProgress(progressBarStatus);
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                        }
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        }
    }

    public void CallGutman(ArrayList<Uri> files, boolean Internal_Free, ProgressBar pbar, TextView showProg, Context context, View view, Activity activity) {
        view.setEnabled(false);
        Handler progressbarHandler = new Handler();
        pbar.setProgress(0);
        if (Internal_Free) {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {

                    Uri uri = files.get(0);
                    String Folder_path = uri.getPath();
                    final FileList fileList = new FileList();
                    final List<String> ListofFiles = fileList.getFilePathList(Folder_path);
                    int listSize = ListofFiles.size();
                    pbar.setMax(listSize);
                    for (String file : ListofFiles) {
                        counter++;
                        file_path = file;
                        try {
                            // 1 - Operation
                            if (shredder.wipeGutmann(file, false, false, Internal_Free)) {
                                progressBarStatus++;
                            }
                            // 2 - sleep
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                            // 3 - update
                            progressbarHandler.post(() -> {
                                showProg.setText(file_path);
                                pbar.setProgress(progressBarStatus);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            ExceptionDebugInfo(e);
                        }
                    }

                    Uri Furi = files.get(0);
                    String RDFolder_path = Furi.getPath();
                    s_file = new File(RDFolder_path);
                    try {
                        FileUtils.cleanDirectory(s_file);
                        Thread.sleep(3000);
                        FileUtils.deleteDirectory(s_file);
                        if (s_file.exists()) {
                            check = s_file.delete();
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        ExceptionDebugInfo(e);
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();

        } else {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {
                    String path;
                    for (Uri file : files) {
                        counter++;
                        path = file.getPath();
                        s_fileName = path;
                        s_file = new File(s_fileName);

                        if (s_file.isDirectory()) {

                            final FileList fileList = new FileList();
                            final List<String> ListofFiles = fileList.getFilePathList(path);
                            int listSize = ListofFiles.size();
                            pbar.setMax(listSize);
                            for (int i = 0; i < listSize; i++) {
                                path = ListofFiles.get(i);
                                file_path = path;
                                try {

                                    // 1 - Operation

                                    if (shredder.wipeGutmann(path, false, false, Internal_Free)) {
                                        progressBarStatus++;
                                    }
                                    // 2 - sleep
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        ExceptionDebugInfo(e);
                                    }
                                    // 3 - update
                                    progressbarHandler.post(() -> {
                                        showProg.setText(file_path);
                                        pbar.setProgress(progressBarStatus);
                                    });

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                            }
//
                        } else {
                            path = file.getPath();
                            file_path = path;
                            try {

                                // 1 - operation
                                if (shredder.wipeGutmann(path, false, false, Internal_Free)) {
                                    progressBarStatus++;
                                }
                                // 2 - sleep
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                                // 3 - update
                                progressbarHandler.post(() -> {
                                    showProg.setText(file_path);
                                    pbar.setProgress(progressBarStatus);
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                        }
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        }
    }

    public void CallOneByte(ArrayList<Uri> files, boolean Internal_Free, ProgressBar pbar, TextView showProg, Context context, Activity activity, View view) {
        view.setEnabled(false);
        Handler progressbarHandler = new Handler();
        pbar.setProgress(0);
        if (Internal_Free) {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {

                    Uri uri = files.get(0);
                    String Folder_path = uri.getPath();
                    final FileList fileList = new FileList();
                    final List<String> ListofFiles = fileList.getFilePathList(Folder_path);
                    int listSize = ListofFiles.size();
                    pbar.setMax(listSize);
                    for (String file : ListofFiles) {
                        counter++;
                        file_path = file;
                        try {
                            // 1 - Operation
                            if (shredder.wipeRandom(file, false, 2, 1, Internal_Free)) {
                                progressBarStatus++;
                            }
                            // 2 - sleep
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                            // 3 - update
                            progressbarHandler.post(() -> {
                                showProg.setText(file_path);
                                pbar.setProgress(progressBarStatus);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            ExceptionDebugInfo(e);
                        }
                    }

                    Uri Furi = files.get(0);
                    String RDFolder_path = Furi.getPath();
                    s_file = new File(RDFolder_path);
                    try {
                        FileUtils.cleanDirectory(s_file);
                        Thread.sleep(3000);
                        FileUtils.deleteDirectory(s_file);
                        if (s_file.exists()) {
                            check = s_file.delete();
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        ExceptionDebugInfo(e);
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        } else {
            int total_files = files.size();
            pbar.setMax(total_files); //total size of progress bar

            Thread thread = new Thread(() -> {
                while (counter < total_files) {
                    String path;
                    for (Uri file : files) {
                        counter++;
                        path = file.getPath();
                        s_fileName = path;
                        s_file = new File(s_fileName);

                        if (s_file.isDirectory()) {

                            final FileList fileList = new FileList();
                            final List<String> ListofFiles = fileList.getFilePathList(path);
                            int listSize = ListofFiles.size();
                            pbar.setMax(listSize);
                            for (int i = 0; i < listSize; i++) {
                                path = ListofFiles.get(i);
                                file_path = path;
                                try {

                                    // 1 - Operation

                                    if (shredder.wipeRandom(path, false, 2, 1, Internal_Free)) {
                                        progressBarStatus++;
                                    }
                                    // 2 - sleep
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        ExceptionDebugInfo(e);
                                    }
                                    // 3 - update
                                    progressbarHandler.post(() -> {
                                        showProg.setText(file_path);
                                        pbar.setProgress(progressBarStatus);
                                    });

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                            }


                        } else {
                            path = file.getPath();
                            file_path = path;
                            try {

                                // 1 - operation
                                if (shredder.wipeRandom(path, false, 2, 1, Internal_Free)) {
                                    progressBarStatus++;
                                }
                                // 2 - sleep
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    ExceptionDebugInfo(e);
                                }
                                // 3 - update
                                progressbarHandler.post(() -> {
                                    showProg.setText(file_path);
                                    pbar.setProgress(progressBarStatus);
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                                ExceptionDebugInfo(e);
                            }
                        }
                    }
                }
                activity.runOnUiThread(() -> resultDialouge("Click Finish to go to Home Screen", context));
            });
            thread.start();
        }
    }


    private void resultDialouge(String msg, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Result");
        alertDialogBuilder
                .setMessage("Shredding Successful. " + msg)
                .setCancelable(false)
                .setNegativeButton("Finish", (dialog, id) -> intentCaller.CallHome(context, Homescreen.class));

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
                        (dialog, id) -> {
                            activity.moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        })

                .setNegativeButton("No", (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    protected void AppExitWithoutDialouge(Activity activity) {
        activity.moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
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
}
