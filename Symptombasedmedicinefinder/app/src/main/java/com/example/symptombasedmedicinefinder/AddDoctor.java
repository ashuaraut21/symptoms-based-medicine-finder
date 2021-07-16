package com.example.symptombasedmedicinefinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

public class AddDoctor extends Activity {
    EditText user_name,contact,email,pass;
    Button user_register;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddoctor);

        user_name=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        user_register=(Button)findViewById(R.id.register_user);

        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean status=true;
                if(user_name.getText().toString().length()<0){
                    user_name.setError("Enter Valid Name");
                    user_name.requestFocus();
                    status=false;
                }

                if(contact.getText().toString().length()!=10){
                    contact.setError("Enter Valid Contact");
                    contact.requestFocus();
                    status=false;

                }
                if(pass.getText().toString().length()==0){
                    pass.setError("Enter Valid Password");
                    pass.requestFocus();
                    status=false;
                 }

                if(email.getText().toString().length()==0){
                    email.setError("Enter Valid Email");
                    email.requestFocus();
                    status=false;
                }

                if(status){
                   LedOnOff gettrans = new LedOnOff();
                    String url1 = "http://mahavidyalay.in/AcademicDevelopment/Symptom%20Based%20Medicine%20Finder/user_register.php?name="+ URLEncoder.encode(user_name.getText().toString())
                            +"&contact="+URLEncoder.encode(contact.getText().toString())
                            +"&email="+URLEncoder.encode(email.getText().toString())
                            +"&password="+URLEncoder.encode(pass.getText().toString())
                            +"&role="+URLEncoder.encode("Doctor");
                    gettrans.execute(url1);
                }


            }
        });
    }


    private class LedOnOff extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        int count=0;
        @Override
        protected String doInBackground(String... geturl) {


            try{
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
            progress = ProgressDialog.show(AddDoctor.this, null, "Updating Register Information...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(AddDoctor.this, ""+result, Toast.LENGTH_LONG).show();
            progress.dismiss();

            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(contact.getText().toString(),null,"user name: "+email.getText().toString()+" password: "+pass.getText().toString(),null,null);

            Intent register_i1 = new Intent(AddDoctor.this, AdminDashboard.class);
            startActivity(register_i1);
            finish();
        }



    }
}
