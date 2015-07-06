package com.dxjia.doubantop.datas.beans.entities;

import java.util.List;

/**
 * Created by 德祥 on 2015/6/29.
 */
public class CelebrityEntity {

    /**
     * works : [...]
     * * gender : 男
     * aka_en : ["Elijah Jordan Wood (本名)","Elwood, Lij and Monkey (昵称)"]
     * aka : ["伊莱贾·伍德"]
     * born_place : 美国,爱荷华州,锡达拉皮兹
     * mobile_url : http://movie.douban.com/celebrity/1054395/mobile
     * name : 伊利亚·伍德
     * alt : http://movie.douban.com/celebrity/1054395/
     * id : 1054395
     * name_en : Elijah Wood
     * avatars : {"small":"http://.../small/51597.jpg","large":"http://.../large/51597.jpg","medium":"http://.../medium/51597.jpg"}
     */
    private List<WorksEntity> works;
    private String gender;
    private List<String> aka_en;
    private List<String> aka;
    private String born_place;
    private String mobile_url;
    private String name;
    private String alt;
    private String id;
    private String name_en;
    private AvatarsEntity avatars;

    public void setWorks(List<WorksEntity> works) {
        this.works = works;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAka_en(List<String> aka_en) {
        this.aka_en = aka_en;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public void setBorn_place(String born_place) {
        this.born_place = born_place;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public void setAvatars(AvatarsEntity avatars) {
        this.avatars = avatars;
    }

    public List<WorksEntity> getWorks() {
        return works;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getAka_en() {
        return aka_en;
    }

    public List<String> getAka() {
        return aka;
    }

    /**
     * 把别名拼成一串返回 [name1, name2]
     * @return
     */
    public String getAkaStr() {
        if (aka == null || aka.size() == 0) return null;
        String akaStr = "[" + aka.get(0);
        for (int i = 1; i < aka.size(); i++) {
            akaStr = akaStr + ", " + aka.get(i);
        }

        akaStr = akaStr + "]";
        return akaStr;
    }

    public String getBorn_place() {
        return born_place;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public String getName() {
        return name;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getName_en() {
        return name_en;
    }

    public AvatarsEntity getAvatars() {
        return avatars;
    }

}
