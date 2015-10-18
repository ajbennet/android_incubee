package incubee.android.views;

import android.content.Context;
import android.util.AttributeSet;
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

    public IncubeeMediaController(Context context) {
        super(context);
    }

    @Override
    public void hide() {

    }
}
