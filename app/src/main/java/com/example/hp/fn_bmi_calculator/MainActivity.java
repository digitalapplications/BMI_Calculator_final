package com.example.hp.fn_bmi_calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_weight,et_height_ft,et_height_inch;
    Button btn_calcualte;
    TextView tv_result;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.imageView);
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

                if(et_weight.getText().toString().equals("") || et_height_ft.getText().toString().equals("") || et_height_inch.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Invalid values",Toast.LENGTH_SHORT).show();
                }else{

                    weight = Double.parseDouble(et_weight.getText().toString());
                    height_ft = Double.parseDouble(et_height_ft.getText().toString());
                    height_inch = Double.parseDouble(et_height_inch.getText().toString());

                    height_inches = (height_ft*12)+height_inch;
                    height_meter = height_inches*.0254;
                    bmi = weight/(height_meter*height_meter);

                    if(bmi < 18.5){
                        msg = "Underweight :(";
                        iv.setImageResource(R.drawable.thin);
                    }else if(bmi >18.5 && bmi<25){
                        msg = "Normal :)";
                        iv.setImageResource(R.drawable.normal);
                    }else if (bmi > 25) {

                        msg = "Overweight :(";
                        iv.setImageResource(R.drawable.fat);
                    }

                    tv_result.setText(msg +" your BMI value is "+(int)bmi);

                    Toast.makeText(MainActivity.this, "Your BMI value is : "+(int)bmi, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

