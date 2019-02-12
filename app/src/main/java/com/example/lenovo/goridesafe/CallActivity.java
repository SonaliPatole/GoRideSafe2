package com.example.lenovo.goridesafe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CallActivity extends AppCompatActivity {
    final int SEND_SMS_PERMISSION_REQUEST_CODE=1;
    private EditText no;
    private Button call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        no=(EditText)findViewById(R.id.txtcall);
        call=(Button)findViewById(R.id.btncall);
        call.setEnabled(false);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
            call.setEnabled(true);
        }else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},SEND_SMS_PERMISSION_REQUEST_CODE);
        }
    }
    public void onClickCall(View view)
    {
        String phoneNum=no.getText().toString();
        Intent intent=new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+phoneNum));
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        startActivity(intent);
    }
}
