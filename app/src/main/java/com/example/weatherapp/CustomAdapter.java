package com.example.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.zip.Inflater;

public class CustomAdapter extends ArrayAdapter<String> {

    private String days[];
    private int icons[];
    private Activity context;

    public CustomAdapter(Activity context, String days[], int icons[]){
        super(context, R.layout.listview_layout, days);

        this.context=context;
        this.days=days;
        this.icons=icons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View r=convertView;
        ViewHolder viewHolder=null;
        if (r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.listview_layout,null, true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) r.getTag();

        }
        viewHolder.iv.setImageResource(icons[position]);
        viewHolder.tv.setText(days[position]);

        return r;
    }

    class ViewHolder{
        TextView tv;
        ImageView iv;

        ViewHolder(View v){
            tv=v.findViewById(R.id.textView);
            iv=v.findViewById(R.id.imageView2);
        }
    }
}
