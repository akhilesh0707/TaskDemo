package com.tesco.sapient.util;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * KeyBoard utility class to manger soft keyboard
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class KeyboardUtil {

    /**
     * Hide soft keyboard
     */
    public static void hideSoftKeyboard(Context context) {
        View focusedView = ((Activity) context).getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}