# DoubanTop
Material design UI以及豆瓣API网络访问练习。显示豆瓣电影排行榜，通过关键字进行电影搜索，可以记录搜索历史以及进行电影条目收藏。
# 功能介绍
程序用来练习网络交互以及Android Support lib的使用等，只使用到几个豆瓣API，功能比较简陋。另外由于豆瓣api的坑比较多（电影api普通开发者基本无法拿到全部的结构，比如排行榜只能拿到20条数据，电影详情的演员数只能拿到最多3个，而且每个演员的信息返回的也很少，很多都对普通开发者屏蔽了，唉，保护的真好啊~~~），程序显示的内容也就比较少啦，练习足矣。<br>
1. 北美票房排行榜<br>
2. 电影排行榜<br>
3. 电影搜索，搜索历史，关键字匹配<br>
4. 电影条目收藏与删除<br>
5. 语音识别(使用Baidu Voice)<br>
# APK
如果你想直接看效果，而不想编译整个工程，可以直接下载 [APK](https://github.com/dxjia/DoubanTop/releases/download/v1.0.0/DoubanTop.apk) 进行安装。
# 使用
下载下来的工程可以直接导入android studio编译并使用，因为douban电影api不带apikey其实也是可以访问的，只是每分钟次数限制在10次，而百度voice不带apikey是不能使用的，如果不需要看这个效果可以不用往工程里加key了。<br>
##添加api key
douban api访问带api_key可以每分钟40次。<br>
按照以下方法增加douban 和 baidu voice的 api key信息：<br>
1.在app\src\main\assets目录下新建文件```api_infos.xml```<br>
内容如下，api_key和secret修改成自己的就可以了。<br>
```xml
<?xml version="1.0" encoding="utf-8"?>
<api_infos>
    　　<api source="douban">
    　　　　<api_key>123456789655545464411</api_key>
    　　　　<secret>698712151515</secret>
    　　</api>
    　　<api source="baiduvoice">
    　　　　<api_key>avMereoiomfnnmsnfemtg</api_key>
    　　　　<secret>698712151515</secret>
    　　</api>
</api_infos>
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
RESTFUL API接口集成类库    [retrofit](https://github.com/square/retrofit)--square<br>
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
