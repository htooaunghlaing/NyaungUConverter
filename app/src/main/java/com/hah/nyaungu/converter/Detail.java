package com.hah.nyaungu.converter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends Activity {

    public TextView txtDetail;
    public Button btnCopyDetail;
    String strDetail = "";
    int sdk = android.os.Build.VERSION.SDK_INT;
    Typeface zawgyiFace, uniFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_detail);

        getIds();

        catchEvents();
    }

    private void catchEvents() {
        // TODO Auto-generated method stub
        Bundle bundle = getIntent().getExtras();
        String detail = bundle.getString("outputDetail");
        int detect = Converter.detector(detail);
        if (detect == 1) {
            txtDetail.setTypeface(uniFace);
        } else if (detect == 2) {
            txtDetail.setTypeface(zawgyiFace);
        }
        txtDetail.setText(detail);


        btnCopyDetail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                strDetail = txtDetail.getText().toString();
                if (strDetail.length() != 0) {
                    if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboard.setText(strDetail);
                        Toast.makeText(getApplicationContext(),
                                "Text Copied to Clipboard", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        android.content.ClipData clip = android.content.ClipData
                                .newPlainText("Clip", strDetail);
                        Toast.makeText(getApplicationContext(),
                                "Text Copied to Clipboard", Toast.LENGTH_SHORT)
                                .show();
                        clipboard.setPrimaryClip(clip);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nothing to Copy",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getIds() {
        // TODO Auto-generated method stub

        zawgyiFace = Typeface.createFromAsset(getAssets(),
                "fonts/zawgyi.ttf");
        uniFace = Typeface.createFromAsset(getAssets(), "fonts/mm3.ttf");
        txtDetail = (TextView) findViewById(R.id.txtOutputDetail);
        txtDetail.setMovementMethod(new ScrollingMovementMethod());
        btnCopyDetail = (Button) findViewById(R.id.btnCopyDetail);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        txtDetail.setText("");
    }
}
