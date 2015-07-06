package com.dxjia.doubantop.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.views.PeopleView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by djia on 15-6-25.
 */
public class DetailsItemAdapter extends RecyclerView.Adapter<DetailsItemAdapter.ViewHolder> {

    // 评分
    public static final int ITEM_RATING = 0;
    // 导演
    public static final int ITEM_DIRECTOR = 1;
    // 主演
    public static final int ITEM_CASTS    = 2;
    // 剧情简介
    public static final int ITEM_SUMMARY  = 3;

    // 排序
    private static int ITEM_POSITIONS[] = {
            ITEM_RATING,
            ITEM_DIRECTOR,
            ITEM_CASTS,
            ITEM_SUMMARY
    };

    private int mCastsCount;
    private String[] mCastsIds;
    private String[] mCastsImages;
    private String mDirectorId;
    private String mDirectorImage;
    private String mScoreStr;
    private String mMovieSummary;

    public DetailsItemAdapter(int castsCount, String[] castsIds,
                              String[] castsImages, String directorId,
                              String directorImage, String score, String summary) {
        this.mCastsCount = castsCount;
        this.mCastsIds = castsIds;
        this.mCastsImages = castsImages;
        this.mDirectorId = directorId;
        this.mDirectorImage = directorImage;
        this.mScoreStr = score;
        this.mMovieSummary = summary;
    }

    public DetailsItemAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO update views;
        int item = ITEM_POSITIONS[position];
        switch (item) {
            case ITEM_RATING:
                holder.setTitle(R.string.detail_item_title_score);
                holder.applyRatingItem();
                break;
            case ITEM_DIRECTOR:
                holder.setTitle(R.string.detail_item_title_director);
                holder.applyDirectorItem(mDirectorId, mDirectorImage);
                break;
            case ITEM_CASTS:
                holder.setTitle(R.string.detail_item_title_casts);
                holder.applyCastsItem(mCastsCount, mCastsIds, mCastsImages);
                break;
            case ITEM_SUMMARY:
                holder.setTitle(R.string.detail_item_title_summary);
                holder.applySummaryItem(mMovieSummary);
                break;
            default: break;
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_POSITIONS.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.item_title)
        TextView mItemTitleView;

        @InjectView(R.id.content_area)
        CardView mContentArea;

        @InjectView(R.id.rating_area)
        LinearLayout mRatingArea;
        @InjectView(R.id.rating_bar)
        RatingBar mRatingBar;
        @InjectView(R.id.score)
        TextView mScore;

        @InjectView(R.id.peoples_area)
        LinearLayout mPeoplesArea;
        @InjectView(R.id.people_1)
        PeopleView mPeopleView1;
        @InjectView(R.id.people_2)
        PeopleView mPeopleView2;
        @InjectView(R.id.people_3)
        PeopleView mPeopleView3;
        @InjectView(R.id.people_4)
        PeopleView mPeopleView4;
        @InjectView(R.id.people_5)
        PeopleView mPeopleView5;

        @InjectView(R.id.movie_summary_area)
        LinearLayout mSummaryArea;
        @InjectView(R.id.movie_summary)
        TextView mMovieSummary;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        /**
         * 设置item标题
         * @param titleRes
         */
        public void setTitle(int titleRes) {
            mItemTitleView.setText(titleRes);
        }

        /**
         * 先隐藏
         */
        public void hideAllViewsFirst() {
            {
                mRatingArea.setVisibility(View.GONE);
                mRatingBar.setVisibility(View.GONE);
                mScore.setVisibility(View.GONE);
            }

            {
                mPeoplesArea.setVisibility(View.GONE);
                mPeopleView1.setVisibility(View.GONE);
                mPeopleView2.setVisibility(View.GONE);
                mPeopleView3.setVisibility(View.GONE);
                mPeopleView4.setVisibility(View.GONE);
                mPeopleView5.setVisibility(View.GONE);
            }
            {
                mSummaryArea.setVisibility(View.GONE);
                mMovieSummary.setVisibility(View.GONE);
            }
        }

        public void applyRatingItem() {
            hideAllViewsFirst();
            mRatingArea.setVisibility(View.VISIBLE);
            mRatingBar.setVisibility(View.VISIBLE);
            mRatingBar.setRating((float)((double)(Float.valueOf(mScoreStr))/(double)(2)));
            mScore.setVisibility(View.VISIBLE);
            mScore.setText(mScoreStr);
        }

        public void applyDirectorItem(String directorId, String directorImage) {
            hideAllViewsFirst();
            mPeoplesArea.setVisibility(View.VISIBLE);
            mPeopleView1.setVisibility(View.VISIBLE);
            mPeopleView1.setPeopleInfo(directorId, directorImage);
        }

        /**
         *
         * @param castsCount
         */
        public void applyCastsItem(int castsCount, String[] castsIds, String[] castsImages) {
            // TODO, here bad design, try to improve?
            if (castsCount < 1) return;
            hideAllViewsFirst();
            int current = 0;
            mPeoplesArea.setVisibility(View.VISIBLE);
            mPeopleView1.setVisibility(View.VISIBLE);
            mPeopleView1.setPeopleInfo(castsIds[current], castsImages[current]);
            current++;
            if (current >= castsCount) return;

            mPeopleView2.setVisibility(View.VISIBLE);
            mPeopleView2.setPeopleInfo(castsIds[current], castsImages[current]);
            current++;
            if (current >= castsCount) return;

            mPeopleView3.setVisibility(View.VISIBLE);
            mPeopleView3.setPeopleInfo(castsIds[current], castsImages[current]);
            current++;
            if (current >= castsCount) return;

            mPeopleView4.setVisibility(View.VISIBLE);
            mPeopleView4.setPeopleInfo(castsIds[current], castsImages[current]);
            current++;
            if (current >= castsCount) return;

            mPeopleView5.setVisibility(View.VISIBLE);
            mPeopleView5.setPeopleInfo(castsIds[current], castsImages[current]);
            current++;
            if (current >= castsCount) return;
        }

        public void applySummaryItem(String summary) {
            hideAllViewsFirst();
            mSummaryArea.setVisibility(View.VISIBLE);
            mMovieSummary.setVisibility(View.VISIBLE);
            mMovieSummary.setText(summary);
        }

        public void updateRatingViews(float score) {

        }
    }
}
