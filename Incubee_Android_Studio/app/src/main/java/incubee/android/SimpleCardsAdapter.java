package incubee.android;

import java.util.ArrayList;

import stackedlist.view.CardStackAdapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

		TextView row = (TextView) convertView.findViewById(R.id.card_view);

		switch(position){
		case 0:
			row.setBackgroundColor(Color.RED);
			row.setText(model);
			break;
		case 1:
			row.setBackgroundColor(Color.GREEN);
			row.setText(model);
			break;
		case 2:
			row.setBackgroundColor(Color.YELLOW);
			row.setText(model);
			break;
		default:
			row.setBackgroundColor(Color.LTGRAY);
			row.setText(model);
			break;


		}

		return convertView;
	}

}
