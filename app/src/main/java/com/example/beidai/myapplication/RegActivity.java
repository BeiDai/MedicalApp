package com.example.beidai.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.lang.UCharacterEnums;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.example.beidai.myapplication.ConstantUtil.SERVER_ADDRESS;
import static com.example.beidai.myapplication.ConstantUtil.SERVER_PORT;

public class RegActivity extends AppCompatActivity {
    MyConnector mc = null;
    String uno = "";
    ProgressDialog pd = null;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    View linearLayout = (LinearLayout)findViewById(R.id.regResult);
                    linearLayout.setVisibility(View.VISIBLE);
                    EditText etUno = (EditText)findViewById(R.id.etUno);
                    etUno.setText(uno);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        Button btnReg = (Button)findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = ProgressDialog.show(RegActivity.this,"请稍后","正在连接服务器...",false);
                register();
            }
        });
        Button btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button btnEnter = (Button)findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegActivity.this,MainActivity.class);
                intent.putExtra("uno",uno);
                startActivity(intent);
                finish();
            }
        });
    }

    public void register(){
        new Thread(){
            public void run(){
//                Looper.prepare();

                EditText etName = (EditText)findViewById(R.id.etName);
                EditText etPwd1 = (EditText)findViewById(R.id.etPwd1);
                EditText etPwd2 = (EditText)findViewById(R.id.etPwd2);
                EditText etEmail = (EditText)findViewById(R.id.etEmail);

                String name = etName.getEditableText().toString().trim();
                String pwd1 = etPwd1.getEditableText().toString().trim();
                String pwd2 = etPwd2.getEditableText().toString().trim();
                String email = etEmail.getEditableText().toString().trim();

                if(name.equals("") || pwd1.equals("") || pwd2.equals("") ){
//                    Toast.makeText(RegActivity.this,"请将注册信息填写完整",Toast.LENGTH_LONG).show();
                        Log.i("AppDebug","请将注册信息填写完整");
                    pd.dismiss();
                    return;
                }

                if(!pwd1.equals(pwd2)){
//                    Toast.makeText(RegActivity.this,"两次输入的密码不一致",Toast.LENGTH_LONG).show();
                       Log.i("AppDebug","两次输入的密码不一致");
                    pd.dismiss();
                    return;
                }

//                try{
//                    mc = new MyConnector(SERVER_ADDRESS,SERVER_PORT);
//                    String regInfo = "<#REGISTER#>" + name + "|" + pwd1 + "|" + email ;
//                    mc.dout.writeUTF(regInfo);
//                    String result = mc.din.readUTF();
//                    pd.dismiss();
//                    if(result.startsWith("<#REG_SUCCESS#>")){
//                        result = result.substring(15);
//                        uno = result;
//                        myHandler.sendEmptyMessage(0);
////                        Toast.makeText(RegActivity.this,"注册成功!",Toast.LENGTH_LONG).show();
//                           Log.i("AppDebug","注册成功!");
//                    }
//                    else{
////                        Toast.makeText(RegActivity.this,"注册失败!",Toast.LENGTH_LONG).show();
//                           Log.i("AppDebug","注册失败!");
//                    }

                     uno = name;
                     pd.dismiss();
                     myHandler.sendEmptyMessage(0);
//                }catch (Exception e){
////                    e.printStackTrace();
//                }
            }
        }.start();
    }
    @Override
    protected void onDestroy(){
        if(mc != null){
            mc.sayBye();
        }
        super.onDestroy();
    }
}
