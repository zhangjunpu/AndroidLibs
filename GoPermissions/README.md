# GoPermissions
android permissions request


## 使用方法

1. 继承 PermissionsActivity

2. 调用checkPermissions方法

   ```kotlin
   checkPermissions(
     arrayOf(
       Manifest.permission.READ_EXTERNAL_STORAGE,
       Manifest.permission.WRITE_EXTERNAL_STORAGE
     )
   ) {
     if (it) {
       // todo request permissions success
     } else {
       // todo request permissions failed
     }
   }
   ```
   
   

