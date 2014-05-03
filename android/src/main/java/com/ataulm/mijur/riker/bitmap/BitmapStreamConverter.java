package com.ataulm.mijur.riker.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ataulm.mijur.riker.StreamConverter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;

public class BitmapStreamConverter implements StreamConverter<Bitmap> {

    private static final int BUFFER_SIZE_BYTES = 128 * 1024;

    public Observable<Bitmap> observableConverting(final InputStream stream, final int width, final int height) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap decoded = decodeSampledBitmapFromResource(stream, width, height);
                subscriber.onNext(decoded);
                subscriber.onCompleted();
            }

            private Bitmap decodeSampledBitmapFromResource(InputStream inputStream, int width, int height) {
                BufferedInputStream wrappedStream = new BufferedInputStream(inputStream);
                wrappedStream.mark(BUFFER_SIZE_BYTES);

                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(wrappedStream, null, options);

                options.inSampleSize = calculateInSampleSize(options, width, height);
                options.inJustDecodeBounds = false;

                try {
                    wrappedStream.reset();
                } catch (IOException e) {
                    throw new RuntimeException("Failed to reset InputStream.");
                }

                return BitmapFactory.decodeStream(wrappedStream, null, options);
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
