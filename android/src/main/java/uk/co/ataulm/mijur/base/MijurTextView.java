package uk.co.ataulm.mijur.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import uk.co.ataulm.mijur.R;

public class MijurTextView extends TextView {

    private final FontWriter fontWriter;

    public MijurTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fontWriter = FontWriter.create(this, attrs, R.styleable.MijurTextView);
        init();
    }

    public MijurTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        fontWriter = FontWriter.create(this, attrs, R.styleable.MijurTextView);
        init();
    }

    private void init() {
        setCustomFont(R.styleable.MijurTextView_font);
    }

    private void setCustomFont(int customFontId) {
        fontWriter.setCustomFont(customFontId);
    }

    public void setCustomFont(String customFontName) {
        fontWriter.setCustomFont(customFontName);
    }
}
