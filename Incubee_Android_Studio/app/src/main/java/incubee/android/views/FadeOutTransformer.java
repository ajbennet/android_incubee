package incubee.android.views;

import android.view.View;

/**
 * Created by samuh on 10/17/2015.
 */
public class FadeOutTransformer extends BaseTransformer {
    @Override
    protected boolean isPagingEnabled() {
        return true;
    }

    @Override
    protected void onTransform(View view, float position) {

        view.setTranslationX(view.getWidth() * -position);

        if(position <= -1.0F || position >= 1.0F) {
            view.setAlpha(0.0F);
        } else if( position == 0.0F ) {
            view.setAlpha(1.0F);
        } else {
            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
            view.setAlpha(1.0F - Math.abs(position));
        }
    }
}
