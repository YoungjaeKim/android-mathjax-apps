package edu.jsu.leathrum.android.mathjaxapp.full;

import android.content.Context;
import android.widget.Toast;

public class WebAppInterface {

	Context _context;
	
	public WebAppInterface(Context c) {
		// TODO Auto-generated constructor stub
	
		_context = c;
	}
	
	//@JavascriptInterface
	public void onClick1(String toast) {
		
		Toast.makeText(_context, toast, Toast.LENGTH_SHORT).show();
	}
}
