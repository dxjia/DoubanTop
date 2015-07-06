package com.dxjia.doubantop;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dxjia.doubantop.datas.beans.MovieInfoBean;
import com.dxjia.doubantop.datas.beans.MovieMajorInfos;
import com.dxjia.doubantop.fragments.BaseFragment;
import com.dxjia.doubantop.fragments.ContainerFragment;
import com.dxjia.doubantop.fragments.DetailsFragment;
import com.dxjia.doubantop.fragments.FavoritesFragment;
import com.dxjia.doubantop.fragments.FragmentsControler;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.navigation_view)
    NavigationView mNavigationView;

    private DrawerLayout.DrawerListener mDrawerListener;

    private ActionBarDrawerToggle mDrawerToggle;

    private NavigationView.OnNavigationItemSelectedListener mNavigationViewListener;

    private int mCurrentFunc = 0;

    private static FragmentsControler mFragmentsControler;

    private boolean mDetailShowing = false;

    private boolean mFavoritesShowing = false;

    private ContainerFragment mContainerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // 初始化toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // 初始化drawer
        initDrawerListener();
        mDrawerLayout.setDrawerListener(mDrawerListener);
        mDrawerToggle
                = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();

        initNavigationViewListener();
        mNavigationView.setNavigationItemSelectedListener(mNavigationViewListener);

        initNavigator();
        mContainerFragment = ContainerFragment.newInstance();
        setNewRootFragment(mContainerFragment);
    }

    private void initNavigator() {
        if(mFragmentsControler != null) return;
        mFragmentsControler = new FragmentsControler(getSupportFragmentManager(), R.id.container);
    }

    private void setNewRootFragment(BaseFragment fragment){
        if(fragment.hasCustomToolbar()){
            hideActionBar();
        }else {
            showActionBar();
        }
        mFragmentsControler.setRootFragment(fragment);
        mDrawerLayout.closeDrawers();
    }

    private void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.hide();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.show();
    }

    private void initDrawerListener() {
        mDrawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerToggle.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerToggle.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                mDrawerToggle.onDrawerStateChanged(newState);
            }
        };
    }

    private void initNavigationViewListener() {
        mNavigationViewListener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        };
    }

    private void selectDrawerItem(MenuItem menuItem) {
        mDrawerLayout.closeDrawers();
        int id = menuItem.getItemId();
        if (id == R.id.drawer_navigation_menu_collections) {
            if (!mFavoritesShowing) {
                showFavorites();
            }
        } else if (id == R.id.drawer_navigation_menu_github) {
            Uri uri = Uri.parse("https://github.com/dxjia/DoubanTop");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            MainActivity.this.startActivity(intent);
        }
        invalidateOptionsMenu();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if(mDrawerLayout.isDrawerOpen(mNavigationView)) {
                    mDrawerLayout.closeDrawers();
                    return true;
                } else {
                    if (mDetailShowing) {
                        closeDetail();
                        return true;
                    }

                    if (mFavoritesShowing) {
                        closeFavorites();
                        return true;
                    }

                    if (mContainerFragment != null) {
                        if ((mContainerFragment).isSearchShowing()) {
                            (mContainerFragment).toggleSearchViewsState();
                            return true;
                        }
                    }
                }
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void openDrawer(){
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void finish() {
        mFragmentsControler = null;
        super.finish();
    }

    public void showDetail(MovieMajorInfos bean) {
        DetailsFragment detailFragment = DetailsFragment.newInstance(bean);
        mFragmentsControler.goTo(detailFragment);
        mDetailShowing = true;
    }

    public void closeDetail() {
        mFragmentsControler.goOneBack();
        mDetailShowing = false;
    }

    public void showFavorites() {
        FavoritesFragment favoritesFragment = FavoritesFragment.newInstance();
        mFragmentsControler.goTo(favoritesFragment);
        mFavoritesShowing = true;
    }

    public void closeFavorites() {
        mFragmentsControler.goOneBack();
        mFavoritesShowing = false;
    }

}
