package com.thirteencubes.prepup.android.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thirteencubes.prepup.android.R;
import com.thirteencubes.prepup.android.ds.ExamInfo;
import com.thirteencubes.prepup.android.utils.DownloadTask;

public class StartExamActivity extends ActionBarActivity {
	private int position;
	private ExamInfo item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_exam);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		Intent i = getIntent();
		position = i.getIntExtra("position", -1);
		if (position != -1) {
			item = ExamInfo.getSampleExamItems().get(position);
		}
			
		//call in bg to get data for the exam:
		//http://apiv1.wellmob.com/app/?q=prevyrtest
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_exam, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onStartExamClicked(View view) {
		Toast toast = Toast.makeText(getApplicationContext(), "Start exam clicked", Toast.LENGTH_SHORT);
    	toast.show();
    	
    	//right now assumes data is there...
    	Intent i = new Intent(getApplicationContext(), ShowQsActivity.class);
    	i.putExtra("position", position);
    	startActivity(i);
   
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_start_exam,
					container, false);
		
			StartExamActivity actvt = (StartExamActivity)getActivity();
			
			 TextView t = (TextView)rootView.findViewById(R.id.start_exam_text);
			 t.setText(actvt.item.getExamWeclomeMsg());
			return rootView;
		}
	}

}
