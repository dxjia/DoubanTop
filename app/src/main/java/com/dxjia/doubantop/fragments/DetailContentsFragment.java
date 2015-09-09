package com.dxjia.doubantop.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.datas.beans.BeansUtils;
import com.dxjia.doubantop.datas.beans.MovieMajorInfos;
import com.dxjia.doubantop.datas.beans.entities.SurveyEntity;
import com.dxjia.doubantop.net.DoubanApiUtils;
import com.dxjia.doubantop.net.RetrofitCallback;
import com.dxjia.doubantop.views.PeopleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by 德祥 on 2015/7/14.
 */
public class DetailContentsFragment extends BaseFragment {

    private static final int EVENT_UPDATE_START = 1;
    private static final int EVENT_UPDATE_FAILED = 2;
    private static final int EVENT_UPDATE_DONE = 3;

    @InjectView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.details_content)
    LinearLayout mWholeContentArea;

    // 评分
    @InjectView(R.id.score_area)
    LinearLayout mScoreArea;
    @InjectView(R.id.rating_bar)
    RatingBar mScoreRatingBar;
    @InjectView(R.id.score_text)
    TextView mScoreTextView;

    // 导演
    @InjectView(R.id.director_area)
    LinearLayout mDirectorArea;
    @InjectView(R.id.director)
    PeopleView mDirectorView;

    // 主演
    // 因为豆瓣API的限制，我们只能拿到最多3个主演信息，真抠门。
    private final static int MAX_CASTS_COUNT = 3;
    @InjectView(R.id.casts_area)
    LinearLayout mCastsArea;
    @InjectView(R.id.casts_1)
    PeopleView mCastsView1;
    @InjectView(R.id.casts_2)
    PeopleView mCastsView2;
    @InjectView(R.id.casts_3)
    PeopleView mCastsView3;

    // 内容简介
    @InjectView(R.id.summary_area)
    LinearLayout mSummaryArea;
    @InjectView(R.id.movie_summary)
    TextView mSummaryTextView;
    private String mSummary = "";

    private MovieMajorInfos mMovieInfos = null;

    private UpdateHandler mUpdateHandler;

    @Override
    protected int getLayout() {
        return R.layout.detail_contents_fragment;
    }

    public static DetailContentsFragment newInstance(MovieMajorInfos movieInfos) {
        DetailContentsFragment fragment = new DetailContentsFragment();
        Bundle bundle = new Bundle();

        if (movieInfos != null) {
            bundle.putParcelable(BeansUtils.MOVIE_MAJOR_INFOS_KEY, movieInfos);
            bundle.setClassLoader(movieInfos.getClass().getClassLoader());
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public DetailContentsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mMovieInfos = args.getParcelable(BeansUtils.MOVIE_MAJOR_INFOS_KEY);
        }

        mUpdateHandler = new UpdateHandler();
        if (mMovieInfos != null) {
            mUpdateHandler.sendEmptyMessageDelayed(EVENT_UPDATE_START, 100);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSwipRefresh();
        // hide first, setup back after we fetched the summary though http
        hideAllViews();
    }

    public void setRefreshEnable(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    private void initSwipRefresh() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cleanViews();
                mUpdateHandler.sendEmptyMessage(EVENT_UPDATE_START);
            }
        });
    }

    private void setupViews() {
        if (mMovieInfos == null) {
            return;
        }

        mWholeContentArea.setVisibility(View.VISIBLE);

        updateScore(mMovieInfos.getMovieScore());
        updateDirector(mMovieInfos.getDirectorId(), mMovieInfos.getDirectorImage());
        updateCasts(mMovieInfos.getCastsCount(), mMovieInfos.getCastsIds(), mMovieInfos.getCastsImages());
        updateMovieSummary(mSummary);
    }

    private void hideAllViews() {
        mWholeContentArea.setVisibility(View.GONE);
    }

    private void updateScore(String scoreStr) {
        if (TextUtils.isEmpty(scoreStr)) {
            mScoreArea.setVisibility(View.GONE);
            return;
        }

        mScoreArea.setVisibility(View.VISIBLE);
        mScoreRatingBar.setRating((float) ((double) (Float.valueOf(scoreStr)) / (double) (2)));
        mScoreTextView.setText(scoreStr);
    }

    private void updateDirector(String directorId, String directorImage) {
        if (TextUtils.isEmpty(directorId) || TextUtils.isEmpty(directorImage)) {
            mDirectorArea.setVisibility(View.GONE);
            return;
        }

        mDirectorArea.setVisibility(View.VISIBLE);
        mDirectorView.setPeopleInfo(directorId, directorImage);
    }

    private void updateCasts(int castsCount, String[] castsIds, String[] castsImages) {
		if (castsCount == 0 || castsIds == null) {
			mCastsArea.setVisibility(View.GONE);
			return;
		}
        int castIdsLen = castsIds.length;
        int castImagesLen = castsImages != null ? castsImages.length : 0;
        int temp, count;
        temp = (castsCount > castIdsLen) ? castIdsLen : castsCount;
        count = (temp > castImagesLen) ? castImagesLen : temp;
        if (count > MAX_CASTS_COUNT) {
            count = MAX_CASTS_COUNT;
        }
        if (count == 0) {
            mCastsArea.setVisibility(View.GONE);
            return;
        }

        mCastsArea.setVisibility(View.VISIBLE);
        List<PeopleView> viewList = new ArrayList<>();
        viewList.add(mCastsView1);
        viewList.add(mCastsView2);
        viewList.add(mCastsView3);
        int i = 0;
        for (; i < count; i++) {
            viewList.get(i).setVisibility(View.VISIBLE);
            viewList.get(i).setPeopleInfo(castsIds[i], castsImages[i]);
        }

        for (; i < MAX_CASTS_COUNT; i++) {
            viewList.get(i).setVisibility(View.GONE);
        }
    }

    private void updateMovieSummary(String summary) {
        if(TextUtils.isEmpty(summary)) {
            mSummaryArea.setVisibility(View.GONE);
            return;
        }

        mSummaryArea.setVisibility(View.VISIBLE);
        mSummaryTextView.setText(summary);
    }

    private void cleanViews() {
        mScoreTextView.setText("");
        mDirectorView.cleanDetails();
        mCastsView1.cleanDetails();
        mCastsView2.cleanDetails();
        mCastsView3.cleanDetails();
        mSummaryTextView.setText("");
        hideAllViews();
    }

    /**
     * UI update handler
     */
    private class UpdateHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_UPDATE_START:
                    mSwipeRefreshLayout.setRefreshing(true);
                    int movieId = Integer.valueOf(mMovieInfos.getMovieId());
                    // 异步请求数据
                    DoubanApiUtils.getMovieApiService().getMovieSubject(movieId, DoubanApiUtils.API_KEY,
                            new RetrofitCallback<>(mUpdateHandler, EVENT_UPDATE_DONE, EVENT_UPDATE_FAILED, SurveyEntity.class));
                    break;
                case EVENT_UPDATE_FAILED:
                    mSummary = "";
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case EVENT_UPDATE_DONE:
                    if (msg.obj != null) {
                        SurveyEntity survey = (SurveyEntity)(msg.obj);
                        mSummary = survey.getSummary();
                        mSwipeRefreshLayout.setRefreshing(false);
                        setupViews();
                    }

                    break;
            }

            super.handleMessage(msg);
        }
    }
}
