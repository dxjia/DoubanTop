package com.dxjia.doubantop.net;

import android.os.Handler;
import android.os.Message;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dxjia on 2015-9-2.
 *
 */
public class RetrofitCallback<T> implements Callback<T> {

    private Handler mHandler;
    private int mSuccessEventCode;
    private int mFailEventCode;

    public static final int EVENT_CALL_FAILED = -1;
    public static final int EVENT_CALL_SUCCESS = 0;

    Class<T> mClassz;

    public RetrofitCallback(Handler handler, Class<T> classz) {
        mHandler = handler;
        mClassz = classz;
        mSuccessEventCode = EVENT_CALL_SUCCESS;
        mFailEventCode = EVENT_CALL_FAILED;
    }

    public RetrofitCallback(Handler handler, int successCode, int failCode, Class<T> classz) {
        mHandler = handler;
        mClassz = classz;
        mSuccessEventCode = successCode;
        mFailEventCode = failCode;
    }

    @Override
    public void success(T t, Response response) {
        Message message = Message.obtain(mHandler, mSuccessEventCode, t);
        message.sendToTarget();
    }

    @Override
    public void failure(RetrofitError error) {
        Message message = Message.obtain(mHandler, mFailEventCode, error);
        message.sendToTarget();
    }
}
