package com.hah.nyaungu.converter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    public static Button btnStartService, btnShowMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnStartService = (Button)findViewById(R.id.btnStartService);
        btnShowMsg = (Button)findViewById(R.id.btnMsg);

        btnStartService.setOnClickListener(lst_StartService);
        btnShowMsg.setOnClickListener(lst_ShowMsg);
    }

    Button.OnClickListener lst_StartService = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            startService(new Intent(MainActivity.this, ChatHeadService.class));
        }

    };


    Button.OnClickListener lst_ShowMsg = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
           stopService(new Intent(MainActivity.this, ChatHeadService.class));
        }

    };


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
           startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
