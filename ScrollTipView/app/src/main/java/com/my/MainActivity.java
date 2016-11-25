package com.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_number;
    private ScrollTipView scroll_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_number = (TextView) findViewById(R.id.tv_number);
        scroll_tip = (ScrollTipView) findViewById(R.id.scroll_tip);
        scroll_tip.bindView(tv_number);
    }
}
