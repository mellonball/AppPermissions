package com.example.breezy.apppermissions;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;


public class DisplayAppsList extends ListActivity {

    private static final String TAG = DisplayAppsList.class.getCanonicalName();
    List<AppInfo> returnedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_apps_list);
        Intent i = getIntent();
        String appsReceived = i.getStringExtra("appsReceived");
        try {
            Log.d(TAG, appsReceived);
            returnedList = JsonParserUtility.parseAppInfoList(appsReceived);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        populateListView();
        //registerClickCallback();

    }

    /*private void registerClickCallback() {
        // set up on click listener then
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setOnClickListener();
    }*/

    private class MyCustomAdapter extends ArrayAdapter<AppInfo> {

        Context context;
        int layoutResourceId;
        List<AppInfo> items;

        public MyCustomAdapter(Context context, int textViewResourceId, List<AppInfo> items) {
            super(context, textViewResourceId, items);
            this.context = context;
            this.layoutResourceId = textViewResourceId;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            AppInfoHolder holder = null;

            if(row == null)
            {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new AppInfoHolder();
                holder.title = (TextView)row.findViewById(R.id.tvTitle);
                holder.creator = (TextView)row.findViewById(R.id.tvCreator);
                holder.rating = (RatingBar)row.findViewById(R.id.ratingBar);

                row.setTag(holder);
            }
            else
            {
                holder = (AppInfoHolder)row.getTag();
            }

            AppInfo app = this.items.get(position);
            holder.title.setText(app.getTitle());
            holder.creator.setText(app.getCreator());
            holder.rating.setRating((float) app.getRatingStars());

            return row;
        }



    }
    static class AppInfoHolder
    {
        TextView title;
        TextView creator;
        RatingBar rating;
    }

    private void populateListView() {
        //build the adapter
        MyCustomAdapter adapter = new MyCustomAdapter(this, R.layout.each_item, returnedList);

        // configure the list view
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
    }


}