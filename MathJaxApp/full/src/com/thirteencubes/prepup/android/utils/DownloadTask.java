package com.thirteencubes.prepup.android.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class DownloadTask extends AsyncTask<Integer, Integer, Void>{
   // private NotificationHelper mNotificationHelper;

    private static final String PEFERENCE_FILE = "preference";
    private static final String ISDOWNLOADED = "isdownloaded";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    Context context;
    String subDir;
    public DownloadTask(Context context, String examId){
        this.context = context;
        this.subDir = examId;
        //mNotificationHelper = new NotificationHelper(context);
    }

    protected void onPreExecute(){
        //Create the notification in the statusbar
        //mNotificationHelper.createNotification();
    }

    //public void StartDownload() {
    	//doInBackground(0);
    //}
    
    @Override
    protected Void doInBackground(Integer... integers) {
        //This is where we would do the actual download stuff
        //for now I'm just going to loop for 10 seconds
        // publishing progress every second
    	//use this for progress bar
    	//http://stackoverflow.com/questions/15542641/how-to-show-download-progress-in-progress-bar-in-android
        //http://stackoverflow.com/questions/12689992/progressbar-during-download-with-downloadmanager-and-sleeping-thread-in-android
    	int count;

        try {

        	URL url = new URL("http://apiv1.wellmob.com/doc6.zip");
//	        URL url = new URL("http://apiv1.wellmob.com/app/?q=prevyrtest");
	        URLConnection conexion = url.openConnection();
	        conexion.connect();
	
	        int lenghtOfFile = conexion.getContentLength();
	        Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
	
	        InputStream input = new BufferedInputStream(url.openStream());
	        File dir = SharedUtils.GetDownloadDirFile(subDir);
	        //OutputStream output = new FileOutputStream("/sdcard/foldername/temp.zip");
	        File file = new File(dir, "test.zip"); 
	        
	        if(!dir.exists())
	         {
	           dir.mkdir();
	         }
	        OutputStream output = new FileOutputStream(file);
	       // OutputStream output = context.openFileOutput("test.zip", Context.MODE_PRIVATE);
	        byte data[] = new byte[1024];
	
	        long total = 0;
		        try {
		            while ((count = input.read(data)) != -1) {
		                total += count;
		                //publishProgress(""+(int)((total*100)/lenghtOfFile));
		                Log.d("%Percentage%",""+(int)((total*100)/lenghtOfFile));
		                onProgressUpdate((int)((total*100)/lenghtOfFile));
		                output.write(data, 0, count);
		            }
		
		            output.flush();
		            output.close();
		            input.close();
		           // File file = //context.getFileStreamPath("test.zip");
		         //   File file = new File(Environment.getExternalStorageDirectory() + "/testprep/"+"test.zip"); 
		           // File path = context.getFilesDir(); //new File(Environment.getExternalStorageDirectory()
		                    //+ "/foldername"); 
		            //File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/testprep/");
                    ZipUtils.unzip(file, dir);
                    settings = this.context.getSharedPreferences(PEFERENCE_FILE, 0);
                    editor = settings.edit();
                    editor.putBoolean(ISDOWNLOADED, true);
                    editor.commit();

		        } catch (IOException e) {
		                Log.d("ZIP UTILL",e.toString());
		        	}
	
        } catch (Exception e) {}


        return null;
    }
    protected void onProgressUpdate(Integer... progress) {
        //This method runs on the UI thread, it receives progress updates
        //from the background thread and publishes them to the status bar
      //  mNotificationHelper.progressUpdate(progress[0]);
    }
    protected void onPostExecute(Void result)    {
        //The task is complete, tell the status bar about it
     //   HyundaiApplication.serviceState=false;
       // mNotificationHelper.completed();
    }
}