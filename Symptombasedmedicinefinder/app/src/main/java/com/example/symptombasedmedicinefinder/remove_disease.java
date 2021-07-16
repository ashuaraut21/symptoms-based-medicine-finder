package com.example.symptombasedmedicinefinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class remove_disease extends AppCompatActivity {

    ListView listView;
    public SharedPreferences sharedpreferences;
    public static String[] disease_name;
    public static String[] prescription;
    public static String[] symptoms;
    public static String[] disease_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_disease);
        listView = (ListView) findViewById(R.id.list_item);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=listView.getItemAtPosition(position).toString();
                Intent show_i1= new Intent(remove_disease.this,show_remove_disease.class);
                Bundle bundle = new Bundle(10);
                bundle.putString("disease_name", disease_name[position]);
                bundle.putString("prescription", prescription[position]);
                bundle.putString("symptoms", symptoms[position]);
                bundle.putString("disease_id", disease_id[position]);
                show_i1.putExtras(bundle);
                startActivity(show_i1);

            }
        });

        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/Symptom%20Based%20Medicine%20Finder/show_disease.php");
        }catch (Exception e){
            //Toast.makeText(view_user.this,""+e,Toast.LENGTH_LONG).show();
        }

    }
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        disease_name = new String[jsonArray.length()];
        prescription = new String[jsonArray.length()];
        symptoms = new String[jsonArray.length()];
        disease_id = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            disease_name[i] = obj.getString("disease_name");
            prescription[i] = obj.getString("prescription");
            symptoms[i] = obj.getString("symptom");
            disease_id[i] = obj.getString("id");
        }
        LevelAdapter levelAdapter=new LevelAdapter(remove_disease.this,disease_name,prescription,prescription);
        listView.setAdapter(levelAdapter);
    }
}
