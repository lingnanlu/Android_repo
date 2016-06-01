package io.github.lingnanlu.customviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by rabbit on 5/28/2016.
 */
public class PieChart extends View {

    boolean mShowText;
    int mTextPos;
    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PieChart,
                0, 0
        );

        try{
            mShowText = a.getBoolean(R.styleable.PieChart_showText1, false);
            mTextPos = a.getInteger(R.styleable.PieChart_labelPosition1, 0);
        } finally {
            a.recycle();
        }

    }

    public int getTextpos(){
        return mTextPos;
    }

    public boolean getShowText() {
        return mShowText;
    }
}
