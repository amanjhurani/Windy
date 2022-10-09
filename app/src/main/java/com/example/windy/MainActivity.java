package com.example.windy;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView, tempView, cloudView, latView, lonView, visView, timeView, cityView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        tempView = findViewById(R.id.temperatureval);
        cloudView = findViewById(R.id.cloudsval);
        latView = findViewById(R.id.latitudeval);
        lonView = findViewById(R.id.longitudeval);
        visView = findViewById(R.id.visibilityval);
        timeView = findViewById(R.id.timezoneval);
        cityView = findViewById(R.id.textView4);
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        tempView = findViewById(R.id.temperatureval);
        cloudView = findViewById(R.id.cloudsval);
        latView = findViewById(R.id.latitudeval);
        lonView = findViewById(R.id.longitudeval);
        visView = findViewById(R.id.visibilityval);
        timeView = findViewById(R.id.timezoneval);
        cityView = findViewById(R.id.textView4);

    }




    public class Downloadtask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            public void findweather(View view){
            Downloadtask task = new Downloadtask();
            editText = (EditText)findViewById(R.id.editText);
            String city = editText.getText().toString();
            task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=a26d9a475a4f262685d020cf32e5cbf1");
            InputMethodManager mgr =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);
                 Downloadtask task = new Downloadtask();
            editText = (EditText)findViewById(R.id.editText);
            String city = editText.getText().toString();
            task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=a26d9a475a4f262685d020cf32e5cbf1");
            InputMethodManager mgr =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);
        }

            try{

                url = new URL(strings[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data!=-1){
                    char current = (char) data;
                    result+=current;
                    data = reader.read();

                }

                return result;


            }
            catch (Exception e){
                e.printStackTrace();
                return "";
            }

        }

        @Override
        protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String wcity, tempe , lat, lon, visible, time;
                String clouds = null;
                try{
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                JSONArray arr = new JSONArray(weatherInfo);


                String name = jsonObject.getString("name");
                wcity = name;
                String main1 = jsonObject.getString("main");
                String temp = main1.substring(8,10);

                tempe = temp+ " deg";
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");

                    if(!main.equals("")&& !description.equals("")){
                        clouds = description;
                    }

                }

                String coordInfo = jsonObject.getString("coord");
                String latitude = coordInfo.substring(7,12);
                lat = latitude;
                String longitude = coordInfo.substring(19,24);
                lon = longitude;


                String visibility = jsonObject.getString("visibility");
                visible =visibility;
                String timezone = jsonObject.getString("timezone");
                time =timezone;




                if(!wcity.equals("") || !tempe.equals("")|| !clouds.equals("")|| !lat.equals("")|| !lon.equals("")|| !visible.equals("")|| !time.equals(""))
                {
                    cityView.setText(wcity);
                    tempView.setText(tempe);
                    cloudView.setText(clouds);
                    latView.setText(lat);
                    lonView.setText(lon);
                    visView.setText(visible);
                    timeView.setText(time);
                    textView.setVisibility(View.VISIBLE);
                    tempView = findViewById(R.id.temperatureval);
                    cloudView = findViewById(R.id.cloudsval);
                    latView = findViewById(R.id.latitudeval);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Couldn't find weather",Toast.LENGTH_SHORT).show();
                }

                if(!wcity.equals("") || !tempe.equals("")|| !clouds.equals("")|| !lat.equals("")|| !lon.equals("")|| !visible.equals("")|| !time.equals(""))
                {
                    cityView.setText(wcity);
                    textView.setVisibility(View.VISIBLE);
                    tempView = findViewById(R.id.temperatureval);
                    cloudView = findViewById(R.id.cloudsval);
                    latView = findViewById(R.id.latitudeval);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Couldn't find weather",Toast.LENGTH_SHORT).show();
                }


            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Couldn't find weather",Toast.LENGTH_SHORT).show();
            }
        }
    }





    public void findweather(View view){
        Downloadtask task = new Downloadtask();
        editText = (EditText)findViewById(R.id.editText);
        String city = editText.getText().toString();
        task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=a26d9a475a4f262685d020cf32e5cbf1");
        InputMethodManager mgr =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }
}
