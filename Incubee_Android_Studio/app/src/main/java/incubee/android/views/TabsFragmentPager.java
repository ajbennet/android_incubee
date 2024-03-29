package incubee.android.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by samuh on 10/10/2015.
 */
public class TabsFragmentPager extends ViewPager {

    private boolean enabled;

    public TabsFragmentPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;

        this.setPageTransformer(false, new FadeOutTransformer());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    } }