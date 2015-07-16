package com.dxjia.doubantop.datas.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 德祥 on 2015/7/6.
 */
public class MovieMajorInfos implements Parcelable {
    private String mMovieId;
    private String mMovieTitle;
    private String mMovieImageUri;
    private int mCastsCount;
    private String[] mCastsIds;
    private String[] mCastsImages;
    private String mDirectorId;
    private String mDirectorImage;
    private String mMovieScore;

    public MovieMajorInfos() {
    }

    protected MovieMajorInfos(Parcel in) {
        mMovieId = in.readString();
        mMovieTitle = in.readString();
        mMovieImageUri = in.readString();
        mCastsCount = in.readInt();
        mCastsIds = in.createStringArray();
        mCastsImages = in.createStringArray();
        mDirectorId = in.readString();
        mDirectorImage = in.readString();
        mMovieScore = in.readString();
    }

    public String getMovieId() {
        return mMovieId;
    }

    public void setMovieId(String mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public void setMovieTitle(String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }

    public String getMovieImageUri() {
        return mMovieImageUri;
    }

    public void setMovieImageUri(String mMovieImageUri) {
        this.mMovieImageUri = mMovieImageUri;
    }

    public int getCastsCount() {
        return mCastsCount;
    }

    public void setCastsCount(int mCastsCount) {
        this.mCastsCount = mCastsCount;
    }

    public String[] getCastsIds() {
        return mCastsIds;
    }

    public void setCastsIds(String[] mCastsIds) {
        this.mCastsIds = mCastsIds;
    }

    public String[] getCastsImages() {
        return mCastsImages;
    }

    public void setCastsImages(String[] mCastsImages) {
        this.mCastsImages = mCastsImages;
    }

    public String getDirectorId() {
        return mDirectorId;
    }

    public void setDirectorId(String mDirectorId) {
        this.mDirectorId = mDirectorId;
    }

    public String getDirectorImage() {
        return mDirectorImage;
    }

    public void setDirectorImage(String mDirectorImage) {
        this.mDirectorImage = mDirectorImage;
    }

    public String getMovieScore() {
        return mMovieScore;
    }

    public void setMovieScore(String mMovieScore) {
        this.mMovieScore = mMovieScore;
    }

    public void fillDatas(String movieId, String movieTitle, String movieImageUri,
                          int castsCount, String[] castsIds, String[] castsImages,
                          String directorId, String directorImage, String movieScore) {
        setMovieId(movieId);
        setMovieTitle(movieTitle);
        setMovieImageUri(movieImageUri);
        setCastsCount(castsCount);
        setCastsIds(castsIds);
        setCastsImages(castsImages);
        setDirectorId(directorId);
        setDirectorImage(directorImage);
        setMovieScore(movieScore);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieMajorInfos> CREATOR = new Creator<MovieMajorInfos>() {
        @Override
        public MovieMajorInfos createFromParcel(Parcel in) {
            return new MovieMajorInfos(in);
        }

        @Override
        public MovieMajorInfos[] newArray(int size) {
            return new MovieMajorInfos[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMovieId);
        dest.writeString(mMovieTitle);
        dest.writeString(mMovieImageUri);
        dest.writeInt(mCastsCount);
        dest.writeStringArray(mCastsIds);
        dest.writeStringArray(mCastsImages);
        dest.writeString(mDirectorId);
        dest.writeString(mDirectorImage);
        dest.writeString(mMovieScore);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MovieMajorInfos {");
        sb.append("id: " + mMovieId + ", ");
        sb.append("title: " + mMovieTitle + ", ");
        sb.append("directorId: " + mDirectorId + ", ");
        sb.append("castCount: " + mCastsCount + "}");
        return sb.toString();
    }
}
