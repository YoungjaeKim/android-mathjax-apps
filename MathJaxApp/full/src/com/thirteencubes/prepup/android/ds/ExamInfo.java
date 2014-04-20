package com.thirteencubes.prepup.android.ds;

import java.util.ArrayList;

public class ExamInfo {

	private String examName;
	private String welcomeMsg;
	private String examId;
	private int[] qIds;
	public ExamInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public ExamInfo(String name, String welcome) {
		examName = name;
		welcomeMsg = welcome;
		examId = "test6";
		qIds = new int[]{1, 2, 3, 4, 5};
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

	public String getExamId() {
		// TODO Auto-generated method stub
		return examId;
	}


}
