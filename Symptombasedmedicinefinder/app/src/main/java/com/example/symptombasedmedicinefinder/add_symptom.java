package com.example.symptombasedmedicinefinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class add_symptom extends AppCompatActivity {
    EditText symptom_title, description;
    Button register_symptom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptom);
        symptom_title=(EditText)findViewById(R.id.symptom_name);
        description=(EditText)findViewById(R.id.symptom_discription);
        register_symptom=(Button)findViewById(R.id.add_symptom_button);
        register_symptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LedOnOff gettrans = new LedOnOff();
                String url1 = "http://mahavidyalay.in/AcademicDevelopment/Symptom%20Based%20Medicine%20Finder/add_symptoms.php?title="+ URLEncoder.encode(symptom_title.getText().toString())
                        +"&description="+URLEncoder.encode(description.getText().toString());
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
            progress = ProgressDialog.show(add_symptom.this, null,
                    "Updating Register Information...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(add_symptom.this, ""+result, Toast.LENGTH_LONG).show();
            progress.dismiss();
            Intent register_i1 = new Intent(add_symptom.this, doctor_home.class);
            startActivity(register_i1);
        }



    }
}
