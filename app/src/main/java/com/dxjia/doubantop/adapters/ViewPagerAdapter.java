package com.dxjia.doubantop.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.fragments.MovieListFragment;
import com.dxjia.doubantop.fragments.SearchPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djia on 15-6-23.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public final static int VIEW_PAGER_COUNT = 3;

    public final static int US_BOX_PAGER_POSITON = 0;
    public final static int TOP_PAGER_POSTION = 1;
    public final static int SEARCH_FRAGMENT_POSITON = 2;

    private Context mContext;


    List<Fragment> mFragments = new ArrayList<>() ;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        initFragments();
    }

    private void initFragments() {
        mFragments.add(MovieListFragment.newInstance(0));
        mFragments.add(MovieListFragment.newInstance(1));
        mFragments.add(SearchPagerFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = mContext.getString(R.string.not_title_set);
        switch (position) {
            case 0:
                title = mContext.getString(R.string.movie_us_box_pager_title);
                break;
            case 1:
                title = mContext.getString(R.string.movie_top_pager_title);
                break;
            case 2:
                title = mContext.getString(R.string.search_pager_title);
                break;
        }
        return title;
    }

    public Fragment getFragment(int position) {
        return mFragments.get(position);
    }

}
