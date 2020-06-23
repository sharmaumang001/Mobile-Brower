package com.sharmaumang001.srpbrowser.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import org.adblockplus.libadblockplus.android.webview.AdblockWebView;

import android.widget.ProgressBar;
import android.widget.Toast;


import com.sharmaumang001.srpbrowser.Adapter.RecyclerViewClicklistner;
import com.sharmaumang001.srpbrowser.Adapter.SitesAdapter;
import com.sharmaumang001.srpbrowser.Database.BookmarkEntity;
import com.sharmaumang001.srpbrowser.Database.DatabaseHelper;
import com.sharmaumang001.srpbrowser.Database.FunctionsBookmark;
import com.sharmaumang001.srpbrowser.Database.FunctionsHistory;
import com.sharmaumang001.srpbrowser.Database.HistoryEntity;
import com.sharmaumang001.srpbrowser.Model.Sites;
import com.sharmaumang001.srpbrowser.utility.MyNetworkState;
import com.sharmaumang001.srpbrowser.utility.MyWebViewClient;
import com.sharmaumang001.srpbrowser.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


import de.mrapp.android.tabswitcher.AbstractState;
import de.mrapp.android.tabswitcher.AddTabButtonListener;
import de.mrapp.android.tabswitcher.Animation;
import de.mrapp.android.tabswitcher.Layout;
import de.mrapp.android.tabswitcher.PeekAnimation;
import de.mrapp.android.tabswitcher.PullDownGesture;
import de.mrapp.android.tabswitcher.RevealAnimation;
import de.mrapp.android.tabswitcher.StatefulTabSwitcherDecorator;
import de.mrapp.android.tabswitcher.SwipeGesture;
import de.mrapp.android.tabswitcher.Tab;
import de.mrapp.android.tabswitcher.TabPreviewListener;
import de.mrapp.android.tabswitcher.TabSwitcher;

import de.mrapp.android.tabswitcher.TabSwitcherListener;
import de.mrapp.android.util.ThemeUtil;
import de.mrapp.android.util.multithreading.AbstractDataBinder;


import static de.mrapp.android.util.DisplayUtil.getDisplayWidth;


public class MainActivity extends AppCompatActivity implements TabSwitcherListener {

    WebView mWebView;
    EditText mUrlText;
    ProgressBar mProgressBar;
    ImageButton mBackButton, mForwardButton, mStopButton, mRefreshButton, mHomeButton;
    RecyclerView mRecyclerView;
    AdblockWebView adblockWebView;
    List<Sites> mSitesList;
    SitesAdapter mAdapter;
    String Url;
    BookmarkEntity bookmarkEntity;
    HistoryEntity historyEntity;
    ImageView  image;
    String mImageDownloadurl;
    DatabaseHelper mydb;
    Boolean check = true;



    private class State extends AbstractState
            implements AbstractDataBinder.Listener<ArrayAdapter<String>, Tab, ListView, Void>,
            TabPreviewListener {


        //      private ArrayAdapter<String> adapter;


        State(@NonNull final Tab tab) {
            super(tab);
        }




        @Override
        public boolean onLoadData(
                @NonNull final AbstractDataBinder<ArrayAdapter<String>, Tab, ListView, Void> dataBinder,
                @NonNull final Tab key, @NonNull final Void... params) {
            history(check);
            return true;
        }

        @Override
        public void onCanceled(
                @NonNull final AbstractDataBinder<ArrayAdapter<String>, Tab, ListView, Void> dataBinder) {

        }

        @Override
        public void onFinished(
                @NonNull final AbstractDataBinder<ArrayAdapter<String>, Tab, ListView, Void> dataBinder,
                @NonNull final Tab key, @Nullable final ArrayAdapter<String> data,
                @NonNull final ListView view, @NonNull final Void... params) {

        }

        @Override
        public final void saveInstanceState(@NonNull final Bundle outState) {

        }

        @Override
        public void restoreInstanceState(@Nullable final Bundle savedInstanceState) {

        }

        @Override
        public boolean onLoadTabPreview(@NonNull final TabSwitcher tabSwitcher,
                                        @NonNull final Tab tab) {
            return !getTab().equals(tab);
        }

    }


    private class Decorator extends StatefulTabSwitcherDecorator<State> {

        @Nullable
        @Override
        protected State onCreateState(@NonNull final Context context,
                                      @NonNull final TabSwitcher tabSwitcher,
                                      @NonNull final View view, @NonNull final Tab tab,
                                      final int index, final int viewType,
                                      @Nullable final Bundle savedInstanceState) {


            return null;
        }

        @Override
        protected void onClearState(@NonNull final State state) {
            tabSwitcher.removeTabPreviewListener(state);
        }

        @Override
        protected void onSaveInstanceState(@NonNull final View view, @NonNull final Tab tab,
                                           final int index, final int viewType,
                                           @Nullable final State state,
                                           @NonNull final Bundle outState) {
            if (state != null) {
                //  state.saveInstanceState(outState);
            }
        }



        @NonNull
        @Override
        public View onInflateView(@NonNull final LayoutInflater inflater,
                                  @Nullable final ViewGroup parent, final int viewType) {
            View view;

            view = inflater.inflate(R.layout.tablayout, parent, false);


            Toolbar toolbar = view.findViewById(R.id.toolbar);
            toolbar.inflateMenu(R.menu.popup_menu);
            toolbar.setOnMenuItemClickListener(createToolbarMenuListener());
            Menu menu = toolbar.getMenu();
            TabSwitcher.setupWithMenu(tabSwitcher, menu, createTabSwitcherButtonListener());
            return view;
        }

        @Override
        public void onShowTab(@NonNull final Context context,
                              @NonNull final TabSwitcher tabSwitcher, @NonNull final View view,
                              @NonNull final Tab tab, final int index, final int viewType,
                              @Nullable final State state,
                              @Nullable final Bundle savedInstanceState) {

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setVisibility(tabSwitcher.isSwitcherShown() ? View.GONE : View.VISIBLE);


            //              WebView code----------------------------------------------------
            mWebView = findViewById(R.id.webView_ID);
            mUrlText = findViewById(R.id.url_EditText_ID);
            mProgressBar = findViewById(R.id.progressBar_ID);
            mBackButton = findViewById(R.id.backButton_ID);
            mForwardButton = findViewById(R.id.forwardButton_ID);
            mStopButton = findViewById(R.id.stopButton_ID);
            mRefreshButton = findViewById(R.id.refreshButon_ID);
            mHomeButton = findViewById(R.id.homeButton_ID);
            mRecyclerView = findViewById(R.id.shortcutlinks_recycler);
            image = findViewById(R.id.img);

            mUrlText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUrlText.requestFocus();
                    InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.showSoftInput(mUrlText , 0);
                    }
                }
            });


              //adBlock

           adblockWebView = findViewById(R.id.adBlock);
           adblockWebView.setAdblockEnabled(true);

	            /*System.setProperty("http.proxyHost", "127.0.0.1");
              change proxy ip and port
            System.setProperty("http.proxyPort", "8080");*/

            mSitesList = new ArrayList<>();
            Sites sites = new Sites("https://www.google.com/","Google",R.drawable.ic_google);
            mSitesList.add(sites);

            sites=new Sites("https://www.facebook.com/","Facebook",R.drawable.ic_facebook);
            mSitesList.add(sites);

            sites=new Sites("https://github.com/","Github",R.drawable.ic_github);
            mSitesList.add(sites);

            sites=new Sites("https://www.youtube.com/","Youtube",R.drawable.ic_youtube);
            mSitesList.add(sites);

            sites=new Sites("https://www.linkedin.com/","LinkedIn",R.drawable.ic_linkedin);
            mSitesList.add(sites);

            sites=new Sites("https://www.instagram.com/","Instagram",R.drawable.ic_instagram);
            mSitesList.add(sites);

            sites = new Sites("https://sevenribbonspictures.com/","  S R P",R.drawable.srp7);
            mSitesList.add(sites);

            mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));

            final RecyclerViewClicklistner listener = new RecyclerViewClicklistner() {
                @Override
                public void onClick(View view1, int position) {
                    Url = mSitesList.get(position).getUrl();
                    mRecyclerView.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                    mWebView.getSettings().setJavaScriptEnabled(true);
                    mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    mWebView.setWebViewClient(new Browser_Home());
                    mWebView.setWebChromeClient(new ChromeClient());
                    WebSettings webSettings = mWebView.getSettings();
                    mWebView.loadUrl(Url);
                    mUrlText.setText(mWebView.getUrl());
                    history(check);
                    image.setVisibility(View.GONE);
                }
            };
            mAdapter = new SitesAdapter(getApplicationContext(), listener,mSitesList);
            mRecyclerView.setAdapter(mAdapter);



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
                        } else {
                            mProgressBar.setVisibility(ProgressBar.VISIBLE);
                        }
                    }
                });
            }
            mWebView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    final String filename = URLUtil.guessFileName(url,contentDisposition,mimetype);

                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                                    String cookie = CookieManager.getInstance().getCookie(url);
                                    request.addRequestHeader("Cookie",cookie);
                                    request.addRequestHeader("User-Agent",userAgent);
                                    request.allowScanningByMediaScanner();
                                    request.setShowRunningNotification(true);
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
                                    manager.enqueue(request);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY , hh:mm a", Locale.getDefault());
                                    File path = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + filename);
                                    addDownload(filename,sdf.format(new Date()),String.valueOf(path));

                }
            });

            mWebView.setWebViewClient(new MyWebViewClient(){
                public void onPageFinished(WebView view, String url) {
                    mUrlText.setText(view.getUrl());
                history(check);
                }
            });


            mUrlText.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        try {
                            if (!MyNetworkState.connectionAvailable(MainActivity.this)) {
                                Toast.makeText(MainActivity.this, "Check Connection", Toast.LENGTH_SHORT).show();
                            } else {
                                mRecyclerView.setVisibility(View.INVISIBLE);
                                mWebView.setVisibility(View.VISIBLE);
                                String url = mUrlText.getText().toString();

                                mWebView.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                                mUrlText.setText(mWebView.getUrl());

                                if (url.contains(".")) {

                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethodManager.hideSoftInputFromWindow(mUrlText.getWindowToken(), 0);

                                    mWebView.loadUrl("https://" + url);
                                    history(check);
                                    image.setVisibility(View.GONE);
                                    mUrlText.setText(mWebView.getUrl());
                                } else {
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethodManager.hideSoftInputFromWindow(mUrlText.getWindowToken(), 0);
                                    mWebView.loadUrl("https://www.google.com/search?q=" + url);
                                    history(check);
                                    image.setVisibility(View.GONE);
                                    mUrlText.setText(mWebView.getUrl());
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                    return false;
                }
            });



            mBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    }else{
                        if (mWebView.canGoBack()) {
                            if(mWebView.getUrl() =="about:blank" ){

                                mWebView.clearCache(true);
                                mWebView.clearHistory();

                                mRecyclerView.setVisibility(View.VISIBLE);
                                image.setVisibility(View.VISIBLE);
                                mWebView.setVisibility(View.GONE);
                                mUrlText.setText("");
                            }else {
                                mWebView.goBack();
                            }
                        }else{
                            mWebView.loadUrl("about:blank");
                            history(check);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            image.setVisibility(View.VISIBLE);
                            mWebView.setVisibility(View.GONE);
                            mWebView.clearCache(true);
                            mWebView.clearHistory();
                            mUrlText.setText("");
                        }

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
                    mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
                    mAdapter = new SitesAdapter(getApplicationContext(),listener,mSitesList);
                    mRecyclerView.setAdapter(mAdapter);

                    mWebView.loadUrl("about:blank");
                    history(check);

                    mRecyclerView.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    mWebView.clearCache(true);
                    mWebView.clearHistory();
                    mUrlText.setText("");
                    Toast.makeText(MainActivity.this, "HOME", Toast.LENGTH_SHORT).show();
                }
            });
            registerForContextMenu(mWebView);


        }


        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getViewType(@NonNull final Tab tab, final int index) {
            Bundle parameters = tab.getParameters();
            return parameters != null ? parameters.getInt(VIEW_TYPE_EXTRA) : 0;
        }

    }




    private static final String VIEW_TYPE_EXTRA = MainActivity.class.getName() + "::ViewType";



    private static final int TAB_COUNT = 1;


    private TabSwitcher tabSwitcher;


    private Decorator decorator;


    private Snackbar snackbar;


    @NonNull
    private OnApplyWindowInsetsListener createWindowInsetsListener() {
        return new OnApplyWindowInsetsListener() {

            @Override
            public WindowInsetsCompat onApplyWindowInsets(final View v,
                                                          final WindowInsetsCompat insets) {
                int left = insets.getSystemWindowInsetLeft();
                int top = insets.getSystemWindowInsetTop();
                int right = insets.getSystemWindowInsetRight();
                int bottom = insets.getSystemWindowInsetBottom();
                tabSwitcher.setPadding(left, top, right, bottom);
                float touchableAreaTop = top;

                if (tabSwitcher.getLayout() == Layout.TABLET) {
                    touchableAreaTop += getResources()
                            .getDimensionPixelSize(R.dimen.tablet_tab_container_height);
                }

                RectF touchableArea = new RectF(left, touchableAreaTop,
                        getDisplayWidth(MainActivity.this) - right, touchableAreaTop +
                        ThemeUtil.getDimensionPixelSize(MainActivity.this, R.attr.actionBarSize));
                tabSwitcher.addDragGesture(
                        new SwipeGesture.Builder().setTouchableArea(touchableArea).create());
                tabSwitcher.addDragGesture(
                        new PullDownGesture.Builder().setTouchableArea(touchableArea).create());
                return insets;
            }

        };
    }


    @NonNull
    private View.OnClickListener createAddTabListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                int index = tabSwitcher.getCount();
                Animation animation = createRevealAnimation();
                tabSwitcher.addTab(createTab(index), 0, animation);
            }

        };
    }


    @NonNull
    private Toolbar.OnMenuItemClickListener createToolbarMenuListener() {
        return new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.setbookmark:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                        final View view = inflater.inflate(R.layout.popup_window, null);
                        builder.setView(view)
                                .setTitle("Bookmark")
                                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EditText editName = view.findViewById(R.id.bookmarkName);
                                        EditText editUrl = view.findViewById(R.id.bookmarkUrl);
                                        String name = editName.getText().toString();
                                        editUrl.setText(mWebView.getUrl());
                                        String url = mWebView.getUrl();
                                        if(url!=null){
                                            bookmarkEntity = new BookmarkEntity(url, name);
                                            try {
                                                Boolean check = new FunctionsBookmark.DBAsyncTask(MainActivity.this, bookmarkEntity, 1).execute().get();
                                                if(check){
                                                    Toast.makeText(
                                                            MainActivity.this,
                                                            "Bookmark added",
                                                            Toast.LENGTH_SHORT
                                                    ).show();
                                                }
                                                else{
                                                    Toast.makeText(
                                                            MainActivity.this,
                                                            "Bookmark not added",
                                                            Toast.LENGTH_SHORT
                                                    ).show();
                                                }
                                            } catch (ExecutionException | InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        else{
                                            Toast.makeText(
                                                    MainActivity.this,
                                                    "URL is empty",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                    }
                                })
                                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(
                                                MainActivity.this,
                                                "Bookmark not added",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                });
                        builder.create();
                        builder.show().getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                        EditText editUrl = view.findViewById(R.id.bookmarkUrl);
                        editUrl.setText(mWebView.getUrl());
                        return true;

                    case R.id.download:
                        Intent intent = new Intent(MainActivity.this, MyDownloadActivity.class);
                        startActivity(intent);
                        finish();
                        return true;

                    case R.id.info:
                        builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Website Info")
                                .setMessage(mWebView.getUrl())
                                .create()
                                .show();
                        return true;

                    case R.id.refresh:
                        mWebView.reload();
                        return true;

                    case R.id.add_tab_menu_item:
                        int index = tabSwitcher.getCount();
                        Tab tab = createTab(index);

                        if (tabSwitcher.isSwitcherShown()) {
                            tabSwitcher.addTab(tab, 0, createRevealAnimation());
                        } else {
                            tabSwitcher.addTab(tab, 0, createPeekAnimation());
                        }
                        return true;

                    case R.id.incognito:
                        if(item.isChecked()){
                            item.setChecked(false);
                            check = true;
                        }
                        else{
                            item.setChecked(true);
                            check = false;
                            Toast.makeText(MainActivity.this, "Incognito Mode", Toast.LENGTH_LONG).show();
                        }
                        return true;


                    case R.id.bookmark:
                        intent = new Intent(MainActivity.this, MyBookmarks.class);
                        startActivity(intent);
                        finish();
                        return true;

                        case R.id.btnAdBlock:
                        if(adblockWebView.isAdblockEnabled()){
                            adblockWebView.setAdblockEnabled(false);
                            item.setChecked(false);
                        }
                        else{
                            adblockWebView.setAdblockEnabled(true);
                            item.setChecked(true);
                        }
                        return true;

                    case R.id.history:
                        startActivity(new Intent(MainActivity.this, BrowserHistoryActivity.class));
                        finish();
                        return true;

                    /*case R.id.audioQueue:
                        intent = new Intent(MainActivity.this, MyAudioQueueActivity.class);
                        startActivity(intent);
                        finish();
                        return true;*/

//                    case R.id.downloadVids:
//                        //
//                        return true;

                    case R.id.clearData:
                        new FunctionsHistory.ClearData(MainActivity.this).execute();
                        new FunctionsBookmark.delete(MainActivity.this).execute();
                        mydb = new DatabaseHelper(MainActivity.this);
                        mydb.deleteData();
                        return true;

                    case R.id.erase:
                        new FunctionsHistory.ClearData(MainActivity.this).execute();
                        mydb = new DatabaseHelper(MainActivity.this);
                        mydb.deleteData();
                        new FunctionsBookmark.delete(MainActivity.this).execute();
                        if (tabSwitcher.isSwitcherShown()) {
                            createRevealAnimation();
                        }
                        else {
                            createPeekAnimation();
                        }
                        tabSwitcher.clear();
                        return true;

                    case R.id.downloads:
                        intent = new Intent(MainActivity.this, MyDownloadActivity.class);
                        startActivity(intent);
                        finish();
                        return true;

                    case R.id.desktopSite:
                        if (item.isChecked()) {
                            item.setChecked(false);
                            setDesktopMode(mWebView, false);
                        } else {
                            item.setChecked(true);
                            setDesktopMode(mWebView, true);
                        }
                        return true;

//                    case R.id.settings_menu_item:
//                        //add settings page
//                        return true;

                    case R.id.appInfo:
                        intent = new Intent(MainActivity.this, Info.class);
                        startActivity(intent);
                        finish();
                        return true;

                    case R.id.donate:
                        mWebView.loadUrl("https://milaap.org/fundraisers/support-pulkit-gupta");
                        mRecyclerView.setVisibility(View.GONE);
                        mWebView.setVisibility(View.VISIBLE);
                        mWebView.getSettings().setLoadsImagesAutomatically(true);
                        mWebView.getSettings().setJavaScriptEnabled(true);
                        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

                        mWebView.setWebViewClient(new Browser_Home());
                        mWebView.setWebChromeClient(new ChromeClient());
                        WebSettings webSettings = mWebView.getSettings();
                        mUrlText.setText(mWebView.getUrl());
                        history(check);
                        image.setVisibility(View.GONE);
                        return true;

                    case R.id.exit:
                        finishAffinity();

                    case R.id.clear_tabs_menu_item:
                        tabSwitcher.clear();
                        return true;

                    default:
                        return false;
                }
            }
        };
    }


    private void inflateMenu() {
        tabSwitcher
                .inflateToolbarMenu(tabSwitcher.getCount() >= 0 ? R.menu.tab_switcher : R.menu.popup_menu,
                        createToolbarMenuListener());
    }


    @NonNull
    private View.OnClickListener createTabSwitcherButtonListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                tabSwitcher.toggleSwitcherVisibility();
            }

        };
    }


    @NonNull
    private AddTabButtonListener createAddTabButtonListener() {
        return new AddTabButtonListener() {

            @Override
            public void onAddTab(@NonNull final TabSwitcher tabSwitcher) {
                int index = tabSwitcher.getCount();
                Tab tab = createTab(index);
                tabSwitcher.addTab(tab, 0);
            }

        };
    }


    @NonNull
    private View.OnClickListener createUndoSnackbarListener(@NonNull final Snackbar snackbar,
                                                            final int index,
                                                            @NonNull final Tab... tabs) {
        return new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                snackbar.setAction(null, null);

                if (tabSwitcher.isSwitcherShown()) {
                    tabSwitcher.addAllTabs(tabs, index);
                } else if (tabs.length == 1) {
                    tabSwitcher.addTab(tabs[0], 0, createPeekAnimation());
                }

            }

        };
    }

    @NonNull
    private BaseTransientBottomBar.BaseCallback<Snackbar> createUndoSnackbarCallback(
            final Tab... tabs) {
        return new BaseTransientBottomBar.BaseCallback<Snackbar>() {

            @Override
            public void onDismissed(final Snackbar snackbar, final int event) {
                if (event != DISMISS_EVENT_ACTION) {
                    for (Tab tab : tabs) {
                        tabSwitcher.clearSavedState(tab);
                        decorator.clearState(tab);
                    }
                }
            }
        };
    }


    private void showUndoSnackbar(@NonNull final CharSequence text, final int index,
                                  @NonNull final Tab... tabs) {
        snackbar = Snackbar.make(tabSwitcher, text, Snackbar.LENGTH_LONG).setActionTextColor(
                ContextCompat.getColor(this, R.color.colorAccent));
        snackbar.setAction(R.string.undo, createUndoSnackbarListener(snackbar, index, tabs));
        snackbar.addCallback(createUndoSnackbarCallback(tabs));
        snackbar.show();
    }


    @NonNull
    private Animation createRevealAnimation() {
        float x = 0;
        float y = 0;
        View view = getNavigationMenuItem();

        if (view != null) {
            int[] location = new int[2];
            view.getLocationInWindow(location);
            x = location[0] + (view.getWidth() / 2f);
            y = location[1] + (view.getHeight() / 2f);
        }

        return new RevealAnimation.Builder().setX(x).setY(y).create();
    }


    @NonNull
    private Animation createPeekAnimation() {
        return new PeekAnimation.Builder().setX(tabSwitcher.getWidth() / 2f).create();
    }


    @Nullable
    private View getNavigationMenuItem() {
        Toolbar[] toolbars = tabSwitcher.getToolbars();

        if (toolbars != null) {
            Toolbar toolbar = toolbars.length > 1 ? toolbars[1] : toolbars[0];
            int size = toolbar.getChildCount();

            for (int i = 0; i < size; i++) {
                View child = toolbar.getChildAt(i);

                if (child instanceof ImageButton) {
                    return child;
                }
            }
        }

        return null;
    }


    @NonNull
    private Tab createTab(final int index) {
        CharSequence title = getString(R.string.tab_title, index + 1);
        Tab tab = new Tab(title);

        Bundle parameters = new Bundle();
        parameters.putInt(VIEW_TYPE_EXTRA, index % 100);
        tab.setParameters(parameters);

        return tab;
    }

    @Override
    public final void onSwitcherShown(@NonNull final TabSwitcher tabSwitcher) {

    }

    @Override
    public final void onSwitcherHidden(@NonNull final TabSwitcher tabSwitcher) {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    @Override
    public final void onSelectionChanged(@NonNull final TabSwitcher tabSwitcher,
                                         final int selectedTabIndex,
                                         @Nullable final Tab selectedTab) {

    }

    @Override
    public final void onTabAdded(@NonNull final TabSwitcher tabSwitcher, final int index,
                                 @NonNull final Tab tab, @NonNull final Animation animation) {
        inflateMenu();
        TabSwitcher.setupWithMenu(tabSwitcher, createTabSwitcherButtonListener());

    }

    @Override
    public final void onTabRemoved(@NonNull final TabSwitcher tabSwitcher, final int index,
                                   @NonNull final Tab tab, @NonNull final Animation animation) {
        CharSequence text = getString(R.string.removed_tab_snackbar, tab.getTitle());
        showUndoSnackbar(text, index, tab);
        inflateMenu();
        TabSwitcher.setupWithMenu(tabSwitcher, createTabSwitcherButtonListener());
    }

    @Override
    public final void onAllTabsRemoved(@NonNull final TabSwitcher tabSwitcher,
                                       @NonNull final Tab[] tabs,
                                       @NonNull final Animation animation) {
        CharSequence text = getString(R.string.cleared_tabs_snackbar);
        showUndoSnackbar(text, 0, tabs);
        inflateMenu();
        TabSwitcher.setupWithMenu(tabSwitcher, createTabSwitcherButtonListener());
    }


    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decorator = new Decorator();
        tabSwitcher = findViewById(R.id.tab_switcher);
        tabSwitcher.clearSavedStatesWhenRemovingTabs(false);
        ViewCompat.setOnApplyWindowInsetsListener(tabSwitcher, createWindowInsetsListener());
        tabSwitcher.setDecorator(decorator);
        tabSwitcher.addListener(this);
        tabSwitcher.showToolbars(true);
        mydb=new DatabaseHelper(getApplicationContext());

        for (int i = 0; i < TAB_COUNT; i++) {
            tabSwitcher.addTab(createTab(i));
        }

        tabSwitcher.showAddTabButton(createAddTabButtonListener());
        tabSwitcher.setToolbarNavigationIcon(R.drawable.ic_plus_24dp, createAddTabListener());
        TabSwitcher.setupWithMenu(tabSwitcher, createTabSwitcherButtonListener());
        inflateMenu();

        //Runtime External storage permission for saving download files

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }
        }

    }

    @Override
    public final void onBackPressed() {
        if (mWebView.getVisibility()==View.GONE) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
            alert.setTitle("Exit")
                    .setMessage("Sure You Want To Exit")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
            alert.create()
                    .show();
        }else{
            if (mWebView.canGoBack()) {
                if(mWebView.getUrl() =="about:blank" ){

                    mRecyclerView.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                }else {
                    mWebView.goBack();
                }
            }else{
                mWebView.loadUrl("about:blank");
                history(check);
                mRecyclerView.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                mWebView.clearCache(true);
                mWebView.clearHistory();
            }

        }
    }

    public void setDesktopMode(WebView webView,boolean enabled) {
        String newUserAgent = webView.getSettings().getUserAgentString();
        if (enabled) {
            try {
                String ua = webView.getSettings().getUserAgentString();
                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            newUserAgent = null;
        }

        webView.getSettings().setUserAgentString(newUserAgent);
        webView.getSettings().setUseWideViewPort(enabled);
        webView.getSettings().setLoadWithOverviewMode(enabled);
        webView.reload();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final WebView.HitTestResult webviewHittestResult = mWebView.getHitTestResult();
        mImageDownloadurl = webviewHittestResult.getExtra();
        if(webviewHittestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                webviewHittestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE)
        {
            if(URLUtil.isNetworkUrl(mImageDownloadurl))
            {
                menu.setHeaderTitle("Download Image from Below");
                menu.add(0,1,0,"Download Image")
                        .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                int Permission_all = 1;
                                String Permission[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
                                if(!hasPermission(MainActivity.this,Permission))
                                {
                                    ActivityCompat.requestPermissions(MainActivity.this,Permission,Permission_all);
                                }
                                else
                                {
                                    String filename = "";
                                    String type = null;
                                    String Mimetype = MimeTypeMap.getFileExtensionFromUrl(mImageDownloadurl);
                                    filename = URLUtil.guessFileName(mImageDownloadurl,mImageDownloadurl,Mimetype);
                                    if(Mimetype!=null)
                                    {
                                        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(Mimetype);
                                    }
                                    if(type==null)
                                    {
                                        filename = filename.replace(filename.substring(filename.lastIndexOf(".")),".png");
                                        type = "image/*";
                                    }
                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mImageDownloadurl));
                                    request.allowScanningByMediaScanner();
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
                                    DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                                    assert manager != null;
                                    manager.enqueue(request);
                                    Toast.makeText(MainActivity.this, "Check Notification", Toast.LENGTH_SHORT).show();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY , hh:mm a", Locale.getDefault());
                                    File path = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + filename);
                                    addDownload(filename,sdf.format(new Date()),String.valueOf(path));
                                }
                                return false;
                            }
                        });
            }
        }
//        else
//        {
//            Toast.makeText(this, "Error Downloading", Toast.LENGTH_SHORT).show();
//        }
    }

    public static boolean hasPermission(Context context, String... permissions)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                context!=null && permissions!=null)
        {
            for(String permission : permissions)
            {
                if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

   public void addDownload(String Title,String Time,String Path)
    {

        String title = Title;
        String time = Time;
        String path = Path;
        boolean isInserted = mydb.insertDataDownload(title,time,path);
        if(isInserted)
        {
            Toast.makeText(this, "Download Added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error Downloading", Toast.LENGTH_SHORT).show();
        }
    }
    class Browser_Home extends WebViewClient {
        Browser_Home(){}

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    public void history(Boolean check){
        if(!mWebView.getUrl().equals("about:blank")){
            try {
                Integer id = new FunctionsHistory.Count(MainActivity.this).execute().get();
                if (check) {
                    id = id+1;
                    historyEntity = new HistoryEntity(id, mWebView.getUrl());
                    new FunctionsHistory.DBAsyncTask(MainActivity.this, historyEntity, 1).execute().get();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Count Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

}