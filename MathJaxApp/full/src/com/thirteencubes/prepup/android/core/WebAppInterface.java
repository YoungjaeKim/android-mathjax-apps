package com.thirteencubes.prepup.android.core;

import android.app.AlertDialog;
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
	
	  @SuppressWarnings("unused") 
	 // @JavascriptInterface
	    public void showHTML(String html)  
	    {  
	        new AlertDialog.Builder(_context)  
	            .setTitle("HTML")  
	            .setMessage(html)  
	            .setPositiveButton(android.R.string.ok, null)  
	        .setCancelable(false)  
	        .create()  
	        .show();  
	    }  
}
