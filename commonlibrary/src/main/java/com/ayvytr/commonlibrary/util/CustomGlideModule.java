package com.ayvytr.commonlibrary.util;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public final class CustomGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    //        设置缓存大小为20mb
        int memoryCacheSizeBytes = 1024 * 1024 * 30; // 20mb
    //        设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
    //        根据SD卡是否可用选择是在内部缓存还是SD卡缓存
    }
}