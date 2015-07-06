package com.dxjia.doubantop.datas.beans;

import com.dxjia.doubantop.datas.beans.entities.SubjectsEntity;

import java.util.List;

/**
 * Created by djia on 15-6-24.
 *
 * https://api.douban.com/v2/movie/us_box
 *
 */
public class MovieUSBox {
    /**
     * date : 6月19日 - 6月21日
     * title : 豆瓣电影北美票房榜
     */
    private String date;
    private List<SubjectsEntity> subjects;
    private String title;

    public void setDate(String date) {
        this.date = date;
    }

    public void setSubjects(List<SubjectsEntity> subjects) {
        this.subjects = subjects;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public List<SubjectsEntity> getSubjects() {
        return subjects;
    }

    public String getTitle() {
        return title;
    }



}
