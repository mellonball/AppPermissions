package com.example.breezy.apppermissions;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class ApplicationInfoActivity extends ActionBarActivity implements View.OnClickListener {
    AppInfo myAppInfo;
    ScrollView myScrollLayout;
    LinearLayout myLayout;
    Button myDownloadButton;
    ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_info);
        //getActionBar().setTitle("Application Information");
        myScrollLayout = (ScrollView) findViewById(R.id.application_info_layout);

        myLayout = (LinearLayout) myScrollLayout.getChildAt(0);
        Intent i = getIntent();

        myAppInfo = i.getParcelableExtra("appInfo");
        addTextViewTitleAndValue("App Name", myAppInfo.getTitle());
        addTextViewTitleAndValue("Creator", myAppInfo.getCreator());
        addTextViewTitleAndValue("Package", myAppInfo.getPackageName());
        addTextViewTitleAndValue("Price", myAppInfo.getPriceAmount());
        addTextViewTitleAndValue("Currency", myAppInfo.getPriceCurrency());
        addTextViewTitleAndValue("Rating", Double.toString(myAppInfo.getRatingStars()));
        addTextViewTitleAndValue("Reviews", Double.toString(myAppInfo.getRatingReviews()));
        addTextViewTitle("Permissions");

        icon = (ImageView) findViewById(R.id.ivDetailIcon);
        if (myAppInfo.getIconUrl().equals(""))
        {
            Picasso.with(this)
                    .load(R.mipmap.no_icon)
                    .into(icon);
        }
        else {
            Picasso.with(this)
                    .load(myAppInfo.getIconUrl())
                    .into(icon);
        }

        for( AppPermission permission : myAppInfo.getAppPermissions() ) {
            addTextViewValue(permission.getPermissionName());
        }
        addTextViewTitle("Unusual Permissions");
        for( AppPermission permission : myAppInfo.getUnusualPermissions() ) {
            addTextViewValue(permission.getPermissionName());
        }

        addTextViewTitle("Dangerous Permissions");
        for( AppPermission permission : myAppInfo.getDangerousPermissions() ) {
            addTextViewValue(permission.getPermissionName());
        }
        myDownloadButton = (Button) findViewById(R.id.download_app_button);
        myDownloadButton.setOnClickListener(this);
    }

    private void addTextViewTitleAndValue(String titleString, String valueString) {
        addTextViewTitle(titleString);
        addTextViewValue(valueString);
    }

    private void addTextViewTitle(String titleString) {
        TextView title = new TextView(this);
        title.setText(titleString);
        title.setTypeface(null, Typeface.BOLD);

        myLayout.addView(title);
    }

    private void addTextViewValue(String valueString) {
        TextView value = new TextView(this);
        value.setText(valueString);
        myLayout.addView(value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_application_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId() ) {
            case R.id.download_app_button:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                            "market://details?id=" + myAppInfo.getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                            "https://play.google.com/store/apps/details?id=" + myAppInfo.getPackageName())));
                }
        }

    }
}
