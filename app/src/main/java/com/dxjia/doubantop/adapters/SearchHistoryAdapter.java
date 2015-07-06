package com.dxjia.doubantop.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dxjia.doubantop.datas.SearchHistory;
import com.dxjia.doubantop.views.SearchHistoryRowView;

import java.util.List;

/**
 * Created by 德祥 on 2015/6/30.
 */
public class SearchHistoryAdapter extends ArrayAdapter<SearchHistory> {
    Context mContext;
    public static List<SearchHistory> mLogs;

    public SearchHistoryAdapter(Context context, int resource, List<SearchHistory> logs) {
        super(context, resource);
        mContext = context;
        mLogs = logs;
    }

    public void setLogs(List<SearchHistory> logs) {
        mLogs = logs;
    }

    public List<SearchHistory> getLogs() {
        return mLogs;
    }

    public void add(SearchHistory log) {
        mLogs.add(log);
    }

    public void remove(SearchHistory log) {
        this.mLogs.remove(log);
    }

    public int getCount() {
        return mLogs.size();
    }

    public SearchHistory getItem(int position) {
        return mLogs.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SearchHistoryRowView view = (SearchHistoryRowView) convertView;
        if (view == null) {
            view = new SearchHistoryRowView(mContext);
        }
        SearchHistory log = getItem(position);
        view.setLog(log);
        return view;
    }
}
