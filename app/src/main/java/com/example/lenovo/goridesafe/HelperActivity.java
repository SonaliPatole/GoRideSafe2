package com.example.lenovo.goridesafe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class HelperActivity extends AppCompatActivity {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private String arrayName[]={"Hospital","Contacts","Police","Fire"};
    private CircleMenu circleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        mDrawerlayout=(DrawerLayout)findViewById(R.id.drawerhelper);
        mToggle=new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        circleMenu=(CircleMenu) findViewById(R.id.circle_menuhelper);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.ic_add_black_24dp,R.drawable.ic_remove_black_24dp)
                .addSubMenu(Color.parseColor("#258CFF"),R.drawable.ic_local_hospital_black_24dp)
                .addSubMenu(Color.parseColor("#6d4c41"),R.drawable.ic_contacts_black_24dp)
                .addSubMenu(Color.parseColor("#ff0000"),R.drawable.policeimg)
                .addSubMenu(Color.parseColor("#03a9f4"),R.drawable.fire)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        Toast.makeText(HelperActivity.this,"You selected "+arrayName[index],Toast.LENGTH_SHORT).show();
                        if(arrayName[index].equals("Contacts"))
                        {
                            Intent intent=new Intent(HelperActivity.this,SMSActivity.class);
                            startActivity(intent);
                        }
                        else if(arrayName[index].equals("Hospital")){
                            Intent intent=new Intent(HelperActivity.this,CallActivity.class);
                            startActivity(intent);
                        }
                        else if(arrayName[index].equals("Fire")){
                            Intent intent=new Intent(HelperActivity.this,NearbyMapActivity.class);
                            startActivity(intent);
                        }

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
