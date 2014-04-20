package com.thirteencubes.prepup.android.core;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.thirteencubes.prepup.android.R;
import com.thirteencubes.prepup.android.ds.ExamInfo;
import com.thirteencubes.prepup.android.utils.CustomLogger;
import com.thirteencubes.prepup.android.utils.OnSwipeTouchListener;
import com.thirteencubes.prepup.android.utils.SharedUtils;

public class ShowQsActivity extends Activity {

	private int position;
	public ExamInfo exam;
	public OnSwipeTouchListener onSwipeTouchListener;
	private int example;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_qs);
		Log.e("Mathqq", "onCreate of Activity called"); 
		
		if (savedInstanceState == null) {
			FragmentManager  fragmentManager  = getFragmentManager();
	        FragmentTransaction ft = fragmentManager.beginTransaction();
	        Fragment pf =  new PlaceholderFragment();
			ft.replace(android.R.id.content, pf);
			ft.commit();
		}
		
		Intent i = getIntent();
		position = i.getIntExtra("position", -1);
		if (position != -1) {
			exam = ExamInfo.getSampleExamItems().get(position);
		}
		
		onSwipeTouchListener = new OnSwipeTouchListener(getApplicationContext()) {
			
			/*	@Override
			    public void onSwipeTop() {
			        Toast.makeText(ctx, "top", Toast.LENGTH_SHORT).show();
			    }
				*/
				@Override
			    public void onSwipeRight() {
					
					if (example == (exam.getQIds().length-1)) {
			        	Toast.makeText(getApplicationContext(), "Reached last Q", Toast.LENGTH_SHORT).show();
			        	return;
			        }
					example++;
			        UpdateQ();
				}
			    public void onSwipeLeft() {
			        //Toast.makeText(ctx, "left", Toast.LENGTH_SHORT).show();
			        if (example == 0) {
			        	Toast.makeText(getApplicationContext(), "Reached first Q..", Toast.LENGTH_SHORT).show();
			        	return;
			        }
			        
			    	example--;
			        UpdateQ();
			    }
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_qs, menu);
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


	public void showRight(View view) {
	     // Kabloey
		// example++;
	      //  UpdateQ();
		
	}
	
	public void showLeft(View view) {
		 //example--;
	       // UpdateQ();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
	    onSwipeTouchListener.getGestureDetector().onTouchEvent(ev); 
	        return super.dispatchTouchEvent(ev);   
	}
	
	private void UpdateQ() {
		//example %= exam.getQIds().length;
		String data = getExample(example);
		
		WebView w = (WebView) findViewById(R.id.webview1);
		
		w.loadUrl("javascript:document.getElementById('tope').innerHTML='"
		       + data + "';");
		
		//parse 
		w.loadUrl("javascript:M.parseMath(document.getElementById('tope'));");
		
		//uncomment for mathjax
		//w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
	}
	
	private String getExample(int index) {
		//return getResources().getStringArray(R.array.tex_examples)[index];
		
		
		String data = SharedUtils.readFileAsString(exam.getExamId(), exam.getQIds()[index] + ".html");
		String baseDir = SharedUtils.GetDir(exam.getExamId());
		data = data.replace("{baseDir}", "file:///" + baseDir);
		CustomLogger.getLogger(ShowQsActivity.class).debug("html: " + data);
        
		return data;
	}
	
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		int example;
		ExamInfo exam;
		WebView w;
		private OnSwipeTouchListener onSwipeTouchListener;
		public PlaceholderFragment() {
		}

		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.e("Mathqq", "onCreateView of fragment called"); 
			
			View rootView = inflater.inflate(R.layout.fragment_show_qs,
					container, false);
			exam = ((ShowQsActivity)getActivity()).exam;
			onSwipeTouchListener = ((ShowQsActivity)getActivity()).onSwipeTouchListener;
			
			/*final Button leftbutton = (Button) rootView.findViewById(R.id.leftbutton);
			final Button rightbutton = (Button) rootView.findViewById(R.id.rightbutton);
			leftbutton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	  if (example == 0) {
				        	Toast.makeText(getActivity().getApplicationContext(), "Reached first Q in Frag", Toast.LENGTH_SHORT).show();
				        	return;
				        }
				      
	            	 example--;
	            	 UpdateQ();
	             }
	         });
			
			rightbutton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 
	            	 if (example == (exam.getQIds().length-1)) {
	     	        	Toast.makeText(getActivity().getApplicationContext(), "Reached last Q", Toast.LENGTH_SHORT).show();
	     	        	return;
	     	        }
	            	 example++;
	            	 UpdateQ();
	             }
	         });
			*/
			
			w = (WebView) rootView.findViewById(R.id.webview1);
			//TextView tbox = (TextView) rootView.findViewById(R.id.textbox);
			
			final Context ctx = getActivity().getApplicationContext();
			w.getSettings().setJavaScriptEnabled(true);
			w.addJavascriptInterface(new WebAppInterface(ctx), "Android");
			w.getSettings().setBuiltInZoomControls(true);
			
			w.getSettings().setAppCacheMaxSize(1024*1024*8); 
		//	w.getSettings().setAppCachePath(ctx.getCacheDir()); 
			w.getSettings().setAppCacheEnabled(true); 
			w.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
			
			String eg = getExample(1);
			
			/*String body =  //"<span id='math' onclick='optionClicked(1)'>"+  eg + "</span>";
			"<body><div id='q'>Qs </div>" +
					   "<div id='option1' onclick='optionClicked(1)'> " + eg + "</div>" +
					   "<div id='option2' onclick='optionClicked(2)'>"+ eg  +"</div>" +
					   "<div id='option3' onclick='optionClicked(3)'> </div>" +
					   "<div id='option4' onclick='optionClicked(4)'> </div></body>"; 
				*/
			String data = getExample(0);
			String body = "<body><div id='tope'>" + data + "</div></body>";
					
			CustomLogger.getLogger(ShowQsActivity.class).debug("body: " + body);
            
			String scriptInc = 
					"<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=UnifrakturMaguntia'>" 
			+ " <link rel='stylesheet' href='file:///android_asset/mathscribe/jqmath-0.4.0.css'>" 
			+ " <script src='file:///android_asset/mathscribe/jquery-1.4.3.min.js'></script>" 
			+ " <script src='file:///android_asset/mathscribe/jqmath-etc-0.4.0.min.js'></script>"
			//+ "<script>M.MathPlayer = false;</script>"
			;

		/*	String scriptInc = 
					"<script type='text/x-mathjax-config'>"
		                      +"MathJax.Hub.Config({ " 
							  	+"showMathMenu: false, "
							  	+"jax: ['input/TeX','output/HTML-CSS'], "
							  	+"extensions: ['tex2jax.js'], " 
							  	+"TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
							  	  +"'noErrors.js','noUndefined.js'] } "
							  +"});</script>"
		                      +"<script type='text/javascript' "
							  +"src='file:///android_asset/MathJax/MathJax.js'"
							  +"></script>" ;
			*/		
		//	w.loadUrl("file:///android_asset/mathscribe/COPY-ME.html");
			String content = "<html><head>" +
					scriptInc +
					  "<script type='text/javascript'>" +
					    "function optionClicked(option) {" +
					     "   Android.onClick1(option); " +
					  //  " divb = document.getElementById('option4');" +
						//"divb.innerHTML = '<math display='block'><mrow><mi mathvariant='normal'>&pi;</mi><mo>≈</mo><mfrac><mn>65</mn><mn>113</mn></mfrac></mrow></math>'"+
					//	"M.parseMath(divb);"+
					    "}" +
					    "function showAndroidToast1() {" +
					     "   Android.onClick1('1 clicked'); " +
					    "}" +
					"</script>" +
					    
					 "</head></html>" +
					 body;
			Log.e("Mathqq", content); 
			w.loadDataWithBaseURL("http://bar", content,"text/html","utf-8","");
			example = 0;
			//UpdateQ();

			
			
			    
			   /* public void onSwipeBottom() {
			        Toast.makeText(ctx, "bottom", Toast.LENGTH_SHORT).show();
			    }*/
			  			

			//w.setOnTouchListener(onSwipeTouchListener);
			return rootView;
		}
		
		public void showRight(View view) {
		     // Kabloey
			if (example == (exam.getQIds().length-1)) {
	        	Toast.makeText(getActivity().getApplicationContext(), "Reached last Q from frag", Toast.LENGTH_SHORT).show();
	        	return;
	        }
			 example++;
		        UpdateQ();
		}
		
		public void showLeft(View view) {
			//Toast.makeText(ctx, "left", Toast.LENGTH_SHORT).show();
	        if (example == 0) {
	        	Toast.makeText(getActivity().getApplicationContext(), "Reached first Q", Toast.LENGTH_SHORT).show();
	        	return;
	        }
	        
			 example--;
		        UpdateQ();
		}
		
		private void UpdateQ() {
			//example %= exam.getQIds().length;
			String data = getExample(example);
			w.loadUrl("javascript:document.getElementById('tope').innerHTML='"
			        + data + "';");
			
			//parse 
			w.loadUrl("javascript:M.parseMath(document.getElementById('tope'));");
			
			/*w.loadUrl("javascript:document.getElementById('option1').innerHTML='\\\\["
			        + data  
					//+doubleEscapeTeX(data)
					  +"\\\\]';");
			w.loadUrl("javascript:document.getElementById('option3').innerHTML='"
			        + data + "';");
			
			//parse 
			w.loadUrl("javascript:M.parseMath(document.getElementById('option3'));");
			*/
			
			//uncomment for mathjax
			//w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
		}
		
		private String getExample(int index) {
			//return getResources().getStringArray(R.array.tex_examples)[index];
			
			
			String data = SharedUtils.readFileAsString(exam.getExamId(), exam.getQIds()[index] + ".html");
			String baseDir = SharedUtils.GetDir(exam.getExamId());
			data = data.replace("{baseDir}", "file:///" + baseDir);
			CustomLogger.getLogger(ShowQsActivity.class).debug("html: " + data);
            
			return data;
		}
		
		
	}

}
