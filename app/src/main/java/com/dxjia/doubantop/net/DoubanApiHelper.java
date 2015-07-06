package com.dxjia.doubantop.net;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 德祥 on 2015/6/23.
 */
public class DoubanApiHelper {
    // douban api key, change to yourself
    public final static String API_KEY = "";
    public final static String SECRET = "";

    public final static String DOUBAN_API_BASE_URI = "https://api.douban.com";
    // https://api.douban.com/v2/movie
    public final static String MOVIE_API_BASE_URI = DOUBAN_API_BASE_URI + "/v2/movie";

    public final static int API_TYPE_UNKOWN = -1;
    public final static int API_TYPE_US_BOX = 0;
    public final static int API_TYPE_TOPS = 1;
    public final static int API_TYPE_SEARCH = 2;

    // 北美票房榜
    // https://api.douban.com/v2/movie/us_box
    public final static String MOVIE_API_US_BOX_URI = appendApiKey(MOVIE_API_BASE_URI + "/us_box", true);

    // 排行榜
    // https://api.douban.com/v2/movie/top250
    public final static String MOVIE_API_TOPS_URI = appendApiKey(MOVIE_API_BASE_URI + "/top250", true);

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

    /**
     * 电影条目信息
     * /v2/movie/subject/:id
     * @param movieId
     * @return null for unavalibel id
     */
    public static String getMovieSubjectUri(String movieId) {
        if (TextUtils.isEmpty(movieId)) {
            return null;
        }
        String uri = MOVIE_API_BASE_URI + "/subject/" +  movieId;
        return appendApiKey(uri, true);
    }

    /**
     * 获取影人条目URI
     * @param celebrityId
     * @return
     */
    public static String getCelebrityUri(String celebrityId) {
        if (TextUtils.isEmpty(celebrityId)) return null;
        String uri = MOVIE_API_BASE_URI + "/celebrity/" + celebrityId;

        return appendApiKey(uri, true);
    }

    /**
     * 获取搜索uri
     * @param key
     * @return
     */
    public static String getSearchUri(String key) {
        if (TextUtils.isEmpty(key)) return null;
        String encodeKey;
        try {
            encodeKey = URLEncoder.encode(key, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodeKey = "";
        }
        if (TextUtils.isEmpty(encodeKey)) {
            return null;
        }
        String uri = MOVIE_API_BASE_URI + "/search?q=" + encodeKey;
        return appendApiKey(uri, false);
    }
}
