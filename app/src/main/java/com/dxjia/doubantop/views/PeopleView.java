package com.dxjia.doubantop.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxjia.doubantop.R;
import com.dxjia.doubantop.datas.beans.entities.CelebrityEntity;
import com.dxjia.doubantop.net.DoubanApiUtils;
import com.dxjia.doubantop.net.RetrofitCallback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 德祥 on 2015/6/29.
 * 用来显示影人信息
 */
public class PeopleView extends RelativeLayout {

    @InjectView(R.id.people_area)
    LinearLayout mPeopleArea;

    @InjectView(R.id.people_name)
    TextView mPeopleNameTitleView;

    @InjectView(R.id.people_avator)
    ImageView mPeopleAvator;
    Drawable mAvator;

    // 性别
    @InjectView(R.id.gender_area)
    LinearLayout mGenderArea;
    @InjectView(R.id.gender_content)
    TextView mGenderContent;
    private String mGender;

    // 星座
    @InjectView(R.id.constellation_area)
    LinearLayout mConstellationArea;
    @InjectView(R.id.constellation_content)
    TextView mConstellationContent;
    private String mConstellation;

    // 出生日期
    @InjectView(R.id.birthday_area)
    LinearLayout mBirthdayArea;
    @InjectView(R.id.birthday_content)
    TextView mBirthdayContent;
    private String mBirthday;

    // 出生地
    @InjectView(R.id.born_place_area)
    LinearLayout mBornPlaceArea;
    @InjectView(R.id.born_place_content)
    TextView mBornPlaceContent;
    private String mBornPlace;

    // 职业
    @InjectView(R.id.professions_area)
    LinearLayout mProfessionsArea;
    @InjectView(R.id.professions_content)
    TextView mProfessionsContent;
    private String mProfessions;

    // 英文名
    @InjectView(R.id.name_en_area)
    LinearLayout mNameenArea;
    @InjectView(R.id.name_en_content)
    TextView mNameenContent;
    private String mNameen;

    // 更多中文名
    @InjectView(R.id.aka_cn_area)
    LinearLayout mAkacnArea;
    @InjectView(R.id.aka_cn_content)
    TextView mAkacnContent;
    private String mAkacn;


    private DetailsUpdateHandler mDetailsUpdateHandler;
    private CelebrityEntity mCelebrityEntity;

    public PeopleView(Context context) {
        super(context);
    }

    public PeopleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.people_view_layout, this);
        ButterKnife.inject(this, view);
        init();
        mDetailsUpdateHandler = new DetailsUpdateHandler(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PeopleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        // avator
        setDefaultAvator();
        // 性别
        setGender("");
        // 星座
        setConstellation("");
        // 出生日期
        setBirthday("");
        // 出生地
        setBornPlace("");
        // 职业
        setProfessions("");
        // 英文名
        setNameen("");
        // 更多中文名
        setAkacn("");
    }

    public void cleanDetails() {
        init();
    }

    /**
     * 标题
     * @param name
     */
    private void setPeopleNameTitle(String name) {
        mPeopleNameTitleView.setText(name);
    }

    /**
     * 设置默认的头像显示
     */
    private void setDefaultAvator() {
        Drawable avator = getContext().getResources().getDrawable(R.mipmap.ic_unkown_image);
        setAvator(avator);
    }

    /**
     * 设置头像
     */
    public void setAvator(Drawable avator) {
        mAvator = avator;
        mPeopleAvator.setImageDrawable(mAvator);
    }

    /**
     * 设置性别
     * @param gender
     */
    public void setGender(String gender) {
        mGender = gender;
        if (TextUtils.isEmpty(mGender)) {
            mGenderArea.setVisibility(GONE);
        } else {
            mGenderArea.setVisibility(VISIBLE);
            mGenderContent.setText(mGender);
        }
    }

    /**
     * 设置星座
     * @param constellation
     */
    public void setConstellation(String constellation) {
        mConstellation = constellation;
        if (TextUtils.isEmpty(mConstellation)) {
            mConstellationArea.setVisibility(GONE);
        } else {
            mConstellationArea.setVisibility(VISIBLE);
            mConstellationContent.setText(mConstellation);
        }
    }

    /**
     * 设置生日
     * @param birthday
     */
    public void setBirthday(String birthday) {
        mBirthday = birthday;
        if (TextUtils.isEmpty(mBirthday)) {
            mBirthdayArea.setVisibility(GONE);
        } else {
            mBirthdayArea.setVisibility(VISIBLE);
            mBirthdayContent.setText(mBirthday);
        }
    }

    /**
     * 设置出生地
     * @param bornPlace
     */
    public void setBornPlace(String bornPlace) {
        mBornPlace = bornPlace;
        if (TextUtils.isEmpty(mBornPlace)) {
            mBornPlaceArea.setVisibility(GONE);
        } else {
            mBornPlaceArea.setVisibility(VISIBLE);
            mBornPlaceContent.setText(mBornPlace);
        }
    }

    /**
     * 设置职业
     * @param professions
     */
    public void setProfessions(String professions) {
        mProfessions = professions;
        if (TextUtils.isEmpty(mProfessions)) {
            mProfessionsArea.setVisibility(GONE);
        } else {
            mProfessionsArea.setVisibility(VISIBLE);
            mProfessionsContent.setText(mProfessions);
        }
    }

    /**
     * 设置英文名
     * @param nameen
     */
    public void setNameen(String nameen) {
        mNameen = nameen;
        if (TextUtils.isEmpty(mNameen)) {
            mNameenArea.setVisibility(GONE);
        } else {
            mNameenArea.setVisibility(VISIBLE);
            mNameenContent.setText(mNameen);
        }
    }

    /**
     * 设置更多中文名
     * @param akacn
     */
    public void setAkacn(String akacn) {
        mAkacn = akacn;
        if (TextUtils.isEmpty(mAkacn)) {
            mAkacnArea.setVisibility(GONE);
        } else {
            mAkacnArea.setVisibility(VISIBLE);
            mAkacnContent.setText(mAkacn);
        }
    }

    /**
     * 设置这个VIEW对应的影人id以及其图像uri
     * 目前的做法是把具体影人信息的获取放在这个view里触发
     * 这样是不是不太好呢，跟UI耦合到一块去啦
     * @param id
     * @param imageUri
     */
    public void setPeopleInfo(String id, String imageUri) {
        // 更新头像
        updatePeopleAvator(imageUri);

        // 更新详情
        updateDetails(id);
    }

    private void updatePeopleAvator(String imageUri) {
        Context context = mPeopleAvator.getContext();
        if (TextUtils.isEmpty(imageUri)) {
            Picasso.with(context)
                    .load(R.mipmap.ic_unkown_image)
                    .resize(140, 200)
                    .centerCrop()
                    .into(mPeopleAvator);
            return;
        }
        Picasso.with(context)
                .load(imageUri)
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_unkown_image)
                .resize(140, 200)
                .centerCrop()
                .into(mPeopleAvator);
    }

    /**
     * 豆瓣提供的API很坑，影人API只能返回有限的信息
     * 像星座、职业、出生日期等豆瓣都做了限制，普通的开发者这些都给屏蔽啦
     *
     * 坑~~~
     *
     * @param id
     */
    private void updateDetails(String id) {
        if (TextUtils.isEmpty(id)) {
            Log.e("PeoPleView", "null id when update details");
            mPeopleNameTitleView.setText("Unkown");
            return;
        }
        int celebrityId = Integer.valueOf(id);
        DoubanApiUtils.getMovieApiService().getCelebrityDetails(celebrityId, DoubanApiUtils.API_KEY,
                new RetrofitCallback<>(mDetailsUpdateHandler, EVENT_UPDATE_DONE, EVENT_UPDATE_FAILED, CelebrityEntity.class));

    }

    private static final int EVENT_UPDATE_FAILED = 102;
    private static final int EVENT_UPDATE_DONE = 103;

    /**
     * UI update handler
     */
    private class DetailsUpdateHandler extends Handler {
        private final Context mContext;

        public DetailsUpdateHandler(Context context) {
            mContext = context;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_UPDATE_FAILED:

                    break;
                case EVENT_UPDATE_DONE:
                    if (msg.obj == null) {
                        break;
                    }
                    mCelebrityEntity = (CelebrityEntity) msg.obj;
                    setPeopleNameTitle(mCelebrityEntity.getName());
                    setGender(mCelebrityEntity.getGender());
                    setBornPlace(mCelebrityEntity.getBorn_place());
                    setNameen(mCelebrityEntity.getName_en());
                    setAkacn(mCelebrityEntity.getAkaStr());
                    break;
            }

            super.handleMessage(msg);
        }
    }
}
