package com.example.symptombasedmedicinefinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class doctor_home extends AppCompatActivity {
    Button add_symptom,remove_symptom,add_disease,remove_disease;
            TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        add_symptom=(Button)findViewById(R.id.add_Symptom);
        remove_symptom=(Button)findViewById(R.id.remove_Symptom);
        add_disease=(Button)findViewById(R.id.add_disease);
        remove_disease=(Button)findViewById(R.id.remove_disease);
        logout=(TextView)findViewById(R.id.logout1);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(doctor_home.this,home.class);
                startActivity(i1);
                finish();
            }
        });

        add_symptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(doctor_home.this, add_symptom.class);
                startActivity(i1);

            }
        });

        remove_symptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(doctor_home.this, remove_symptom.class);
                startActivity(i2);
            }
        });


        add_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(doctor_home.this, add_disease.class);
                startActivity(i3);

            }
        });


        remove_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4=new Intent(doctor_home.this, remove_disease.class);
                startActivity(i4);

            }
        });


    }
}
