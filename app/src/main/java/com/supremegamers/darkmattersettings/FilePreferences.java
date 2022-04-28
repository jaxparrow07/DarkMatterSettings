package com.supremegamers.darkmattersettings;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class FilePreferences {

    private final String TAG = "FilePreferences";

    public FilePreferences() {
        super();
    }

    // Main functions of the class
    private void writeToFile(String fileName, String data) throws IOException {

        FileOutputStream fos;

        File sdcard = Environment.getExternalStorageDirectory();
        //File sdcard = mContext.getFilesDir();
        File myFile = new File(sdcard,fileName + ".cfg");

        Log.d(TAG, "File at : " + myFile.getAbsolutePath());

        if (!myFile.exists())

        {
            Log.d(TAG, "Creating file at : " + myFile.getAbsolutePath());
            myFile.createNewFile();

        }

        fos = new FileOutputStream(myFile);
        fos.write(data.getBytes());
        fos.close();

    }
    private String getFromFile(String fileName) throws IOException {

        File sdcard = Environment.getExternalStorageDirectory();
        //File sdcard = mContext.getFilesDir();
        File file = new File(sdcard, fileName + ".cfg");
        StringBuilder text = new StringBuilder();
        String out;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            text.append(line);
        }

        br.close();
        out = text.toString();
        return out;

    }


    // Setters
    public void putInt(String key, int value) {
        try {
            writeToFile("." + key, String.valueOf(value));
        } catch (Exception e) {
            Log.e(TAG,"Write Error", e);
        }
    }

    public void putBoolean(String key, boolean value) {
        try {
            writeToFile("." + key, value ? "0" : "1");
        } catch (Exception e) {
            Log.e(TAG,"Write Error", e);
        }
    }

    public void putString(String key, String value) {
        try {
            writeToFile("." + key, value);
        } catch (Exception e) {
            Log.e(TAG,"Write Error", e);
        }
    }


    // Getters
    public String getString(String key, String valueIfNotFound) {
        try {
            return getFromFile("." + key);
        } catch (Exception e) {
            return valueIfNotFound;
        }
    }

    public int getInt(String key, int valueIfNotFound) {
        try {
            return Integer.parseInt(getFromFile("." + key));
        } catch (Exception e) {
            return valueIfNotFound;
        }
    }

    public boolean getBoolean(String key, boolean valueIfNotFound) {
        try {
            return getFromFile("." + key).equals("0");
        } catch (Exception e) {
            return valueIfNotFound;
        }
    }




}
