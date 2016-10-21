package com.jagatintl.aquaguard;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Prabhjot Singh on 14-10-2016.
 */
public class ServiceHandler {
    static String response = null;
    public ServiceHandler() {
    }
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url) {
        InputStream in=null;
        try{
            URL requrl = new URL(url);
            HttpURLConnection conn=(HttpURLConnection)requrl.openConnection();
            conn.setRequestMethod("GET");
            in=new BufferedInputStream(conn.getInputStream());
            BufferedReader r=new BufferedReader(new InputStreamReader(in));

            StringBuilder sb= new StringBuilder();
            String line;

            while((line=r.readLine())!=null)
            {
                sb.append(line).append('\n');
            }
            conn.disconnect();
            response=sb.toString();
        }catch (Exception e)
        {
            Log.e("Error",e.getMessage());
        }finally {
            try {
                if(in!=null)
                    in.close();
            } catch (Exception e1) {
                Log.e("Error",e1.getMessage());
            }
        }
        return response;

    }

}
