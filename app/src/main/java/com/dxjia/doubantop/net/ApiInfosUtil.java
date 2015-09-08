package com.dxjia.doubantop.net;

import android.util.Xml;

import com.dxjia.doubantop.DoubanTopApplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dxjia on 2015/7/23.
 */
public class ApiInfosUtil {
    /**
     * 以下信息用来从xml中解析douban apikey和baidu voice apikey信息
     */
    public static final String API_INFOS_FILE_NAME = "api_infos.xml";
    public static final String API_INFOS_SOURCE_TAG = "source";
    public static final String API_SOURCE_DOUBAN_STR = "douban";
    public static final String API_SOURCE_BAIDU_VOICE_STR = "baiduvoice";
    public static final String API_INFOS_TAG = "api_infos";
    public static final String API_TAG = "api";
    public static final String API_KEY_TAG = "api_key";
    public static final String API_SECRET_TAG = "secret";

    public static final int API_SOURCE_DOUBAN = 0;
    public static final int API_SOURCE_BAIDU_VOICE = 1;

    public static class ApiInfo {
        private int source;
        private String apikey;
        private String secret;

        public ApiInfo() {
            this(API_SOURCE_DOUBAN, "", "");
        }

        public ApiInfo(int source, String apikey, String secret) {
            this.source = source;
            this.apikey = apikey;
            this.secret = secret;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public int getSource() {
            return source;
        }

        public String getApikey() {
            return apikey;
        }

        public String getSecret() {
            return secret;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            if (source == API_SOURCE_DOUBAN) {
                sb.append("douban");
            } else if (source == API_SOURCE_BAIDU_VOICE) {
                sb.append("baiduvoice");
            }

            sb.append(", ");
            sb.append("apikey=[" + apikey + "], ");
            sb.append("secret=[" + secret + "]}");
            return sb.toString();
        }

    }

    public static List<ApiInfo> parseApiInfos() {

        List<ApiInfo> apiInfos = null;
        InputStream inputStream = null;

        try {
            inputStream = DoubanTopApplication.getContext().getAssets().open(API_INFOS_FILE_NAME);
            if (inputStream == null) {
                return null;
            }

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();
            ApiInfo currentApi = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        apiInfos = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if (tag.equalsIgnoreCase(API_TAG)) {
                            currentApi = new ApiInfo();
                            String source = parser.getAttributeValue(null, API_INFOS_SOURCE_TAG);
                            if (source.equalsIgnoreCase(API_SOURCE_DOUBAN_STR)) {
                                currentApi.setSource(API_SOURCE_DOUBAN);
                            } else if (source.equalsIgnoreCase(API_SOURCE_BAIDU_VOICE_STR)) {
                                currentApi.setSource(API_SOURCE_BAIDU_VOICE);
                            }
                        } else {
                            if (currentApi != null) {
                                if (tag.equalsIgnoreCase(API_KEY_TAG)) {
                                    currentApi.setApikey(parser.nextText());
                                } else if (tag.equalsIgnoreCase(API_SECRET_TAG)) {
                                    currentApi.setSecret(parser.nextText());
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equalsIgnoreCase(API_TAG) && currentApi != null) {
                            apiInfos.add(currentApi);
                            currentApi = null;
                        }
                        break;
                }

                eventType = parser.next();
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return apiInfos;
    }
}
