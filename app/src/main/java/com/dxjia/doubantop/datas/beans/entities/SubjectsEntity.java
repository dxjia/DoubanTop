package com.dxjia.doubantop.datas.beans.entities;

/**
 * Created by 德祥 on 2015/6/24.
 */
public class SubjectsEntity {
    /**
     * new : false
     * rank : 1
     * box : 102019000
     */
    private boolean newX;
    private SubjectEntity subject;
    private int rank;
    private int box;

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public boolean isNewX() {
        return newX;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public int getRank() {
        return rank;
    }

    public int getBox() {
        return box;
    }

}