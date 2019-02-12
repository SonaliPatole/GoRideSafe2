package com.example.lenovo.goridesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private EditText name,email,mobile,pass,retype;
    private Button signup;
    private TextView alreadysignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=(EditText)findViewById(R.id.tname);
        email=(EditText)findViewById(R.id.temail);
        mobile=(EditText)findViewById(R.id.tmobile);
        pass=(EditText)findViewById(R.id.tpass);
        retype=(EditText)findViewById(R.id.tretype);
        signup=(Button)findViewById(R.id.signup);
        alreadysignup=(TextView)findViewById(R.id.tvalready);

        alreadysignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //register in database
                }
            }
        });
    }
    private Boolean validate()
    {
        Boolean result=false;
        String nm=name.getText().toString();
        String passwrd=pass.getText().toString();
        String retyp=retype.getText().toString();
        String mail=email.getText().toString();
        if(nm.isEmpty() && passwrd.isEmpty() && mail.isEmpty() && retyp.isEmpty())
        {
            Toast.makeText(this,"please enter all the details",Toast.LENGTH_SHORT).show();
        }else
        {
            result=true;
        }
        return result;
    }
}
