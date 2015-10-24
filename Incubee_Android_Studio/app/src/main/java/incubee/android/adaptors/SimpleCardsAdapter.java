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
import android.widget.TextView;

import com.sprylab.android.widget.TextureVideoView;

import java.util.ArrayList;

import incubee.android.R;
import incubee.android.views.GridImagesAdapter;
import incubee.android.views.IncubeeMediaController;
import services.models.IncubeeProfile;
import stackedlist.view.CardStackAdapter;

public class SimpleCardsAdapter extends CardStackAdapter<IncubeeProfile> {

	private ArrayList<IncubeeProfile> mIncubeeProfileList;
	private final String TAG = "SimpleCardsAdapter";

	public SimpleCardsAdapter(Activity context, ArrayList<IncubeeProfile> items) {
		super(context, items);
		
		mIncubeeProfileList = items;
		mContext = context;

		mInflator = LayoutInflater.from(context);
		
	}

	private Activity mContext ;

	private LayoutInflater mInflator;


	@Override
	protected View getCardView(int position, IncubeeProfile model, View convertView,
			ViewGroup parent) {
		if(convertView == null){
			convertView = mInflator.inflate(R.layout.card, parent, false);
		}

		RecyclerView recyclerView = (RecyclerView)convertView.findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);

		StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(gridLayoutManager);


        View anchor = convertView.findViewById(R.id.anchor_view);
        TextView companyName = (TextView) convertView.findViewById(R.id.company_name);

        companyName.setText(mIncubeeProfileList.get(position).getCompany_name());


		GridImagesAdapter rcAdapter = new GridImagesAdapter(mContext, mIncubeeProfileList.get(position).getImages());
		recyclerView.setAdapter(rcAdapter);


		TextureVideoView textureVideoView = (TextureVideoView) convertView.findViewById(R.id.video_view);
		initVideoView(textureVideoView, anchor, position);

		return convertView;
	}

	private void initVideoView(final TextureVideoView textureVideoView, final View anchor, int position) {
		final IncubeeMediaController mediaController = new IncubeeMediaController(mContext, anchor);
		textureVideoView.setVideoURI(getVideoUri(position));
     	textureVideoView.setMediaController(mediaController);

		textureVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				textureVideoView.start();
				textureVideoView.seekTo(100);
				mediaController.show();
				textureVideoView.pause();

			}
		});




	}

	private void startVideoPlayback(TextureVideoView textureVideoView) {
		// "forces" anti-aliasing - but increases time for taking frames - so keep it disabled
		// mVideoView.setScaleX(1.00001f);
		textureVideoView.start();
	}

	private void stopVideoPlayback(TextureVideoView textureVideoView){
		textureVideoView.stopPlayback();
	}


	public IncubeeProfile getTopCard() {
		int pos = getNormalizedPosition(getCount() - 1);
		return mIncubeeProfileList.get(pos);
	}

	private Uri getVideoUri(int position) {

		String url = mIncubeeProfileList.get(position).getVideo_url();
		try {
			return Uri.parse(url);
		} catch(Exception e) {
			Log.e(TAG, "exception; getVideoUri "+e.getMessage()+ " url: "+url);
			return Uri.parse("https://incubee-images.s3.amazonaws.com/vid_78fab564-c311-4cdd-8589-d4390673440e");
		}
	}
}
