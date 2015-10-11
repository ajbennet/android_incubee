package incubee.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import incubee.android.views.GridImagesAdapter;
import stackedlist.view.CardStackAdapter;

public class SimpleCardsAdapter extends CardStackAdapter<String> {

	public SimpleCardsAdapter(Context context, ArrayList<String> items) {
		super(context, items);
		
		
		mContext = context;

		mInflator = LayoutInflater.from(context);
		
	}

	private Context mContext ;

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




		return convertView;
	}

}
