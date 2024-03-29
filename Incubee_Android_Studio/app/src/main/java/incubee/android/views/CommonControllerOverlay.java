package incubee.android.views;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * The common playback controller for the Movie Player or Video Trimming.
 */
public abstract class CommonControllerOverlay extends FrameLayout implements
        ControllerOverlay,
        OnClickListener,
        TimeBar.Listener {
    protected enum State {
        PLAYING,
        PAUSED,
        ENDED,
        ERROR,
        LOADING
    }
    private static final float ERROR_MESSAGE_RELATIVE_PADDING = 1.0f / 6;
    protected Listener mListener;
    protected final View mBackground;
    protected TimeBar mTimeBar;
    protected View mMainView;
    protected final LinearLayout mLoadingView;
    protected final TextView mErrorView;
    protected final ImageView mPlayPauseReplayView;
    protected State mState;
    protected boolean mCanReplay = true;
    public void setSeekable(boolean canSeek) {
        mTimeBar.setSeekable(canSeek);
    }
    public CommonControllerOverlay(Context context) {
        super(context);
        mState = State.LOADING;
        // TODO: Move the following layout code into xml file.
        LayoutParams wrapContent =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams matchParent =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mBackground = new View(context);
        mBackground.setBackgroundColor(Color.parseColor("#55000000"));
        addView(mBackground, matchParent);
        // Depending on the usage, the timeBar can show a single scrubber, or
        // multiple ones for trimming.
        createTimeBar(context);
        addView(mTimeBar, wrapContent);
        mTimeBar.setContentDescription("TimeBar");
        
        mLoadingView = new LinearLayout(context);
        mLoadingView.setOrientation(LinearLayout.VERTICAL);
        mLoadingView.setGravity(Gravity.CENTER_HORIZONTAL);
        ProgressBar spinner = new ProgressBar(context);
        spinner.setIndeterminate(true);
        mLoadingView.addView(spinner, wrapContent);
        TextView loadingText = createOverlayTextView(context);
        loadingText.setText("Loading Video");
        mLoadingView.addView(loadingText, wrapContent);
        addView(mLoadingView, wrapContent);
        mPlayPauseReplayView = new ImageView(context);
        mPlayPauseReplayView.setImageResource(android.R.drawable.ic_media_play);//R.drawable.icn_actions_playtrim
        mPlayPauseReplayView.setContentDescription("Play Video");      
        mPlayPauseReplayView.setScaleType(ScaleType.CENTER);
        mPlayPauseReplayView.setFocusable(true);
        mPlayPauseReplayView.setClickable(true);
        mPlayPauseReplayView.setOnClickListener(this);
        addView(mPlayPauseReplayView, wrapContent);
        mErrorView = createOverlayTextView(context);
        addView(mErrorView, matchParent);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        hide();
    }
    abstract protected void createTimeBar(Context context);
    private TextView createOverlayTextView(Context context) {
        TextView view = new TextView(context);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(0xFFFFFFFF);
        view.setPadding(0, 15, 0, 15);
        return view;
    }
    @Override
    public void setListener(Listener listener) {
        this.mListener = listener;
    }
    @Override
    public void setCanReplay(boolean canReplay) {
        this.mCanReplay = canReplay;
    }
    @Override
    public View getView() {
        return this;
    }
    @Override
    public void showPlaying() {
        mState = State.PLAYING;
        showMainView(mPlayPauseReplayView);
    }
    @Override
    public void showPaused() {
        mState = State.PAUSED;
        showMainView(mPlayPauseReplayView);
    }
    @Override
    public void showEnded() {
        mState = State.ENDED;
        if (mCanReplay) showMainView(mPlayPauseReplayView);
    }
    @Override
    public void showLoading() {
        mState = State.LOADING;
        showMainView(mLoadingView);
    }
    @Override
    public void showErrorMessage(String message) {
        mState = State.ERROR;
        int padding = (int) (getMeasuredWidth() * ERROR_MESSAGE_RELATIVE_PADDING);
        mErrorView.setPadding(
                padding, mErrorView.getPaddingTop(), padding, mErrorView.getPaddingBottom());
        mErrorView.setText(message);
        showMainView(mErrorView);
    }
    @Override
    public void setTimes(int currentTime, int totalTime,
            int trimStartTime, int trimEndTime) {
        mTimeBar.setTime(currentTime, totalTime, trimStartTime, trimEndTime);
    }
    public void hide() {
        mPlayPauseReplayView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.INVISIBLE);
        mBackground.setVisibility(View.INVISIBLE);
        mTimeBar.setVisibility(View.INVISIBLE);
        setVisibility(View.INVISIBLE);
        setFocusable(true);
        requestFocus();
    }
    private void showMainView(View view) {
        mMainView = view;
        mErrorView.setVisibility(mMainView == mErrorView ? View.VISIBLE : View.INVISIBLE);
        mLoadingView.setVisibility(mMainView == mLoadingView ? View.VISIBLE : View.INVISIBLE);
        mPlayPauseReplayView.setVisibility(
                mMainView == mPlayPauseReplayView ? View.VISIBLE : View.INVISIBLE);
        show();
    }
    @Override
    public void show() {
        updateViews();
        setVisibility(View.VISIBLE);
        setFocusable(false);
    }
    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view == mPlayPauseReplayView) {
                if (mState == State.ENDED) {
                    if (mCanReplay) {
                        mListener.onReplay();
                    }
                } else if (mState == State.PAUSED || mState == State.PLAYING) {
                    mListener.onPlayPause();
                }
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (super.onTouchEvent(event)) {
            return true;
        }
        return false;
    }
    // The paddings of 4 sides which covered by system components. E.g.
    // +-----------------+\
    // | Action Bar | insets.top
    // +-----------------+/
    // | |
    // | Content Area | insets.right = insets.left = 0
    // | |
    // +-----------------+\
    // | Navigation Bar | insets.bottom
    // +-----------------+/
    // Please see View.fitSystemWindows() for more details.
    private final Rect mWindowInsets = new Rect();
    @Override
    protected boolean fitSystemWindows(Rect insets) {
        // We don't set the paddings of this View, otherwise,
        // the content will get cropped outside window
        mWindowInsets.set(insets);
        return true;
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Rect insets = mWindowInsets;
        int pl = insets.left; // the left paddings
        int pr = insets.right;
        int pt = insets.top;
        int pb = insets.bottom;
        int h = bottom - top;
        int w = right - left;
        boolean error = mErrorView.getVisibility() == View.VISIBLE;
        int y = h - pb;
        // Put both TimeBar and Background just above the bottom system
        // component.
        // But extend the background to the width of the screen, since we don't
        // care if it will be covered by a system component and it looks better.
        mBackground.layout(0, y - mTimeBar.getBarHeight(), w, y);
        mTimeBar.layout(pl, y - mTimeBar.getPreferredHeight(), w - pr, y);
        // Put the play/pause/next/ previous button in the center of the screen
        layoutCenteredView(mPlayPauseReplayView, 0, 0, w, h);
        if (mMainView != null) {
            layoutCenteredView(mMainView, 0, 0, w, h);
        }
    }
    private void layoutCenteredView(View view, int l, int t, int r, int b) {
        int cw = view.getMeasuredWidth();
        int ch = view.getMeasuredHeight();
        int cl = (r - l - cw) / 2;
        int ct = (b - t - ch) / 2;
        view.layout(cl, ct, cl + cw, ct + ch);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }
    protected void updateViews() {
        mBackground.setVisibility(View.VISIBLE);
        mTimeBar.setVisibility(View.VISIBLE);
        Resources resources = getContext().getResources();
        int imageResource = 0;//R.drawable.icn_actions_retrytrim;
        String contentDescription = "Reload Video";
        if (mState == State.PAUSED) {
            imageResource = android.R.drawable.ic_media_play;//R.drawable.icn_actions_playtrim;
            contentDescription = "Play Video";
        } else if (mState == State.PLAYING) {
            imageResource = android.R.drawable.ic_media_pause;//R.drawable.icn_actions_pausetrim;
            contentDescription = "Pause Video";
        }
        mPlayPauseReplayView.setImageResource(imageResource);
        mPlayPauseReplayView.setContentDescription(contentDescription);
        mPlayPauseReplayView.setVisibility(
                (mState != State.LOADING && mState != State.ERROR &&
                !(mState == State.ENDED && !mCanReplay))
                ? View.VISIBLE : View.GONE);
        requestLayout();
    }
    // TimeBar listener
    @Override
    public void onScrubbingStart() {
        mListener.onSeekStart();
    }
    @Override
    public void onScrubbingMove(int time) {
        mListener.onSeekMove(time);
    }
    @Override
    public void onScrubbingEnd(int time, int trimStartTime, int trimEndTime) {
        mListener.onSeekEnd(time, trimStartTime, trimEndTime);
    }
}
