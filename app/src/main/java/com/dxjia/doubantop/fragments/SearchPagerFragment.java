package com.dxjia.doubantop.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.interfaces.SearchStateToggleListener;
import com.dxjia.doubantop.net.DoubanApiUtils;

import butterknife.InjectView;

/**
 * Created by 德祥 on 2015/7/14.
 */
public class SearchPagerFragment extends BaseFragment {

    @InjectView(R.id.empty_view)
    TextView mEmptyView;

    @InjectView(R.id.search_result_container)
    FrameLayout mSearchResultContainer;

    private String mSearchKey;

    private MovieListFragment mSearchResultFragment;

    private SearchStateToggleListener mSearchStateToggleListener;

    @Override
    protected int getLayout() {
        return R.layout.search_pager_fragment;
    }

    public static SearchPagerFragment newInstance() {
        return new SearchPagerFragment();
    }

    public SearchPagerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchResultFragment = MovieListFragment.newInstance(DoubanApiUtils.API_TYPE_SEARCH);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        updateViews();
    }

    private void initViews() {
        // setup search result container fragment
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.search_result_container, mSearchResultFragment, "SearchContentFragment");
        transaction.commit();

        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchStateToggleListener != null) {
                    mSearchStateToggleListener.toggleSearchState();
                }
            }
        });
    }

    private void updateViews() {
        if (TextUtils.isEmpty(mSearchKey)) {
            mEmptyView.setVisibility(View.VISIBLE);
            mSearchResultContainer.setVisibility(View.INVISIBLE);
        } else {
            mEmptyView.setVisibility(View.INVISIBLE);
            mSearchResultContainer.setVisibility(View.VISIBLE);
        }
    }

    public void setSearchStateToggleListener(SearchStateToggleListener l) {
        mSearchStateToggleListener = l;
    }

    public void startSearch(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        mSearchKey = key;
        updateViews();
        if (mSearchResultFragment != null) {
            mSearchResultFragment.startNewSearch(key);
        }
    }
}
