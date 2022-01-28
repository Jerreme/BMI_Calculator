package com.example.bmi_calculator;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.color.DynamicColors;

public class MainActivity extends AppCompatActivity {
    EditText age , weight;
    TextView remarks, bmi_int, bmi_dec, bmiLbl;
    NumberPicker ft;
    NumberPicker inch;

    Button clrBtn;
    Button calBtn;

    int activeCol = Color.rgb(107, 109, 108),
        inactiveCol = Color.rgb(163, 163, 163),

        bmi_cleared = Color.rgb(116, 108, 112),
        bmi_active = Color.rgb(118, 148, 171);

    int ageVal = 0,
        weightVal = 0,
        ftVal = 0,
        inVal = 0;

    double BMI_RESULT= 0.0f;
    boolean cleared = false;

    private static final Double KILOGRAMS_IN_LBS = 2.2;
    private static final int INCHES_IN_FOOT = 12;
    private static final int BMI_IMPERIAL_WEIGHT_SCALAR = 703;

    public static final String BMI_CATEGORY_UNDERWEIGHT = "Underweight";
    public static final String BMI_CATEGORY_NORMAL = "Normal";
    public static final String BMI_CATEGORY_OVERWEIGHT = "Overweight";
    public static final String BMI_CATEGORY_OBESE1 = "Obese";
    public static final String BMI_CATEGORY_OBESE2 = "Severely Obese";
    public static final String BMI_CATEGORY_OBESE3 = "Morbid Obese";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start your code here:
        age = findViewById(R.id.age_val);
        weight = findViewById(R.id.weight_val);

        ft = findViewById(R.id.ft_val);
        inch = findViewById(R.id.in_val);

        remarks = findViewById(R.id.remarks);
        bmi_int = findViewById(R.id.bmi_Int);
        bmi_dec = findViewById(R.id.bmi_Dec);
        bmiLbl = findViewById(R.id.bmilbl);

        clrBtn = findViewById(R.id.clearBtn);
        calBtn = findViewById(R.id.calculateBtn);


        ft.setMinValue(0);  inch.setMinValue(0);
        ft.setMaxValue(9);  inch.setMaxValue(12);

        ft.setValue(5); inch.setValue(4);



        //Action Listeners
        ft.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                ft.setTextColor(activeCol);
                ftVal = newVal;
            }
        });

        inch.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                inch.setTextColor(activeCol);
                inVal = newVal;
            }
        });


        //Clear
        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        //Calculate
        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFields();
            }
        });
    }


    @SuppressLint("NewApi")
    private void clearFields() {
        age.getText().clear();  weight.getText().clear();
        ft.setValue(5);         inch.setValue(4);
        bmi_int.setText("0");   bmi_dec.setText(".0");

        ft.setTextColor(inactiveCol);
        inch.setTextColor(inactiveCol);

        bmi_int.setTextColor(bmi_cleared);
        bmi_dec.setTextColor(bmi_cleared);
        remarks.setTextColor(bmi_cleared);
    }

    @SuppressLint("NewApi")
    private void calculateFields() {
        if(age.length() == 0)
            age.setText("18");

        if(weight.length() == 0)
            weight.setText("50");

        if(ft.getValue() == 5)
            ft.setTextColor(activeCol);

        if(inch.getValue() == 4)
            inch.setTextColor(activeCol);

        double weightKg = 0.0f;
        int heightFt = 0;
        int heightIn = 0;
        int weightLbs = 0;
        double totalHeightInInches = 0.0f;

        try {
            //            int ageYr = Integer.parseInt(age.getText().toString());
            weightKg = Double.parseDouble(weight.getText().toString());
            heightFt = Integer.parseInt(String.valueOf(ft.getValue()));
            heightIn = Integer.parseInt(String.valueOf(inch.getValue()));

            weightLbs = (int) ((int)weightKg * KILOGRAMS_IN_LBS);
            totalHeightInInches = (heightFt * INCHES_IN_FOOT) + heightIn;
            BMI_RESULT = (BMI_IMPERIAL_WEIGHT_SCALAR * weightLbs) / (totalHeightInInches * totalHeightInInches);
            BMI_RESULT = (double) (Math.round(BMI_RESULT*10.0)/10.0);

            String[] arrOfStr = String.valueOf(BMI_RESULT).split("[.]");

            bmi_int.setText(arrOfStr[0]);
            bmi_dec.setText("."+arrOfStr[1]);
            bmi_int.setTextColor(bmi_active);
            bmi_dec.setTextColor(bmi_active);

            classifyBMI(BMI_RESULT);
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this,ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void classifyBMI(double bmi) {
        String category = "";

        if (bmi < 18.5) {
            category= BMI_CATEGORY_UNDERWEIGHT;
            remarks.setTextColor(Color.rgb(135, 177, 217)); // blue
        } else if (bmi >= 18.5 && bmi < 25) {
            category= BMI_CATEGORY_NORMAL;
            remarks.setTextColor(Color.rgb(61, 211, 101)); // green
        } else if (bmi >= 25 && bmi < 30) {
            category= BMI_CATEGORY_OVERWEIGHT;
            remarks.setTextColor(Color.rgb(241, 229, 108)); // yellow
        } else if (bmi >= 30 && bmi < 35) {
            category= BMI_CATEGORY_OBESE1;
            remarks.setTextColor(Color.rgb(253, 128, 46)); // orange
        } else if (bmi >= 35 && bmi < 40) {
            category= BMI_CATEGORY_OBESE2;
            remarks.setTextColor(Color.rgb(255, 108, 23)); // orange/red
        } else if (bmi >= 40) {
            category= BMI_CATEGORY_OBESE3;
            remarks.setTextColor(Color.rgb(255, 23, 23)); // red
        }

        remarks.setText(category.toUpperCase());
    }
}