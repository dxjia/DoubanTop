package com.dxjia.doubantop.net;

import android.text.TextUtils;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by dxjia on 2015-9-8.
 * Use retrofit library for douban RESTFUL api
 */
public class DoubanApiUtils {
    // douban api v2 version base uri
    public final static String DOUBAN_API_V2_BASE_URI = "https://api.douban.com/v2/";

    private static DoubanMovieApiService mApiService = null;

    // douban api key, change to yourself in assets/api_infos.xml
    public final static String API_KEY = getDoubanApiKey();
    public final static String SECRET = getDoubanApiSecret();

    public final static int API_TYPE_UNKOWN = -1;
    public final static int API_TYPE_US_BOX = 0;
    public final static int API_TYPE_TOPS = 1;
    public final static int API_TYPE_SEARCH = 2;


    // sigleton
    public static DoubanMovieApiService getMovieApiService() {
        if (mApiService == null) {
            OkHttpClient mOkHttpClient = new OkHttpClient();
            mOkHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(DOUBAN_API_V2_BASE_URI)
                    .setClient(new OkClient(mOkHttpClient))
                    .build();
            mApiService = restAdapter.create(DoubanMovieApiService.class);
        }

        return mApiService;
    }


    /**
     * 将网址带上api key
     * @param uri
     * @param useQ true for use ? append, false for using &
     * @return
     */
    public static String appendApiKey(String uri, boolean useQ) {
        if (TextUtils.isEmpty(uri)) return null;
        if (TextUtils.isEmpty(API_KEY)) return uri;
        return useQ ? (uri + "?apikey=" + API_KEY) : (uri + "&apikey=" + API_KEY);
    }


    private static String getDoubanApiKey() {
        String apikey = "";
        // parsing xml
        List<ApiInfosUtil.ApiInfo> apiInfos = ApiInfosUtil.parseApiInfos();
        if (apiInfos != null) {
            for (ApiInfosUtil.ApiInfo apiInfo : apiInfos) {
                if (apiInfo.getSource() == ApiInfosUtil.API_SOURCE_DOUBAN) {
                    apikey = apiInfo.getApikey();
                    break;
                }
            }
        }

        return apikey;
    }

    private static String getDoubanApiSecret() {
        String secret = "";
        // parsing xml
        List<ApiInfosUtil.ApiInfo> apiInfos = ApiInfosUtil.parseApiInfos();
        if (apiInfos != null) {
            for (ApiInfosUtil.ApiInfo apiInfo : apiInfos) {
                if (apiInfo.getSource() == ApiInfosUtil.API_SOURCE_DOUBAN) {
                    secret = apiInfo.getSecret();
                    break;
                }
            }
        }

        return secret;
    }
}
