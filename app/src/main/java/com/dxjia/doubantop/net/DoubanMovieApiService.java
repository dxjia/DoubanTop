package com.dxjia.doubantop.net;

import com.dxjia.doubantop.datas.beans.MovieTops;
import com.dxjia.doubantop.datas.beans.MovieUSBox;
import com.dxjia.doubantop.datas.beans.entities.CelebrityEntity;
import com.dxjia.doubantop.datas.beans.entities.SearchResultEntity;
import com.dxjia.doubantop.datas.beans.entities.SurveyEntity;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by dxjia on 2015-9-8.
 * douban movie v2 api service
 */
public interface DoubanMovieApiService {
    /**
     * 异步获取北美排行榜
     * @param apikey douban apikey
     * @param callback 异步callback
     */
    @GET("/movie/us_box")
    public void getMoviceUSBox(@Query("apikey") String apikey,
                               Callback<MovieUSBox> callback);

    /**
     * 异步获取TOP250
     * @param apikey douban apikey
     * @param callback 异步callback
     */
    @GET("/movie/top250")
    public void getTop250(@Query("apikey") String apikey,
                          Callback<MovieTops> callback);


    /**
     * 电影条目信息
     * @param movieid movie id
     * @param apikey douban apikey
     * @param callback retrofit callback
     */
    @GET("/movie/subject/{id}")
    public void getMovieSubject(@Path("id") int movieid,
                                @Query("apikey") String apikey,
                                Callback<SurveyEntity> callback);

    /**
     * 获取影人条目
     * @param celebrityid celebrity id
     * @param apikey douban api key
     * @param callback retrofit callback
     */
    @GET("/movie/celebrity/{id}")
    public void getCelebrityDetails(@Path("id") int celebrityid,
                                    @Query("apikey") String apikey,
                                    Callback<CelebrityEntity> callback);

    /**
     * 搜索
     * @param encodedSearchKey 编码后的搜索关键字
     * @param apikey douban api key
     * @param callback retrofit callback
     */
    @GET("/movie/search")
    public void doSearch(@Query("q") String encodedSearchKey,
                         @Query("apikey") String apikey,
                         Callback<SearchResultEntity> callback);
}
