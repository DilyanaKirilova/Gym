package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dkirilova on 7/11/2017.
 */

public class HttpDataHandler {

    public HttpDataHandler(){

    }

    public String getHttpData(String requestUrl){

        URL url;
        StringBuilder response = new StringBuilder();

        try{
            url = new URL(requestUrl);
            HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application-/x-www-from-urlencoded");

            int responseCode = connection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = br.readLine()) != null){
                    response.append(line);
                }
            }else {
                response = null;
            }

        }catch (ProtocolException e){
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response != null? response.toString() : "";
    }
}
