package com.example.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Myadapter extends ArrayAdapter {
    private static final String TAG = "Myadapter";

    public Myadapter(Context context, int resourse, ArrayList<HashMap<String, String>> list) {
        super(context, resourse, list);


    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.listhuilv, parent, false);
        }
        Map<String, String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemtitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemdetail);

        title.setText("Title:" + map.get("itemtitle"));
        detail.setText("detail:" + map.get("itemdetail"));
        return itemView;
    }


}