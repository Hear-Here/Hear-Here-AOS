package com.hearhere.presentation.util

import android.graphics.*
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.min

@RequiresApi(Build.VERSION_CODES.N)
fun Bitmap.getCircledBitmap(): Bitmap {
    val squareBitmapWidth = min(this.width, this.height)
    val dstBitmap = Bitmap.createBitmap(
        squareBitmapWidth, squareBitmapWidth, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(dstBitmap)
    val paint = Paint()
    paint.isAntiAlias = true

    val rect = Rect(0, 0, squareBitmapWidth, squareBitmapWidth)
    val rectF = RectF(rect)

    canvas.drawOval(rectF, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    val left = ((squareBitmapWidth - this.width) / 2).toFloat()
    val top = ((squareBitmapWidth - this.height) / 2).toFloat()

    canvas.drawBitmap(this, left, top, paint)
    // this.recycle()
    return dstBitmap
}
