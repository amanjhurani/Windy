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

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText2);

    }




    public class Downloadtask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

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
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                JSONArray arr = new JSONArray(weatherInfo);

                String msg= "";
                String name = jsonObject.getString("name");
                msg +="City : " + name+"\n";
                String main1 = jsonObject.getString("main");
                String temp = main1.substring(8,10);

                msg += "Temperature : "+temp+ " deg" +"\n";
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");

                    if(!main.equals("")&& !description.equals("")){
                        msg += main+" : "+description + "\n";
                    }

                }

                String coordInfo = jsonObject.getString("coord");
                String latitude = coordInfo.substring(7,12);
                msg += "Latitude : "+latitude+"\n";
                String longitude = coordInfo.substring(19,24);
                msg += "Longitude : "+longitude+"\n";


                String visibility = jsonObject.getString("visibility");
                msg +="Visibility : " + visibility+"\n";
                String timezone = jsonObject.getString("timezone");
                msg +="Timezone : " + timezone+"\n";




                if(!msg.equals("")){
                    textView.setText(msg);
                    textView.setVisibility(View.VISIBLE);
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
        editText = (EditText)findViewById(R.id.editText2);
        String city = editText.getText().toString();
        task.execute("https://openweathermap.org/data/2.5/weather?q="+city+"&appid=b6907d289e10d714a6e88b30761fae22");
        InputMethodManager mgr =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }
}
