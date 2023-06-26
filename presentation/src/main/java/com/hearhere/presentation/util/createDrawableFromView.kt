package com.hearhere.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import com.hearhere.presentation.common.component.MarkerImageView

fun Context.createDrawableFromView(bitmap: Bitmap, isSelected: Boolean): Bitmap {
    val customView = MarkerImageView(this)
    customView.setMarkerFocus(isSelected)
    customView.setMarkerBitmapImage(bitmap)

    customView.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )

    val bmp = Bitmap.createBitmap(
        customView.measuredWidth, customView.measuredHeight, Bitmap.Config.ARGB_8888
    )
    customView.layout(0, 0, customView.measuredWidth, customView.measuredHeight)
    customView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

    val canvas = Canvas(bmp)
    customView.draw(canvas)

    return bmp
}
