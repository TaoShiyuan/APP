package com.example.firstapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Listjump2 extends AppCompatActivity {
    String TAG="rateCalc";
    float rate=0f;
    TextView input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listjump2);
        String title =getIntent().getStringExtra("title");
        rate=getIntent().getFloatExtra("rate",0f);
        ((TextView)findViewById(R.id.title)).setText(title); //转成TextView对象，匿名对象
        input=findViewById(R.id.input);
        input.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            //
            @Override
            public void afterTextChanged(Editable s) {
                TextView result=(TextView)Listjump2.this.findViewById(R.id.result);
                if(s.length()>0){
                    float val=Float.parseFloat(s.toString());
                    result.setText(val+"RMB==>"+(rate*val));

                }
                else {
                    result.setText("");
                }
            }
        });
    }
}