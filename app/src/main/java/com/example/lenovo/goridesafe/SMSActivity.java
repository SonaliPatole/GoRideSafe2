package com.example.lenovo.goridesafe;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SMSActivity extends AppCompatActivity {
    final int SEND_SMS_PERMISSION_REQUEST_CODE=1;
    Button send;
    EditText no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        send=(Button)findViewById(R.id.btnsms);
        no=(EditText)findViewById(R.id.mono);
        send.setEnabled(false);
        if(check(Manifest.permission.SEND_SMS)){
            send.setEnabled(true);
        }else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);

        }
    }
    public void onSend(View v)
    {
        String phoneNum=no.getText().toString();
        String msg="Hello";
        if(phoneNum==null || phoneNum.length()==0 || msg==null || msg.length()==0)
        {
            return ;
        }
        if(check(Manifest.permission.SEND_SMS)){
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum,null,msg,null,null);
            Toast.makeText(this,"Message Sent!",Toast.LENGTH_SHORT);
        }else
        {
            Toast.makeText(this,"Permission denied!",Toast.LENGTH_SHORT);
        }
    }
    public boolean check(String permission){
        int check=ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
