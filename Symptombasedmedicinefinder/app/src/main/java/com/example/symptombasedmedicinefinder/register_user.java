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
import android.widget.CheckBox;
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

public class register_user extends AppCompatActivity {

    EditText user_name,contact,email,pass;
    Button user_register;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        user_name=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        user_register=(Button)findViewById(R.id.register_user);
        check=(CheckBox)findViewById(R.id.checkBox);


        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_name.getText().length()>0){
                    if(contact.length()==10){
                        if(email.length()>0){
                            if(pass.length()>0){
                                if(check.isChecked()) {
                                    LedOnOff gettrans = new LedOnOff();
                                    String url1 = "http://mahavidyalay.in/AcademicDevelopment/Symptom%20Based%20Medicine%20Finder/user_register.php?name=" + URLEncoder.encode(user_name.getText().toString())
                                            + "&contact=" + URLEncoder.encode(contact.getText().toString())
                                            + "&email=" + URLEncoder.encode(email.getText().toString())
                                            + "&password=" + URLEncoder.encode(pass.getText().toString())
                                            + "&role=" + URLEncoder.encode("User");
                                    gettrans.execute(url1);
                                }else{
                                    Toast.makeText(register_user.this,"Please accept terms and conditions",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                pass.setError("Enter valid password");
                                pass.requestFocus(20);
                            }

                        }else{
                            email.setError("Enter valid email");
                            email.requestFocus(20);
                        }

                    }else{
                        contact.setError("Enter valid contact");
                        contact.requestFocus(20);
                    }
                }else{
                    user_name.setError("Enter valid user name");
                    user_name.requestFocus(20);
                }
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
            progress = ProgressDialog.show(register_user.this, null, "Updating Register Information...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(register_user.this, ""+result, Toast.LENGTH_LONG).show();
            progress.dismiss();

            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(contact.getText().toString(),null,"user name: "+email.getText().toString()+" password: "+pass.getText().toString(),null,null);
            Intent register_i1 = new Intent(register_user.this, home.class);
            startActivity(register_i1);
            finish();
        }



    }
}
