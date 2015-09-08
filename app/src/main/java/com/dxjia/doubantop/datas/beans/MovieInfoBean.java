package com.dxjia.doubantop.datas.beans;

import android.text.TextUtils;

import com.dxjia.doubantop.datas.beans.entities.AvatarsEntity;
import com.dxjia.doubantop.datas.beans.entities.CastsEntity;
import com.dxjia.doubantop.datas.beans.entities.DirectorsEntity;
import com.dxjia.doubantop.datas.beans.entities.ImagesEntity;
import com.dxjia.doubantop.datas.beans.entities.RatingEntity;
import com.dxjia.doubantop.datas.beans.entities.SubjectEntity;
import com.dxjia.doubantop.net.DoubanApiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djia on 15-6-24.
 */
public class MovieInfoBean {

    private SubjectEntity mMovieDetails;

    public MovieInfoBean(SubjectEntity mMovieDetails) {
        this.mMovieDetails = mMovieDetails;
    }

    public MovieInfoBean() {
    }

    /**
     * get formated generes
     * [动作, 科幻, 冒险]
     * @return
     */
    public String getFormatedGenres() {
        return (mMovieDetails == null) ? null : mMovieDetails.formatGenres();
    }

    /**
     * get the large image uri for this movie
     * @return uri
     */
    public String getImageUri() {
        if (mMovieDetails == null) {
            return null;
        }
        ImagesEntity images = mMovieDetails.getImages();
        if (images == null) {
            return null;
        }

        switch (BeansUtils.getImageSizePrefer()) {
            case BeansUtils.IMAGE_URI_USE_SMALL:
                return DoubanApiUtils.appendApiKey(images.getSmall(), true);
            case BeansUtils.IMAGE_URI_USE_MEDIUM:
                return DoubanApiUtils.appendApiKey(images.getMedium(), true);
            case BeansUtils.IMAGE_URI_USE_LARGE:
                return DoubanApiUtils.appendApiKey(images.getLarge(), true);
            default:
                return DoubanApiUtils.appendApiKey(images.getLarge(), true);
        }
    }

    /**
     * 获取ID
     * @return
     */
    public String getId() {
        return (mMovieDetails == null) ? null : mMovieDetails.getId();
    }

    /**
     * get title
     * @return
     */
    public String getTitle() {
        return (mMovieDetails == null) ? null : mMovieDetails.getTitle();
    }

    /**
     * 获取评分
     * @return
     */
    public String getAverage() {
        if (mMovieDetails == null) {
            return null;
        }
        RatingEntity rating = mMovieDetails.getRating();
        if (rating == null) {
            return null;
        }
        return (mMovieDetails == null) ? null : rating.getAverageStr();
    }

    /**
     * 获取主演个数
     * @return
     */
    public int getCastsCount() {
        if (mMovieDetails == null) {
            return 0;
        }

        String[] ids = getCastsIds();
        if (ids == null) {
            return 0;
        }
        return ids.length;
    }

    /**
     * 获取到所有主演的id数组
     * @return
     */
    public String[] getCastsIds() {
        if (mMovieDetails == null) return null;

        List<CastsEntity> casts = mMovieDetails.getCasts();
        if (casts == null || casts.size() == 0) return null;

        List<String> idArray = new ArrayList<String>();
        CastsEntity castsEntity;
        int count = 0;
        for (int i = 0; i < casts.size(); i++) {
            castsEntity = casts.get(i);
            // skip invalid casts
            if (castsEntity == null) {
                continue;
            }
            String id = castsEntity.getId();
            // skip invalid casts
            if (TextUtils.isEmpty(id)) {
                continue;
            }
            idArray.add(id);
            count++;
        }

        return idArray.toArray(new String[count]);
    }

    /**
     * 获取到所有的主演的头像图片uri数组
     * @return
     */
    public String[] getCastsAvatorUris() {
        if (mMovieDetails == null) return null;

        List<CastsEntity> casts = mMovieDetails.getCasts();
        if (casts == null || casts.size() == 0) return null;

        List<String> urisList = new ArrayList<>();

        CastsEntity castsEntity;
        int count = 0;
        for (int i = 0; i < casts.size(); i++) {
            castsEntity = casts.get(i);
            if (castsEntity == null) {
                continue;
            }
            AvatarsEntity avatars = castsEntity.getAvatars();
            if (avatars == null) {
                continue;
            }

            // skip invalid casts
            if (TextUtils.isEmpty(castsEntity.getId())) {
                continue;
            }

            switch (BeansUtils.getImageSizePrefer()) {
                case BeansUtils.IMAGE_URI_USE_SMALL:
                    urisList.add(DoubanApiUtils.appendApiKey(avatars.getSmall(), true));
                    break;
                case BeansUtils.IMAGE_URI_USE_MEDIUM:
                    urisList.add(DoubanApiUtils.appendApiKey(avatars.getMedium(), true));
                    break;
                case BeansUtils.IMAGE_URI_USE_LARGE:
                    urisList.add(DoubanApiUtils.appendApiKey(avatars.getLarge(), true));
                    break;
                default:
                    urisList.add(DoubanApiUtils.appendApiKey(avatars.getLarge(), true));
            }
            count++;
        }
        return urisList.toArray(new String[count]);
    }

    /**
     * 获取导演ID
     * @return
     */
    public String getDirectorId() {
        DirectorsEntity director = getDirector();
        if (director == null) {
            return null;
        }
        return (mMovieDetails == null) ? null : director.getId();
    }

    /**
     * 获取导演
     */
    private DirectorsEntity getDirector() {
        if (mMovieDetails == null) {
            return null;
        }
        List<DirectorsEntity> directors = mMovieDetails.getDirectors();
        if (directors == null || directors.size() == 0) {
            return null;
        }

        // just get the first for now
        DirectorsEntity director = directors.get(0);

        return director;
    }

    /**
     * 获取导演的图片地址
     * @return
     */
    public String getDirectorImageUri() {
        DirectorsEntity director = getDirector();
        if (director == null) {
            return null;
        }
        AvatarsEntity avatars = director.getAvatars();
        if (avatars == null) {
            return null;
        }

        switch (BeansUtils.getImageSizePrefer()) {
            case BeansUtils.IMAGE_URI_USE_SMALL:
                return DoubanApiUtils.appendApiKey(avatars.getSmall(), true);
            case BeansUtils.IMAGE_URI_USE_MEDIUM:
                return DoubanApiUtils.appendApiKey(avatars.getMedium(), true);
            case BeansUtils.IMAGE_URI_USE_LARGE:
                return DoubanApiUtils.appendApiKey(avatars.getLarge(), true);
            default:
                return DoubanApiUtils.appendApiKey(avatars.getLarge(), true);
        }
    }
}
