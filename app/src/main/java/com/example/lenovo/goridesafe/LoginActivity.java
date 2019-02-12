package com.example.lenovo.goridesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
   private EditText name;
   private EditText pass;
    private Button login;
    private TextView forget;
    private Button helper;
    private Button signup;
    private int counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=(EditText) findViewById(R.id.userName);
        pass=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        forget=(TextView)findViewById(R.id.tvforget);
        helper=(Button)findViewById(R.id.helper);
        signup=(Button)findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(),pass.getText().toString());
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,HelperActivity. class);
                startActivity(intent);
            }
        });


    }
    private void validate(String uname,String pass)
    {
        if(uname.equals("Admin") && pass.equals("1234"))
        {
            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }else
        {
            counter--;
            if(counter==0)
            {
                Intent intent =new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        }
    }
}
