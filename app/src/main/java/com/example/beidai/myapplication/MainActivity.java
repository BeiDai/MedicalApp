package com.example.beidai.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    private Button button;
    private TextView textView;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        textView = (TextView) findViewById(R.id.book);
        final EditText editText = (EditText) findViewById(R.id.message);
        button = (Button) this.findViewById(R.id.button);
        Log.i("AppDebug","initView");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AppDebug","Thread");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("AppDebug","run");
                        result = new SocketUtil(editText.getText().toString(),"192.168.4.1").sendMessage();
                    }
                }).start();
            }
        });
    }
}

