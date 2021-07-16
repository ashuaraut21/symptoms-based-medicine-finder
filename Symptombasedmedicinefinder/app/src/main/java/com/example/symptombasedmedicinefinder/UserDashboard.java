package com.example.symptombasedmedicinefinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class UserDashboard extends Activity {
    ListView lst;
    String symptoms[];
    EditText et1;
    TextView tv1;
    Button b1,reset,logout;
    String selectedsymptom="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdashboard);

        et1=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button);
        logout=(Button)findViewById(R.id.logout);
        lst=(ListView)findViewById(R.id.listView1);
        tv1=(TextView)findViewById(R.id.selectedsymptoms);
        reset=(Button)findViewById(R.id.reset);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(UserDashboard.this,MainActivity.class);
                startActivity(i1);
                finish();

            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText("");
                selectedsymptom="";


            }
        });

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    String symptoms=et1.getText().toString();

                    GetData gettrans=new GetData();
                    DbParameter host=new DbParameter();
                    String url=host.getHostpath();
                    url=url+"/GetSymptoms.php?symptom="+URLEncoder.encode(symptoms);
                    // url=url+"pass="+URLEncoder.encode(upass)+"&";
                    gettrans.execute(url);
                }catch(Exception e){
                    Toast.makeText(UserDashboard.this, ""+e, Toast.LENGTH_LONG).show();
                }
            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {




                 selectedsymptom=selectedsymptom+","+symptoms[position];
                 tv1.setText(selectedsymptom);
                 et1.setText("");



            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(UserDashboard.this,SearchMedicine.class);
                i1.putExtra("symptom",selectedsymptom);
                startActivity(i1);
            }
        });


    }



    private class GetData extends AsyncTask<String, Integer, String> {
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
            //progress = ProgressDialog.show(UserDashboard.this, null,"Loading...");

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("product_info");
                int arraylength=jsonMainNode.length();
                symptoms=new String[arraylength];
                /*
                kname =new String[arraylength];
                ownername =new String[arraylength];
                address=new String[arraylength];
                cnumber=new String[arraylength];
                email=new String[arraylength];
                */

                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    symptoms[i]=jsonChildNode.optString("title");
                    /*
                    ownername[i]=jsonChildNode.optString("OwnerName");
                    address[i]=jsonChildNode.optString("Address");
                    cnumber[i]=jsonChildNode.optString("ContactNumber");
                    email[i]=jsonChildNode.optString("Email");
                    */
                }

            }catch(Exception e){
                Toast.makeText(UserDashboard.this," Json"+e, Toast.LENGTH_LONG).show();
            }

            try{

                LevelAdapter2 adapter=new LevelAdapter2(UserDashboard.this,symptoms);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(UserDashboard.this,"No records Found", Toast.LENGTH_LONG).show();
            }

           // progress.dismiss();
        }



    }
}
