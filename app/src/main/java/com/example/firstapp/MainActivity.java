package com.example.firstapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaParser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    float dollarRate=0.1464f,euroRate=0.1258f,wonRate=171.9833f;
    TextView textView,textView2,textView3;
    EditText rmb_input;
    Button dollar,euro,won;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifen);
        rmb_input=findViewById(R.id.rmb_put);
        dollar=findViewById(R.id.dollar);
        euro=findViewById(R.id.euro);
        won=findViewById(R.id.won);
        //Toast.makeText(this, "please input number",Toast.LENGTH_SHORT).show();

    }
    public void dollar(View btn){
        Toast.makeText(this, "please input number",Toast.LENGTH_SHORT).show();
        String str1 =rmb_input.getText().toString();
        //然后是转化为double类型
        float a = Float.parseFloat(str1);
        if(btn.getId()== R.id.dollar){

            float b = a *dollarRate;
            String output=String.valueOf(b);
            rmb_input.setText(output+"美元");
        }else if(btn.getId()== R.id.euro){
            float b = a *euroRate;
            String output=String.valueOf(b);
            rmb_input.setText(output+"欧元");
        }else{
            float b = a *wonRate;
            String output=String.valueOf(b);
            rmb_input.setText(output+"韩元");
        }
    }
    public void change(View btn){
        //open activityopen
        Intent intent=new Intent(this,activityopen.class);
        Bundle bundle=new Bundle();

        bundle.putFloat("dollar_rate_key",dollarRate);
        bundle.putFloat("euro_rate_key",euroRate);
        bundle.putFloat("won_rate_key",wonRate);
        intent.putExtras(bundle);
        //Log.i(TAG,"openOne:dollarRate="+dollarRate);
        //startActivity(second);
        startActivityForResult(intent,2);

    }
    protected void onActivityResult(int requestcode, int resultcode,Intent data){
//        if(requestcode==1&&resultcode==2){
            Bundle bundle=data.getExtras();
            dollarRate=bundle.getFloat("dollar_rate_key",0);
            euroRate=bundle.getFloat("euro_rate_key",0);
            wonRate=bundle.getFloat("won_rate_key",0);
//        }
        super.onActivityResult(requestcode,resultcode,data);
        textView=findViewById(R.id.textView);
        String output=String.valueOf(dollarRate);
        textView.setText("美元汇率为："+output);
        textView2=findViewById(R.id.textView2);
        String output2=String.valueOf(euroRate);
        textView2.setText("欧元汇率为："+output2);
        textView3=findViewById(R.id.textView3);
        String output3=String.valueOf(wonRate);
        textView3.setText("韩元汇率为："+output3);
    }
}



