package com.dxjia.doubantop.datas.beans.entities;

/**
 * Created by 德祥 on 2015/6/24.
 */
public class ImagesEntity {
    /**
     * small : http://img3.douban.com/view/movie_poster_cover/ipst/public/p480747492.jpg
     * large : http://img3.douban.com/view/movie_poster_cover/lpst/public/p480747492.jpg
     * medium : http://img3.douban.com/view/movie_poster_cover/spst/public/p480747492.jpg
     */
    private String small;
    private String large;
    private String medium;

    public void setSmall(String small) {
        this.small = small;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }
}