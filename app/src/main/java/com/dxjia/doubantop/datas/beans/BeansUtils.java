package com.dxjia.doubantop.datas.beans;

/**
 * Created by djia on 15-6-23.
 */
public class BeansUtils {
    public static final int IMAGE_SRC_TYPE_FROM_RESOURCE = 0;
    public static final int IMAGE_SRC_TYPE_FROM_URI = 1;

    public static final int MAX_COUNT_MOVIE_TOPS = 200;

    public static final int IMAGE_URI_USE_SMALL = 0;
    public static final int IMAGE_URI_USE_MEDIUM = 1;
    public static final int IMAGE_URI_USE_LARGE = 2;

    public static final int IMAGE_URI_SIZE_PREFER = IMAGE_URI_USE_LARGE;

    public static final String MOVIE_MAJOR_INFOS_KEY = "movie_major_infos";
    public static final String ARG_ID = "id";
    public static final String ARG_TITLE = "title";
    public static final String ARG_IMAGE_URI = "image_uri";
    public static final String ARG_CASTS_COUNT = "casts_count";
    public static final String ARG_CASTS_IDS   = "casts_ids";
    public static final String ARG_CASTS_IMAGES = "casts_images";
    public static final String ARG_DIRECTOR_ID  = "director_id";
    public static final String ARG_DIRECTOR_IMAGE = "director_image";
    public static final String ARG_MOVIE_SCORE = "movie_score";
    public static final String ARG_SEARCH_KEY   = "search_key";

    public static int getImageSizePrefer() {
        return IMAGE_URI_SIZE_PREFER;
    }
}
