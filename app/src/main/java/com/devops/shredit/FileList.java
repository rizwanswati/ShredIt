package com.devops.shredit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileList {

    // the lists for the paths
    private ArrayList<String> FilePathList;
    private ArrayList<String> OnlyFilePathList;
    private ArrayList<File> FileList;

    /**
     * Empty Constructor
     */
    public FileList() {
        // empty constructor
    }

    /**
     * Get a List of File Paths below the StartPath-Point
     *
     * @param StartPath where to start
     * @return a list of paths
     */
    public List<String> getFilePathList(String StartPath) {
        FilePathList = new ArrayList<String>();
        dirRunner(StartPath);
        return FilePathList;
    }

    /**
     * private Method to run through the directory tree
     *
     * @param path entry point
     */
    private void dirRunner(String path) {
        File f = new File(path);

        if (f.isDirectory()) {
            for (String contains : f.list()) {
                dirRunner(f.getAbsolutePath() + File.separator + contains);
            }
        }
        FilePathList.add(f.getAbsolutePath());
    }


    /**
     * Method to run through the directory tree, but return only
     * the Files, no directories
     *
     * @param StartPath path entry point
     * @return a String-List of Files
     */
    public List<String> getFilesOnlyPathList(String StartPath) {
        OnlyFilePathList = new ArrayList<String>();
        fileRunner(StartPath);
        return OnlyFilePathList;
    }


    /**
     * private Method to run through the directory tree
     * and create a String-List of paths to files (NO directories)
     *
     * @param path entry point
     */
    private void fileRunner(String path) {
        File f = new File(path);


        if (f.isDirectory()) {
            for (String contains : f.list()) {
                fileRunner(f.getAbsolutePath() + File.separator + contains);
            }
        } else {
            OnlyFilePathList.add(f.getAbsolutePath());
        }
    }

    /**
     * Get a List of Files below the StartPath-Point
     *
     * @param StartPath where to start
     * @return a File-List of files and directories
     */
    public List<File> getFileList(String StartPath) {
        FileList = new ArrayList<File>();
        FileDirRunner(StartPath);
        return FileList;
    }


    /**
     * private Method to run through the directory tree
     * and create a File-List of files and directories
     *
     * @param path entry point
     */
    private void FileDirRunner(String path) {
        File f = new File(path);

        if (f.isDirectory()) {
            for (String contains : f.list()) {
                FileDirRunner(f.getAbsolutePath() + File.separator + contains);
            }
        }
        FileList.add(f);
    }

}