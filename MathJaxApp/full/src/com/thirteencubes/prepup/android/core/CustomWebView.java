package com.thirteencubes.prepup.android.core;

import android.webkit.WebView;
import android.webkit.WebViewClient;

class CustomWebView extends WebViewClient {
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
            String ht = "javascript:window.droid.print(document.getElementsByTagName('html')[0].innerHTML);";
            view.loadUrl(ht);
    }
}
