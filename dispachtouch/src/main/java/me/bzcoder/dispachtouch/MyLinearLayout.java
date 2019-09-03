package me.bzcoder.dispachtouch;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author : BaoZhou
 * @date : 2019/9/3 16:57
 */
public class MyLinearLayout extends LinearLayout {
    private View viewA;
    private View viewB;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        viewA = getChildAt(0);
        viewB = getChildAt(1);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Rect rect = new Rect();
                int[] location = new int[2];
                viewA.getLocationOnScreen(location);
                rect.left = location[0];
                rect.top = location[1];
                rect.right = viewA.getWidth() + location[0];
                rect.bottom = viewA.getHeight() + location[1];
                if (rect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    intercept = true;
                }
            }
        }
        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect rect = new Rect();
        int[] location = new int[2];
        viewA.getLocationOnScreen(location);
        rect.left = location[0];
        rect.top = location[1];
        rect.right = viewA.getWidth() + location[0];
        rect.bottom = viewA.getHeight() + location[1];
        if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {

            return viewB.onTouchEvent(event);
        } else {

            return super.onTouchEvent(event);
        }
    }
}
