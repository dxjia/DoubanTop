package com.dxjia.doubantop.datas.beans;

import com.dxjia.doubantop.datas.beans.entities.SubjectEntity;

import java.util.List;

/**
 * Created by 德祥 on 2015/6/24.
 *
 * https://api.douban.com/v2/movie/top250
 *
 */
public class MovieTops {
    /**
     * total : 250
     * subjects: [....]
     * count : 20
     * start : 0
     * title : 豆瓣电影Top250
     */
    private int total;
    private List<SubjectEntity> subjects;
    private int count;
    private int start;
    private String title;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal() {
        return total;
    }

    public List<SubjectEntity> getSubjects() {
        return subjects;
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public String getTitle() {
        return title;
    }

}
