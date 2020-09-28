package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class activityopen extends AppCompatActivity {
//    private static final String TAG = "";
    EditText dollarset;
    EditText euroset;
    EditText wonset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityopen);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        float dollar2=bundle.getFloat("dollar_rate_key",0);
        float euro2=bundle.getFloat("euro_rate_key",0);
        float won2=bundle.getFloat("won_rate_key",0);

        dollarset=findViewById(R.id.dollarset);
        euroset=findViewById(R.id.euroset);
        wonset=findViewById(R.id.wonset);
        dollarset.setText(String.valueOf(dollar2));
        euroset.setText(String.valueOf(euro2));
        wonset.setText(String.valueOf(won2));

//        Log.i(TAG,"onCreate:dollar2="+dollar2);
//        Log.i(TAG,"onCreate:euro2="+euro2);
//        Log.i(TAG,"onCreate:won2="+won2);
    }
    public void onClick(View v) {
        dollarset=findViewById(R.id.dollarset);
        euroset=findViewById(R.id.euroset);
        wonset=findViewById(R.id.wonset);


        Intent intent=new Intent();         //为返回值bundler1分配空间
        Bundle bdl=new Bundle();

        String newdollar=dollarset.getText().toString();
        String neweuro=euroset.getText().toString();
        String newwon=wonset.getText().toString();
        bdl.putFloat("dollar_rate_key",Float.parseFloat(newdollar));
        bdl.putFloat("euro_rate_key",Float.parseFloat(neweuro));
        bdl.putFloat("won_rate_key",Float.parseFloat(newwon));
        intent.putExtras(bdl);
        setResult(2,intent);
        finish();

    }

}