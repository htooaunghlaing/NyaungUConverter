package com.hah.nyaungu.converter;

import java.util.ArrayList;


import com.material.widget.FloatingEditText;

import com.cengalabs.flatui.views.FlatRadioButton;



import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ConvertDialog extends Activity {

	public static boolean active = false;
	public static Activity myDialog;

	FloatingEditText input;
	TextView txtOutput;

	Button btnConvert, btnCopy;
	FlatRadioButton rbUnicode, rbZawgyi;
	View top;
	Typeface uniFace, zawgyiFace;
	private RadioGroup radioFontGroup;
	private FlatRadioButton radioFontButton, radioCheckedEnabled;
	ImageButton btnClear;
	public int flag = 1;
	int sdk = android.os.Build.VERSION.SDK_INT;
	public static String tmpTextZG = "";
	public static String tmpTextUNI = "";
	String CopyText = "";
	public static String strClipBoardText = "";

	private ArrayList<FlatRadioButton> flatRadioButtons = new ArrayList<FlatRadioButton>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.mydialog);

		getIDs();
		
		isClickBoardHasDataOrNot();

		catchEvents();

		myDialog = ConvertDialog.this;

	}

		private void isClickBoardHasDataOrNot() {
		  // TODO Auto-generated method stub
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB){
        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        strClipBoardText = clipboard.getText().toString();
       

        }else{

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if(clipboard.hasPrimaryClip()== true){
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                strClipBoardText = item.getText().toString();
                
                Log.e("Init", strClipBoardText+"***");
               

                }else{

                    Toast.makeText(getApplicationContext(), "Nothing to Paste", Toast.LENGTH_SHORT).show();

                }
          }
        //detect process
        int intDetect = Converter.detector(strClipBoardText);
        if(intDetect == 2)
        {
        	//Toast.makeText(getApplicationContext(), "Input is Zawgyi", Toast.LENGTH_SHORT).show();
        	//
        	radioCheckedEnabled = (FlatRadioButton) findViewById(R.id.radioUnicode);
    		flatRadioButtons.add(radioCheckedEnabled);
    		radioCheckedEnabled.setChecked(true);
			input.setTypeface(zawgyiFace);
    		input.setText(strClipBoardText);
        	changeUni(strClipBoardText);
        }
        else if(intDetect == 1)
        {
        	//Toast.makeText(getApplicationContext(), "Input is Uni", Toast.LENGTH_SHORT).show();
        	radioCheckedEnabled = (FlatRadioButton) findViewById(R.id.radioZawgyi);
    		flatRadioButtons.add(radioCheckedEnabled);
    		radioCheckedEnabled.setChecked(true);
			input.setTypeface(uniFace);
    		input.setText(strClipBoardText);
        	changeZawgyi(strClipBoardText);
        }
        
        }
	

	private void copyText() {

		// TODO Auto-generated method stub
		CopyText = txtOutput.getText().toString();
		if (CopyText.length() != 0) {
			if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
				android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				clipboard.setText(CopyText);
				Toast.makeText(getApplicationContext(),
						"Text Copied to Clipboard", Toast.LENGTH_SHORT).show();
			} else {
				ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData
						.newPlainText("Clip", CopyText);
				Toast.makeText(getApplicationContext(),
						"Text Copied to Clipboard", Toast.LENGTH_SHORT).show();
				clipboard.setPrimaryClip(clip);
			}
		} else {
			Toast.makeText(getApplicationContext(), "Nothing to Copy",
					Toast.LENGTH_SHORT).show();
		}

	}

	private void catchEvents() {
		top.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				input.setText("");
			}
		});

		radioFontGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group,
												 int selectedId) {

						// Toast.makeText(ConvertDialog.this,
						// checkedId+" SELECTED ID", Toast.LENGTH_LONG).show();
						if (selectedId == R.id.radioUnicode) {
							input.setTypeface(zawgyiFace);
						//	Toast.makeText(ConvertDialog.this, "Checked Unicode", Toast.LENGTH_SHORT).show();
							flag = 1;

						}
						if (selectedId == R.id.radioZawgyi) {
						//	Toast.makeText(ConvertDialog.this, "Checked Zawgyi", Toast.LENGTH_SHORT).show();
							input.setTypeface(uniFace);
							flag = 2;
						}

					}
				});

		btnConvert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (flag == 1)
				{
					if(Converter.detector(input.getText().toString().trim()) == 2)
					{
						Toast.makeText(ConvertDialog.this,
								"Convert Zawgyi to Unicode", Toast.LENGTH_SHORT)
								.show();
						String str = input.getText().toString().trim();
						changeUni(str);
					}
					else
					{
						Toast.makeText(ConvertDialog.this,
								"Please input Zawgyi Text to Convert Unicode", Toast.LENGTH_SHORT)
								.show();
					}

				}
				if (flag == 2)
				{
					if(Converter.detector(input.getText().toString().trim()) == 1)
					{
						Toast.makeText(ConvertDialog.this,
								"Convert Unicode to Zawgyi", Toast.LENGTH_SHORT)
								.show();
						String str = input.getText().toString().trim();
						changeZawgyi(str);
					}
					else
					{
						Toast.makeText(ConvertDialog.this,
								"Please input Unicode Text to Convert Zawgyi", Toast.LENGTH_SHORT)
								.show();
					}

				}
				//methodDetail();
			}

		});

		btnCopy.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {

				copyText();
			}

		});

	}

	private void methodDetail() {
		txtOutput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (txtOutput.length() > 100) {

					if (flag == 1) {
						Intent i = new Intent(ConvertDialog.this, Detail.class);
						i.putExtra("outputDetail", tmpTextUNI);
						startActivity(i);
					} else if (flag == 2) {
						Intent i = new Intent(ConvertDialog.this, Detail.class);
						i.putExtra("outputDetail", tmpTextZG);
						Log.e("Check ZG", tmpTextZG + "***");
						startActivity(i);
					}
				}

			}
		});
	}

	private void getIDs() {
		input = (FloatingEditText) findViewById(R.id.etInput);
		txtOutput = (TextView) findViewById(R.id.txtOutput);
		btnConvert = (Button) findViewById(R.id.btnConvert);
		btnCopy = (Button) findViewById(R.id.btnCopy);
		top = (View) findViewById(R.id.topView);

		btnClear = (ImageButton)findViewById(R.id.btnDelete);

		zawgyiFace = Typeface.createFromAsset(getAssets(),
				"fonts/zawgyi.ttf");
		uniFace = Typeface.createFromAsset(getAssets(), "fonts/mm3.ttf");

		// Radio Config
		radioFontGroup = (RadioGroup) findViewById(R.id.radioFont);

		flatRadioButtons.add((FlatRadioButton) findViewById(R.id.radioUnicode));
		flatRadioButtons.add((FlatRadioButton) findViewById(R.id.radioZawgyi));

		radioCheckedEnabled = (FlatRadioButton) findViewById(R.id.radioUnicode);
		flatRadioButtons.add(radioCheckedEnabled);
		radioCheckedEnabled.setChecked(true);



	}

	private void changeZawgyi(String str) {

		txtOutput.setTypeface(zawgyiFace);
		tmpTextZG = Converter.uni2zg(str);


		int size = tmpTextZG.length();

		if (size < 100) {
			txtOutput.setText(tmpTextZG);// " . . . ."
		} else {
			txtOutput.setText(tmpTextZG.substring(0, 100) + " . . . ."); //

				txtOutput.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (txtOutput.length() > 100) {
							Intent i = new Intent(ConvertDialog.this, Detail.class);
							i.putExtra("outputDetail", tmpTextZG);
							Log.e("Check ZG", tmpTextZG + "***");
							startActivity(i);
						}
					}
				});

		}
		//methodDetail();
	}

	protected void changeUni(String str) {

		txtOutput.setTypeface(uniFace);
		tmpTextUNI = Converter.zg2uni(str);
		// txtOutput.setText(tmpText);

		int size = tmpTextUNI.length();

		if (size < 100) {
			txtOutput.setText(tmpTextUNI);
		} else {
			txtOutput.setText(tmpTextUNI.substring(0, 100) + " . . . .");
			txtOutput.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (txtOutput.length() > 100) {
						Intent i = new Intent(ConvertDialog.this, Detail.class);
						i.putExtra("outputDetail", tmpTextUNI);
						Log.e("Check Uni", tmpTextUNI + "***");
						startActivity(i);
					}
				}
			});

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		active = true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		active = false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		active = false;
	}











}
