package com.dxjia.doubantop.activitys;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.adapters.FavoritesAdapter;
import com.dxjia.doubantop.datas.Favorites;
import com.dxjia.doubantop.datas.beans.BeansUtils;
import com.dxjia.doubantop.datas.beans.MovieMajorInfos;
import com.dxjia.doubantop.interfaces.FavoritesItemClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavoritesActivity extends AppCompatActivity implements FavoritesItemClickListener {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.favorites_list)
    RecyclerView mFavoritesList;

    private FavoritesAdapter mFavoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupRecycleView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupRecycleView() {
        if (mFavoritesList == null) return;
        mFavoritesList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mFavoritesList.setLayoutManager(layoutManager);
        mFavoritesList.setItemAnimator(new DefaultItemAnimator());
        mFavoritesAdapter = new FavoritesAdapter(Favorites.all());
        mFavoritesAdapter.setItemClickListener(this);
        mFavoritesList.setAdapter(mFavoritesAdapter);
    }

    @Override
    public void onItemClick(View view, Favorites favorites) {
        MovieMajorInfos movieMajorInfos = new MovieMajorInfos();
        movieMajorInfos.fillDatas(favorites.getMoiveId(), favorites.getMovieTitle(), favorites.getMovieImageUri(),
                favorites.getCastsCount(), favorites.getCastsIds(), favorites.getCastsImageUris(),
                favorites.getDirectorId(), favorites.getDirectorImageUri(), favorites.getMovieScore());
        showDetail(movieMajorInfos);
    }

    private void showDetail(MovieMajorInfos movieMajorInfos) {
        Intent intent = new Intent(FavoritesActivity.this, DetailActivity.class);
        intent.putExtra(BeansUtils.MOVIE_MAJOR_INFOS_KEY, movieMajorInfos);
        startActivity(intent);
    }
}
