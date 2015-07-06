package com.dxjia.doubantop.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.adapters.DetailsItemAdapter;
import com.dxjia.doubantop.datas.beans.BeansUtils;
import com.dxjia.doubantop.datas.beans.entities.SurveyEntity;
import com.dxjia.doubantop.net.DoubanApiHelper;
import com.dxjia.doubantop.net.HttpUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by djia on 15-6-25.
 */
public class DetailsContentFragment extends Fragment {
    private static final int EVENT_UPDATE_START = 1;
    private static final int EVENT_UPDATE_FAILED = 2;
    private static final int EVENT_UPDATE_DONE = 3;

    @InjectView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @InjectView(R.id.details_list)
    RecyclerView mRecyclerView;

    private DetailsItemAdapter mItemAdapter;
    private UpdateHandler mUpdateHandler;

    private String mMovieId;
    private int mCastsCount;
    private String[] mCastsIds;
    private String[] mCastsImages;
    private String mDirectorId;
    private String mDirectorImage;
    private String mScore;
    private String mSummary;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailsContentFragment.
     */
    public static DetailsContentFragment newInstance(String movieId,
                                                     int castsCount, String[] castsIds, String[] castsImages,
                                                     String directorId, String directorImage, String score) {
        DetailsContentFragment fragment = new DetailsContentFragment();
        Bundle args = new Bundle();
        args.putString(BeansUtils.ARG_ID, movieId);
        args.putInt(BeansUtils.ARG_CASTS_COUNT, castsCount);
        args.putStringArray(BeansUtils.ARG_CASTS_IDS, castsIds);
        args.putStringArray(BeansUtils.ARG_CASTS_IMAGES, castsImages);
        args.putString(BeansUtils.ARG_DIRECTOR_ID, directorId);
        args.putString(BeansUtils.ARG_DIRECTOR_IMAGE, directorImage);
        args.putString(BeansUtils.ARG_MOVIE_SCORE, score);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mMovieId = args.getString(BeansUtils.ARG_ID);
            mCastsCount = args.getInt(BeansUtils.ARG_CASTS_COUNT);
            mCastsIds = args.getStringArray(BeansUtils.ARG_CASTS_IDS);
            mCastsImages = args.getStringArray(BeansUtils.ARG_CASTS_IMAGES);
            mDirectorId = args.getString(BeansUtils.ARG_DIRECTOR_ID);
            mDirectorImage = args.getString(BeansUtils.ARG_DIRECTOR_IMAGE);
            mScore = args.getString(BeansUtils.ARG_MOVIE_SCORE);
        }
        mUpdateHandler = new UpdateHandler(getActivity());
        mUpdateHandler.sendEmptyMessageDelayed(EVENT_UPDATE_START, 200);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_content_fragment, container, false);
        ButterKnife.inject(this, view);
        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        if (mItemAdapter == null) {
            mRecyclerView.setAdapter(null);
        }

        mRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUpdateHandler.sendEmptyMessage(EVENT_UPDATE_START);
            }
        });
    }

    /**
     * 异步请求数据
     */
    private void doUpdateWork(String uri) {
        HttpUtils.enqueue(uri, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // TODO 失败处理
                mUpdateHandler.sendEmptyMessage(EVENT_UPDATE_FAILED);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response != null) {
                    if (response.isSuccessful()) {
                        // 使用Gson解析返回的json数据
                        Gson gson = new Gson();
                        SurveyEntity survey = gson.fromJson(response.body().charStream(), SurveyEntity.class);
                        mSummary = survey.getSummary();

                        // 处理结束，更新UI
                        mUpdateHandler.sendEmptyMessage(EVENT_UPDATE_DONE);
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } else {
                    throw new IOException("got null response!");
                }
            }
        });
    }

    /**
     * UI update handler
     */
    private class UpdateHandler extends Handler {
        private final Context mContext;

        public UpdateHandler(Context context) {
            mContext = context;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_UPDATE_START:
                    mRecyclerView.setAdapter(null);
                    mRefreshLayout.setRefreshing(true);
                    doUpdateWork(DoubanApiHelper.getMovieSubjectUri(mMovieId));
                    break;
                case EVENT_UPDATE_FAILED:
                    mRefreshLayout.setRefreshing(false);
                    mRefreshLayout.setEnabled(false);
                    break;
                case EVENT_UPDATE_DONE:
                    mRefreshLayout.setRefreshing(false);
                    mItemAdapter = new DetailsItemAdapter(mCastsCount, mCastsIds, mCastsImages,
                            mDirectorId, mDirectorImage, mScore, mSummary);
                    mRecyclerView.setAdapter(mItemAdapter);
                    mRefreshLayout.setEnabled(false);
                    break;
            }

            super.handleMessage(msg);
        }
    }

    public void setRefreshEnable(boolean enable) {
        if(mRefreshLayout != null && !mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setEnabled(enable);
        }
    }
}
