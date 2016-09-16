package com.example.lukas.pesquisa;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Conectar extends AsyncTask<String,String, JSONObject> {

    /*public Conectar(){
        this.execute("https://raw.githubusercontent.com/lukasepramos/Pesquisa/master/jsonQuestionario.txt");
    }*/


    @Override
    protected JSONObject doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        JSONObject json;
        json=null;
        try{
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line ="";

            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            json = new JSONObject(buffer.toString());
            return json;
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(connection != null)
                connection.disconnect();
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /*@Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);

    }*/




}


