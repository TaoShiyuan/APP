package com.example.firstapp;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class List extends ListActivity implements Runnable,AdapterView.OnItemClickListener{
    Handler handler;
    ListView mylistview;

    @SuppressLint("HandlerLeak")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.list);
       // mylistview=findViewById(R.id.mylistview);
       //mylistview.setAdapter(new Myadapter(List.this,));
        //java.util.List list1 = new ArrayList<String>();
        //爬取网络汇率数据
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    ArrayList<HashMap<String,String>> list2 = (ArrayList<HashMap<String,String>>) msg.obj;
                    //java.util.List list3 = (java.util.List) msg.obj;
                    //ListAdapter adapter=new ArrayAdapter<String>(
                    //      List.this,android.R.layout.simple_list_item_1,list3
                    //);

                    ListAdapter listItemAdapter= new SimpleAdapter(List.this,list2, R.layout.listhuilv, new String[]{"itemtitle", "itemdetail"},
                            new int[]{R.id.itemtitle, R.id.itemdetail});
                    setListAdapter(listItemAdapter);
                    //mylistview.setAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };

    }
//
//        for(int i=1;i<100;i++){
//            list1.add("item"+i);
//        }
//        String[] list_data={"one","two","three","four"};
//        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);
//        setListAdapter(adapter);

        Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 1000 * 3600*24);// 间隔1天
        }
        void update() {
            //刷新msg的内容
        }
    };
    @Override
    protected void onDestroy() {

        handler.removeCallbacks(runnable); //停止刷新
        super.onDestroy();
    }
    //解析网络数据
    @Override
    public void run() {
        Log.i(TAG,"run:");
        Message msg=handler.obtainMessage(7);
        String url = "https://www.usd-cny.com/bankofchina.htm";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(0);
            Elements tds = table6.getElementsByTag("td");
            //java.util.List list3=new ArrayList<String>();
            ArrayList<HashMap<String,String>> listItem=new ArrayList<HashMap<String,String>>();
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();
                String str2=str1+"==>"+val;
                Log.i(TAG,
                        "run:" + str1 + "==>" + val);
                //list3.add(str2);
                HashMap<String,String> map=new HashMap<String,String>();
                map.put("itemtitle","rate:"+str1);
                map.put("itemdetail","detail:"+val);
                listItem.add(map);
            }
           // msg.obj=list3;
            msg.obj=listItem;
            handler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
//

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Object itemAtPosition=mylistview.getItemAtPosition(position);
        HashMap<String,String> map=(HashMap<String, String>) itemAtPosition;
        String titlestr=map.get("itemtitle");
        String detailstr=map.get("itemdetail");
        Log.i(TAG,"onitemclick:titlestr="+titlestr);
        Log.i(TAG,"onitemclick:detailstr="+detailstr);
    }
}

