package com.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText et_number;
    private ScrollTipView<EditText> scroll_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        et_number = (EditText) findViewById(R.id.et_number);
        scroll_tip = (ScrollTipView<EditText>) findViewById(R.id.scroll_tip);


        // bind View (View must extend TextView)
        scroll_tip.bindView(et_number);


        //EditText input  listener
        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (TextUtils.isEmpty(text)) {
                    et_number.setText("0");
                    return;
                }
                Integer intValue = Integer.valueOf(text);


                if (intValue < 0)
                    scroll_tip.setCurrentProgress(0);

                if (intValue > 100)
                    scroll_tip.setCurrentProgress(100);

                if (scroll_tip.getCurrentProgress() != intValue)
                    scroll_tip.setCurrentProgress(intValue);


                et_number.setSelection(text.length());

            }
        });
    }
}
