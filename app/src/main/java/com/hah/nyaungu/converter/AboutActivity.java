package com.hah.nyaungu.converter;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class AboutActivity extends ActionBarActivity {

    Typeface tf;
    ImageButton fb, share, feedback;
    TextView license, version;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("About Nyaung U Converter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIDs();

        catchEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, "a23ec9426682910fe99e77969a2aa5c0");

        mixpanel.track("On Resume", null);

        mixpanel.flush();
    }

    private void catchEvents() {
        version.setText("Version " + BuildConfig.VERSION_NAME);
        fb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String url = "fb://page/1569111940029835";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        feedback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"htooaunghlaing1@gmail.com"});
                Email.putExtra(Intent.EXTRA_SUBJECT,
                        "Feedback for Nyaung U Converter App");
                Email.putExtra(Intent.EXTRA_TEXT, "Dear Htoo Aung Hlaing," + "");
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
            }
        });

        share.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // App Download Link...
                String shareBody = "https://play.google.com/store/apps/details?id=com.hah.nyaungu.converter";

                Intent sharingIntent = new Intent(
                        Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent
                        .putExtra(Intent.EXTRA_SUBJECT,
                                "APP NAME (Open it in Google Play Store to Download the Application)");

                sharingIntent.putExtra(Intent.EXTRA_TEXT,
                        shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        license.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(AboutActivity.this);
                View promptsView = li.inflate(R.layout.layout_license, (ViewGroup) findViewById(R.id.op_license));

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AboutActivity.this);
                alertDialogBuilder.setTitle("Open Source License");

                alertDialogBuilder.setView(promptsView);

                final WebView webview = (WebView) promptsView
                        .findViewById(R.id.mywebView);
                webview.loadUrl("file:///android_asset/opensource.html");
                final AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });

    }

    private void getIDs() {
        fb = (ImageButton) findViewById(R.id.fb);

        share = (ImageButton) findViewById(R.id.share);

        feedback = (ImageButton) findViewById(R.id.email);

        license = (TextView) findViewById(R.id.open_source);

        version = (TextView) findViewById(R.id.version);
    }
}
