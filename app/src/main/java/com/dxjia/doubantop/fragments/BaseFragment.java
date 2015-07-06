package com.dxjia.doubantop.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dxjia.doubantop.MainActivity;
import com.dxjia.doubantop.R;

import butterknife.ButterKnife;

/**
 * Created by djia on 15-6-23.
 */
public abstract class BaseFragment extends Fragment {
    Toolbar mToolbar;

    public MainActivity getMainActivity(){
        return ((MainActivity) super.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!hasCustomToolbar()) return;
        mToolbar = ButterKnife.findById(view, getToolbarId());
        mToolbar.setTitle(getTitle());
        setToolbarNavigationAction(view);
    }

    // default action
    protected void setToolbarNavigationAction(View view) {
        mToolbar.setNavigationIcon(R.mipmap.ic_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().openDrawer();
            }
        });
    }

    protected int getToolbarId(){
        return R.id.toolbar;
    }

    public boolean hasCustomToolbar(){
        return false;
    }

    protected String getTitle(){
        return getResources().getString(R.string.not_title_set);
    }

    protected abstract int getLayout();

    public Toolbar getToolbar() {
        if (hasCustomToolbar()) {
            return mToolbar;
        }
        return null;
    }

}
