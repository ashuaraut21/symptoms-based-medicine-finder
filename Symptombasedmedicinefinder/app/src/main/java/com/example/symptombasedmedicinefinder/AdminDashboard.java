package com.example.symptombasedmedicinefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminDashboard extends Activity {

Button adddoctor,viewdoctors,logout;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindashboard);


        adddoctor=(Button)findViewById(R.id.newreg);
        adddoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(AdminDashboard.this,AddDoctor.class);
                startActivity(i1);
            }
        });

        viewdoctors=(Button)findViewById(R.id.viewdoctor);
        viewdoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(AdminDashboard.this,ViewDoctors.class);
                startActivity(i1);
            }
        });

        logout=(Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1= new Intent(AdminDashboard.this,MainActivity.class);
                startActivity(i1);
                finish();
            }
        });

    }
}
