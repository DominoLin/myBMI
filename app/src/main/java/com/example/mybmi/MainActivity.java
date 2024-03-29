package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText user_height, user_weight;
    private Button BMI_calculate, BMI_save, show_view;
    private TextView result_content;
    double result = 0.0;
    String suggestion;
    String nowTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_height = findViewById(R.id.user_height);
        user_weight = findViewById(R.id.user_weight);
        BMI_calculate = findViewById(R.id.BMI_calculate);
        BMI_save = findViewById(R.id.BMI_save);
        show_view = findViewById(R.id.show_view);

        result_content = (TextView) findViewById(R.id.result_content);


        BMI_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String height = user_height.getText().toString();
                String weight = user_weight.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy" +
                        "年MM月dd日 HH:mm:ss");
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                nowTime = simpleDateFormat.format(date);
                if (height == null || height.equals("")) {
                    Toast.makeText(getApplicationContext(), "身高不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (weight == null || weight.equals("")) {
                    Toast.makeText(getApplicationContext(), "体重不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                requestResult(height, weight);
                requestSuggestion();

            }
        });

        BMI_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.getDatabase();
                if (result != 0.0 && !user_weight.getText().toString().equals("") &&
                        !user_height.getText().toString().equals("")) {
                    saveToLocal(result, nowTime);
                    user_height.setText("");
                    user_weight.setText("");
                    Toast.makeText(getApplicationContext(), "保存成功！！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "请先输入！！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        show_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordViewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveToLocal(double result, String nowTime) {
        Log.d("MainActivity", "现在时间" + nowTime);
        RecordBean bmiRecord = new RecordBean();
        bmiRecord.setResult(result);
        bmiRecord.setTime(nowTime);
        bmiRecord.save();
    }

    private void requestSuggestion() {
        if (result < 18.5) {
            suggestion = "\tBMI值" + result + "\n\t偏瘦体质";
            result_content.setText(suggestion);


        }else if (result >= 18.5 && result <= 23.9) {
            suggestion = "\tBMI值" + result + "\n\t正常体质";
            result_content.setText(suggestion);
            result_content.setTextColor(Color.parseColor("#3CB371"));

        }else if (result >= 24 && result <= 28) {
            suggestion = "\tBMI值" + result + "\n\t偏胖体质";
            result_content.setText(suggestion);
            result_content.setTextColor(Color.parseColor("#FFC0CB"));

        }else if (result > 28) {
            suggestion = "\tBMI值" + result + "\n\t肥胖体质";
            result_content.setText(suggestion);
            result_content.setTextColor(Color.parseColor("#DC143C"));

        }else {
            suggestion="\tBMI值" + result + "\n\t已超出可视范围";
            result_content.setText(suggestion);
        }

    }

    private void requestResult(String height, String weight) {
        double heightNumber = Double.valueOf(height);
        double weightNumber = Double.valueOf(weight);
        result = weightNumber / ((heightNumber * heightNumber) / (100 * 100));
    }

}
