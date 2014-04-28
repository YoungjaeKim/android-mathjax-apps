package com.thirteencubes.prepup.android.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.os.Environment;

public class SharedUtils {

	public static String GetDownloadDir() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	
	public static String GetDir(String examId) {
		return SharedUtils.GetDownloadDir() + File.separator + examId;
	}
	public static File GetDownloadDirFile(String examId) {
		 File dir = new File(SharedUtils.GetDir(examId));
		 return dir;
	}

	public static String readFileAsString(String examId, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
        	String dir = GetDir(examId);
        	File fl = new File(dir, fileName);
        	CustomLogger.getLogger(SharedUtils.class).debug("file path to read " + fl.getAbsolutePath());
            in = new BufferedReader(new FileReader(fl));
            while ((line = in.readLine()) != null) stringBuilder.append(line);

        } catch (FileNotFoundException e) {
            //Logger.logError(TAG, e);
        } catch (IOException e) {
            //Logger.logError(TAG, e);
        } 
        finally {
        	try {
        		if (in != null) {
        			in.close();
        		}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }
}
