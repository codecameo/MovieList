package com.example.hp.assessment.databinding.adapters;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hp.assessment.R;
import com.example.hp.assessment.utils.Constants;

/**
 * Created by Md. Sifat-Ul Haque on 6/17/2017.
 */

public class ImageBindAdapter {

    private static final String TAG = "ImageBindAdapter";

    @BindingAdapter(value={"imageUrl", "placeholder"}, requireAll=false)
    public static void setImageUrl(ImageView imageView, String url,
                                   Drawable placeHolder) {
        Log.d(TAG, "setImageUrl: "+url);

        if (!TextUtils.isEmpty(url))
            Glide.with(imageView.getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .dontAnimate()
                    .centerCrop()
                    .into(imageView);
        else if (placeHolder != null)
            Glide.with(imageView.getContext())
                    .load(Constants.EMPTY_STRING)
                    .placeholder(placeHolder)
                    .centerCrop()
                    .into(imageView);
        else
            Glide.with(imageView.getContext())
                    .load(R.drawable.movie_place_holder)
                    .into(imageView);
    }
}
