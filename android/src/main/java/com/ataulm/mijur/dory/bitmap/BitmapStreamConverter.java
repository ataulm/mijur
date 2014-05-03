package com.ataulm.mijur.dory.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ataulm.mijur.dory.StreamConverter;

import java.io.*;

import rx.Observable;
import rx.Subscriber;

public class BitmapStreamConverter implements StreamConverter<Bitmap> {

    private static final int BUFFER_SIZE_BYTES = 128 * 1024;

    public Observable<Bitmap> observableConverting(final InputStream stream, final int width, final int height) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap decoded = decodeSampledBitmapFromSource(stream, width, height);
                subscriber.onNext(decoded);
                subscriber.onCompleted();
            }

            private byte[] byteArrayFrom(InputStream stream) {
                try {
                    return byteArrayFromStreamOrThrow(stream);
                } catch (IOException e) {
                    throw new RuntimeException("Couldn't create bytearray from stream", e);
                }
            }

            private byte[] byteArrayFromStreamOrThrow(InputStream stream) throws IOException {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[BUFFER_SIZE_BYTES];
                int n;
                while ((n = stream.read(buf)) >= 0) {
                    baos.write(buf, 0, n);
                }
                return baos.toByteArray();
            }

            private Bitmap decodeSampledBitmapFromSource(InputStream inputStream, int width, int height) {
                byte[] streamAsBytes = byteArrayFrom(inputStream);

                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new ByteArrayInputStream(streamAsBytes), null, options);

                options.inSampleSize = calculateInSampleSize(options, width, height);
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeStream(new ByteArrayInputStream(streamAsBytes), null, options);
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
