package com.dxjia.doubantop.net;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by 德祥 on 2015/6/23.
 * 辅助类，只提供异步接口
 */
public class HttpUtils {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    static{
        mOkHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
    }

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 开启异步线程访问网络
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 异步访问网络
     * @param uri 网址
     * @param responseCallback 返回结果处理
     */
    public static void enqueue(String uri, Callback responseCallback){
        Request request = new Request.Builder().url(uri).build();
        enqueue(request, responseCallback);
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     * @param request
     */
    public static void enqueue(Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response arg0) throws IOException {
            }
            @Override
            public void onFailure(Request arg0, IOException arg1) {
            }
        });
    }

}
