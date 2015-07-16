package com.dxjia.doubantop.fragments;

import android.os.Bundle;

/**
 * Created by 德祥 on 2015/7/13.
 */
public class MovieListFragment extends SwipeRefreshFragment {

    public static MovieListFragment newInstance(int requestType) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_API_TYPE, requestType);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
