package incubee.android.adaptors;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import incubee.android.views.MoviePlayer;
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
	protected View getCardView(int position, final IncubeeProfile model, View convertView,
			ViewGroup parent) {
		if(convertView == null){
			convertView = mInflator.inflate(R.layout.card, parent, false);
		}

		RecyclerView recyclerView = (RecyclerView)convertView.findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(false);

		GridLayoutManager manager = new GridLayoutManager(mContext, 2);
		recyclerView.setLayoutManager(manager);


        View anchor = convertView.findViewById(R.id.anchor_view);
        TextView companyName = (TextView) convertView.findViewById(R.id.company_name);
        TextView companyFounder = (TextView) convertView.findViewById(R.id.company_founder);

        companyName.setText(mIncubeeProfileList.get(position).getCompany_name());
        companyFounder.setText(mIncubeeProfileList.get(position).getFounder());


		GridImagesAdapter rcAdapter = new GridImagesAdapter(mContext, mIncubeeProfileList.get(position).getImages());
		recyclerView.setAdapter(rcAdapter);


		TextureVideoView textureVideoView = (TextureVideoView) convertView.findViewById(R.id.video_view);
		initVideoView(convertView, textureVideoView, anchor, position);

		return convertView;
	}

	private void initVideoView(final View root, final TextureVideoView textureVideoView, final View anchor, int position) {
		final IncubeeMediaController mediaController = new IncubeeMediaController(mContext, anchor);
		if(getVideoUri(position) == null) {
			textureVideoView.setVisibility(View.GONE);
			return;
		} else {
			textureVideoView.setVisibility(View.VISIBLE);
		}


		MoviePlayer player = new MoviePlayer(anchor, mContext,
		getVideoUri(position), true,
		textureVideoView);
	}

	private void startVideoPlayback(TextureVideoView textureVideoView) {
		// "forces" anti-aliasing - but increases time for taking frames - so keep it disabled
		// mVideoView.setScaleX(1.00001f);
		textureVideoView.start();
	}

	private void stopVideoPlayback(TextureVideoView textureVideoView){
		textureVideoView.stopPlayback();
	}

	private Uri getVideoUri(int position) {

		String url = mIncubeeProfileList.get(position).getVideo();
		try {
			return Uri.parse(url);
		} catch(Exception e) {
			Log.e(TAG, "exception; getVideoUri "+e.getMessage()+ " url: "+url);
		}
		return null;
	}

	public void cleanUp(int currentSelection){

	}
}
