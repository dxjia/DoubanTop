package com.dxjia.doubantop.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.datas.Favorites;
import com.dxjia.doubantop.interfaces.FavoritesItemClickListener;

import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by 德祥 on 2015/7/6.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> implements View.OnClickListener {

    private List<Favorites> mDatasList;

    public FavoritesAdapter(List<Favorites> datasList) {
        mDatasList = datasList;
    }

    private FavoritesItemClickListener mItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorates_item, parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateViews(mDatasList.get(position));
        holder.itemView.setTag(mDatasList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatasList != null ? mDatasList.size() : 0;
    }

    public void setItemClickListener(FavoritesItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v, (Favorites)v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleView;
        private ImageView mImageBtn;
        private Favorites mFavorites;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleView = ButterKnife.findById(itemView, R.id.favorites_item_title);
            mImageBtn = ButterKnife.findById(itemView, R.id.image_btn_delete_item);
        }

        public void updateViews(final Favorites favorites) {
            mTitleView.setText(favorites.getMovieTitle());
            mImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mDatasList.indexOf(favorites);
                    if (index < 0) {
                        return;
                    }
                    favorites.delete();
                    mDatasList.remove(index);
                    notifyItemRemoved(index);
                }
            });
        }
    }


}
