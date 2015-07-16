package com.dxjia.doubantop.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.speech.SpeechRecognizer;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.adapters.SearchHistoryAdapter;
import com.dxjia.doubantop.adapters.ViewPagerAdapter;
import com.dxjia.doubantop.datas.SearchHistory;
import com.dxjia.doubantop.datas.beans.BeansUtils;
import com.dxjia.doubantop.datas.beans.MovieMajorInfos;
import com.dxjia.doubantop.fragments.SearchPagerFragment;
import com.dxjia.doubantop.interfaces.MovieInfoActionsListener;
import com.dxjia.doubantop.interfaces.SearchStateToggleListener;
import com.dxjia.doubantop.net.BaiduVoiceUtils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, SearchStateToggleListener,
        MovieInfoActionsListener {

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.tab_bar)
    TabLayout mTabLayout;

    @InjectView(R.id.nvView)
    NavigationView mNavigationView;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;

    private ActionBarDrawerToggle mDrawerToggle;

    private int mCurrentPagerPosition = ViewPagerAdapter.US_BOX_PAGER_POSITON;
    private final static String CURRENT_PAGER_POSITION_KEY = "pager_pos";

    // 用来显示搜索历史
    @InjectView(R.id.search_history_listview)
    ListView mSearchHistoryList;
    private SearchHistoryAdapter mSearchHistoryAdapter;
    /**
     * Search views
     */
    // divider
    @InjectView(R.id.line_divider)
    View mHistoryListHeaderLineDivider;
    // search backgroud mask
    @InjectView(R.id.view_search_mask)
    RelativeLayout mSearchViewBackMaskView;
    // search view 主体
    @InjectView(R.id.search_body)
    CardView mSearchView;
    @InjectView(R.id.image_search_back)
    ImageView mSearchViewHide;
    @InjectView(R.id.clearSearch)
    ImageView mCleanSearchInputText;
    @InjectView(R.id.search_text_input_edit)
    EditText mSearchInputEditText;
    @InjectView(R.id.do_search)
    ImageView mSearchBtn;

    private static final int VOICE_ACTIVITY_RESULT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        if (savedInstanceState != null) {
            mCurrentPagerPosition = savedInstanceState.getInt(CURRENT_PAGER_POSITION_KEY);
        }

        setupToolbar();

        setupDrawerItemSelectListener();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        initSearchViews();

        setupTabBar();
        setupViewPager();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /**
         * 设置toolbar上的search action响应
         */
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItem = item.getItemId();
                switch (menuItem) {
                    case R.id.action_about:
                        startAboutActivity();
                        break;
                    case R.id.action_search:
                        toggleSearchViewsState();
                        adjustHeaderLineDivider();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void setupTabBar() {
        int tabTextColor = getResources().getColor(R.color.titleTextColor);
        int tabSelectedTextColor = getResources().getColor(R.color.titleTextColorFocused);
        mTabLayout.setTabTextColors(tabTextColor, tabSelectedTextColor);
    }

    private void setupViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//this is the new nice thing ;
        mViewPager.addOnPageChangeListener(this);
    }

    private void setupDrawerItemSelectListener() {
        if(mNavigationView == null) {
            return;
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerItemSelected(menuItem);
                return true;
            }
        });
    }

    private void drawerItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.drawer_navigation_menu_collections:
                startFavoritesActivity();
                break;
            case R.id.drawer_navigation_menu_github:
                startGitHubWeb();
                break;
        }

        mDrawerLayout.closeDrawers();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGER_POSITION_KEY, mCurrentPagerPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.removeItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_about:
                startAboutActivity();
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if(mDrawerLayout.isDrawerOpen(mNavigationView)) {
                    mDrawerLayout.closeDrawers();
                    return true;
                } else {
                    if (isSearchShowing()) {
                        toggleSearchViewsState();
                        return true;
                    }
                }
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        mCurrentPagerPosition = position;

        if (mToolbar == null) {
            return;
        }

        if (mSearchView.getVisibility() == View.VISIBLE && position != 2) {
            // hide
            toggleSearchViewsState();
        }

        Menu menu = mToolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }

        mToolbar.inflateMenu(R.menu.menu_main);
        if (position == ViewPagerAdapter.SEARCH_FRAGMENT_POSITON) {
            Fragment fragment = mViewPagerAdapter.getFragment(position);

            if (fragment != null && (fragment instanceof SearchPagerFragment)) {
                ((SearchPagerFragment)fragment).setSearchStateToggleListener(this);
            }
        } else {
            menu = mToolbar.getMenu();
            menu.removeItem(R.id.action_search);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 整个container fragment其实一直包含有search view的布局，
     * 我们只是在不是第3个Pager的时候将他们隐藏而已
     */
    private void initSearchViews() {

        mSearchHistoryAdapter = new SearchHistoryAdapter(MainActivity.this, 0, SearchHistory.all());
        mSearchHistoryList.setAdapter(mSearchHistoryAdapter);

        /**
         * 点击背景关闭search views
         */
        mSearchViewBackMaskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSearchViewsState();
            }
        });

        /**
         * 设置搜索历史点击后的动作
         */
        mSearchHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchHistory searchHistory = mSearchHistoryAdapter.getItem(position);
                mSearchInputEditText.setText(searchHistory.getName());
                mSearchHistoryList.setVisibility(View.GONE);
                // 隐藏
                toggleSearchViewsState();

                doSearchWork(searchHistory.getName());
            }
        });

        /**
         * 设置搜索编辑框文字发生变化后的历史列表显示
         */
        mSearchInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mSearchInputEditText.getText().toString().length() == 0) {
                    mSearchHistoryAdapter = new SearchHistoryAdapter(MainActivity.this, 0, SearchHistory.all());
                    updateSearchCleanOrMicState(false);
                } else {
                    mSearchHistoryAdapter = new SearchHistoryAdapter(MainActivity.this, 0, SearchHistory.filterByName(mSearchInputEditText.getText().toString()));
                    updateSearchCleanOrMicState(true);
                }
                mSearchHistoryList.setAdapter(mSearchHistoryAdapter);
                adjustHeaderLineDivider();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /**
         * 设置清除编辑框文字listener
         * 起始默认设置为voice search功能
         * 当编辑框文字有变化时更新为 clean listener
         */
        mCleanSearchInputText.setOnClickListener(mVoiceCombinedClickListener);

        /**
         * 搜索键
         */
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchInputEditText.getText().toString().trim().length() > 0) {
                    String searchStr = mSearchInputEditText.getText().toString();
                    addToSearchHistory(searchStr);
                    mSearchHistoryList.setVisibility(View.GONE);
                    // 隐藏
                    toggleSearchViewsState();
                    doSearchWork(searchStr);
                }
            }
        });
        /**
         * 关闭 search view 按钮listener
         */
        mSearchViewHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSearchViewsState();
            }
        });

        /**
         * 设置输入法的 搜索 按钮响应
         */
        mSearchInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mSearchInputEditText.getText().toString().trim().length() > 0) {
                        String searchStr = mSearchInputEditText.getText().toString();
                        addToSearchHistory(searchStr);
                        mSearchHistoryList.setVisibility(View.GONE);
                        // 隐藏
                        toggleSearchViewsState();
                        doSearchWork(searchStr);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private View.OnClickListener mCleanInputClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSearchInputEditText.getText().toString().length() != 0) {
                mSearchInputEditText.setText("");
                mSearchHistoryList.setVisibility(View.VISIBLE);
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                adjustHeaderLineDivider();
            }
        }
    };

    /**
     * 更新search控件上右侧的 x 按钮或者 语音识别按钮
     * 它俩做在同一个imagebtn上，就是根据edit框里有没有文字来动态更新
     * @param showCleanBtn false for show mic image, true for show X image
     */
    private void updateSearchCleanOrMicState(boolean showCleanBtn) {
        if (showCleanBtn) {
            mCleanSearchInputText.setImageResource(R.mipmap.ic_close);
            mCleanSearchInputText.setOnClickListener(mCleanInputClickListener);
        } else {
            mCleanSearchInputText.setImageResource(R.mipmap.ic_keyboard_voice);
            mCleanSearchInputText.setOnClickListener(mVoiceCombinedClickListener);
        }
    }


    private void adjustHeaderLineDivider() {
        if (mSearchHistoryAdapter.getCount() == 0) {
            mHistoryListHeaderLineDivider.setVisibility(View.GONE);
        } else {
            mHistoryListHeaderLineDivider.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 弹出语音识别控件
     */
    private View.OnClickListener mVoiceCombinedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 先隐藏掉输入法
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(mSearchViewBackMaskView.getWindowToken(), 0);
            // 关闭掉搜索历史列表
            mSearchHistoryList.setVisibility(View.GONE);
            BaiduVoiceUtils.startVoiceRecognizer(MainActivity.this, VOICE_ACTIVITY_RESULT_CODE);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSearchHistoryList.setVisibility(View.VISIBLE);
        if (requestCode == VOICE_ACTIVITY_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            Bundle results = data.getExtras();
            if (results == null) {
                return;
            }
            ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (nbest == null || nbest.size() == 0) {
                return;
            }
            // now we just get the first result
            String result = nbest.get(0);
            mSearchInputEditText.setText(result);
            mSearchInputEditText.setSelection(result.length());
        }
    }


    /**
     * 将 搜索文字 加入到搜索历史数据库
     */
    private void addToSearchHistory(String key) {
        SearchHistory recentLog = new SearchHistory();
        recentLog.setName(key);
        recentLog.setDate(new Date());
        recentLog.save();
        mSearchHistoryAdapter.add(recentLog);
        mSearchHistoryAdapter.notifyDataSetChanged();
    }


    /**
     * 变换 search views的显示与隐藏
     */
    public void toggleSearchViewsState() {
        InputMethodManager inputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        final Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        final Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        if (mSearchView.getVisibility() == View.VISIBLE) {
            // 隐藏
            inputMethodManager.hideSoftInputFromWindow(mSearchViewBackMaskView.getWindowToken(), 0);
            mSearchViewBackMaskView.startAnimation(fade_out);
            fade_out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mSearchViewBackMaskView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mSearchView.setVisibility(View.GONE);
            mSearchInputEditText.setText("");
            mSearchInputEditText.clearFocus();
            mSearchView.setEnabled(false);

        } else {
            // 显示
            mSearchViewBackMaskView.setVisibility(View.VISIBLE);
            mSearchViewBackMaskView.startAnimation(fade_in);
            mSearchView.setVisibility(View.VISIBLE);
            mSearchView.setEnabled(true);
            mSearchHistoryList.setVisibility(View.VISIBLE);
            mSearchInputEditText.requestFocus();
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    /**
     * 异步获取搜索结果
     * @param key 搜索关键字
     */
    private void doSearchWork(String key) {
        if (TextUtils.isEmpty(key) || mViewPagerAdapter == null) {
            return;
        }

        Fragment fragment = mViewPagerAdapter.getFragment(ViewPagerAdapter.SEARCH_FRAGMENT_POSITON);

        if (fragment instanceof SearchPagerFragment) {
            ((SearchPagerFragment)fragment).startSearch(key);
        }
    }

    private void startAboutActivity() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    private void startFavoritesActivity() {
        Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
        startActivity(intent);
    }

    private void startGitHubWeb() {
        Uri uri = Uri.parse("https://github.com/dxjia/DoubanTop");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private boolean isSearchShowing() {
        return (mSearchView != null) && (mSearchView.getVisibility() == View.VISIBLE);
    }

    @Override
    public void toggleSearchState() {
        toggleSearchViewsState();
    }

    @Override
    public void showDetails(MovieMajorInfos movieInfos) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(BeansUtils.MOVIE_MAJOR_INFOS_KEY, movieInfos);
        startActivity(intent);
    }
}
