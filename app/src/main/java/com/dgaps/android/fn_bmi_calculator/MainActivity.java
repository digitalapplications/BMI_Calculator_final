package com.dgaps.android.fn_bmi_calculator;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;
import android.widget.Toast;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;

public class MainActivity extends AppCompatActivity {

    EditText et_weight,et_height_ft,et_height_inch;
    Button btn_calcualte;
    TextView tv_result;
    LinearLayout layout;
    Dialog dialog;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.mycolor));

        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        layout = (LinearLayout) findViewById(R.id.linear);
        final GifImageButton gib = new GifImageButton(MainActivity.this);
        layout.addView(gib);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_height_ft = (EditText) findViewById(R.id.et_height_ft);
        et_height_inch = (EditText) findViewById(R.id.et_height_inch);
        btn_calcualte = (Button) findViewById(R.id.btn_claculate);
        tv_result = (TextView) findViewById(R.id.tv_result);

        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_weight.getText().toString().length()>1){
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

                double weight;
                double height_ft;
                double height_inch;
                double bmi;
                double  height_meter;
                double height_inches;
                String msg = "";

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        et_height_inch.getWindowToken(), 0);

                if(et_weight.getText().toString().equals("") || et_height_ft.getText().toString().equals("") || et_height_inch.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Invalid values",Toast.LENGTH_SHORT).show();

                }
                else if(Integer.parseInt(et_height_ft.getText().toString())==0){
                    Toast.makeText(MainActivity.this,"Height can not be 0",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(et_height_ft.getText().toString())>15){

                    et_height_ft.setError("Height must be less than 15 feet");
                }
                else if(Integer.parseInt(et_height_inch.getText().toString())>=13){
                    Toast.makeText(MainActivity.this,"Height in inch can not be more than 12",Toast.LENGTH_SHORT).show();
                }
                else{

                    weight = Double.parseDouble(et_weight.getText().toString());
                    height_ft = Double.parseDouble(et_height_ft.getText().toString());
                    height_inch = Double.parseDouble(et_height_inch.getText().toString());

                    height_inches = (height_ft*12)+height_inch;
                    height_meter = height_inches*.0254;
                    bmi = weight/(height_meter*height_meter);
                    if(bmi < 12) {
                        msg = "Underweight :(";
                        gib.setImageResource(R.drawable.thin2);
                        final MediaController mc = new MediaController(MainActivity.this);
                        mc.setMediaPlayer((GifDrawable) gib.getDrawable());
                        mc.setAnchorView(gib);
                    } else if(bmi < 18.5 && bmi > 12){
                        msg = "Underweight :(";
                        gib.setImageResource(R.drawable.thin2);
                        final MediaController mc = new MediaController(MainActivity.this);
                        mc.setMediaPlayer((GifDrawable)gib.getDrawable());
                        mc.setAnchorView(gib);

                    }else if(bmi >18.5 && bmi<25){
                        msg = "Normal :)";
                        gib.setImageResource(R.drawable.normal);
                        final MediaController mc = new MediaController(MainActivity.this);
                        mc.setMediaPlayer((GifDrawable)gib.getDrawable());
                        mc.setAnchorView(gib);

                    }else if (bmi > 25 && bmi < 30) {

                        msg = "Overweight :(";
                        gib.setImageResource(R.drawable.fat_ani);
                        final MediaController mc = new MediaController(MainActivity.this);
                        mc.setMediaPlayer((GifDrawable)gib.getDrawable());
                        mc.setAnchorView(gib);

                    }else if (bmi > 30 && bmi < 35) {

                        msg = "Overweight :(";
                        gib.setImageResource(R.drawable.fat_ani1);
                        final MediaController mc = new MediaController(MainActivity.this);
                        mc.setMediaPlayer((GifDrawable)gib.getDrawable());
                        mc.setAnchorView(gib);

                    }else if (bmi > 35) {

                        msg = "Overweight :(";
                        gib.setImageResource(R.drawable.fat_ani2);
                        final MediaController mc = new MediaController(MainActivity.this);
                        mc.setMediaPlayer((GifDrawable)gib.getDrawable());
                        mc.setAnchorView(gib);

                    }

                    tv_result.setText(msg +"\n your BMI value is "+(int)bmi);

                   // Toast.makeText(MainActivity.this, "Your BMI value is : "+(int)bmi, Toast.LENGTH_SHORT).show();
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void imageClick(View view) {
        et_height_ft.setText("");
        et_weight.setText("");
        et_height_inch.setText("");
        et_weight.requestFocus();
    }
}

