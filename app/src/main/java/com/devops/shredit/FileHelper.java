package com.devops.shredit;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;

import com.nbsp.materialfilepicker.utils.FileTypeUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Random;

class FileHelper {
    /**
     * copyFile is used to copy a file.
     *
     * @throws IOException
     */
    public static void copyFile(String orig, String copy) throws IOException {
        InputStream fis = new FileInputStream(orig);
        OutputStream fout = new FileOutputStream(copy);

        copyFile(fis, fout);
    }

    /**
     * copyFile is used to copy a file.
     *
     * @throws IOException
     */
    public static void copyFile(InputStream input, OutputStream output) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        while ((len = input.read(buf)) > 0) {
            output.write(buf, 0, len);
        }
        input.close();
        output.flush();
        output.close();
    }

    /**
     * copyFile is used to copy a file.
     *
     * @throws IOException
     */
    public static void copyFile(File orig, File copy) throws IOException {
        copyFile(new FileInputStream(orig), new FileOutputStream(copy));
    }

    @NotNull
    public static String GenerateRandomName(Boolean file) {
        Random rand = new Random();
        if (!file) {
            String name = "com_android_Navigation_shredder";

            int random = rand.nextInt(100);
            String f_name = name + "_" + random;
            return f_name;
        } else {
            String name = "dummy_";
            int random = rand.nextInt(100000);
            String f_name = name + "_" + random;
            return f_name;
        }
    }

    @NotNull
    public static String CreateDir_File(Boolean fileCheck, String FolderPath, long File_size_in_Bytes) {

        File file = null;

        if (!fileCheck && FolderPath == null) {
            String file_name = null;
            File path = Environment.getExternalStorageDirectory();
            String f_path = path.getPath();
            file_name = GenerateRandomName(false);
            try {
                file = new File(path + File.separator + file_name);
                if (!file.exists()) {
                    boolean chck = true;
                    chck = file.mkdir();
                }
            } catch (Exception e) {
                Log.e("Error_mkdir", e.toString());
            }
            return file.getAbsolutePath().toString();
        } else {
            String file_name = null;
            file_name = GenerateRandomName(true);
            try {
                file = new File(FolderPath + File.separator + file_name + ".bin");
                if (!file.exists()) {
                    file.createNewFile();
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.setLength(File_size_in_Bytes);
                    randomAccessFile.writeByte(new Random().nextInt(5000));
                    randomAccessFile.close();
                }
            } catch (Exception e) {
                Log.w("FILE_ERROR_CREATION", "Following is the exception");
                Log.e("Error_File", e.toString());
            }
            return file.getAbsolutePath().toString();
        }
    }

    public static long ReturnFreeSpace() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable;
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } else {
            bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        }

////        double KBAvailable = bytesAvailable / 1000;
////        double megAvailable = KBAvailable/1000;

        return bytesAvailable;
    }

    public static String humanize(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format(Locale.ENGLISH, "%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static void CreateFiles(String FolderPath, int NumberOfFile, long FileSize) {
        for (int i = 0; i < NumberOfFile; i++) {
            CreateDir_File(true, FolderPath, FileSize);
        }
    }

    public static String ReturnStoragePath() {
        File path = Environment.getExternalStorageDirectory();
        String f_path = path.getPath();
        return f_path;
    }


    public static String getRemoveableMedia(Context context, boolean removable) {
        String path = getExternalStoragePath(context, removable);
        return path;
    }

    private static String getExternalStoragePath(Context mContext, boolean is_removable) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removable == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
