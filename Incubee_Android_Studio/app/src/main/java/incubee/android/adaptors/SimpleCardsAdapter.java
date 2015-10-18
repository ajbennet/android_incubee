package incubee.android.adaptors;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sprylab.android.widget.TextureVideoView;

import java.util.ArrayList;

import incubee.android.R;
import incubee.android.views.GridImagesAdapter;
import incubee.android.views.IncubeeMediaController;
import stackedlist.view.CardStackAdapter;

public class SimpleCardsAdapter extends CardStackAdapter<String> {

	public SimpleCardsAdapter(Activity context, ArrayList<String> items) {
		super(context, items);
		
		
		mContext = context;

		mInflator = LayoutInflater.from(context);
		
	}

	private Activity mContext ;

	private LayoutInflater mInflator;


	@Override
	protected View getCardView(int position, String model, View convertView,
			ViewGroup parent) {
		if(convertView == null){
			convertView = mInflator.inflate(R.layout.card, parent, false);
		}

		RecyclerView recyclerView = (RecyclerView)convertView.findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);

		StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(gridLayoutManager);




		GridImagesAdapter rcAdapter = new GridImagesAdapter(mContext, null);
		recyclerView.setAdapter(rcAdapter);


		TextureVideoView textureVideoView = (TextureVideoView) convertView.findViewById(R.id.video_view);
		initVideoView(textureVideoView);

		return convertView;
	}

	private void initVideoView(final TextureVideoView textureVideoView) {
		final IncubeeMediaController mediaController = new IncubeeMediaController(mContext);
		textureVideoView.setVideoURI(getVideoUri());
		textureVideoView.setMediaController(mediaController);
		textureVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(final MediaPlayer mp) {
				Log.d("initVideoView", "onPrepared called!");
				startVideoPlayback(textureVideoView);
				mediaController.show(0);
			}
		});
	}

	private void startVideoPlayback(TextureVideoView textureVideoView) {
		// "forces" anti-aliasing - but increases time for taking frames - so keep it disabled
		// mVideoView.setScaleX(1.00001f);
		textureVideoView.start();
	}

	private Uri getVideoUri() {
//		return "android.resource://" + mContext.getPackageName() + "/" + R.raw.video;
		return Uri.parse("https://incubee-images.s3.amazonaws.com/vid_78fab564-c311-4cdd-8589-d4390673440e");
	}
}
