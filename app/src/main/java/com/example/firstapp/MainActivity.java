package com.example.firstapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  final  int scoreArray[]={1,2,3};
    private Button button3,button4,button5,button6;
    private TextView textView6;
    private int Score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inintView();

    }

    private void inintView() {
        //获取按钮Id
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
        button6=findViewById(R.id.button6);
        textView6=findViewById(R.id.textView6);

        //按钮实现监听
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button5:
                scoreAdd(scoreArray[0]);

                break;
            case R.id.button4:
                scoreAdd(scoreArray[1]);

                break;
            case R.id.button3:
                scoreAdd(scoreArray[2]);

                break;

            case  R.id.button6:
                reset();
                break;

            default:
                break;
        }
    }
    private void reset() {
        Score=0;
        textView6.setText(Integer.toString(Score));
    }
    private   void  scoreAdd(int score){





        Score+=score;
        ShowText();
    }
    private  void ShowText(){
        textView6.setText(Integer.toString(Score));

    }

}


