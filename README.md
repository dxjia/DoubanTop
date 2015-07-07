# DoubanTop
Material design UI以及豆瓣API网络访问练习。显示豆瓣电影排行榜，通过关键字进行电影搜索，可以记录搜索历史以及进行电影条目收藏。代码设计并不是特别好，还有待改进。
# 功能介绍
程序用来练习网络交互以及Android Support lib的使用等，只使用到几个豆瓣API，功能比较简陋。另外由于豆瓣api的坑比较多（电影api普通开发者基本无法拿到全部的结构，比如排行榜只能拿到20条数据，电影详情的演员数只能拿到最多3个，而且每个演员的信息返回的也很少，很多都对普通开发者屏蔽了，唉，保护的真好啊~~~），程序显示的内容也就比较少啦，练习足矣。<br>
1. 北美票房排行榜<br>
2. 电影排行榜<br>
3. 电影搜索，搜索历史，关键字匹配<br>
4. 电影条目收藏与删除<br>
5. 语音识别(使用Baidu Voice)<br>
# 使用
使用之前请先将douban APIKEY改成自己的，这里写为空了，douban api访问频率有限制，不带api_key，每分钟只能请求10次，而带api_key可以每分钟访问40次。<br>
com.dxjia.doubantop.net.DoubanApiHelper<br>
```java
    // douban api key, change to yourself
    public final static String API_KEY = "";
    public final static String SECRET = "";
```
百度语音API_KEY也改成自己的<br>
com.dxjia.doubantop.net.BaiduVoiceUtils<br>
```java
    public static final String BAIDU_VOICE_API_KEY = "";
    public static final String BAIDU_VOICE_SECRET = "";
```
# ScreenShots
![Img](https://github.com/dxjia/DoubanTop/blob/master/screeshots/1.gif)
![Img](https://github.com/dxjia/DoubanTop/blob/master/screeshots/2.gif)<br>
![Img](https://github.com/dxjia/DoubanTop/blob/master/screeshots/3.gif)
![Img](https://github.com/dxjia/DoubanTop/blob/master/screeshots/4.gif)

# Reference & Thanks
[android-design-support-lib-sample](https://github.com/swissonid/android-design-support-lib-sample)--swissonid<br>
[material-design-library](https://github.com/DenisMondon/material-design-library)--DenisMondon
# Open Source
json解析利器    [gson](http://code.google.com/p/google-gson/)--Google<br>
对象-数据库ORM映射    [androrm](http://www.androrm.com/)--androrm<br>
android懒人库    [butterknife](https://github.com/JakeWharton/butterknife)--JakeWharton<br>
图片下载缓存    [picasso](https://github.com/square/picasso)--square<br>
高效http    [okhttp](https://github.com/square/okhttp)--square<br>
圆角图片    [selectableroundedimageview](https://github.com/pungrue26/SelectableRoundedImageView)--Joonho Kim<br>
BaiduVoice Android Studio版    [baiduvoicehelper](https://github.com/dxjia/BaiduVoiceHelper)--dxjia<br>
控件水波纹效果    [RippleEffect](https://github.com/traex/RippleEffect)--traex
# License
```
Copyright (C) 2015 dxjia

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
