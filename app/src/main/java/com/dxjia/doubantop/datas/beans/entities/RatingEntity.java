package com.dxjia.doubantop.datas.beans.entities;

/**
 * Created by 德祥 on 2015/6/24.
 */
public class RatingEntity {
    /**
     * average : 9.6
     * min : 0
     * max : 10
     * stars : 50
     */
    private double average;
    private int min;
    private int max;
    private String stars;

    public void setAverage(double average) {
        this.average = average;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public double getAverage() {
        return average;
    }

    public String getAverageStr() {
        return String.valueOf(average);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getStars() {
        return stars;
    }
}