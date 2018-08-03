package com.example.beidai.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.example.beidai.myapplication.ConstantUtil.SERVER_ADDRESS;
import static com.example.beidai.myapplication.ConstantUtil.SERVER_PORT;

public class LoginActivity extends AppCompatActivity {
    ProgressDialog pd;
    MyConnector mc = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        checkIfRemember();

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = ProgressDialog.show(LoginActivity.this,"请稍后","正在连接服务器",true,true);
                login();
            }
        });

        Button btnReg = (Button)findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,com.example.beidai.myapplication.RegActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton ibExit = (ImageButton)findViewById(R.id.ibExit);
        ibExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    public void login(){
        new Thread(){
            public void run(){
//                Looper.prepare();
                try{
//                    if(mc == null){
//                        mc = new MyConnector(SERVER_ADDRESS,SERVER_PORT);
//                    }
                    EditText etUid = (EditText)findViewById(R.id.etUid);
                    EditText etPwd = (EditText)findViewById(R.id.etPwd);
                    String uid = etUid.getEditableText().toString().trim();
                    String pwd = etPwd.getEditableText().toString().trim();
                    if(uid.equals("") || pwd.equals("")){
                     //   pd.dismiss();
                     //   Toast.makeText(LoginActivity.this,"请输入账号和密码！",Toast.LENGTH_SHORT).show();
                        Log.i("AppDebug","Toast");
                        return;
                    }
//                    String msg = "<#LOGIN#>" + uid + "|" + pwd;
//                    mc.dout.writeUTF(msg);
//                    String receivedMsg = mc.din.readUTF();
                    pd.dismiss();
//                    if(receivedMsg.startsWith("<#LOGIN_SUCCESS#>")){
//                        receivedMsg = receivedMsg.substring(17);
//                        String [] sa = receivedMsg.split("\\|");
                           CheckBox cb = (CheckBox)findViewById(R.id.cbRemember);
                           if(cb.isChecked()){
                              rememberMe(uid,pwd);
                           }
                            if(!cb.isChecked()){
                                forgetMe(uid,pwd);
                            }
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
//                    }
//                    else if(msg.startsWith("<#LOGIN_FAIL#>")){
//                        Toast.makeText(LoginActivity.this,msg.substring(14),Toast.LENGTH_LONG).show();
//                        Looper.loop();
//                        Looper.myLooper().quit();
//                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void rememberMe(String uid,String pwd){
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("uid",uid);
        editor.putString("pwd",pwd);
        editor.commit();
    }

    public void forgetMe(String uid,String pwd){
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("uid",null);
        editor.putString("pwd",null);
        editor.commit();
    }


    public void checkIfRemember(){
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        String uid = sp.getString("uid",null);
        String pwd = sp.getString("pwd",null);
        if(uid != null && pwd != null){
            EditText etUid = (EditText)findViewById(R.id.etUid);
            EditText etPwd = (EditText)findViewById(R.id.etPwd);
            CheckBox cbRemember = (CheckBox)findViewById(R.id.cbRemember);
            etUid.setText(uid);
            etPwd.setText(pwd);
            cbRemember.setChecked(true);
        }
    }
    @Override
    protected void onDestroy(){
        if(mc != null){
            mc.sayBye();
        }
        super.onDestroy();
    }

}
