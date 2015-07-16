package com.dxjia.doubantop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.datas.beans.MovieInfoBean;
import com.dxjia.doubantop.datas.beans.MovieMajorInfos;
import com.dxjia.doubantop.interfaces.MovieInfoActionsListener;
import com.dxjia.doubantop.views.MovieView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by dxjia on 15-6-23.
 */
public class ContentItemAdapter extends RecyclerView.Adapter<ContentItemAdapter.ViewHolder> {

    final private List<MovieInfoBean> mMoviesList;
    final private MovieInfoActionsListener mActionListener;

    public ContentItemAdapter(MovieInfoActionsListener l, List<MovieInfoBean> moviesList) {
        mActionListener = l;
        mMoviesList = moviesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieInfoBean bean = mMoviesList.get(position);
        holder.updateViews(bean);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public MovieView mMovieView;
        public Button mDetailButton;
        public MovieInfoBean mBean;

        public void updateViews(MovieInfoBean details) {
            mBean = details;
            mMovieView.setTitle(mBean.getTitle());
            mMovieView.setDescription(mBean.getFormatedGenres());
            mMovieView.setAverage(mBean.getAverage() + "'");
            mDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MovieMajorInfos movieMajorInfos = new MovieMajorInfos();
                    movieMajorInfos.fillDatas(mBean.getId(), mBean.getTitle(), mBean.getImageUri(),
                            mBean.getCastsCount(), mBean.getCastsIds(), mBean.getCastsAvatorUris(),
                            mBean.getDirectorId(), mBean.getDirectorImageUri(), mBean.getAverage());
                    mActionListener.showDetails(movieMajorInfos);
                }
            });
            Context context = mImageView.getContext();
            Picasso.with(context)
                    .load(mBean.getImageUri())
                    .placeholder(R.mipmap.ic_loading)
                    .error(R.mipmap.ic_unkown_image)
                    .fit()
                    .into(mImageView);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            mMovieView = ButterKnife.findById(itemView, R.id.cardview);
            mMovieView.setTitle("Card View");
            mImageView = mMovieView.getImageView();
            mDetailButton = mMovieView.getHighlightButton();
        }
    }
}
