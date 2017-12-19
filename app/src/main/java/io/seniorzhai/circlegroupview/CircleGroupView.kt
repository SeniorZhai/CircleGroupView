package io.seniorzhai.circlegroupview

import android.content.Context
import android.graphics.*
import android.support.annotation.DrawableRes
import android.util.ArrayMap
import android.util.AttributeSet
import android.util.Size
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.Target
import java.lang.ref.WeakReference


class CircleGroupView : View {

    private val mPaint: Paint by lazy {
        Paint().apply { isAntiAlias = true }
    }

    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private var count = 0
    private val map = ArrayMap<String, Bitmap>()
    private var bitmapReference: WeakReference<Bitmap>? = null
    private var divider: Int = 0
    @DrawableRes
    private var placeHolder: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        val tArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
        divider = tArray.getDimensionPixelSize(R.styleable.CircleImageView_divider_size, 0)
        placeHolder = tArray.getResourceId(R.styleable.CircleImageView_place_holder, View.NO_ID)
        tArray.recycle()
    }

    private var maskBitmap: Bitmap? = null
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val displayBitmap: Bitmap? =
                if (bitmapReference == null) {
                    null
                } else {
                    bitmapReference!!.get()
                }
        if (displayBitmap == null || displayBitmap.isRecycled) {
            splice()
        } else {
            canvas.drawBitmap(displayBitmap, 0f, 0f, null)
        }
    }


    private fun splice() {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val drawCanvas = Canvas(bitmap)
        for (i in 0 until count) {
            val rect = getArea(i)
            val bp = map.valueAt(i)
            bp?.let {
                drawCanvas.drawBitmap(bp, null, rect, null)
            }
        }
        if (maskBitmap == null || maskBitmap!!.isRecycled) {
            maskBitmap = createMaskBitmap()
        }
        mPaint.reset()
        mPaint.isFilterBitmap = false
        mPaint.xfermode = xfermode

        drawCanvas.drawBitmap(maskBitmap!!, 0f, 0f, mPaint)
        bitmapReference = WeakReference(bitmap)
        mPaint.xfermode = null
        invalidate()
    }


    private fun createMaskBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)   //抗锯齿
        paint.color = Color.BLACK
        canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), (width / 2).toFloat(), paint)
        return bitmap
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        isShow = true
        loadImage()
    }

    private var isShow = false

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isShow = false
    }

    private fun loadImage() {
        for (i in 0 until count) {
            val size = getSize(i)
            if (count == 3) {
                Glide.with(this).asBitmap().apply(bitmapTransform(MultiTransformation(CenterCrop(), FanCrop(i, context)))).load(map.keyAt(i)).listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        map.put(map.keyAt(i), resource)
                        splice()
                        return false
                    }
                }).submit(size.width, size.height)
            } else {
                Glide.with(this).asBitmap().apply(bitmapTransform(CenterCrop())).load(map.keyAt(i)).listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        map.put(map.keyAt(i), resource)
                        splice()
                        return false
                    }
                }).submit(size.width, size.height)
            }
        }
    }

    fun loadImage(vararg url: String) {
        map.clear()
        count = url.size
        for (i in 0 until count) {
            map.put(url[i], BitmapFactory.decodeResource(context.resources, placeHolder))
        }
        if (isShow) {
            loadImage()
        }
    }

    private fun getSize(index: Int): Size {
        return when (count) {
            2 -> {
                Size(measuredWidth / 2 - divider, measuredHeight)
            }
            3 -> {
                if (index == 3) {
                    Size(measuredWidth, measuredHeight / 2)
                } else {
                    Size(measuredWidth / 2, measuredHeight)
                }
            }
            4 -> {
                Size(measuredWidth / 2 - divider, measuredHeight / 2 - divider)
            }
            else -> {
                Size(measuredWidth, measuredHeight)
            }
        }
    }

    private fun getArea(index: Int): Rect {
        return when (count) {
            2 -> {
                if (index == 0) {
                    Rect(0, 0, measuredWidth / 2 - divider, measuredHeight)
                } else {
                    Rect(measuredWidth / 2 + divider, 0, measuredWidth, measuredHeight)
                }
            }
            3 -> {
                when (index) {
                    0 -> Rect(0, 0, measuredWidth / 2, measuredHeight)
                    1 -> Rect(measuredWidth / 2, 0, measuredWidth, measuredHeight)
                    else -> Rect(0, measuredHeight / 2, measuredWidth, measuredHeight)
                }
            }
            4 -> {
                when (index) {
                    0 -> Rect(0, 0, measuredWidth / 2 - divider, measuredHeight / 2 - divider)
                    1 -> Rect(0, measuredHeight / 2 + divider, measuredWidth / 2 - divider, measuredHeight)
                    2 -> Rect(measuredWidth / 2 + divider, 0, measuredWidth, measuredHeight / 2 - divider)
                    else -> Rect(measuredWidth / 2 + divider, measuredHeight / 2 + divider, measuredWidth, measuredHeight)
                }
            }
            else -> Rect(0, 0, measuredWidth, measuredHeight)
        }
    }
}
