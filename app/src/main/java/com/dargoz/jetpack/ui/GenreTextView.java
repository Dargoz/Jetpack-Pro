package com.dargoz.jetpack.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.dargoz.jetpack.R;
import com.dargoz.jetpack.utils.Utils;

public class GenreTextView extends AppCompatTextView {
    public GenreTextView(Context context) {
        super(context);
    }

    public GenreTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenreTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackground(getResources().getDrawable(R.drawable.genre_border_bg));
        setPadding(Utils.convertDpToPixel(8,getContext()),
                Utils.convertDpToPixel(4,getContext()),
                Utils.convertDpToPixel(8,getContext()),
                Utils.convertDpToPixel(4,getContext())
        );
        setTextSize(14);
        setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.leftMargin = 8;
        marginLayoutParams.rightMargin = 8;
        marginLayoutParams.bottomMargin = 16;
        setLayoutParams(marginLayoutParams);
    }
}
