# HMAlbum
自定义微信相册
=====
自定义类似微信的相册

功能
----
1.多选
2.单选
3.裁剪
4.看大图

使用方法
----
```java
 new HM_StartAlbum
 .Bulide(this,new HM_PicassoEngine())//图片加载器
 .setMaxChoose(5)//多选数量 默认只能选择一张
 .bulide();

```

图片加载器
----
默认提供两个 HM_GlideEngine HM_PicassoEngine
启动相册时候需要传入加载器

权限适配
----
demo 中使用了google easypermissions


