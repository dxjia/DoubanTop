package com.dxjia.doubantop.datas.beans.entities;

import java.util.List;

/**
 * Created by 德祥 on 2015/6/25.
 * 电影概况：https://api.douban.com/v2/movie/:id
 * [:id]为电影条目id
 */
public class SurveyEntity {

    /**
     * summary : 杰克（爱德华•诺顿 饰）是一个大汽车公司的职员，患有严重的失眠症，对周围的一切充满危机和憎恨。...
     * image : http://img4.douban.com/view/movie_poster_cover/ipst/public/p1910926158.jpg
     * alt_title : 搏击俱乐部 / 搏击会(港)
     * author : [{"name":"大卫·芬奇 David Fincher"}]
     * rating : {"average":"9.0","min":0,"max":10,"numRaters":318220}
     * alt : http://movie.douban.com/movie/1292000
     * mobile_link : http://m.douban.com/movie/subject/1292000/
     * id : http://api.douban.com/movie/1292000
     * title : Fight Club
     * attrs : {"country":["美国","德国"],"cast":["爱德华·诺顿 Edward Norton","布拉德·皮特 Brad Pitt","海伦娜·邦汉·卡特 Helena Bonham Carter",
     *          "扎克·格雷尼尔 Zach Grenier","米特·洛夫 Meat Loaf","杰瑞德·莱托 Jared Leto","艾恩·贝利 Eion Bailey","里奇蒙德·阿奎特  Richmond Arquette",
     *          "乔治·马奎尔 George Maguire"],"movie_duration":["139 分钟"],"year":["1999"],"director":["大卫·芬奇 David Fincher"],"language":["英语"],
     *          "writer":["恰克·帕拉尼克 Chuck Palahniuk","Jim Uhls"],"title":["Fight Club"],"movie_type":["剧情","动作","悬疑","惊悚"],"pubdate":["1999-09-10(威尼斯电影节)","1999-10-15(美国)"]}
     * tags : [{"count":50815,"name":"心理"},{"count":46738,"name":"美国"},{"count":37391,"name":"暴力"},{"count":34169,"name":"经典"},
     *          {"count":33696,"name":"悬疑"},{"count":22254,"name":"剧情"},{"count":18507,"name":"黑色"},{"count":13452,"name":"犯罪"}]
     */
    private String summary;
    private String image;
    private String alt_title;
    private List<AuthorEntity> author;
    private RatingEntity rating;
    private String alt;
    private String mobile_link;
    private String id;
    private String title;
    private AttrsEntity attrs;
    private List<TagsEntity> tags;

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public void setAuthor(List<AuthorEntity> author) {
        this.author = author;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setMobile_link(String mobile_link) {
        this.mobile_link = mobile_link;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAttrs(AttrsEntity attrs) {
        this.attrs = attrs;
    }

    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }

    public String getSummary() {
        return summary;
    }

    public String getImage() {
        return image;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public List<AuthorEntity> getAuthor() {
        return author;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public String getAlt() {
        return alt;
    }

    public String getMobile_link() {
        return mobile_link;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public AttrsEntity getAttrs() {
        return attrs;
    }

    public List<TagsEntity> getTags() {
        return tags;
    }

    public class AuthorEntity {
        /**
         * name : 大卫·芬奇 David Fincher
         */
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public class RatingEntity {
        /**
         * average : 9.0
         * min : 0
         * max : 10
         * numRaters : 318220
         */
        private String average;
        private int min;
        private int max;
        private int numRaters;

        public void setAverage(String average) {
            this.average = average;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public int getNumRaters() {
            return numRaters;
        }
    }

    public class AttrsEntity {
        /**
         * country : ["美国","德国"]
         * cast : ["爱德华·诺顿 Edward Norton","布拉德·皮特 Brad Pitt","海伦娜·邦汉·卡特 Helena Bonham Carter","扎克·格雷尼尔 Zach Grenier",
         *          "米特·洛夫 Meat Loaf","杰瑞德·莱托 Jared Leto","艾恩·贝利 Eion Bailey","里奇蒙德·阿奎特  Richmond Arquette","乔治·马奎尔 George Maguire"]
         * movie_duration : ["139 分钟"]
         * year : ["1999"]
         * director : ["大卫·芬奇 David Fincher"]
         * language : ["英语"]
         * writer : ["恰克·帕拉尼克 Chuck Palahniuk","Jim Uhls"]
         * title : ["Fight Club"]
         * movie_type : ["剧情","动作","悬疑","惊悚"]
         * pubdate : ["1999-09-10(威尼斯电影节)","1999-10-15(美国)"]
         */
        private List<String> country;
        private List<String> cast;
        private List<String> movie_duration;
        private List<String> year;
        private List<String> director;
        private List<String> language;
        private List<String> writer;
        private List<String> title;
        private List<String> movie_type;
        private List<String> pubdate;

        public void setCountry(List<String> country) {
            this.country = country;
        }

        public void setCast(List<String> cast) {
            this.cast = cast;
        }

        public void setMovie_duration(List<String> movie_duration) {
            this.movie_duration = movie_duration;
        }

        public void setYear(List<String> year) {
            this.year = year;
        }

        public void setDirector(List<String> director) {
            this.director = director;
        }

        public void setLanguage(List<String> language) {
            this.language = language;
        }

        public void setWriter(List<String> writer) {
            this.writer = writer;
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }

        public void setMovie_type(List<String> movie_type) {
            this.movie_type = movie_type;
        }

        public void setPubdate(List<String> pubdate) {
            this.pubdate = pubdate;
        }

        public List<String> getCountry() {
            return country;
        }

        public List<String> getCast() {
            return cast;
        }

        public List<String> getMovie_duration() {
            return movie_duration;
        }

        public List<String> getYear() {
            return year;
        }

        public List<String> getDirector() {
            return director;
        }

        public List<String> getLanguage() {
            return language;
        }

        public List<String> getWriter() {
            return writer;
        }

        public List<String> getTitle() {
            return title;
        }

        public List<String> getMovie_type() {
            return movie_type;
        }

        public List<String> getPubdate() {
            return pubdate;
        }
    }

    public class TagsEntity {
        /**
         * count : 50815
         * name : 心理
         */
        private int count;
        private String name;

        public void setCount(int count) {
            this.count = count;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public String getName() {
            return name;
        }
    }
}
