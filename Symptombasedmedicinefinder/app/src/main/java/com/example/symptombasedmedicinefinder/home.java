package com.example.symptombasedmedicinefinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.tech.TagTechnology;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class home extends AppCompatActivity {
    EditText user_name,password;
    Button login;
    TextView register;
    public SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user_name=(EditText)findViewById(R.id.user_name);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        register=(TextView)findViewById(R.id.register_new_user);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(home.this,register_user.class);
                startActivity(i1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status=true;

                if(user_name.getText().toString().contentEquals("")){
                    user_name.setError("Enter Valid Username");
                    status=false;
                }

                if(password.getText().toString().contentEquals("")){
                    password.setError("Enter Valid Password");
                    status=false;
                }

                if(status){

                    if(user_name.getText().toString().contentEquals("admin")&&password.getText().toString().contentEquals("admin")){

                        Intent i1= new Intent(home.this,AdminDashboard.class);
                        startActivity(i1);

                    }else{

                        LedOnOff gettrans = new LedOnOff();
                        String url1 = "http://mahavidyalay.in/AcademicDevelopment/Symptom%20Based%20Medicine%20Finder/login.php?username="+user_name.getText().toString()
                                +"&pass="+password.getText().toString();
                        gettrans.execute(url1);
                    }

                }



            }
        });
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
    }
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
            progress = ProgressDialog.show(home.this, null,
                    "Login...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
             Toast.makeText(home.this, ""+result, Toast.LENGTH_LONG).show();

             String role="";

            try{
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");



                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    role=jsonChildNode.optString("Role");

                }

                if(role.contentEquals("Doctor")){

                    Intent i1= new Intent(home.this,doctor_home.class);
                    startActivity(i1);
                    finish();
                }

                if(role.contentEquals("User")){

                    Intent i1= new Intent(home.this,UserDashboard.class);
                    startActivity(i1);
                    finish();
                }

            }catch(Exception e){
                Toast.makeText(home.this,""+e, Toast.LENGTH_LONG).show();
            }
            /*
            if(result.contentEquals("fail")) {
                progress.dismiss();
                Toast.makeText(home.this,"Please Enter valid Username & password",Toast.LENGTH_LONG).show();

            }else{

                //Toast.makeText(home.this,""+result1,Toast.LENGTH_LONG).show();
                String result1 = result.substring(0, 8);
                progress.dismiss();

                if (result1.contentEquals("success1")) {
                    Intent verify_i1 = new Intent(home.this, UserDashboard.class);
                    startActivity(verify_i1);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("OWNER", result.substring(8));
                    editor.commit();
                    finish();

                } else {
                    Toast.makeText(home.this, "please enter valid username and password", Toast.LENGTH_LONG).show();


                }

            }
 */
        }



    }
}
