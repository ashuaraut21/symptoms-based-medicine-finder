package com.example.symptombasedmedicinefinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class user_home extends AppCompatActivity {
    Button search_disease,log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        search_disease=(Button)findViewById(R.id.search_disease);
        log_out=(Button)findViewById(R.id.log_out);
        search_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(user_home.this,select_symptom.class);
                startActivity(i1);
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(user_home.this,home.class);
                startActivity(i1);
                finish();
            }
        });
    }
}
