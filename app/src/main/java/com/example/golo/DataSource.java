package com.example.golo;

import android.content.Intent;
import android.os.AsyncTask;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataSource<T> {
    private final String API_token = "e251f2f69b2b4413aaba270a02148849";
    private String url = "http://api.football-data.org/v2/";

        public String getJsonfromURL(final String apiURL) throws Exception {
            URL url = new URL(apiURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //opens a new connection with that URL
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Auth-Token", API_token); //set a parameter("X-Auth-Token") with my API Token

            int status = connection.getResponseCode();
            if (status == 200) { //if the connection was made sucessfully
                StringBuffer content = new StringBuffer();
                String inputLine;

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((inputLine = in.readLine()) != null)
                    content.append(inputLine).append("\n");

                in.close();
                return content.toString();
            } else if (status == 429) {
                throw new Exception("429");
            } else {
                throw new Exception("HTTP STATUS: " + status);
            }
        }


        public T getObjectfromJson(final String apiURL, Class<T> myClass) throws Exception {
            String json;
            Gson gson = new Gson();
            T result;

            json = getJsonfromURL(apiURL);
            result = gson.fromJson(json, myClass);
            return result;
        }

    public String getUrl() {
        return url;
    }
}
