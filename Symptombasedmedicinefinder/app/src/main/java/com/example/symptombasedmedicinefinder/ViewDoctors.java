package com.example.symptombasedmedicinefinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

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

public class ViewDoctors extends Activity {
    ListView lst;
    public SharedPreferences sharedpreferences;
    public static String[] name;
    public static String[] contact;
    public static String[] email;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.viewdoctors);

        lst=(ListView)findViewById(R.id.listView1);
        try{


            GetData gettrans=new GetData();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"/ViewDoctors.php";
            // url=url+"pass="+URLEncoder.encode(upass)+"&";
            gettrans.execute(url);
        }catch(Exception e){
            Toast.makeText(ViewDoctors.this, ""+e, Toast.LENGTH_LONG).show();
        }

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
            progress = ProgressDialog.show(ViewDoctors.this, null,"Searching Doctors...");

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            //Toast.makeText(ViewDoctors.this,""+out, Toast.LENGTH_LONG).show();

            try{
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();
                name=new String[arraylength];
                contact=new String[arraylength];
                email=new String[arraylength];



                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    name[i]="Name : "+jsonChildNode.optString("name")+"\n Contact : "+jsonChildNode.optString("contact")+"\n Email : "+jsonChildNode.optString("email");
                    contact[i]=jsonChildNode.optString("contact");
                    email[i]=jsonChildNode.optString("email");


                }




                LevelAdapter2 adapter=new LevelAdapter2(ViewDoctors.this, name);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(ViewDoctors.this,""+e, Toast.LENGTH_LONG).show();
            }

            progress.dismiss();
        }



    }


}
