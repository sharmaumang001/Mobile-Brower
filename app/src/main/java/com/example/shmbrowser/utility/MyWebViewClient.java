package com.example.shmbrowser.utility;

import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.shmbrowser.Activity.MainActivity;

public class MyWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        CookieManager.getInstance().setAcceptCookie(true);
        return true;
    }
}
