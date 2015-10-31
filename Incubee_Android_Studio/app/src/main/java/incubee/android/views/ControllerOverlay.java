package incubee.android.views;

import android.view.View;


/**
 * Ported from Android Gallery code base. 
 * See: android.googlesource.com/platform/packages/apps/Gallery2/
 * 
 * Interface method that a view acting as VideoView/MediaPlayer controller 
 * should implement.
 *
 */
public interface ControllerOverlay {
	interface Listener {
		void onPlayPause();
		void onSeekStart();
		void onSeekMove(int time);
		void onSeekEnd(int time, int trimStartTime, int trimEndTime);
		void onShown();
		void onHidden();
		void onReplay();
	}
	void setListener(Listener listener);
	void setCanReplay(boolean canReplay);
	/**
	 * @return The overlay view that should be added to the player.
	 */
	View getView();
	void show();
	void showPlaying();
	void showPaused();
	void showEnded();
	void showLoading();
	void showErrorMessage(String message);
	void setTimes(int currentTime, int totalTime,
				  int trimStartTime, int trimEndTime);
}
