package edu.jsu.leathrum.android.mathjaxapp.full;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import edu.jsu.leathrum.utils.OnSwipeTouchListener;

public class ShowQsActivity extends ActionBarActivity {

	private View rootView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_qs);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		int example;
		WebView w;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_qs,
					container, false);
			
			
			w = (WebView) rootView.findViewById(R.id.webview1);
			TextView tbox = (TextView) rootView.findViewById(R.id.textbox);
			
			final Context ctx = getActivity().getApplicationContext();
			w.getSettings().setJavaScriptEnabled(true);
			w.addJavascriptInterface(new WebAppInterface(ctx), "Android");
			w.getSettings().setBuiltInZoomControls(true);
			
			w.getSettings().setAppCacheMaxSize(1024*1024*8); 
		//	w.getSettings().setAppCachePath(ctx.getCacheDir()); 
			w.getSettings().setAppCacheEnabled(true); 
			w.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
			
			String eg = getExample(1);
			
			String body =  //"<span id='math' onclick='optionClicked(1)'>"+  eg + "</span>";
			"<body><div id='q'>Qs </div>" +
					   "<div id='option1' onclick='optionClicked(1)'> " + eg + "</div>" +
					   "<div id='option2' onclick='optionClicked(2)'>"+ eg  +"</div>" +
					   "<div id='option3' onclick='optionClicked(3)'> </div>" +
					   "<div id='option4' onclick='optionClicked(4)'> </div></body>"; 
						
			Log.e("DEBUG", body); 
			String scriptInc = 
					"<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=UnifrakturMaguntia'>" 
			+ "<link rel='stylesheet' href='file:///android_asset/mathscribe/jqmath-0.4.0.css'>" 
			+ " <script src='file:///android_asset/mathscribe/jquery-1.4.3.min.js'></script>" 
			+ "<script src='file:///android_asset/mathscribe/jqmath-etc-0.4.0.min.js'></script>"
			+ "<script>M.MathPlayer = false;</script>"
			;

			/*String scriptInc = 
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
			w.loadDataWithBaseURL("http://bar", 
									scriptInc +
								  "<script type='text/javascript'>" +
								    "function optionClicked(option) {" +
								     "   Android.onClick1(option); " +
								    "}" +
								    "function showAndroidToast1() {" +
								     "   Android.onClick1('1 clicked'); " +
								    "}" +
								"</script>" +
								 body
								   ,"text/html","utf-8","");
		

			
			
			w.setOnTouchListener(new OnSwipeTouchListener(ctx) {
				
				@Override
			    public void onSwipeTop() {
			        Toast.makeText(ctx, "top", Toast.LENGTH_SHORT).show();
			    }
				
				@Override
			    public void onSwipeRight() {
			      //  Toast.makeText(ctx, "right", Toast.LENGTH_SHORT).show();
			        example++;
			        UpdateQ();
				}
			    public void onSwipeLeft() {
			        //Toast.makeText(ctx, "left", Toast.LENGTH_SHORT).show();
			        example--;
			        UpdateQ();
			    }
			    
			    public void onSwipeBottom() {
			        Toast.makeText(ctx, "bottom", Toast.LENGTH_SHORT).show();
			    }
				//public boolean onTouch(View v, MotionEvent event) {
					//return false;
				// return gestureDetector.onTouchEvent(event);
				//}
			});

			return rootView;
		}
		
		private void UpdateQ() {
			example %= 5;
			String data = getExample(example);
			w.loadUrl("javascript:document.getElementById('option1').innerHTML='\\\\["
			        + data  
					//+doubleEscapeTeX(data)
					  +"\\\\]';");
			w.loadUrl("javascript:document.getElementById('option3').innerHTML="
			        + data + "");
			
			//w.reload();
			//w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
		}
		
		private String getExample(int index) {
			return getResources().getStringArray(R.array.tex_examples)[index];
		}
		
		
	}

}
