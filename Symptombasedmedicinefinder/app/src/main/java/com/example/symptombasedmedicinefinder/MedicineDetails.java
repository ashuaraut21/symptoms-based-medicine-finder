package com.example.symptombasedmedicinefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MedicineDetails extends Activity {

TextView disease,presc,symptom,dailydose,agedetails,notedetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicinedetails);

        disease=(TextView)findViewById(R.id.diseasename);
        presc=(TextView)findViewById(R.id.prescription);
        symptom=(TextView)findViewById(R.id.symptom_match);

        dailydose=(TextView)findViewById(R.id.dailydose);
        agedetails=(TextView)findViewById(R.id.agedetails);
        notedetails=(TextView)findViewById(R.id.notedetails);

        Intent i1=getIntent();
        disease.setText("Disease Name : "+i1.getStringExtra("disease_name"));
        presc.setText("Medicnes : "+i1.getStringExtra("medname"));
        symptom.setText("Symptoms Found : "+i1.getStringExtra("symptom"));

        dailydose.setText("Daily Dose : "+i1.getStringExtra("dailydose"));
        agedetails.setText("Age Details : "+i1.getStringExtra("agedetails"));
        notedetails.setText("Other : "+i1.getStringExtra("notedetails"));




    }
}

