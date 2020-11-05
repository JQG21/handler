package com.example.handler;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                TextView textView = findViewById(R.id.textContent);
                String s = msg.arg1+"";
                textView.setText(s);
            }
        };
        final Runnable myWorker = new Runnable() { //一、实现Runnable接口，run中实现耗时运算
            @Override
            public void run() {
                int progress = 0;
                while(progress<100){
                    Message msg = new Message();
                    msg.arg1=progress;
                    handler.sendMessage(msg);
                    progress+=10;
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();
                msg.arg1 = progress;
                handler.sendMessage(msg);
            }
        };

        Button start = findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null,myWorker,"WorkThread");
                //二、创建Thread对象，将Runnable传递给Thread对象
                workThread.start(); //调用start方法启动线程，当run返回时，该线程结束。
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.textContent);
                String s ="40";
                textView.setText(s);
            }
        });
    }
}