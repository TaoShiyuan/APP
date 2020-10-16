package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.firstapp.R.id.itemdetail;

public class Listjump extends AppCompatActivity implements Runnable,AdapterView.OnItemClickListener{

    ListView mylistview;
    Handler handler;
    //从网络上提取数据的时候才用得到

    private ArrayList<HashMap<String, String>> listItem;
    //存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listjump);
// 调用

        //与页面布局文件中的listview关联
        mylistview = (ListView) findViewById(R.id.mylistview);

        ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate：" + i);
            // 标题文字
            map.put("ItemDetail", "detail" + i);
            // 详情描述
            listItems.add(map);
        }

        //实现Runnable接口，开启线程
        // 创建新线程
        Thread t = new Thread((Runnable) this);
        // 开启线程
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 9) {
                    List<HashMap<String, String>> list2 = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(Listjump.this, list2, //listItem数据源
                            R.layout.listhuilv,
                            //ListItem的XML布局实现
                            new String[]{"ItemTitle", "ItemDetail"},
                            //数据的key
                            new int[]{R.id.itemtitle, itemdetail}//布局里的id，k与id一一匹配
                    );
                    //listview.setAdapter(myAdapter);
                    mylistview.setAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };

        mylistview.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAtPosition = mylistview.getItemAtPosition(position);
        //通过map获取数据

//通过view获取数据
        TextView title=view.findViewById(R.id.itemtitle);
        TextView detail=view.findViewById(itemdetail);
        String title2=String.valueOf(title.getText());
        String detail2=String.valueOf(detail.getText());

//

        //打开新的界面，传递参数
        Intent intent=new Intent(this,Listjump2.class);
        intent.putExtra("title",title2);
        // intent.putExtra("detail",detail2);
        float mn=Float.parseFloat(detail2);
        intent.putExtra("rate",Float.parseFloat(detail2));
        startActivity(intent);

    }


    @Override
    public void run() {
        URL url = null;
        String myre = null;
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();

        try {
            url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            //所以获取的数据已经保存在这里了，html
            String html = inputStream2String(in);
            Log.i("thread", "run:html=" + html);
            //直接调用然后进行处理
            //      myre = useJsoup(html);
            String str = html;
            Document doc = Jsoup.parse(str);
            Elements trs = doc.select("table").get(0).select("tr");
//接下来写一个for循环得到一个string 数组

            //首先是获取长度，先获得
            int tablelen = 0;
            String cou = "ran";
            String huil = "ran";
            //  tablelen=trs.
            //一共是有28行Table表
            for (int i = 1; i < 28; i++) {
                Elements t1 = trs.get(i).select("td");
                cou = t1.get(0).text();

                huil = t1.get(5).text();
                //直接汇率和间接汇率的转化
                float bw = 100f / Float.parseFloat(huil);
                String er = bw + "";
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", cou);
                map.put("ItemDetail", er);
                retList.add(map);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析网页中的数据，我看是网页先输入，然后在解析

        Message msg = handler.obtainMessage(9);
        msg.obj = retList;
        handler.sendMessage(msg);

    }
    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}