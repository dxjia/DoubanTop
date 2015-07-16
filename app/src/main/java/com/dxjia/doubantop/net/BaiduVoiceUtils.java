package com.dxjia.doubantop.net;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dxjia.library.BaiduVoiceHelper;


/**
 * Created by 德祥 on 2015/7/3.
 */
public class BaiduVoiceUtils {
    // TODO personal infos, hide this when push to github
    public static final String BAIDU_VOICE_API_KEY = "";
    public static final String BAIDU_VOICE_SECRET = "";

    /**
     *
     * @param activity
     * @param requestId
     */
    public static void startVoiceRecognizer(Activity activity, int requestId) {
        BaiduVoiceHelper.startBaiduVoiceDialogForResult(activity, BAIDU_VOICE_API_KEY, BAIDU_VOICE_SECRET, requestId);
    }

    /**
     *
     * @param activity
     * @param intent
     * @param requestId
     */
    public static void startVoiceRecognizer(Activity activity, Intent intent, int requestId) {
        BaiduVoiceHelper.startBaiduVoiceDialogForResult(activity, BAIDU_VOICE_API_KEY, BAIDU_VOICE_SECRET, intent, requestId);
    }

    public static void startVoiceRecognizer(Fragment fragment, int requestId) {
        BaiduVoiceHelper.startBaiduVoiceForV4Fragment(fragment, BAIDU_VOICE_API_KEY, BAIDU_VOICE_SECRET, requestId);
    }


}
