package com.ccpp.pgw.android.demo.merchant.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class ImageHelper {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }
}
