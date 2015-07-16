package com.dxjia.doubantop.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.datas.SearchHistory;

/**
 * Created by 德祥 on 2015/6/30.
 */
public class SearchHistoryRowView extends LinearLayout {
    Context mContext;
    SearchHistory mLog;

    public SearchHistoryRowView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SearchHistoryRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_history_row, this);
    }

    public void setLog(SearchHistory log) {
        mLog = log;
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(mLog.getName());
    }
}
