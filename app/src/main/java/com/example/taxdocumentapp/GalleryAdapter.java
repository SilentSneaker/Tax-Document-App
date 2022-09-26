package com.example.taxdocumentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class GalleryAdapter extends ArrayAdapter<Document> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder{
        TextView total;
        TextView date;
        TextView tags;
        ImageView img;
    }


    public GalleryAdapter(Context context, int resource, ArrayList<Document> obj){
        super(context, resource, obj);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        setupImageLoader();

        String nDate = getItem(position).getDate();
        String imgLocation = getItem(position).getImageLocation();
        String Tag = getItem(position).getTag();
        String nTotal = getItem(position).getTotal();



        final View result;

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from((mContext));
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.tags = (TextView) convertView.findViewById(R.id.tags);
            holder.total = (TextView) convertView.findViewById(R.id.total);
            holder.img = (ImageView) convertView.findViewById(R.id.adapterImage);

            result = convertView;
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

            ImageLoader imageLoader = ImageLoader.getInstance();
            int defaultimage = mContext.getResources().getIdentifier("@drawable/image_failed", null, mContext.getPackageName());

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true).cacheOnDisk(true)
                    .resetViewBeforeLoading(true).showImageForEmptyUri(defaultimage )
                    .showImageOnFail(defaultimage).showImageOnLoading(defaultimage).build();


            imageLoader.displayImage(imgLocation, holder.img, options);

            holder.total.setText(nTotal);
            holder.tags.setText(Tag);
            holder.date.setText(nDate);


        return convertView;
    }
    private void setupImageLoader(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).defaultDisplayImageOptions(defaultOptions).memoryCache(new WeakMemoryCache())
                .diskCacheSize(100*1024*1024).build();

        ImageLoader.getInstance().init(config);
    }
}
