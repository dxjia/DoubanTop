package com.dxjia.doubantop.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.datas.beans.BeansUtils;
import com.dxjia.doubantop.net.DoubanApiHelper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.InjectView;

/**
 * Created by 德祥 on 2015/6/30.
 */
public class SearchContentFragment extends BaseFragment {

    @InjectView(R.id.empty_view)
    TextView mEmptyView;

    @InjectView(R.id.search_result_container)
    FrameLayout mSearchResultContainer;

    private String mSearchKey;

    private PagerContentFragment mPagerContentFragment;
    private ContainerFragment mParent;

    public static SearchContentFragment newInstance() {
        SearchContentFragment fragment = new SearchContentFragment();
        return fragment;
    }

    public static SearchContentFragment newInstance(String search_key) {
        SearchContentFragment fragment = new SearchContentFragment();
        Bundle args = new Bundle();
        args.putString(BeansUtils.ARG_SEARCH_KEY, search_key);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mSearchKey = args.getString(BeansUtils.ARG_SEARCH_KEY);
        } else {
            mSearchKey = "";
        }

        mPagerContentFragment = PagerContentFragment.newInstance(DoubanApiHelper.API_TYPE_SEARCH);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
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
        mPagerContentFragment.setActivity(getMainActivity());
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.search_result_container, mPagerContentFragment, "SearchContentFragment");
        transaction.commit();

        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mParent != null) {
                    mParent.toggleSearchViewsState();
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

    @Override
    protected int getLayout() {
        return R.layout.search_pager_content_fragment;
    }

    public void startNewSearch(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        mSearchKey = key;
        updateViews();
        if (mPagerContentFragment != null) {
            mPagerContentFragment.setSearchKeyValue(key);
        }
    }

    public void setParent(ContainerFragment parent) {
        mParent = parent;
    }

}
