package incubee.android.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import incubee.android.R;

/**
 * Created by samuh on 10/11/2015.
 */
public class GridImagesAdapter extends RecyclerView.Adapter<GridImagesAdapter.GridImagesHolders> {

    private List<String> imageURLs;
    private Context context;

    public GridImagesAdapter(Context context, List<String> itemList) {
        this.imageURLs = itemList;
        this.context = context;
    }

    @Override
    public GridImagesHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_image, null);
        GridImagesHolders imageHolder = new GridImagesHolders(layoutView);
        return imageHolder;
    }

    @Override
    public void onBindViewHolder(GridImagesHolders holder, int position) {
        if (imageURLs == null) {
            Glide.with(context).load(R.drawable.h_save_proj).fitCenter().into(holder.companyPhoto);
        }
        else {
            Glide.with(context).load(imageURLs.get(position)).placeholder(R.drawable.h_save_proj).fitCenter().into(holder.companyPhoto);
        }
    }

    @Override
    public int getItemCount() {
        if (imageURLs == null || (imageURLs.size() <= 3)) {
            return 1;
        }

        return this.imageURLs.size();
    }


    public static class GridImagesHolders extends RecyclerView.ViewHolder {

        public ImageView companyPhoto;

        public GridImagesHolders(View itemView) {
            super(itemView);
            companyPhoto = (ImageView) itemView.findViewById(R.id.company_photo);
        }

    }

}
