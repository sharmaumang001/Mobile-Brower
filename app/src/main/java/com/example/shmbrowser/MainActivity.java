package com.example.shmbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    EditText mUrlText;
    ProgressBar mProgressBar;
    ImageButton mBackButton, mForwardButton, mStopButton, mRefreshButton, mHomeButton,mGoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView= findViewById(R.id.webView_ID);
        mUrlText = findViewById(R.id.url_EditText_ID);
        mProgressBar = findViewById(R.id.progressBar_ID);
        mBackButton = findViewById(R.id.backButton_ID);
        mForwardButton = findViewById(R.id.forwardButton_ID);
        mStopButton = findViewById(R.id.stopButton_ID);
        mRefreshButton = findViewById(R.id.refreshButon_ID);
        mHomeButton = findViewById(R.id.homeButton_ID);
        mGoButton = findViewById(R.id.go_Button_ID);

        if (savedInstanceState != null) {
            mWebView.restoreState(savedInstanceState);
        } else {
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().setSupportMultipleWindows(true);
            mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            mWebView.setBackgroundColor(Color.WHITE);

            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    mProgressBar.setProgress(newProgress);
                    if (newProgress < 100 && mProgressBar.getVisibility() == ProgressBar.GONE) {
                        mProgressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                    if (newProgress == 100) {
                        mProgressBar.setVisibility(ProgressBar.GONE);
                    }else{
                        mProgressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                }
            });
        }

        mWebView.setWebViewClient(new MyWebViewClient());
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(!MyNetworkState.connectionAvailable(MainActivity.this)){
                        Toasty.error(MainActivity.this, "Check Connection", Toasty.LENGTH_SHORT).show();
                    }else {

                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(mUrlText.getWindowToken(), 0);
                        mWebView.loadUrl("https://" + mUrlText.getText().toString());
                        mUrlText.setText("");
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }
            }
        });
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                }
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.stopLoading();
            }
        });

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("https://google.com");
            }
        });

    }
}