package com.example.symptombasedmedicinefinder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class add_disease extends AppCompatActivity {
    ListView listView;
    Button getChoice;

    public static final String TAG = "ListViewExample";
    public static String[] name;
    public static String[] discription;
    public static String[] id_medi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disease);
        listView = (ListView)findViewById(R.id.listview);
        getChoice=(Button)findViewById(R.id.get);
        getChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = "";



                int cntChoice = listView.getCount();

                SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();

                for(int i = 0; i < cntChoice; i++){

                    if(sparseBooleanArray.get(i)) {

                        selected += listView.getItemAtPosition(i).toString() + ",";



                    }

                }



                Toast.makeText(add_disease.this, selected, Toast.LENGTH_LONG).show();
                Intent i1=new Intent(add_disease.this,disease_name_prescription.class);
                Bundle bundle=new Bundle(10);
                bundle.putString("select",selected);
                i1.putExtras(bundle);
                startActivity(i1);

            }
        });



        try {

            getJSON("http://mahavidyalay.in/AcademicDevelopment/Symptom%20Based%20Medicine%20Finder/show_symptom.php");
        }catch (Exception e){
            Toast.makeText(add_disease.this,""+e,Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
        name = new String[jsonArray.length()];
        discription = new String[jsonArray.length()];
        id_medi = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            name[i] = obj.getString("title");
            discription[i] = obj.getString("description");
            id_medi[i] = obj.getString("id");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, name);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(arrayAdapter);
    }




}
