package com.dxjia.doubantop.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.adapters.ContentItemAdapter;
import com.dxjia.doubantop.datas.beans.MovieInfoBean;
import com.dxjia.doubantop.datas.beans.MovieTops;
import com.dxjia.doubantop.datas.beans.MovieUSBox;
import com.dxjia.doubantop.datas.beans.entities.SearchResultEntity;
import com.dxjia.doubantop.datas.beans.entities.SubjectEntity;
import com.dxjia.doubantop.datas.beans.entities.SubjectsEntity;
import com.dxjia.doubantop.net.DoubanApiHelper;
import com.dxjia.doubantop.net.HttpUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by 德祥 on 2015/7/13.
 * The base fragment
 */
public class SwipeRefreshFragment extends BaseFragment {

    protected static final String ARG_API_TYPE = "api_type";

    private static final int EVENT_UPDATE_INIT = 0;
    private static final int EVENT_UPDATE_START = 1;
    private static final int EVENT_UPDATE_FAILED = 2;
    private static final int EVENT_UPDATE_DONE = 3;

    private int mApiType = DoubanApiHelper.API_TYPE_UNKOWN;
    private String mApiUri;
    private String mSearchKey;

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.movies_list)
    RecyclerView mRecyclerView;

    private ArrayList<MovieInfoBean> mMoviesList = new ArrayList<MovieInfoBean>();

    private ContentItemAdapter mArrayAdapter;
    private UpdateHandler mUpdateHandler;

    @Override
    protected int getLayout() {
        return R.layout.swipe_refresh_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mApiType = getArguments().getInt(ARG_API_TYPE);
        }

        switch (mApiType) {
            case DoubanApiHelper.API_TYPE_US_BOX:
                mApiUri = DoubanApiHelper.MOVIE_API_US_BOX_URI;
                break;
            case DoubanApiHelper.API_TYPE_TOPS:
                mApiUri = DoubanApiHelper.MOVIE_API_TOPS_URI;
                break;
            case DoubanApiHelper.API_TYPE_SEARCH:
                mSearchKey = "";
                mApiUri = "";
                break;
            default:
                mApiUri = DoubanApiHelper.MOVIE_API_US_BOX_URI;
                break;
        }

        mMoviesList.clear();
        mUpdateHandler = new UpdateHandler(getActivity());

        if (mApiType != DoubanApiHelper.API_TYPE_SEARCH) {
            startRequestDelay(100);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    protected void startRequestDelay(int delay) {
        mUpdateHandler.sendEmptyMessageDelayed(EVENT_UPDATE_INIT, delay);
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        if (mMoviesList != null && mMoviesList.size() > 0) {
            mArrayAdapter = new ContentItemAdapter(getActionsListener(), mMoviesList);
            mRecyclerView.setAdapter(mArrayAdapter);
        }

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doUpdateWork(mApiUri);
            }
        });
        if (DoubanApiHelper.API_TYPE_SEARCH == mApiType) {
            setRefreshEnable(false);
        }
    }

    /**
     * 设置refresh控件是否可用
     * @param enable
     */
    public void setRefreshEnable(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    /**
     * 按评分从高到低对列表进行排序
     */
    private void sortListByScore() {
        if (mMoviesList == null || mMoviesList.size() <= 1) {
            return;
        }

        Collections.sort(mMoviesList, new Comparator<MovieInfoBean>() {
            @Override
            public int compare(MovieInfoBean lhs, MovieInfoBean rhs) {
                return (Float.valueOf(rhs.getAverage())).compareTo(Float.valueOf(lhs.getAverage()));
            }
        });
    }


    public void startNewSearch(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        if (mApiType == DoubanApiHelper.API_TYPE_SEARCH) {
            mSearchKey = key;
            setRefreshEnable(true);
            fetchSearchContents();
        }
    }

    /**
     * 进行搜索
     */
    private void fetchSearchContents() {
        mApiUri = DoubanApiHelper.getSearchUri(mSearchKey);
        mSwipeRefreshLayout.setRefreshing(true);
        doUpdateWork(mApiUri);
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
                case EVENT_UPDATE_INIT:
                    mSwipeRefreshLayout.setRefreshing(true);
                    doUpdateWork(mApiUri);
                    break;
                case EVENT_UPDATE_START:
                    mMoviesList.clear();
                    mRecyclerView.setAdapter(null);
                    mRecyclerView.invalidate();
                    break;
                case EVENT_UPDATE_FAILED:
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (mApiType == DoubanApiHelper.API_TYPE_SEARCH) {
                        setRefreshEnable(false);
                    }
                    break;
                case EVENT_UPDATE_DONE:
                    // search的结果就不排序了
                    if(mApiType != DoubanApiHelper.API_TYPE_SEARCH) {
                        sortListByScore();
                    }
                    mArrayAdapter = new ContentItemAdapter(getActionsListener(), mMoviesList);
                    mRecyclerView.setAdapter(mArrayAdapter);
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (mApiType == DoubanApiHelper.API_TYPE_SEARCH) {
                        setRefreshEnable(false);
                    }
                    break;
            }

            super.handleMessage(msg);
        }
    }

    /**
     * 异步请求数据
     */
    private void doUpdateWork(String uri) {
        if(TextUtils.isEmpty(uri)) {
            return;
        }
        mUpdateHandler.sendEmptyMessage(EVENT_UPDATE_START);
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

                        List<SubjectsEntity> subjects;
                        SubjectsEntity subjectsEntity;
                        SubjectEntity subject;

                        int i;
                        if (mApiType == DoubanApiHelper.API_TYPE_US_BOX) {
                            MovieUSBox usBox = gson.fromJson(response.body().charStream(), MovieUSBox.class);
                            subjects = usBox.getSubjects();
                            if (subjects != null) {
                                for (i = 0; i < subjects.size(); i++) {
                                    subjectsEntity = subjects.get(i);
                                    if (subjectsEntity == null) {
                                        continue;
                                    }
                                    subject = subjectsEntity.getSubject();
                                    if (subject == null) {
                                        continue;
                                    }

                                    mMoviesList.add(new MovieInfoBean(subject));
                                }
                            }
                        } else if (mApiType == DoubanApiHelper.API_TYPE_TOPS) {
                            MovieTops movieTops = gson.fromJson(response.body().charStream(), MovieTops.class);
                            List<SubjectEntity> subjectEntities;
                            subjectEntities = movieTops.getSubjects();
                            if (subjectEntities != null) {
                                for (i = 0; i < subjectEntities.size(); i++) {
                                    subject = subjectEntities.get(i);
                                    if (subject == null) {
                                        continue;
                                    }
                                    mMoviesList.add(new MovieInfoBean(subject));
                                }
                            }
                        } else {
                            if (mApiType == DoubanApiHelper.API_TYPE_SEARCH) {
                                List<SearchResultEntity.SubjectsEntity> searResSubjectsList;
                                SearchResultEntity searchResult = gson.fromJson(response.body().charStream(), SearchResultEntity.class);
                                searResSubjectsList = searchResult.getSubjects();
                                if (searResSubjectsList != null) {
                                    for (i = 0; i < searResSubjectsList.size(); i++) {
                                        SearchResultEntity.SubjectsEntity subsEntity = searResSubjectsList.get(i);
                                        if (subsEntity == null) {
                                            continue;
                                        }
                                        subject = SubjectEntity.cloneFromSearchSubjectsEntity(subsEntity);
                                        if (subject == null) {
                                            continue;
                                        }

                                        mMoviesList.add(new MovieInfoBean(subject));
                                    }
                                }
                            }
                        }

                        // 处理结束，更新UI
                        mUpdateHandler.sendEmptyMessage(EVENT_UPDATE_DONE);
                    } else {
                        mUpdateHandler.sendEmptyMessage(EVENT_UPDATE_FAILED);
                        throw new IOException("Unexpected code " + response);
                    }
                } else {
                    mUpdateHandler.sendEmptyMessage(EVENT_UPDATE_FAILED);
                    throw new IOException("got null response!");
                }
            }
        });
    }

}
