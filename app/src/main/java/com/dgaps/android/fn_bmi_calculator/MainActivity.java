package com.dgaps.android.fn_bmi_calculator;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;

public class MainActivity extends AppCompatActivity {

    EditText et_weight,et_height_ft,et_height_inch;
    Button btn_calcualte;
    TextView tv_result;
    LinearLayout layout;
    Dialog dialog;
    double weight;
    double height_ft;
    double height_inch;
    double bmi;
    GifImageButton gib;
    double  height_meter;
    double cm;
    String msg = "";
    String error = "";
    double height_inches;
    private RadioGroup radioGroup,radioGroup2;
    private RadioButton radioButton,radioButton2;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.mycolor));

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.radiogroup2);

        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        layout = (LinearLayout) findViewById(R.id.linear);
       gib  = new GifImageButton(MainActivity.this);
        layout.addView(gib);
        layout.setVisibility(View.INVISIBLE);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_height_ft = (EditText) findViewById(R.id.et_height_ft);
        et_height_inch = (EditText) findViewById(R.id.et_height_inch);
        btn_calcualte = (Button) findViewById(R.id.btn_claculate);
        tv_result = (TextView) findViewById(R.id.tv_result);

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.radiocm:
                        et_height_inch.setVisibility(View.INVISIBLE);
                        et_height_ft.setHint("cm");
                        break;
                    case R.id.radiofeet:

                        et_height_inch.setVisibility(View.VISIBLE);
                        et_height_ft.setHint("ft");
                        et_height_inch.setHint("inch");
                        break;
                }
            }
        });

        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_weight.getText().toString().length()>2){
                    et_height_ft.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_height_ft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(et_height_ft.getText().toString().length()>0){
                    et_height_inch.requestFocus();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_height_ft.getText().toString().length()>0){
                    et_height_inch.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(et_height_ft.getText().toString().length()>0){
                    et_height_inch.requestFocus();
                }
            }
        });

        et_height_inch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(et_height_inch.getText().toString().length()==1){
                    btn_calcualte.setFocusable(true);
                    InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            et_height_inch.getWindowToken(), 0);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_height_inch.getText().toString().length()==1){
                    btn_calcualte.setFocusable(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btn_calcualte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        et_height_inch.getWindowToken(), 0);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                radioButton2 = (RadioButton) findViewById(radioGroup2.getCheckedRadioButtonId());

                if(et_weight.getText().toString().equals("")){
                    error = "Weight";
                    tv_result.setText(error+" can not be empty");
                    layout.setVisibility(View.INVISIBLE);
                    et_weight.requestFocus();
                }
                else if(et_weight.getText().toString().length()>3){
                    tv_result.setText("Weight value must be in 3 digits");
                    layout.setVisibility(View.INVISIBLE);
                }
                else if (radioButton2.getText().toString().equals("cm") && et_height_ft.getText().toString().equals("")){
                    tv_result.setText("height can not be null");
                    et_height_ft.requestFocus();
                    layout.setVisibility(View.INVISIBLE);
                }
                else if(radioButton2.getText().toString().equals("feet") && et_height_ft.getText().toString().equals("") || Integer.parseInt(et_height_ft.getText().toString())==0){
                    tv_result.setText("Height must be greater than 0");
                    et_height_ft.requestFocus();
                    layout.setVisibility(View.INVISIBLE);
                }
                else if(radioButton2.getText().toString().equals("feet") && Integer.parseInt(et_height_ft.getText().toString())>15){
                    tv_result.setText("Feet must be less then 15");
                    et_height_ft.requestFocus();
                    layout.setVisibility(View.INVISIBLE);
                }
                else if(radioButton2.getText().toString().equals("feet") && et_height_inch.getText().toString().equals("")){
                    tv_result.setText("Inches must not be null");
                    et_height_inch.requestFocus();
                    layout.setVisibility(View.INVISIBLE);
                }
                else if(radioButton2.getText().toString().equals("feet") && Integer.parseInt(et_height_inch.getText().toString())>12){
                    tv_result.setText("Inches must less than 12");
                    et_height_inch.requestFocus();
                    layout.setVisibility(View.INVISIBLE);
                }
                else if(radioButton.getText().toString().equals("Pounds")&&radioButton2.getText().toString().equals("cm")){
                    calculateWeight();
                    test();
                }
                else if(radioButton.getText().toString().equals("Pounds") && radioButton2.getText().toString().equals("feet")){
                    calculateWeight();
                    test();
                }
                else if(radioButton.getText().toString().equals("Kg") && radioButton2.getText().toString().equals("feet")){
                    calculateWeight();
                    test();
                }
                else if(radioButton.getText().toString().equals("Kg") && radioButton2.getText().toString().equals("cm")){
                    calculateWeight();
                    test();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                dialog.show();
                TextView textView = (TextView) dialog.findViewById(R.id.tv_about);
                textView.setText("Developed by Digital Application");
                break;
            case R.id.ourapps:
                Toast.makeText(MainActivity.this,"our apps",Toast.LENGTH_LONG).show();
                break;
            case R.id.help:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setInverseBackgroundForced(true);
                builder.setTitle("Help");
                builder.setMessage("BMI Categories: \n" +
                        "Underweight = <18.5\n" +
                        "Normal weight = 18.5–24.9 \n" +
                        "Overweight = 25–29.9 \n" +
                        "Obesity = BMI of 30 or greater");
                builder.show();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void calculateWeight(){

        if(radioButton.getText().toString().equals("Pounds") && radioButton2.getText().toString().equals("feet")){
            weight = Double.parseDouble(et_weight.getText().toString());
            weight = (weight/2.20 );
            height_ft = Double.parseDouble(et_height_ft.getText().toString());
            height_inch = Double.parseDouble(et_height_inch.getText().toString());
            height_inches = (height_ft*12)+height_inch;
            height_meter = height_inches*.0254;
            bmi = (int)weight/(height_meter*height_meter);
        }
        if(radioButton.getText().toString().equals("Pounds") && radioButton2.getText().toString().equals("cm")){
            weight = Double.parseDouble(et_weight.getText().toString());
            height_ft = Double.parseDouble(et_height_ft.getText().toString());
            height_meter = height_ft*.01;
            weight = (weight/2.20 );
            bmi = (int)weight/(height_meter*height_meter);
        }
        if(radioButton.getText().toString().equals("Kg") && radioButton2.getText().toString().equals("feet")){
            weight = Double.parseDouble(et_weight.getText().toString());
            height_ft = Double.parseDouble(et_height_ft.getText().toString());
            height_inch = Double.parseDouble(et_height_inch.getText().toString());
            height_inches = (height_ft*12)+height_inch;
            height_meter = height_inches*.0254;
            bmi = (int)weight/(height_meter*height_meter);
        }
        if(radioButton.getText().toString().equals("Kg") && radioButton2.getText().toString().equals("cm")){
            weight = Double.parseDouble(et_weight.getText().toString());
            height_ft = Double.parseDouble(et_height_ft.getText().toString());
            height_meter = height_ft*.01;
            bmi = (int)weight/(height_meter*height_meter);
        }
    }
    public void imageClick(View view) {
        et_height_ft.setText("");
        et_weight.setText("");
        et_height_inch.setText("");
        et_weight.requestFocus();
        tv_result.setText("Result");
        layout.setVisibility(View.GONE);
        height_ft = 0.0;
        weight= 0.0;
        bmi = 0.0;
    }


    public void test(){
        if (bmi < 12) {
            msg = "Underweight :(";
            gib.setImageResource(R.drawable.thin2);
            final MediaController mc = new MediaController(MainActivity.this);
            mc.setMediaPlayer((GifDrawable) gib.getDrawable());
            mc.setAnchorView(gib);
            tv_result.setText(msg +"\n your BMI value is "+(int)bmi);
        } else if (bmi < 18.5 && bmi > 12) {
            msg = "Underweight :(";
            gib.setImageResource(R.drawable.thin2);
            final MediaController mc = new MediaController(MainActivity.this);
            mc.setMediaPlayer((GifDrawable) gib.getDrawable());
            mc.setAnchorView(gib);
            tv_result.setText(msg +"\n your BMI value is "+(int)bmi);

        } else if (bmi > 18.5 && bmi < 25) {
            msg = "Normal :)";
            gib.setImageResource(R.drawable.normal);
            final MediaController mc = new MediaController(MainActivity.this);
            mc.setMediaPlayer((GifDrawable) gib.getDrawable());
            mc.setAnchorView(gib);
            tv_result.setText(msg +"\n your BMI value is "+(int)bmi);

        } else if (bmi > 25 && bmi < 30) {

            msg = "Overweight :(";
            gib.setImageResource(R.drawable.fat_ani);
            final MediaController mc = new MediaController(MainActivity.this);
            mc.setMediaPlayer((GifDrawable) gib.getDrawable());
            mc.setAnchorView(gib);
            tv_result.setText(msg +"\n your BMI value is "+(int)bmi);

        } else if (bmi > 30 && bmi < 35) {

            msg = "Overweight :(";
            gib.setImageResource(R.drawable.fat_ani1);
            final MediaController mc = new MediaController(MainActivity.this);
            mc.setMediaPlayer((GifDrawable) gib.getDrawable());
            mc.setAnchorView(gib);
            tv_result.setText(msg +"\n your BMI value is "+(int)bmi);

        } else if (bmi > 35) {

            msg = "Overweight :(";
            gib.setImageResource(R.drawable.fat_ani2);
            final MediaController mc = new MediaController(MainActivity.this);
            mc.setMediaPlayer((GifDrawable) gib.getDrawable());
            mc.setAnchorView(gib);
            tv_result.setText(msg +"\n your BMI value is "+(int)bmi);

        }

    }
}

