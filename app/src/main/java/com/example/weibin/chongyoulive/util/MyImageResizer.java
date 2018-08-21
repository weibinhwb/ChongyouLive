package com.example.weibin.chongyoulive.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by weibinhuang on 18-5-4.
 */

public class MyImageResizer {

    private static final String TAG = "ImagerResizer";
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqHeight == 0 || reqWidth == 0) {
            return  1;
        }
        //原始高度 宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG, "origin, w=" + width + "h=" + height);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight= height / 2;
            final int halfWidth = width / 2;

            //计算最大的inSampleSize值
            //高度和宽度大于要求的高度和宽度
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }

    public static byte[] decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath , options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        BitmapFactory.decodeFile(filePath,options).compress(Bitmap.CompressFormat.JPEG, 70, baos);
        return baos.toByteArray();
    }
}
