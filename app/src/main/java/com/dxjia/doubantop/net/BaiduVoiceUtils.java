package com.dxjia.doubantop.net;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.dxjia.library.BaiduVoiceHelper;

import java.util.List;


/**
 * Created by 德祥 on 2015/7/3.
 */
public class BaiduVoiceUtils {
    // baidu voice api key, change to yourself in assets/api_infos.xml
    public static final String BAIDU_VOICE_API_KEY = getBaiduVoiceApiKey();
    public static final String BAIDU_VOICE_SECRET = getBaiduVoiceApiSecret();

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


    private static String getBaiduVoiceApiKey() {
        String apikey = "";
        // parsing xml
        List<ApiInfosUtil.ApiInfo> apiInfos = ApiInfosUtil.parseApiInfos();
        if (apiInfos != null) {
            for (ApiInfosUtil.ApiInfo apiInfo : apiInfos) {
                if (apiInfo.getSource() == ApiInfosUtil.API_SOURCE_BAIDU_VOICE) {
                    apikey = apiInfo.getApikey();
                    break;
                }
            }
        }

        return apikey;
    }

    private static String getBaiduVoiceApiSecret() {
        String secret = "";
        // parsing xml
        List<ApiInfosUtil.ApiInfo> apiInfos = ApiInfosUtil.parseApiInfos();
        if (apiInfos != null) {
            for (ApiInfosUtil.ApiInfo apiInfo : apiInfos) {
                if (apiInfo.getSource() == ApiInfosUtil.API_SOURCE_BAIDU_VOICE) {
                    secret = apiInfo.getSecret();
                    break;
                }
            }
        }

        return secret;
    }

}
