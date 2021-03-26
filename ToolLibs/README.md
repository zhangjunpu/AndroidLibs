# ToolLibrary

## AutoUpdate

讯飞自动升级
* 注意
此SDK版本较老，target必须设置为23或以下，否则报错

1. 跟AndroidManifest.xml中配置以下
```
<!-- 讯飞 -->
<meta-data android:name="IFLYTEK_CHANNEL" android:value="<CHANNEL_VALUE>" />
<!-- 讯飞 -->
<meta-data android:name="IFLYTEK_APPKEY" android:value="<VALUE>" />
```
2. 调用检查升级，toast是否吐司
```
AutoUpdateManager.getInstance().init(applicationContext).toast(true).checkShowDialog()
```

## Resources
基础资源库

## Utils
各种资源库