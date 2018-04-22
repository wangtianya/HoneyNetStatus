
package com.qjuzi.yaa.other.deprecated.imagev1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageUtil {


    public static Bitmap makeReflectionBitmap(Bitmap originalImage) {
        final int reflectionGap = 4;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        // 实现图片的反转
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        matrix.postRotate(360f);

        // 创建反转后的图片Bitmap对象，图片高是原图的一半。
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
        // 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
        // 创建画布对象，将原图画于画布，起点是原点位置。
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);// (originalImage, matrix,
        // new Paint());
        // 将反转后的图片画到画布中。
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        // 创建线性渐变LinearGradient 对象。
        LinearGradient shader =
                new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap,
                                          0X70ffffff, 0X00ffffff, TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
        return bitmapWithReflection;
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        try {
            // 4. inNativeAlloc 属性设置为true，可以不把使用的内存算到VM里
            BitmapFactory.Options.class.getField("inNativeAlloc").setBoolean(options, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, options);
    }

    // 将byte[]转换成InputStream
    public static InputStream Byte2InputStream(byte[] b) {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        return bais;
    }

    // 将InputStream转换成byte[]
    public static byte[] InputStream2Bytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        @SuppressWarnings("unused")
        // TODO
                int readCount = -1;
        try {
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 将Bitmap转换成InputStream
    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 将Bitmap转换成InputStream
    public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 将InputStream转换成Bitmap
    public static Bitmap InputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    // Drawable转换成InputStream
    public static InputStream Drawable2InputStream(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return Bitmap2InputStream(bitmap);
    }

    // InputStream转换成Drawable
    public static Drawable InputStream2Drawable(InputStream is) {
        Bitmap bitmap = InputStream2Bitmap(is);
        return bitmap2Drawable(bitmap);
    }

    /**
     * 图片转换 图片转字节
     *
     * @param bm
     *
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // Drawable转换成byte[]
    public static byte[] Drawable2Bytes(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return Bitmap2Bytes(bitmap);
    }

    // byte[]转换成Drawable
    public static Drawable Bytes2Drawable(byte[] b) {
        Bitmap bitmap = Bytes2Bitmap(b);
        return bitmap2Drawable(bitmap);
    }

    // byte[]转换成Bitmap
    public static Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    // Drawable转换成Bitmap
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                                                   drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                                                           : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap转换成Drawable
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        @SuppressWarnings("deprecation")
        // TODO
                BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }
}
