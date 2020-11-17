package com.titaniel.moonit;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class MoonActivity extends AppCompatActivity {

    private static final double SYN_DUR = 29.530588d * 24d * 60d * 60d;
    private static final DateTime LAST_FULL_MOON =
            new DateTime(2018, 1, 31, 14, 26, 46);

    private Handler mHandler = new Handler();
    private TextView mTvOutput, mTvDateTime;
    private MoonView mMoonView;

    private Runnable calculator = new Runnable() {
        @Override
        public void run() {
            calc();
            mHandler.postDelayed(calculator, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon);

        mTvOutput = findViewById(R.id.tvOutput);
        mTvDateTime = findViewById(R.id.tvDateTime);
        mMoonView = findViewById(R.id.moonView);

        mHandler.post(calculator);
    }

    int h = 0;
    private void calc() {
        DateTime currentDate = DateTime.now();

        double value = (double) Seconds.secondsBetween(LAST_FULL_MOON, currentDate).getSeconds()/SYN_DUR;
        value = value - (int)value;
        mTvOutput.setText(String.valueOf(value));
        mTvDateTime.setText(getString(R.string.temp_date_time,
                currentDate.getDayOfMonth(),
                currentDate.getMonthOfYear(),
                currentDate.getYear(),
                currentDate.getHourOfDay(),
                currentDate.getMinuteOfHour(),
                currentDate.getSecondOfMinute()));

        mMoonView.setValue(value);
    }

}
