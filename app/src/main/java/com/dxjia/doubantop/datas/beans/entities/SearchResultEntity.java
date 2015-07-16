package com.dxjia.doubantop.datas.beans.entities;

import java.util.List;

/**
 * Created by 德祥 on 2015/6/30.
 */
public class SearchResultEntity {

    /**
     * total : 713
     * subjects : [......]
     * count : 2
     * start : 0
     * title : 搜索 "dog" 的结果
     */
    private int total;
    private List<SubjectsEntity> subjects;
    private int count;
    private int start;
    private String title;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSubjects(List<SubjectsEntity> subjects) {
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

    public List<SubjectsEntity> getSubjects() {
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

    public class SubjectsEntity {
        /**
         * images : {......}
         * casts : []
         * original_title : Dog
         * subtype : movie
         * year : 2002
         * genres : ["短片","动画"]
         * directors : [......]
         * rating : {"average":7.5,"min":0,"max":10,"stars":"40"}
         * alt : http://movie.douban.com/subject/1466688/
         * id : 1466688
         * title : 狗
         * collect_count : 620
         */
        private ImagesEntity images;
        private List<CastsEntity> casts;
        private String original_title;
        private String subtype;
        private String year;
        private List<String> genres;
        private List<DirectorsEntity> directors;
        private RatingEntity rating;
        private String alt;
        private String id;
        private String title;
        private int collect_count;

        public void setImages(ImagesEntity images) {
            this.images = images;
        }

        public void setCasts(List<CastsEntity> casts) {
            this.casts = casts;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public void setDirectors(List<DirectorsEntity> directors) {
            this.directors = directors;
        }

        public void setRating(RatingEntity rating) {
            this.rating = rating;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public ImagesEntity getImages() {
            return images;
        }

        public List<CastsEntity> getCasts() {
            return casts;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public String getYear() {
            return year;
        }

        public List<String> getGenres() {
            return genres;
        }

        public List<DirectorsEntity> getDirectors() {
            return directors;
        }

        public RatingEntity getRating() {
            return rating;
        }

        public String getAlt() {
            return alt;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public int getCollect_count() {
            return collect_count;
        }


    }
}
