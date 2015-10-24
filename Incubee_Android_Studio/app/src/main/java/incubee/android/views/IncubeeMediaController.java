package incubee.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.MediaController;

/**
 * Overridden by sanat :)
 */
public class IncubeeMediaController extends MediaController{
    public IncubeeMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IncubeeMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);

    }

    public IncubeeMediaController(Context context, View anchor) {

        super(context);
        super.setAnchorView(anchor);
    }



    @Override
    public void setAnchorView(View view)     {
        // Do nothing
    }

    @Override
    public void show() {
        show(0);
    }

    @Override
    public void show(int timeout) {
        super.show(0);
    }
}
