package com.dxjia.doubantop.datas;

import android.content.Context;

import com.dxjia.doubantop.DoubanTopApplication;
import com.dxjia.doubantop.datas.beans.MovieMajorInfos;
import com.orm.androrm.CharField;
import com.orm.androrm.Filter;
import com.orm.androrm.IntegerField;
import com.orm.androrm.Model;
import com.orm.androrm.QuerySet;

import java.util.List;

/**
 * Created by 德祥 on 2015/7/6.
 */
public class Favorites extends Model {
    protected CharField movie_id;
    protected CharField movie_title;
    protected CharField movie_image_uri;
    protected IntegerField casts_count;
    protected CharField casts_ids;
    protected CharField casts_image_uris;
    protected CharField director_id;
    protected CharField director_image_uri;
    protected CharField movie_score;

    public Favorites() {
        super(false);
        movie_id = new CharField();
        movie_title = new CharField();
        movie_image_uri = new CharField();
        casts_count = new IntegerField();
        casts_ids = new CharField();
        casts_image_uris = new CharField();
        director_id = new CharField();
        director_image_uri = new CharField();
        movie_score = new CharField();
    }

    public String getMoiveId() {
        return movie_id.get();
    }

    public void setMovieId(String movieId) {
        movie_id.set(movieId);
    }

    public String getMovieTitle() {
        return movie_title.get();
    }

    public void setMovieTitle(String movieTitle) {
        movie_title.set(movieTitle);
    }

    public String getMovieImageUri() {
        return movie_image_uri.get();
    }

    public void setMovieImageUri(String uri) {
        movie_image_uri.set(uri);
    }

    public Integer getCastsCount() {
        return casts_count.get();
    }

    public void setCastsCount(int castsCount) {
        casts_count.set(castsCount);
    }

    public String[] getCastsIds() {
        String ids = casts_ids.get();
        // 用逗号分隔合理嘛
        String[] idArray = ids.split(",");
        return idArray;
    }

    public void setCastsIds(String[] castids) {
        if (castids == null || castids.length == 0) return;
        String ids = castids[0];
        for (int i = 1; i < castids.length; i++) {
            ids = ids + "," + castids[i];
        }
        casts_ids.set(ids);
    }

    public String[] getCastsImageUris() {
        String images = casts_image_uris.get();
        String[] imagesArray = images.split(",");
        return imagesArray;
    }

    public void setCastsImageUris(String[] castsImageUris) {
        if (castsImageUris == null || castsImageUris.length == 0) return;
        String images = castsImageUris[0];
        for (int i = 1; i < castsImageUris.length; i++) {
            images = images + "," + castsImageUris[i];
        }
        casts_image_uris.set(images);
    }

    public String getDirectorId() {
        return director_id.get();
    }

    public void setDirectorId(String directorId) {
        director_id.set(directorId);
    }

    public String getDirectorImageUri() {
        return director_image_uri.get();
    }

    public void setDirectorImageUri(String directorImageUri) {
        director_image_uri.set(directorImageUri);
    }

    public String getMovieScore() {
        return movie_score.get();
    }

    public void setMovieScore(String movieScore) {
        movie_score.set(movieScore);
    }

    public void fillDatas(MovieMajorInfos movieInfos) {
        fillDatas(movieInfos.getMovieId(), movieInfos.getMovieTitle(), movieInfos.getMovieImageUri(),
                movieInfos.getCastsCount(), movieInfos.getCastsIds(), movieInfos.getCastsImages(),
                movieInfos.getDirectorId(), movieInfos.getDirectorImage(), movieInfos.getMovieScore());
    }

    public void fillDatas(String movieId, String movieTitle, String movieImageUri,
                          int castsCount, String[] castsIds, String[] castsImages,
                          String directorId, String directorImage, String movieScore) {
        setMovieId(movieId);
        setMovieTitle(movieTitle);
        setMovieImageUri(movieImageUri);
        setCastsCount(castsCount);
        setCastsIds(castsIds);
        setCastsImageUris(castsImages);
        setDirectorId(directorId);
        setDirectorImageUri(directorImage);
        setMovieScore(movieScore);
    }

    public boolean edit() {
        return this.save(getAppContext());
    }

    public boolean delete() {
        return this.delete(getAppContext());
    }

    public static List<Favorites> all() {
        return Favorites.objects().all().toList();
    }

    public boolean save() {
        return this.save(getAppContext());
    }

    public static List<Favorites> filterByMoiveId(String id) {
        Filter filter = new Filter();
        filter.contains("movie_id", id);
        return Favorites.objects().filter(filter).orderBy("movie_id").toList();
    }

    private static Context getAppContext() {
        return DoubanTopApplication.getContext();
    }

    public static QuerySet<Favorites> objects() {
        return Favorites.objects(getAppContext(), Favorites.class);
    }
}
