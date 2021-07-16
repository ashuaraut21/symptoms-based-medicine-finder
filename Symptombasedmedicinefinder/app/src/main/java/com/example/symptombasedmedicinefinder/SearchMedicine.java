package com.example.symptombasedmedicinefinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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

public class SearchMedicine extends Activity {

TextView tv1;
String symptoms[];
String medicinene_name[];
String disease_name[];
String dailydose[];
String agedetails[];
String notedetails[];
String getsymptoms;
ListView lst;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchmedicine);

        tv1=(TextView)findViewById(R.id.textView13);
        lst=(ListView)findViewById(R.id.listView1);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i1=new Intent(SearchMedicine.this, MedicineDetails.class);
                i1.putExtra("medname",medicinene_name[position]);
                i1.putExtra("disease_name", disease_name[position]);
                i1.putExtra("symptom", symptoms[position]);

                i1.putExtra("dailydose",dailydose[position]);
                i1.putExtra("agedetails", agedetails[position]);
                i1.putExtra("notedetails", notedetails[position]);


                startActivity(i1);

            }
        });

        // Extracting the User Symptoms
        Intent i1= getIntent();
        getsymptoms=i1.getStringExtra("symptom");
        getsymptoms=getsymptoms.substring(1);
        tv1.setText(getsymptoms);

        try{


           GetData gettrans=new GetData();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"/GetMedicines.php?symptom="+ URLEncoder.encode(getsymptoms);
            // url=url+"pass="+URLEncoder.encode(upass)+"&";
            gettrans.execute(url);
        }catch(Exception e){
            Toast.makeText(SearchMedicine.this, ""+e, Toast.LENGTH_LONG).show();
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
            progress = ProgressDialog.show(SearchMedicine.this, null,"Searching Medicine...");

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{

                //- Extracting the Dataset Symptoms through JSon Decoding
                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("product_info");
                int arraylength=jsonMainNode.length();
                symptoms=new String[arraylength];
                medicinene_name=new String[arraylength];
                disease_name=new String[arraylength];

                dailydose=new String[arraylength];
                agedetails=new String[arraylength];
                notedetails=new String[arraylength];
                /*
                kname =new String[arraylength];
                ownername =new String[arraylength];
                address=new String[arraylength];
                cnumber=new String[arraylength];
                email=new String[arraylength];
                */

                for (int i = 0; i < jsonMainNode.length(); i++) {

                    // Problability getting
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    symptoms[i]=jsonChildNode.optString("symptom");

                    disease_name[i]=jsonChildNode.optString("disease_name");
                    dailydose[i]=jsonChildNode.optString("DailyDose");
                    agedetails[i]=jsonChildNode.optString("AgeDetails");
                    notedetails[i]=jsonChildNode.optString("NoteDetails");



                    // Spliting by seperator

                    //splitting input
                    String inputedsymptoms[]=getsymptoms.split(",");

                  //spitting dataset symptoms
                    String dbsymptoms[]=symptoms[i].split(",");

                    //----calculation A
                   int matchcount=0;
                   int totalsymptoms=0;

                    //----calculation B
                    //Calculating Length

                    for(int i1=0;i1<inputedsymptoms.length;i1++){

                        for(int k1=0;k1<dbsymptoms.length;k1++){
                            // perform symptom mathcing
                            if(inputedsymptoms[i1].toLowerCase().trim().contentEquals(dbsymptoms[k1].toLowerCase().trim())){

                                matchcount=matchcount+1;
                            }

                        }

                    }

                    // calculating probability
                    float prob1=(float)matchcount/(float)dbsymptoms.length;

                    //--8 Generate Final result
                    medicinene_name[i]=jsonChildNode.optString("prescription")+"\n Match Ration : "+prob1;

                }

            }catch(Exception e){
                Toast.makeText(SearchMedicine.this," ", Toast.LENGTH_LONG).show();
            }

            try{

                LevelAdapter2 adapter=new LevelAdapter2(SearchMedicine.this, medicinene_name);
                lst.setAdapter(adapter)	;


            }catch(Exception e){
                Toast.makeText(SearchMedicine.this,"No records Found", Toast.LENGTH_LONG).show();
            }

              progress.dismiss();
        }



    }
}
