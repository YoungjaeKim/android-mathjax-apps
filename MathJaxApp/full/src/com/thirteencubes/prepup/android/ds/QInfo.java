package com.thirteencubes.prepup.android.ds;

public class QInfo {

	private int qid;
	private int[] ans;
	public QInfo(int qid, int[] answers) {
		ans = answers;
		this.qid = qid;
	}
	
	public int getQid() {
		return qid;
	}
	
	public int[] getAns() {
		return ans;
	}
}
