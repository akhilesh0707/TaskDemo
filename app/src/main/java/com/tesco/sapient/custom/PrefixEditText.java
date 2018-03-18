package com.tesco.sapient.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Custom EditText Class to prefix $ or pound sign in EditText
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class PrefixEditText extends AppCompatEditText {

    float mOriginalLeftPadding = -1;

    /**
     * Constructor of PrefixEditText class
     *
     * @param context
     */
    public PrefixEditText(Context context) {
        super(context);
    }

    /**
     * Constructor of PrefixEditText class
     *
     * @param context
     * @param attrs
     */
    public PrefixEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor of PrefixEditText class
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public PrefixEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Measuring height and width
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        calculatePrefix();
    }

    /**
     * Calculating the prefect padding and position
     */
    private void calculatePrefix() {
        if (mOriginalLeftPadding == -1) {
            String prefix = (String) getTag();
            float[] widths = new float[prefix.length()];
            getPaint().getTextWidths(prefix, widths);
            float textWidth = 0;
            for (float w : widths) {
                textWidth += w;
            }
            mOriginalLeftPadding = getCompoundPaddingLeft();
            setPadding((int) (textWidth + mOriginalLeftPadding), getPaddingRight(), getPaddingTop(), getPaddingBottom());
        }
    }

    /**
     * OnDraw method to draw prefix and EditText
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String prefix = (String) getTag();
        canvas.drawText(prefix, mOriginalLeftPadding, getLineBounds(0, null), getPaint());
    }
}