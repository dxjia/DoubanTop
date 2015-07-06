package com.dxjia.doubantop.datas;

import android.content.Context;

import com.dxjia.doubantop.DoubanTopApplication;
import com.orm.androrm.CharField;
import com.orm.androrm.DateField;
import com.orm.androrm.Filter;
import com.orm.androrm.Model;
import com.orm.androrm.QuerySet;

import java.util.Date;
import java.util.List;

/**
 * Created by 德祥 on 2015/6/30.
 */
public class SearchHistory extends Model {
    protected CharField meal_name;
    protected DateField date;

    public SearchHistory() {
        super(true);
        meal_name = new CharField();
        date = new DateField();
    }

    public static SearchHistory create(String mealName, Date mDate) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setName(mealName);
        searchHistory.setDate(mDate);
        searchHistory.save();
        return searchHistory;
    }

    public String getName() {
        return meal_name.get();
    }

    public void setName(String count) {
        meal_name.set(count);
    }

    public void setDate(Date d) {
        date.set(d);
    }

    public Date getDate() {
        return date.get();
    }

    private static String formatProjectForQuery(String name) {
        String name1 = name;
        return name1;
    }

    public static List<SearchHistory> logSortByProjectType(String Key_) {
        String query_string = formatProjectForQuery(Key_);
        Filter filter = new Filter();
        filter.contains("meal_name", query_string);
        return SearchHistory.objects().filter(filter).orderBy("meal_name").toList();
    }

    public boolean save() {
        int id = SearchHistory.objects(getAppContext(), SearchHistory.class).all().count() + 1;
        return this.save(getAppContext(), id);
    }

    public boolean edit() {
        return this.save(getAppContext());
    }

    public boolean delete() {
        return this.delete(getAppContext());
    }

    public static List<SearchHistory> all() {
        return SearchHistory.objects().all().orderBy("-date").toList();
    }

    public static List<SearchHistory> filterByName(String name) {
        Filter filter = new Filter();
        filter.contains("meal_name", name);
        return SearchHistory.objects().filter(filter).orderBy("meal_name").toList();
    }

    public static QuerySet<SearchHistory> objects() {
        return SearchHistory.objects(getAppContext(), SearchHistory.class);
    }

    private static Context getAppContext() {
        return DoubanTopApplication.getContext();
    }
}
