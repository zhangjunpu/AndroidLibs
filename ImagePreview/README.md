# ImagePreview
Android 图片预览

### 项目简介：
对图片进行预览，支持本地图片和网络图片，可以传入一张或一组图片地址。
项目采用Glide库和PhotoView进行简单封装。

依赖库版本：  
Glide：3.7.0  
PhotoView：1.3.1

### 使用方法：
**1. 添加远程仓库**

```
allprojects {
    repositories {
        ...
        maven { url "http://oss.abooc.com/repository/maven-public/" }
        maven { url "https://jitpack.io" }
    }
}
```

**2. 添加依赖**

```
compile 'com.junpu.imagepreview:imagepreview:1.0.0'
```

**3. 启动预览**

```
// IMAGE_URL：图片地址，可以是一个字符串、字符串数组或者ArrayList
ImagePreviewActivity.launch(getActivity(), IMAGE_URL);
```
