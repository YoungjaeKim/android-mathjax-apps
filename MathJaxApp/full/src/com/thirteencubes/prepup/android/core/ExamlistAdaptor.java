package com.thirteencubes.prepup.android.core;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thirteencubes.prepup.android.R;
import com.thirteencubes.prepup.android.ds.ExamInfo;

public class ExamlistAdaptor extends ArrayAdapter<ExamInfo> {
			    Context context;

		    public ExamlistAdaptor(Context context, int textViewResourceId, List<ExamInfo> objects) {
		        super(context, textViewResourceId, objects);
		        // TODO Auto-generated constructor stub
		        this.context = context;
		    }

		    /*private view holder class*/
		    private class ViewHolder {
		      //  ImageView imageView;
		        TextView examName;
		       // TextView msgInfo;
		        //TextView msgSubject;
		    }

		    public View getView(int position, View convertView, ViewGroup parent) {
		        ViewHolder holder = null;
		        ExamInfo rowItem = getItem(position);

		        LayoutInflater mInflater = (LayoutInflater) context
		                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		        if (convertView == null) {
		            convertView = mInflater.inflate(R.layout.examlist_row, null);
		            holder = new ViewHolder();
		            holder.examName = (TextView) convertView.findViewById(R.id.exam_name);
		            convertView.setTag(holder);
		        } else
		            holder = (ViewHolder) convertView.getTag();

		        holder.examName.setText(rowItem.getExamName());

		        return convertView;
		    }
	}
