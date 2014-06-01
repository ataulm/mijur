package com.ataulm.mijur.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ZoomingImageView extends ImageView  {

    private final PhotoViewAttacher attacher;

    public ZoomingImageView(Context context, AttributeSet attr) {
        super(context, attr);
        attacher = new PhotoViewAttacher(this);
    }

    public ZoomingImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        attacher = new PhotoViewAttacher(this);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        attacher.update();
    }

    @Override
    public void setOnClickListener(final OnClickListener l) {
        if (l == null) {
            attacher.setOnViewTapListener(null);
        } else {
            attacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

                @Override
                public void onViewTap(View view, float v, float v2) {
                    l.onClick(view);
                }

            });
        }
    }

}
