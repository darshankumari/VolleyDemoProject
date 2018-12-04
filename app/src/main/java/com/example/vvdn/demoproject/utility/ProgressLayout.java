package com.example.vvdn.demoproject.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
public class ProgressLayout extends FrameLayout {
    private int spotsCount;

    public ProgressLayout(Context context) {
        this(context, null);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.Dialog, defStyleAttr, defStyleRes);
//        this.spotsCount = a.getInt(R.styleable.Dialog_DialogSpotCount, 5);
//        a.recycle();
    }

    public int getSpotsCount() {
        return this.spotsCount;
    }
}
