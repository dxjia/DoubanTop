package com.dxjia.doubantop.net;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by dxjia on 2015-8-31.
 */
public class HttpCallBack<T> implements Callback{

    private Handler mHandler;
    private static final int EVENT_CALL_START = 1;
    private static final int EVENT_CALL_FAILED = 2;
    private static final int EVENT_CALL_DONE = 3;

    Class<T> mClassz;

    public HttpCallBack(Handler handler, Class<T> classz) {
        mHandler = handler;
        mClassz = classz;
    }

    @Override
    public void onFailure(Request request, IOException e) {
        mHandler.sendEmptyMessage(EVENT_CALL_FAILED);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (response != null) {
            if (response.isSuccessful()) {
                // 使用Gson解析返回的json数据
                Gson gson = new Gson();
                T survey = gson.fromJson(response.body().charStream(), mClassz);

                Message message = Message.obtain(mHandler, EVENT_CALL_DONE, survey);

                message.sendToTarget();

            } else {
                throw new IOException("Unexpected code " + response);
            }
        } else {
            mHandler.sendEmptyMessage(EVENT_CALL_FAILED);
        }
    }
}
