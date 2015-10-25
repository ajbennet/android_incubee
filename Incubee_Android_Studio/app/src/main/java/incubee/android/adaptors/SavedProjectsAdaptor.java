package incubee.android.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import incubee.android.R;
import services.models.IncubeeProfile;

/**
 * Copyright © 2015 Zonoff, Inc.  All Rights Reserved.
 */
public class SavedProjectsAdaptor extends BaseAdapter{

    private Context mContext;
    private List<IncubeeProfile> mList;

    public SavedProjectsAdaptor(Context context, List<IncubeeProfile> itemList) {
        mContext = context;
        mList = itemList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public IncubeeProfile getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.savedprojects_list_item, parent, false);
            viewHolder.imageView = (CircleImageView) convertView.findViewById(R.id.company_image);
            viewHolder.companyName =(TextView) convertView.findViewById(R.id.company_name);
            viewHolder.companyHighConcept = (TextView) convertView.findViewById(R.id.company_high_concept);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        IncubeeProfile incubeeProfile = getItem(position);
        if(incubeeProfile.getImages() != null && !incubeeProfile.getImages().isEmpty()) {
            Glide.with(mContext).load(incubeeProfile.getImages().get(0)).fitCenter().into(viewHolder.imageView);
        }
        viewHolder.companyName.setText(incubeeProfile.getCompany_name());
        viewHolder.companyHighConcept.setText(incubeeProfile.getHigh_concept());
        return convertView;
    }

    private static class ViewHolder {
        CircleImageView imageView;
        TextView companyName;
        TextView companyHighConcept;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
