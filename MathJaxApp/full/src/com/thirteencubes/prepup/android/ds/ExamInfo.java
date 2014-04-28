package com.thirteencubes.prepup.android.ds;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

import com.thirteencubes.prepup.android.utils.MyApplication;

public class ExamInfo {

	private String examName;
	private String welcomeMsg;
	//id for the given exam e.g: IIT-pre, IIT-main, PMT
	private String examId;
	
	//is unique for each test
	//same exam can have multiple tests and each will have unique id
	private String testId;
	
	private int[] qIds;
	private String files[];
	private QInfo[] qInfo;
	private boolean isMultiChoice;
	private SharedPreferences settings;
	public ExamInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public ExamInfo(String name, String welcome) {
		examName = name;
		welcomeMsg = welcome;
		examId = "exam11";
		testId = "test11";
		qInfo = new QInfo[] { new QInfo(1, null), new QInfo(2, null), new QInfo(3, null), new QInfo(4, null), new QInfo(5, null)}; 
		qIds = new int[qInfo.length];
		for(int i = 0; i < qInfo.length; i++) {
			qIds[i] = qInfo[i].getQid();
		}
		
		files = new String[] {"doc11.zip" };//{"1_10.zip", "11_20.zip" };
		isMultiChoice = false;
		settings = getResultsFile(testId);
		//setS
	}
	
	
	
	public static ArrayList<ExamInfo> getSampleExamItems() {
		// TODO Auto-generated method stub
		ArrayList<ExamInfo> rv = new ArrayList<ExamInfo>();
		String msg = "We will start practise Qs. You will find 50 Qs with multiple choice. In order to move to next Q, swipe to left. To move to right Q, swipe to right. You can go back and forth using various control on the page. There is timer running which will tell how much time is left. You can ignore if you are just starting but later this will be important";
		rv.add(new ExamInfo("IIT 2014", msg));
		rv.add(new ExamInfo("IIT 2014", msg));
		return rv;
	}

	public int[] getQIds() {
		
		return qIds;
	}
	public String getExamName() {
		// TODO Auto-generated method stub
		return examName;
	}

	public String getExamWeclomeMsg() {
		// TODO Auto-generated method stub
		return welcomeMsg;
	}

	public String getTestId() {
		// TODO Auto-generated method stub
		return testId;
	}

	public String[] getFileNamesToDownload() {
		return files;
	}

	public boolean getState(int qIndex, int ansId) {
		return settings.getBoolean(qIndex + "_" + ansId, false);
	}
	
	public boolean toggleState(int qIndex, int ansId) {
		if (!settings.getBoolean(qIndex + "_" + ansId, false)) {
			ansSelected(qIndex, ansId);
			return true;
		} else {
			ansDeselected(qIndex, ansId);
			return false;
		}
	}
	
	public void ansSelected(int qIndex, int ansId) {
		//you can put validation here for single choice answer
		SharedPreferences.Editor editor = settings.edit();
	      editor.putBoolean(qIndex + "_" + ansId, true);
	      // Commit the edits!
	      editor.commit();
	
	}
	
	public void ansDeselected(int qId, int ansId) {
		 SharedPreferences.Editor editor = settings.edit();
	      editor.putBoolean(qId + "_" + ansId, false);
	      // Commit the edits!
	      editor.commit();
	}
	
	private SharedPreferences getResultsFile(String testId) {
		Context ctx = MyApplication.getAppContext();
		SharedPreferences settings = ctx.getSharedPreferences(testId, 0);
		return settings;
	}
	
}
