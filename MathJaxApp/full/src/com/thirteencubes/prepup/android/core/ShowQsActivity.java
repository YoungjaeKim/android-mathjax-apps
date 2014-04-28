package com.thirteencubes.prepup.android.core;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.thirteencubes.prepup.android.R;
import com.thirteencubes.prepup.android.ds.ExamInfo;
import com.thirteencubes.prepup.android.utils.CustomLogger;
import com.thirteencubes.prepup.android.utils.OnSwipeTouchListener;
import com.thirteencubes.prepup.android.utils.SharedUtils;

public class ShowQsActivity extends Activity {

	private int position;
	private ExamInfo exam;
	private OnSwipeTouchListener onSwipeTouchListener;
	private WebAppInterface webAppIntf;
	private int example;
	private WebView w;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_qs);
		Log.e("Mathqq", "onCreate of Activity called"); 
		webAppIntf = new WebAppInterface(getApplicationContext());
		
		if (savedInstanceState == null) {
			/*FragmentManager  fragmentManager  = getFragmentManager();
	        FragmentTransaction ft = fragmentManager.beginTransaction();
	        Fragment pf =  new PlaceholderFragment();
			ft.replace(android.R.id.content, pf);
			ft.commit();
			*/
		}
		
		Intent i = getIntent();
		position = i.getIntExtra("position", -1);
		if (position != -1) {
			exam = ExamInfo.getSampleExamItems().get(position);
		}
		
		onSwipeTouchListener = new OnSwipeTouchListener(getApplicationContext()) {
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
		
		InstantiateWebView();
		
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
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
	    onSwipeTouchListener.getGestureDetector().onTouchEvent(ev); 
	        return super.dispatchTouchEvent(ev);   
	}
	
	private void Reset() {
		webAppIntf.QIndex = example;
	}
	
	private void UpdateQ() {
		//example %= exam.getQIds().length;
		String data = getExample(example);
		
		WebView w = (WebView) findViewById(R.id.webview1);
		
		w.loadUrl("javascript:document.getElementById('tope').innerHTML='"
		       + data + "';");
		//parse 
		w.loadUrl("javascript:M.parseMath(document.getElementById('tope'));");
		
		setAnsColor(example, 1);
		setAnsColor(example, 2);
		setAnsColor(example, 3);
		setAnsColor(example, 4);
		
		Reset();
	}
	
	private void InstantiateWebView() {
		w = (WebView) findViewById(R.id.webview1);
		
		w.getSettings().setJavaScriptEnabled(true);
		
		w.addJavascriptInterface(webAppIntf, "Android");
		w.getSettings().setBuiltInZoomControls(true);
		
		String data = getExample(0);
		String body = "<body><button type='button' onclick='showHtml()'>Html</button> <div id='tope'>" + data + "</div></body>";
		//UpdateQ();
		
		//CustomLogger.getLogger(ShowQsActivity.class).debug("body: " + body);
        
		String scriptInc = 
				"<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=UnifrakturMaguntia'>" 
		+ " <link rel='stylesheet' href='file:///android_asset/mathscribe/jqmath-0.4.0.css'>" 
		+ " <link rel='stylesheet' href='file:///android_asset/css/QnA.css'>"
		
		+ " <script src='file:///android_asset/mathscribe/jquery-1.4.3.min.js'></script>" 
		+ " <script src='file:///android_asset/mathscribe/jqmath-etc-0.4.0.min.js'>"
		//+ "<script>M.MathPlayer = false;</script>"
		+ "</script>";
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
				     "   function showHtml() {" +
				     	"Android.showHTML(document.getElementsByTagName('html')[0].innerHTML);" +
				     "}" +
				"</script>" +
				    
				 "</head>" +
				 body +
				 "</html>" 
				 ;
		//Log.e("Mathqq", content); 
		w.loadDataWithBaseURL("http://bar", content,"text/html","utf-8","");
		example = 0;
	
	}
	
	
	private void setAnsColor(int qIndex, int answerNo) {

		if (exam.getState(qIndex, answerNo)) {
			w.loadUrl("javascript:document.getElementById('option" + answerNo 
					+ "').className='selected_ans';");
		} else {
			w.loadUrl("javascript:document.getElementById('option" + answerNo 
					+ "').className='unselected_ans';");
		}
	}
	
	private String getExample(int index) {
		String data = SharedUtils.readFileAsString(exam.getTestId(), exam.getQIds()[index] + ".html");
		String baseDir = SharedUtils.GetDir(exam.getTestId());
		data = data.replace("{baseDir}", "file:///" + baseDir);
		CustomLogger.getLogger(ShowQsActivity.class).debug("html: " + data);
        
		return data;
	}
	
	
	public class WebAppInterface {

		Context _context;
		//ExamInfo examInfo;
		public int QIndex; 
		
		public WebAppInterface(Context c) {
			// TODO Auto-generated constructor stub
			_context = c;
		}
		
		public void toggleState(int ansId) {
			exam.toggleState(QIndex, ansId);
		}
		
		//@JavascriptInterface
		public void onClick1(int toast) {
			
			Toast.makeText(_context,  Integer.toString(toast), Toast.LENGTH_SHORT).show();
			Handler mainHandler = new Handler(Looper.getMainLooper());
			
			final int ansId = toast;
			final boolean state = exam.toggleState(example, toast);
			mainHandler.post(new Runnable() {

			    @Override
			    public void run() {
			        // run code
			    	if (state) {
			    	w.loadUrl("javascript:document.getElementById('option" + ansId   
							+ "').className='selected_ans';");
			    	}
			    	else {
			    		w.loadUrl("javascript:document.getElementById('option" + ansId   
								+ "').className='unselected_ans';");
				    	
			    	}
			    }
			});
			
		}
		
		  @SuppressWarnings("unused") 
		 // @JavascriptInterface
		    public void showHTML(String html)  
		    {  
		        LayoutInflater inflater = (LayoutInflater)
		        	    
		        		getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        		View popupView = inflater.inflate(R.layout.popup_dialogue, null, false);
		        	    final PopupWindow pw = new PopupWindow(
		        	    		popupView, 
		        	       LayoutParams.MATCH_PARENT
		        	       ,LayoutParams.MATCH_PARENT 
		        	       , true);
		        	    
		        	    Button close = (Button) popupView.findViewById(R.id.closeButton);
		        	    close.setOnClickListener(new OnClickListener() {

		        	              public void onClick(View popupView) {
		        	                pw.dismiss();
		        	              }
		        	            });
		        	    
		        	    // The code below assumes that the root container has an id called 'main'
		        	    pw.showAtLocation(findViewById(R.id.webview1), Gravity.CENTER, 0, 0);
		        	    View v= pw.getContentView();
		                TextView tv= (TextView) v.findViewById(R.id.popup_textview);
		                tv.setMovementMethod(new ScrollingMovementMethod());
		                tv.setText(html);
		                
		                
		                pw.setTouchInterceptor(new View.OnTouchListener() {

		                    @Override
		                    public boolean onTouch(View v, MotionEvent event) {
		                        // TODO Auto-generated method stub
		                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		                            pw.dismiss();
		                        }
		                        return true;
		                    }
		                });
		                

		            //    pw.setBackgroundDrawable(new BitmapDrawable());
		              //  pw.setOutsideTouchable(true);
		                //pw.showAsDropDown(btnSelectWeight);
		    }  
	}
	
	

}
