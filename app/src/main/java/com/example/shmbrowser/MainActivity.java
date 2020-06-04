package com.example.shmbrowser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.ProgressBar;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;



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

import es.dmoral.toasty.Toasty;

import static de.mrapp.android.util.DisplayUtil.getDisplayWidth;





public class MainActivity extends AppCompatActivity implements TabSwitcherListener {

    WebView mWebView;

    EditText mUrlText;
    ProgressBar mProgressBar;
    ImageButton mBackButton, mForwardButton, mStopButton, mRefreshButton, mHomeButton,mGoButton,mPopUPButton;

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
                state.saveInstanceState(outState);
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



            //               WebView code----------------------------------------------------
            mWebView= findViewById(R.id.webView_ID);
            mUrlText = findViewById(R.id.url_EditText_ID);
            mProgressBar = findViewById(R.id.progressBar_ID);
            mBackButton = findViewById(R.id.backButton_ID);
            mForwardButton = findViewById(R.id.forwardButton_ID);
            mStopButton = findViewById(R.id.stopButton_ID);
            mRefreshButton = findViewById(R.id.refreshButon_ID);
            mHomeButton = findViewById(R.id.homeButton_ID);
            //  mGoButton = findViewById(R.id.go_Button_ID);
            //   mPopUPButton= findViewById(R.id.popUpPanel_ID);

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


            mUrlText.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        try {
                            if(!MyNetworkState.connectionAvailable(MainActivity.this)){
                                Toasty.error(MainActivity.this, "Check Connection", Toasty.LENGTH_SHORT).show();
                            }else {

                                String url = mUrlText.getText().toString();
                                if (url.contains(".")) {

                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethodManager.hideSoftInputFromWindow(mUrlText.getWindowToken(), 0);

                                    mWebView.loadUrl("https://" +url);
                                    mUrlText.setText("");
                                }else {
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethodManager.hideSoftInputFromWindow(mUrlText.getWindowToken(), 0);
                                    mWebView.loadUrl("https://www.google.com/search?q=" +url);
                                    mUrlText.setText("");
                                }
                            }

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                    return false;
                }
            });


//                mGoButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        try {
//                            if(!MyNetworkState.connectionAvailable(MainActivity.this)){
//                                Toasty.error(MainActivity.this, "Check Connection", Toasty.LENGTH_SHORT).show();
//                            }else {
//
//                                String url = mUrlText.getText().toString();
//                                if (url.contains(".")) {
//
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                    inputMethodManager.hideSoftInputFromWindow(mUrlText.getWindowToken(), 0);
//
//                                    mWebView.loadUrl("https://" +url);
//                                    mUrlText.setText("");
//                                }else {
//                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                    inputMethodManager.hideSoftInputFromWindow(mUrlText.getWindowToken(), 0);
//                                    mWebView.loadUrl("https://www.google.com/search?q=" +url);
//                                    mUrlText.setText("");
//                                }
//                            }
//
//                        }catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

//                mPopUPButton.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        PopupMenu popup = new PopupMenu(MainActivity.this, mPopUPButton);
//
//                        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
//
//
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            public boolean onMenuItemClick(MenuItem item) {
//                                Toasty.success(MainActivity.this,"You Clicked : " + item.getTitle(), Toasty.LENGTH_SHORT).show();
//                                return true;
//                            }
//                        });
//
//                        popup.show();
//                    }
//                });

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
                    Toasty.info(MainActivity.this,"HOME : GOOGLE.COM",Toasty.LENGTH_SHORT).show();
                }
            });


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


    private static final String ADAPTER_STATE_EXTRA = State.class.getName() + "::%s::AdapterState";


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
                    case R.id.remove_tab_menu_item:
                        Tab selectedTab = tabSwitcher.getSelectedTab();

                        if (selectedTab != null) {
                            tabSwitcher.removeTab(selectedTab);
                        }

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
                    case R.id.clear_tabs_menu_item:
                        tabSwitcher.clear();
                        return true;
                    case R.id.settings_menu_item:

                    default:
                        return false;
                }
            }

        };
    }


    private void inflateMenu() {
        tabSwitcher
                .inflateToolbarMenu(tabSwitcher.getCount() > 0 ? R.menu.tab_switcher : R.menu.popup_menu,
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

        for (int i = 0; i < TAB_COUNT; i++) {
            tabSwitcher.addTab(createTab(i));
        }

        tabSwitcher.showAddTabButton(createAddTabButtonListener());
        tabSwitcher.setToolbarNavigationIcon(R.drawable.ic_plus_24dp, createAddTabListener());
        TabSwitcher.setupWithMenu(tabSwitcher, createTabSwitcherButtonListener());
        inflateMenu();
    }

}