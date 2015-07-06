package com.dxjia.doubantop.fragments;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.datas.Favorites;
import com.dxjia.doubantop.datas.beans.BeansUtils;
import com.dxjia.doubantop.datas.beans.MovieInfoBean;
import com.dxjia.doubantop.datas.beans.MovieMajorInfos;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by djia on 15-6-23.
 */
public class DetailsFragment extends BaseFragment {


    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @InjectView(R.id.image_expland)
    ImageView mImageView;

    @InjectView(R.id.details_app_bar)
    AppBarLayout mAppBar;


    @InjectView(R.id.toolbar_flexible_space_with_image)
    Toolbar mToolbar;

    private DetailsContentFragment mDetailsContentFragment;

    private String mId;
    private String mTitle;
    private String mImageUri;

    private int mCastsCount;
    private String[] mCastsIds;
    private String[] mCastsImages;
    private String mDirectorId;
    private String mDirectorImage;
    private String mAverager;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailsFragment.
     */
    public static DetailsFragment newInstance(MovieMajorInfos movieMajorInfos) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        collectArgs(args, movieMajorInfos);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mId = args.getString(BeansUtils.ARG_ID);
            mTitle = args.getString(BeansUtils.ARG_TITLE);
            mImageUri = args.getString(BeansUtils.ARG_IMAGE_URI);

            mCastsCount = args.getInt(BeansUtils.ARG_CASTS_COUNT);
            mCastsIds = args.getStringArray(BeansUtils.ARG_CASTS_IDS);
            mCastsImages = args.getStringArray(BeansUtils.ARG_CASTS_IMAGES);
            mDirectorId = args.getString(BeansUtils.ARG_DIRECTOR_ID);
            mDirectorImage = args.getString(BeansUtils.ARG_DIRECTOR_IMAGE);
            mAverager = args.getString(BeansUtils.ARG_MOVIE_SCORE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupDetailContentView(view);
        setupToolbarMenu();
        return view;
    }

    /**
     * 加载toolbar menu
     */
    private void setupToolbarMenu() {
        Menu menu = mToolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        mToolbar.inflateMenu(R.menu.details_menu);

        updateStarIcon();

        /**
         * 设置toolbar上的search action响应
         */
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItem = item.getItemId();
                switch (menuItem) {
                    case R.id.action_collect:
                        toggleStarStatus();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 判断是否被收藏
     */
    private boolean isStared() {
        if (TextUtils.isEmpty(mId)) {
            return false;
        }

        List<Favorites> stars = Favorites.filterByMoiveId(mId);
        if (stars.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 更新收藏状态
     */
    private void updateStarIcon() {
        Menu menu = mToolbar.getMenu();
        if (menu != null) {
            MenuItem menuItem = menu.getItem(0);
            if (isStared()) {
                menuItem.setIcon(R.mipmap.ic_stared);
            } else {
                menuItem.setIcon(R.mipmap.ic_unstared);
            }
        }
    }

    private void toggleStarStatus() {
        List<Favorites> stars = Favorites.filterByMoiveId(mId);
        Menu menu = mToolbar.getMenu();
        MenuItem menuItem = menu.getItem(0);

        if (stars == null || stars.size() == 0) {
            if (menu != null) {
                menuItem.setIcon(R.mipmap.ic_stared);
            }
            Favorites favorites = new Favorites();
            favorites.fillData(mId, mTitle, mImageUri, mCastsCount,
                    mCastsIds, mCastsImages, mDirectorId, mDirectorImage, mAverager);
            favorites.save();
        } else {
            Favorites star = stars.get(0);
            star.delete();
            if (menu != null) {
                menuItem.setIcon(R.mipmap.ic_unstared);
            }
        }
    }

    private AppBarLayout.OnOffsetChangedListener mOffsetChangedListener =
                    new AppBarLayout.OnOffsetChangedListener() {
                        @Override
                        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                            if (mDetailsContentFragment != null) {
                                if (i == 0) {
                                    mDetailsContentFragment.setRefreshEnable(true);
                                } else {
                                    mDetailsContentFragment.setRefreshEnable(false);
                                }
                            }
                        }
                    };

    private void setupDetailContentView(View view) {
        //details_container
        FragmentManager fm = getChildFragmentManager();
        mDetailsContentFragment =
                            DetailsContentFragment.newInstance(mId, mCastsCount, mCastsIds,
                                        mCastsImages, mDirectorId, mDirectorImage, mAverager);
        fm.beginTransaction()
                .replace(R.id.details_container, mDetailsContentFragment, "DetailsContentFragment")
                .commit();
        fm.executePendingTransactions();

        mAppBar.addOnOffsetChangedListener(mOffsetChangedListener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCollapsingToolbar.setTitle(getTitle());
        Picasso.with(getActivity())
                .load(mImageUri)
                .resizeDimen(R.dimen.image_width, R.dimen.image_height)
                .centerCrop()
                .into(mImageView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAppBar.removeOnOffsetChangedListener(mOffsetChangedListener);
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar_flexible_space_with_image;
    }

    @Override
    protected String getTitle() {
        return mTitle;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.details_fragment;
    }

    /**
     * 从MovieInfoBean实例中搜集必要信息，写入Bundle
     * @param args
     * @param movieMajorInfos
     */
    private static void collectArgs(Bundle args, MovieMajorInfos movieMajorInfos) {
        args.putString(BeansUtils.ARG_ID, movieMajorInfos.getMovieId());
        args.putString(BeansUtils.ARG_TITLE, movieMajorInfos.getMovieTitle());
        args.putString(BeansUtils.ARG_IMAGE_URI, movieMajorInfos.getMovieImageUri());
        args.putInt(BeansUtils.ARG_CASTS_COUNT, movieMajorInfos.getCastsCount());
        args.putStringArray(BeansUtils.ARG_CASTS_IDS, movieMajorInfos.getCastsIds());
        args.putStringArray(BeansUtils.ARG_CASTS_IMAGES, movieMajorInfos.getCastsImages());
        args.putString(BeansUtils.ARG_DIRECTOR_ID, movieMajorInfos.getDirectorId());
        args.putString(BeansUtils.ARG_DIRECTOR_IMAGE, movieMajorInfos.getDirectorImage());
        args.putString(BeansUtils.ARG_MOVIE_SCORE, movieMajorInfos.getMovieScore());
    }
}
