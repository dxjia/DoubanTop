package com.dxjia.doubantop.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dxjia.doubantop.interfaces.MovieInfoActionsListener;

import butterknife.ButterKnife;

/**
 * Created by 德祥 on 2015/7/13.
 *
 */
public abstract class BaseFragment extends Fragment {

    /**
     * this can let our all fragments call the activity`s methods
     */
    protected MovieInfoActionsListener mMovieInfoActionsListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mMovieInfoActionsListener = (MovieInfoActionsListener) activity;
        } catch (ClassCastException e) {
            Log.d(activity.getClass().getSimpleName(), " make sure that dont need to implent MovieInfoActionsListener!");
        }
    }

    protected MovieInfoActionsListener getActionsListener() {
        return mMovieInfoActionsListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    protected abstract int getLayout();
}
