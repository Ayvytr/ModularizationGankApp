package com.ayvytr.girl.adapter

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.ayvytr.baseadapter.CommonAdapter
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.easykotlin.context.getScreenWidth
import com.ayvytr.girl.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.util.*

/**
 * @author admin
 */
class GirlsAdapter(context: Context, layoutResId: Int) : CommonAdapter<Gank>(context, layoutResId) {
    private val halfScreenWidth: Int

    private val mPhotoSizeMap = LinkedHashMap<String, Int>()

    val options = RequestOptions()

    init {
        halfScreenWidth = context.getScreenWidth() / 2
        options.error(R.drawable.ic_launcher)
    }

    override fun convert(holder: ViewHolder, gank: Gank, position: Int) {
        val iv = holder.getView<ImageView>(R.id.iv)
        Glide.with(mContext)
            .asBitmap()
            .load(gank.url)
            .apply(options)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>,
                                          isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>,
                                             dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    var height: Int? = mPhotoSizeMap[gank.url]
                    if (height == null) {
                        height = halfScreenWidth * resource.height / resource.width
                        mPhotoSizeMap[gank.url!!] = height
                    }

                    val lp = holder.itemView.layoutParams
                    lp.width = halfScreenWidth
                    lp.height = height
                    return false
                }
            })
            .into(iv)
        iv.layoutParams.width = halfScreenWidth
    }
}
