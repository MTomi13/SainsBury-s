package com.example.tmarton.sainsbury.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tmarton.sainsbury.R;
import com.example.tmarton.sainsbury.model.Item;
import com.example.tmarton.sainsbury.util.ViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tamas Marton.
 */
public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ItemsViewHolderHandler> {

    private static final String BASE_IMG_URL = "http://c2.sainsburys.co.uk";

    @Getter
    @Setter
    private ArrayList<Item> itemsList;

    private final DisplayImageOptions options;

    public MainActivityAdapter() {
        options = ViewHelper.setImageOptions();
    }

    @Override
    public ItemsViewHolderHandler onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
        return new ItemsViewHolderHandler(view);
    }

    @Override
    public void onBindViewHolder(final ItemsViewHolderHandler holder, final int position) {
        final Item item = itemsList.get(position);
        setupViews(holder, item);
        setupImage(holder, item);
    }

    /**
     * @param holder
     * @param item   fill the views with content
     */
    private void setupViews(final ItemsViewHolderHandler holder, final Item item) {
        holder.nameTextView.setText(item.getName());
        holder.priceTextView.setText(item.getPrice());
        holder.priceMeasureTextView.setText(item.getPricePerMesure());
    }

    /**
     * @param holder
     * @param item   load the main, and the additional image
     */
    private void setupImage(final ItemsViewHolderHandler holder, final Item item) {
        ImageLoader.getInstance().displayImage(BASE_IMG_URL + item.getImageUrl(), holder.imageView, options, new SimpleImageLoadingListener());
        if (item.getAdditionalImageUrl() != null) {
            ImageLoader.getInstance().displayImage(BASE_IMG_URL + item.getAdditionalImageUrl(), holder.additionalImageView, options, new SimpleImageLoadingListener());
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    protected class ItemsViewHolderHandler extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView priceTextView;
        private final TextView priceMeasureTextView;
        private final ImageView imageView;
        private final ImageView additionalImageView;

        public ItemsViewHolderHandler(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            priceTextView = (TextView) itemView.findViewById(R.id.price);
            priceMeasureTextView = (TextView) itemView.findViewById(R.id.price_measure);
            imageView = (ImageView) itemView.findViewById(R.id.main_image);
            additionalImageView = (ImageView) itemView.findViewById(R.id.additional_image);
        }
    }
}