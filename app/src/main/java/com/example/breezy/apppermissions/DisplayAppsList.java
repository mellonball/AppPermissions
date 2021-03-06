package com.example.breezy.apppermissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;


public class DisplayAppsList extends ActionBarActivity {

    private static final String TAG = DisplayAppsList.class.getCanonicalName();
    private static final String MENU_MORE_INFO = "More info";
    private static final String MENU_DOWNLOAD = "Download";
    MyCustomAdapter adapter;
    List<AppInfo> returnedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_apps_list);
        Intent i = getIntent();
        String appsReceived = i.getStringExtra("appsReceived");
        try {
            //Log.d(TAG, appsReceived);
            returnedList = JsonParserUtility.parseAppInfoList(appsReceived);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        populateListView();
        registerClickCallback();

    }

    private void registerClickCallback() {
        // set up on click listener
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent intent = new Intent(DisplayAppsList.this, ApplicationInfoActivity.class);
                intent.putExtra("appInfo", adapter.getItem(position));
                startActivity(intent);
            }
        });
    }

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
                holder.icon = (ImageView)row.findViewById(R.id.ivIcon);
                row.setTag(holder);
            }
            else
            {
                holder = (AppInfoHolder)row.getTag();
            }

            AppInfo app = this.items.get(position);
            if (app.getIconUrl().equals(""))
            {
                Picasso.with(this.context)
                        .load(R.mipmap.no_icon)
                        .into(holder.icon);
            }
            else {
                Picasso.with(this.context)
                        .load(app.getIconUrl())
                        .into(holder.icon);
            }
            holder.title.setText(app.getTitle());
            holder.creator.setText(app.getCreator());
            holder.rating.setRating((float) app.getRatingStars());

            if (app.getDangerousPermissions().size() > 0) {
                row.setBackgroundColor(Color.parseColor("#ff6666")); //red
                //Log.d(TAG, app.getUnusualPermissions().toString() + " " + app.getUnusualPermissions().size() + " " + app.getTitle());

            }
            else if (app.getUnusualPermissions().size() > 0 || app.hasCustomPermissions()) {
                row.setBackgroundColor(Color.parseColor("#ffff7f")); //yellow

                //Log.d(TAG, app.getUnusualPermissions().toString() + " " + app.getUnusualPermissions().size() + " " + app.getTitle());

            }
            else { row.setBackgroundColor(Color.parseColor("#7fe67f"));}  //green

            return row;
        }

    }

    static class AppInfoHolder
    {
        TextView title;
        TextView creator;
        RatingBar rating;
        ImageView icon;
    }

    private void populateListView() {
        //build the adapter
        adapter = new MyCustomAdapter(this, R.layout.each_item, returnedList);

        // configure the list view
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
    }


}