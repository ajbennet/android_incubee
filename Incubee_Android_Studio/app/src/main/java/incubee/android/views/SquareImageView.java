package incubee.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Custom View that is always Square!
 * @author samuh
 *
 */
public class SquareImageView extends ImageView {

	public SquareImageView(Context context) {
		super(context);
		
	}

	public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public SquareImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		setMeasuredDimension(width, width);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		super.onSizeChanged(w, w, oldw, oldh);
		
	}
	

}
