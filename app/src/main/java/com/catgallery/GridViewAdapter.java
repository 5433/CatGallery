package com.catgallery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Anmar Hindi on 11/27/15.
 */
public class GridViewAdapter extends ArrayAdapter<ImageItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList<>();
    private HashMap<ImageItem, String> map;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data, HashMap<ImageItem,String> map) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.map = map;
    }

    //retrieves item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        ImageItem item = data.get(position);
        //holder.imageTitle.setText(item.getTitle());
        holder.imageTitle.setText(map.get(item));
        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    // Holds the image
    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}