package com.dxjia.doubantop.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.datas.Favorites;
import com.dxjia.doubantop.datas.beans.BeansUtils;
import com.dxjia.doubantop.datas.beans.MovieMajorInfos;
import com.dxjia.doubantop.fragments.DetailContentsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @InjectView(R.id.image_expland)
    ImageView mMovieImageView;

    @InjectView(R.id.details_app_bar)
    AppBarLayout mAppBar;

    private DetailContentsFragment mDetailContentsFragment;

    private MovieMajorInfos mMovieInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        if (intent == null && savedInstanceState == null) {
            return;
        }
        if (intent != null) {
            mMovieInfos = intent.getParcelableExtra(BeansUtils.MOVIE_MAJOR_INFOS_KEY);
        } else {
            mMovieInfos = savedInstanceState.getParcelable(BeansUtils.MOVIE_MAJOR_INFOS_KEY);
        }
        if (mMovieInfos == null) {
            return;
        }

        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupContentsView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMovieInfos != null) {
            mCollapsingToolbar.setTitle(mMovieInfos.getMovieTitle());
            Picasso.with(DetailActivity.this)
                    .load(mMovieInfos.getMovieImageUri())
                    .resizeDimen(R.dimen.image_width, R.dimen.image_height)
                    .centerCrop()
                    .into(mMovieImageView);
        }
        mAppBar.addOnOffsetChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        updateStarIcon(menu.getItem(0));
        return true;
    }

    private void setupContentsView() {
        //details_container
        FragmentManager fm = getSupportFragmentManager();
        mDetailContentsFragment =
                DetailContentsFragment.newInstance(mMovieInfos);
        fm.beginTransaction()
                .replace(R.id.details_container, mDetailContentsFragment, "DetailContentsFragment")
                .commit();
        fm.executePendingTransactions();
    }


    /**
     * 判断是否被收藏
     */
    private boolean isStared() {
        if (mMovieInfos == null) {
            return false;
        }

        String id = mMovieInfos.getMovieId();
        if (TextUtils.isEmpty(id)) {
            return false;
        }

        List<Favorites> stars = Favorites.filterByMoiveId(id);
        if (stars.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 更新收藏状态
     */
    private void updateStarIcon(MenuItem menuItem) {
        if (menuItem != null) {
            if (isStared()) {
                menuItem.setIcon(R.mipmap.ic_stared);
            } else {
                menuItem.setIcon(R.mipmap.ic_unstared);
            }
        }
    }

    private void toggleStarStatus(MenuItem menuItem) {
        if (mMovieInfos == null) {
            return;
        }

        List<Favorites> stars = Favorites.filterByMoiveId(mMovieInfos.getMovieId());

        if (stars == null || stars.size() == 0) {
            if (menuItem != null) {
                menuItem.setIcon(R.mipmap.ic_stared);
            }
            Favorites favorites = new Favorites();
            favorites.fillDatas(mMovieInfos);
            favorites.save();
        } else {
            Favorites star = stars.get(0);
            star.delete();
            if (menuItem != null) {
                menuItem.setIcon(R.mipmap.ic_unstared);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mAppBar.removeOnOffsetChangedListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_collect:
                toggleStarStatus(item);
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mDetailContentsFragment != null) {
            if (i == 0) {
                mDetailContentsFragment.setRefreshEnable(true);
            } else {
                mDetailContentsFragment.setRefreshEnable(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        if (mMovieInfos != null) {
            out.putParcelable(BeansUtils.MOVIE_MAJOR_INFOS_KEY, mMovieInfos);
            out.setClassLoader(mMovieInfos.getClass().getClassLoader());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_MENU){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
