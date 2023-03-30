package com.example.cappmesaj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainMesaj extends AppCompatActivity {
    ListView listview;
    int zilsayi;
    Button btnn;

    String formattedDateTime = "null";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onStart() {
        super.onStart();

        setContentView(R.layout.activity_main_mesaj);

        btnn = (Button) findViewById(R.id.buttonAll);

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onRestart();

            }
        });

        listview = findViewById(R.id.listview);
        try{
            JSONObject obj = new JSONObject(LoadJsonFromAsset());
            JSONArray array = obj.getJSONArray("notifications");
            HashMap<String,String> list ;
            ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
            ArrayList<HashMap<String,String>> arrayList2 = new ArrayList<>();
            int a = 0;
            int b = 0;
            zilsayi = 0;
            for(int i=0;i<array.length();i++){
                JSONObject o = array.getJSONObject(i);
                ;

                String okumaN;
                String usertype = o.getString("sender_user_type");
                String senddatetime = o.getString("ntf_send_datetime") == "SYSTEM_ADMIN" ? "Admin" : o.getString("ntf_send_datetime");
                String title = o.getString("ntf_title");
                String content = o.getString("ntf_content");
                list = new HashMap<>();
                if (o.getString("ntfd_read_datetime") == formattedDateTime){

                    okumaN = "News";


                    if(a==0){
                        a++;
                        list.put("ntf_News",okumaN);
                    }
                    list.put("ntf_title",title);
                    list.put("ntf_content",content);
                    list.put("sender_user_type",usertype);
                    list.put("ntf_send_datetime","Gönderim Tarihi " + senddatetime );
                    list.put("ntfd_read_datetime", "Okumadı");

                    arrayList.add(list);
                    zilsayi++;
                }
                else {


                    okumaN = "Readed";


                    if(b==0){
                        b++;
                        list.put("ntf_News", okumaN);
                    }
                    list.put("ntf_title",title);
                    list.put("ntf_content",content);
                    list.put("sender_user_type",usertype);
                    list.put("ntf_send_datetime","Gönderim Tarihi " + senddatetime);
                    if (o.getString("ntfd_read_datetime") != "null"){
                        list.put("ntfd_read_datetime", "Okuma Tarihi " + o.getString("ntfd_read_datetime"));
                    }else{
                        list.put("ntfd_read_datetime", "Okuma Tarihi " + formattedDateTime);
                    }


                    arrayList2.add(list);
                }



            }

            arrayList.addAll(arrayList2);
            ListAdapter adapter = new SimpleAdapter(this,arrayList,R.layout.list_viewdegn,new String[]{"ntf_News","ntf_title","ntf_content","sender_user_type","ntf_send_datetime","ntfd_read_datetime"},new int[]{R.id.ntf_News,R.id.ntf_title,R.id.ntf_content,R.id.ntf_sender_user_type,R.id.ntf_send_datetime,R.id.ntf_read_datetime});
            listview.setAdapter(adapter);



        }catch (JSONException e){
            e.printStackTrace();
        }



        System.out.println("zilsayi");
        System.out.println("Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        formattedDateTime = formatter.format(date);
        onStart();

        System.out.println("Restart"+ formattedDateTime);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent git = new Intent(MainMesaj.this,MainActivity.class);
        git.putExtra("zilSay",Integer.toString(zilsayi));
        startActivity(git);
    }

    public  String LoadJsonFromAsset(){
        String json=null;
        try {
            InputStream in = this.getAssets().open("response.json");
            int size = in.available();
            byte[] bbuffer = new byte[size];
            in.read(bbuffer);
            in.close();
            json = new String(bbuffer,"UTF-8");
        } catch (IOException e) {
           e.printStackTrace();
           return null;
        }
        return json;
    }
}