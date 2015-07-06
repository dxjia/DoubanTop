package com.dxjia.doubantop.datas.beans.entities;

/**
 * Created by 德祥 on 2015/6/24.
 */
public class DirectorsEntity {
    /**
     * alt : http://movie.douban.com/celebrity/1047973/
     * name : 弗兰克·德拉邦特
     * id : 1047973
     * avatars : {"small":"http://img3.douban.com/img/celebrity/small/230.jpg","large":"http://img3.douban.com/img/celebrity/large/230.jpg","medium":"http://img3.douban.com/img/celebrity/medium/230.jpg"}
     */
    private String alt;
    private String name;
    private String id;
    private AvatarsEntity avatars;

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAvatars(AvatarsEntity avatars) {
        this.avatars = avatars;
    }

    public String getAlt() {
        return alt;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public AvatarsEntity getAvatars() {
        return avatars;
    }

}
