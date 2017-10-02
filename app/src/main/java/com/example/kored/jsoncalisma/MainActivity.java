package com.example.kored.jsoncalisma;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    String url = "http://api.fixer.io/latest";
    SpecialAsyncClass specialAsyncClass = new SpecialAsyncClass();
    specialAsyncClass.execute(url);
  }

  private class SpecialAsyncClass extends AsyncTask<String, Void, String> {

    String result = "";
    URL url;
    HttpURLConnection httpURLConnection;

    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);
      if (s != null) {
        Log.e("Gelen Değer =>", s);
      }
    }

    @Override
    protected String doInBackground(String... strings) {
      try {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
            Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
          url = new URL(strings[0]);
          httpURLConnection = (HttpURLConnection) url.openConnection();
          InputStream inputStream = httpURLConnection.getInputStream();
          InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
          int data = inputStreamReader.read();
          while (data > 0) {
            char character = (char) data;
            result += character;
            data = inputStreamReader.read();
          }
          Toast.makeText(MainActivity.this, "Wifi Var", Toast.LENGTH_SHORT).show();
          return result;
        } else {
          Toast.makeText(MainActivity.this, "Wifi Yok", Toast.LENGTH_SHORT).show();
          return null;
        }
      } catch (Exception e) {
        return null;
      }
    }
  }
}

