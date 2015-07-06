package com.dxjia.doubantop.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.adapters.FavoritesAdapter;
import com.dxjia.doubantop.datas.Favorites;
import com.dxjia.doubantop.datas.beans.MovieMajorInfos;

import butterknife.InjectView;

/**
 * Created by 德祥 on 2015/7/6.
 */
public class FavoritesFragment extends BaseFragment {

    @InjectView(R.id.favorites_list)
    RecyclerView mFavoritesList;

    private FavoritesAdapter mFavoritesAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavoritesFragment.
     */
    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycleView();
    }

    private void setupRecycleView() {
        if (mFavoritesList == null) return;
        mFavoritesList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mFavoritesList.setLayoutManager(layoutManager);
        mFavoritesList.setItemAnimator(new DefaultItemAnimator());
        mFavoritesAdapter = new FavoritesAdapter(Favorites.all());
        mFavoritesAdapter.setItemClickListener(new FavoritesItemClickListener() {
            @Override
            public void onItemClick(View view, Favorites favorites) {
                MovieMajorInfos movieMajorInfos = new MovieMajorInfos();
                movieMajorInfos.fillDatas(favorites.getMoiveId(), favorites.getMovieTitle(), favorites.getMovieImageUri(),
                        favorites.getCastsCount(), favorites.getCastsIds(), favorites.getCastsImageUris(),
                        favorites.getDirectorId(), favorites.getDirectorImageUri(), favorites.getMovieScore());
                getMainActivity().showDetail(movieMajorInfos);
            }
        });
        mFavoritesList.setAdapter(mFavoritesAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.favorites_fragment;
    }


    // default action
    protected void setToolbarNavigationAction(View view) {
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().closeFavorites();
            }
        });
    }

    protected int getToolbarId(){
        return R.id.favorites_toolbar;
    }

    public boolean hasCustomToolbar(){
        return true;
    }

    protected String getTitle(){
        return getResources().getString(R.string.favorites);
    }


}
