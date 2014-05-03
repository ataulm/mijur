package com.ataulm.mijur.dory.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ataulm.mijur.dory.StreamConverter;

import java.io.*;

import rx.Observable;
import rx.Subscriber;

public class BitmapStreamConverter implements StreamConverter<Bitmap> {

    public Observable<Bitmap> observableConverting(final byte[] input, final int width, final int height) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap decoded = decodeSampledBitmapFromSource(input, width, height);
                subscriber.onNext(decoded);
                subscriber.onCompleted();
            }

            private Bitmap decodeSampledBitmapFromSource(byte[] input, int width, int height) {
                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new ByteArrayInputStream(input), null, options);

                options.inSampleSize = calculateInSampleSize(options, width, height);
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeStream(new ByteArrayInputStream(input), null, options);
            }

            private int calculateInSampleSize(BitmapFactory.Options options, int targetWidth, int targetHeight) {
                // Raw height and width of image
                final int height = options.outHeight;
                final int width = options.outWidth;
                int inSampleSize = 1;

                if (height > targetHeight || width > targetWidth) {

                    final int halfHeight = height / 2;
                    final int halfWidth = width / 2;

                    // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                    // height and width larger than the requested height and width.
                    while ((halfHeight / inSampleSize) > targetHeight
                            && (halfWidth / inSampleSize) > targetWidth) {
                        inSampleSize *= 2;
                    }
                }

                return inSampleSize;
            }

        });
    }

}
