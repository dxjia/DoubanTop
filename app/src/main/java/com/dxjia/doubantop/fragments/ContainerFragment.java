package com.dxjia.doubantop.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
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

import com.dxjia.doubantop.AboutActivity;
import com.dxjia.doubantop.R;
import com.dxjia.doubantop.adapters.SearchHistoryAdapter;
import com.dxjia.doubantop.adapters.ViewPagerAdapter;
import com.dxjia.doubantop.datas.SearchHistory;
import com.dxjia.doubantop.net.BaiduVoiceUtils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.InjectView;

/**
 * Created by djia on 15-6-23.
 * The main content container.
 */
public class ContainerFragment extends BaseFragment {
    @InjectView(R.id.tab_layout)
    TabLayout mTabLayout;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

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

    // 用来显示搜索历史
    @InjectView(R.id.search_history_listview)
    ListView mSearchHistoryList;

    private ViewPagerAdapter mViewPagerAdapter;

    private SearchHistoryAdapter mSearchHistoryAdapter;

    private int mCurrentViewPagerPos = 0;

    @InjectView(R.id.app_bar_layout)
    AppBarLayout mAppBar;

    private String mSearchStr;

    private static final int VOICE_ACTIVITY_RESULT_CODE = 0;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContainerFragment.
     */
    public static ContainerFragment newInstance() {
        return new ContainerFragment();
    }

    public ContainerFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getToolbarId() {
        return R.id.toolbar_tabbar;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected String getTitle() {
        return getResources().getString(R.string.content_title);
    }

    @Override
    protected int getLayout() {
        return R.layout.container_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), getActivity());
        mSearchHistoryAdapter = new SearchHistoryAdapter(getMainActivity(), 0, SearchHistory.all());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAppBar.removeOnOffsetChangedListener(mOffsetChangedListener);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabTextColor();
        initSearchViews();
        setupViewPager();
        mAppBar.addOnOffsetChangedListener(mOffsetChangedListener);
        getToolbar().inflateMenu(R.menu.menu_main);
        if (mCurrentViewPagerPos != ViewPagerAdapter.SEARCH_FRAGMENT_POSITON) {
            getToolbar().getMenu().removeItem(R.id.action_search);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setupTabTextColor() {
        int tabTextColor = getResources().getColor(R.color.titleTextColor);
		int tabSelectedTextColor = getResources().getColor(R.color.titleTextColorFocused);
        mTabLayout.setTabTextColors(tabTextColor, tabSelectedTextColor);
    }

    private void setupViewPager() {
        //You could use the normal supportFragmentManger if you like
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//this is the new nice thing ;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentViewPagerPos = position;

                if (mSearchView.getVisibility() == View.VISIBLE && position != 2) {
                    // hide
                    toggleSearchViewsState();
                }
                Toolbar toolbar = getToolbar();
                Menu menu = toolbar.getMenu();
                if (menu != null) {
                    menu.clear();
                }

                toolbar.inflateMenu(R.menu.menu_main);
                if (position == ViewPagerAdapter.SEARCH_FRAGMENT_POSITON) {
                    Fragment fragment = mViewPagerAdapter.getFragment(ViewPagerAdapter.SEARCH_FRAGMENT_POSITON);

                    if (fragment != null && (fragment instanceof SearchContentFragment)) {
                        ((SearchContentFragment)fragment).setParent(ContainerFragment.this);
                    }
                } else {
                    menu = toolbar.getMenu();
                    menu.removeItem(R.id.action_search);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private AppBarLayout.OnOffsetChangedListener mOffsetChangedListener =
            new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    if (mViewPagerAdapter != null) {
                        Fragment fragment = mViewPagerAdapter.getFragment(mCurrentViewPagerPos);
                        if (i == 0) {
                            if (fragment != null) {
                                if (fragment instanceof PagerContentFragment)
                                    ((PagerContentFragment)fragment).setRefreshEnable(true);
                            }
                        } else {
                            if (fragment != null) {
                                if (fragment instanceof PagerContentFragment)
                                    ((PagerContentFragment)fragment).setRefreshEnable(false);
                            }
                        }
                    }
                }
            };
    /**
     * 整个container fragment其实一直包含有search view的布局，
     * 我们只是在不是第3个Pager的时候将他们隐藏而已
     */
    private void initSearchViews() {

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
         * 设置toolbar上的search action响应
         */
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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
                    mSearchHistoryAdapter = new SearchHistoryAdapter(getActivity(), 0, SearchHistory.all());
                    updateSearchCleanOrMicState(false);
                } else {
                    mSearchHistoryAdapter = new SearchHistoryAdapter(getActivity(), 0, SearchHistory.filterByName(mSearchInputEditText.getText().toString()));
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
                    mSearchStr = mSearchInputEditText.getText().toString();
                    addToSearchHistory(mSearchInputEditText.getText().toString());
                    mSearchHistoryList.setVisibility(View.GONE);
                    // 隐藏
                    toggleSearchViewsState();

                    doSearchWork(mSearchStr);
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
                        mSearchStr = mSearchInputEditText.getText().toString();
                        addToSearchHistory(mSearchInputEditText.getText().toString());
                        mSearchHistoryList.setVisibility(View.GONE);
                        // 隐藏
                        toggleSearchViewsState();

                        doSearchWork(mSearchStr);
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
            if (mSearchInputEditText.getText().toString().length() == 0) {

            } else {
                cancelSearchWork();
                mSearchInputEditText.setText("");
                mSearchHistoryList.setVisibility(View.VISIBLE);
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                adjustHeaderLineDivider();
            }
        }
    };

    private void startAboutActivity() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    /**
     * 弹出语音识别控件
     */
    private View.OnClickListener mVoiceCombinedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 先隐藏掉输入法
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(mSearchViewBackMaskView.getWindowToken(), 0);
            // 关闭掉搜索历史列表
            mSearchHistoryList.setVisibility(View.GONE);
            BaiduVoiceUtils.startVoiceRecognizer(ContainerFragment.this, VOICE_ACTIVITY_RESULT_CODE);
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
     * 异步获取搜索结果
     * @param key
     */
    private void doSearchWork(String key) {
        if (TextUtils.isEmpty(key) || mViewPagerAdapter == null) {
            return;
        }

        Fragment fragment = mViewPagerAdapter.getFragment(ViewPagerAdapter.SEARCH_FRAGMENT_POSITON);

        if (fragment instanceof SearchContentFragment) {
            ((SearchContentFragment)fragment).startNewSearch(key);
        }
    }

    /**
     * 取消搜索结果获取
     */
    private void cancelSearchWork() {

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
        Context context = getActivity();
        InputMethodManager inputMethodManager = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        final Animation fade_in = AnimationUtils.loadAnimation(context.getApplicationContext(), android.R.anim.fade_in);
        final Animation fade_out = AnimationUtils.loadAnimation(context.getApplicationContext(), android.R.anim.fade_out);
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

    public boolean isSearchShowing() {
        return mSearchView.getVisibility() == View.VISIBLE;
    }

}
