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
            Glide.with(context).load(
                            "https://incubee-images.s3.amazonaws.com/img_246b5df9-bd5d-46be-ba4f-301c971c1b5b"
            ).fitCenter().placeholder(R.mipmap.ic_launcher).into(holder.companyPhoto);
        } else {
            Glide.with(context).load(imageURLs.get(position)).fitCenter().placeholder(R.mipmap.ic_launcher).into(holder.companyPhoto);
        }
    }

    @Override
    public int getItemCount() {
        if (imageURLs == null) {
            return 4;
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
