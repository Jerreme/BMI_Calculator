package com.example.bmi_calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.color.DynamicColors;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    NumberPicker ft, inch;

    int ageVal = 0,
        weightVal = 0,
        ftVal = 0,
        inVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView5);
        ft = findViewById(R.id.numPicker);
        inch = findViewById(R.id.numPicker2);

        ft.setMinValue(0);  inch.setMinValue(0);
        ft.setMaxValue(9);  inch.setMaxValue(12);

        ft.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                ft.setTextColor(Color.rgb(61, 80, 94));
                ftVal = newVal;
//                textView.setText(String.valueOf(newVal));
            }
        });

        inch.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                textView.setText(String.valueOf(newVal));
                inch.setTextColor(Color.rgb(61, 80, 94));
                inVal = newVal;
            }
        });





    }



}