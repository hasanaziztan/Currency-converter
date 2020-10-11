package com.azo.dovuzcevirici;

import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView chfView;
    TextView usdView;
    TextView jpyView;
    TextView tryView;
    TextView cadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chfView = findViewById(R.id.cadView);
        usdView = findViewById(R.id.usdView);
        jpyView = findViewById(R.id.jpyView);
        tryView = findViewById(R.id.tryView);
        cadView = findViewById(R.id.cadView);
    }

    public void getRates (View view) {

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://data.fixer.io/api/latest?access_key=3e4a5b10bf15869c089c94cadfcb9773&format=1";

            downloadData.execute(url);

        }catch (Exception e){
            System.out.println("hata getrates");

        }

    }


    //AsyncTask senkronize olmadan çalışmak
    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            //URL hatası için
            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");//utf8 = türkçe karakter okusun diye


                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(inputStreamReader, 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                result = sb.toString();
            } catch (Exception e) {
                // Oops
            }







return result;

                //herşeyi result kaydettik ve return ettik


        }

        //işlem bittikten sonra ne olsun istiyorsak buraya yazıyoruz
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //System.out.println("Alinan data" + s);

            try {
//                s=s.replace("\n","").replace(" ","").replace("\"","");
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                //System.out.println("base:" + base);

                String rates = jsonObject.getString("rates");

                JSONObject jsonObject1 = new JSONObject(rates);
                String turkLira = jsonObject1.getString("TRY");
                tryView.setText("TRY:"+ turkLira);


                String chf = jsonObject1.getString("CHF");
                chfView.setText("CHF:" + chf);


                String usd = jsonObject1.getString("USD");
                usdView.setText("USD:"+  usd);


                String jpy = jsonObject1.getString("JPY");
                jpyView.setText("JPY: "+ jpy);


                String cad = jsonObject1.getString("CAD");
                cadView.setText("CAD:"+ cad);




            }catch (Exception e){
                System.out.println("Json hata !!");
                }
            }
        }
    }

