package com.dxjia.doubantop.datas.beans.entities;

/**
 * Created by 德祥 on 2015/6/24.
 */
public class AvatarsEntity {
    /**
     * small : http://img3.douban.com/img/celebrity/small/230.jpg
     * large : http://img3.douban.com/img/celebrity/large/230.jpg
     * medium : http://img3.douban.com/img/celebrity/medium/230.jpg
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