package com.example.cappmesaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView txtZil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        txtZil = findViewById(R.id.textView);
        Intent verial = getIntent();
        String alinanzil = verial.getStringExtra("zilSay");

        txtZil.setText(alinanzil == null ? "0" : alinanzil);

        btn = (Button) findViewById(R.id.zilButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,MainMesaj.class );
                startActivity(it);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();


    }
}