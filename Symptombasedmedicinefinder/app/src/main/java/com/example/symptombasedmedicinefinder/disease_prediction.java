package com.example.symptombasedmedicinefinder;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class disease_prediction extends AppCompatActivity {
    public static String[] disease_name;
    public static String[] prescription;
    public static String[] symptoms;
    public static String[] disease_id;
    TextView pred,presc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_prediction);
        pred=(TextView)findViewById(R.id.pred);
        presc=(TextView)findViewById(R.id.presc);
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
        Bundle b1=getIntent().getExtras();


        String[] symptom_match = new String[b1.getString("select").split(",").length];
        //substitute_match=content.split(",");
        int m=0;

            for (int k1 = 0; k1 < disease_name.length; k1++) {
                //match=0;
                String mcontent = symptoms[k1];
                String sep_content[] = mcontent.split(",");

                for (int k2 = 0; k2 < sep_content.length; k2++) {
                    String[] match_symptom=b1.getString("select").split(",");
                    try {
                    for (int k3 = 0; k3 < match_symptom.length; k3++) {

                        if (sep_content[k2].contentEquals(match_symptom[k3])) {

                                pred.setText(disease_name[k1]);
                                presc.setText(prescription[k1]);

                        }
                    }
                    }catch (Exception e){
                        Toast.makeText(disease_prediction.this,"first"+e,Toast.LENGTH_LONG).show();
                    }

                }

            }

    }
}
