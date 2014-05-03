package com.ataulm.mijur.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.ataulm.mijur.R;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

class TypefaceFactory {

    private static final Hashtable<FontType, SoftReference<Typeface>> FONT_CACHE = new Hashtable<FontType, SoftReference<Typeface>>();
    private static final int INVALID_FONT_ID = -1;
    private static final Typeface DEFAULT_TYPEFACE = null;

    public Typeface createFrom(Context context, AttributeSet attrs) {
        int fontId = getFontId(context, attrs);
        if (!isValidId(fontId)) {
            return DEFAULT_TYPEFACE;
        }
        FontType fontType = getFontType(fontId);
        return getTypeFace(context, fontType);
    }

    private int getFontId(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextAppearance);
        if (typedArray == null) {
            return INVALID_FONT_ID;
        }

        try {
            return typedArray.getInt(R.styleable.CustomTextAppearance_font, INVALID_FONT_ID);
        } finally {
            typedArray.recycle();
        }
    }

    private boolean isValidId(int fontId) {
        return fontId > INVALID_FONT_ID && fontId < FontType.values().length;
    }

    private FontType getFontType(int fontId) {
        return FontType.values()[fontId];
    }

    private Typeface getTypeFace(Context context, FontType fontType) {
        synchronized (FONT_CACHE) {
            if (fontExistsInCache(fontType)) {
                return getCachedTypeFace(fontType);
            }

            Typeface typeface = createTypeFace(context, fontType);
            saveFontToCache(fontType, typeface);

            return typeface;
        }
    }

    private boolean fontExistsInCache(FontType fontType) {
        return FONT_CACHE.get(fontType) != null && getCachedTypeFace(fontType) != null;
    }

    private Typeface getCachedTypeFace(FontType fontType) {
        return FONT_CACHE.get(fontType).get();
    }

    private Typeface createTypeFace(Context context, FontType fontType) {
        return Typeface.createFromAsset(context.getAssets(), fontType.assetUrl);
    }

    private void saveFontToCache(FontType fontType, Typeface typeface) {
        FONT_CACHE.put(fontType, new SoftReference<Typeface>(typeface));
    }

}