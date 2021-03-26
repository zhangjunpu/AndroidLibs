# Toast

android全局自定义toast



## 使用方式

1. 在applicaiton中初始化toastContext

   ```kotlin
   toastContext = this
   ```

2. 调用toast方法、支持String、stringResId两种方式



### 自定义样式

* 方式1，修改现有布局的样式

  * 文字颜色：`<color name="toast_text"></color>`
  * 背景颜色：`<color name="toast_bg"></color>`
  * 自定义背景：用名字为`toast_bg`的`drawable`资源，与上边的背景颜色二选一

* 方式二，自定义toast布局

  创建名为`toast_view`的布局文件，内部需要放一个`id`为`textToast`的`TextView`

