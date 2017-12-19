package io.seniorzhai.circlegroupview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.graphics.PathParser;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class FanCrop extends BitmapTransformation {
    private static final String ID = "io.seniorzhai.circlegroupview.FanCrop";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
    private int index;
    private Context context;
    private Paint paint;

    public FanCrop(int index, Context context) {
        super();
        this.context = context;
        this.index = index;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0XFF000000);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return BitmapUtils.getCroppedBitmap(toTransform, getHeartPath(toTransform));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FanCrop;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }


    @SuppressLint("RestrictedApi")
    private Path getHeartPath(Bitmap src) {
        return resizePath(PathParser.createPathFromPathData(context.getString(R.string.heart)),
                src.getWidth(), src.getHeight());
    }


    public static Path resizePath(Path path, float width, float height) {
        RectF bounds = new RectF(0, 0, width, height);
        Path resizedPath = new Path(path);
        RectF src = new RectF();
        resizedPath.computeBounds(src, true);

        Matrix resizeMatrix = new Matrix();
        resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.CENTER);
        resizedPath.transform(resizeMatrix);

        return resizedPath;
    }
}