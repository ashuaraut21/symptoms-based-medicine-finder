package com.example.symptombasedmedicinefinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class disease_name_prescription extends AppCompatActivity {
    TextView selected;
    EditText disease_name,prescription,dailydose,agedetails,notes;
    Button register;
    public static String select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_name_prescription);
        selected=(TextView)findViewById(R.id.selected_symptom);

        disease_name=(EditText)findViewById(R.id.disease_name);
        prescription=(EditText)findViewById(R.id.prescription);
        dailydose=(EditText)findViewById(R.id.dailydose);
        agedetails=(EditText)findViewById(R.id.agelimit);
        notes=(EditText)findViewById(R.id.cantake);



        register=(Button)findViewById(R.id.add_disease);
        Bundle extras=getIntent().getExtras();
        selected.setText(extras.getString("select"));
        select=extras.getString("select");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LedOnOff gettrans = new LedOnOff();
                String url1 = "http://mahavidyalay.in/AcademicDevelopment/Symptom%20Based%20Medicine%20Finder/add_disease.php?disease_name="+ URLEncoder.encode(disease_name.getText().toString())
                        +"&prescription="+URLEncoder.encode(prescription.getText().toString())
                        +"&symptom="+URLEncoder.encode(select)
                        +"&dailydose="+URLEncoder.encode(dailydose.getText().toString())
                        +"&agedetails="+URLEncoder.encode(agedetails.getText().toString())
                        +"&notes="+URLEncoder.encode(notes.getText().toString());

                gettrans.execute(url1);

            }
        });




    }


    //code for send data to the server
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class LedOnOff extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        int count=0;
        @Override
        protected String doInBackground(String... geturl) {


            try{
                //	String url= ;


                HttpClient http=new DefaultHttpClient();
                HttpPost http_get= new HttpPost(geturl[0]);
                HttpResponse response=http.execute(http_get);
                HttpEntity http_entity=response.getEntity();
                BufferedReader br= new BufferedReader(new InputStreamReader(http_entity.getContent()));
                out = br.readLine();

            }catch (Exception e){

                out= e.toString();
            }
            return out;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(disease_name_prescription.this, null,
                    "Updating Register Information...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(disease_name_prescription.this, ""+result, Toast.LENGTH_LONG).show();
            progress.dismiss();
            Intent register_i1 = new Intent(disease_name_prescription.this, doctor_home.class);
            startActivity(register_i1);
        }



    }
}
