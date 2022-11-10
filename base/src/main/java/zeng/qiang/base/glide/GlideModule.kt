package zeng.qiang.base.glide

import android.annotation.SuppressLint
import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

@GlideModule
class GlideModule : AppGlideModule() {
    @SuppressLint("CheckResult")
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        /**
         * DiskCacheStrategy.NONE： 表示不缓存任何内容。
         * DiskCacheStrategy.DATA： 表示只缓存原始图片。
         * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
         * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
         * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
         */
        //默认配置
        builder.setDefaultRequestOptions(
            RequestOptions().apply
        {
            diskCacheStrategy(DiskCacheStrategy.ALL)
            format(DecodeFormat.PREFER_RGB_565)
            override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//解决某些图片模糊问题
        })

        builder.setMemoryCache(null)
        /**
         * 优先外部存储作为磁盘缓存目录，防止内部存储文件过大
         * 外部存储目录默认地址为：/sdcard/Android/data/com.sina.weibolite/cache/image_manager_disk_cache
         */
        /**
         * 优先外部存储作为磁盘缓存目录，防止内部存储文件过大
         * 外部存储目录默认地址为：/sdcard/Android/data/com.sina.weibolite/cache/image_manager_disk_cache
         */
//        builder.setDiskCache(ExternalCacheDiskCacheFactory(context))
        //设置Bitmap的缓存池
        //设置Bitmap的缓存池
//        builder.setBitmapPool(LruBitmapPool(30))

        //设置全局选项

        super.applyOptions(context, builder)

    }
}