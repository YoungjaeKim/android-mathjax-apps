package com.thirteencubes.prepup.android.core;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.thirteencubes.prepup.android.R;
import com.thirteencubes.prepup.android.ds.ExamInfo;
import com.thirteencubes.prepup.android.utils.DownloadTask;

public class ExamlistFragment extends ListFragment {
			public static final String ARG_SECTION_NUMBER = "section_number";

		private View rootView;
	    private ListView listView;
	    private ArrayList<ExamInfo> examList;
	    private ExamlistAdaptor mAdapter;

	    public ExamlistFragment() {
	    	
	    }
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        rootView = inflater.inflate(R.layout.examlist_fragment,
	                container, false);
	        listView = (ListView) rootView.findViewById(android.R.id.list);

	        return rootView;
	    }

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        
	        //int num = getArguments().getInt(ARG_SECTION_NUMBER);
	        // GlobalList is a class that holds global variables, arrays etc
	        // getMenuCategories returns global arraylist which is initialized in GlobalList class
	        examList = ExamInfo.getSampleExamItems() ;//GlobalList.getMenuCategories().get(num).getMenu();
	        
	        
	        final ExamlistAdaptor mAdapter1 = new ExamlistAdaptor(getActivity(), android.R.id.list, examList);
	       listView.setAdapter(mAdapter1);
	    }
	    
	    @Override
	    public void onListItemClick (ListView l, View v, int position, long id) {
	    	String examName = examList.get(position).getExamName();
	    	String examId = examList.get(position).getExamId();
	    	Toast toast = Toast.makeText(getActivity().getApplicationContext(), examName, Toast.LENGTH_SHORT);
	    	toast.show();
	    	
	    	Intent i = new Intent(getActivity().getApplicationContext(), StartExamActivity.class);
	    	i.putExtra("position", position);
	    	//i.putExtra("examId", examId);
	    	DownloadTask task = new DownloadTask(getActivity().getApplicationContext(), examId);
	    	task.execute(0);
	    	startActivity(i);
	    }

	}
