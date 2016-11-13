package com.jagatintl.aquaguard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SplashScreen extends Activity {

    public static final String url = "http://nanhea.netne.net/get-data.php";

    // JSON Node names
    public static final String TAG_CONTACTS = "result";

    public static final String TAG_NAME = "name";

    public static final String TAG_ADDRESS = "address";

    public static final String TAG_IMAGE = "image";

    // contacts JSONArray
    JSONArray contacts = null;

    // Hashmap for ListView
    public static ArrayList<HashMap<String, String>> contactList=new ArrayList<>();
    public static ArrayList<HashMap<String, String>> productList=new ArrayList<>();
    public static ArrayList<Bitmap> ProductImages=new ArrayList<>();

    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /**
         * Showing splashscreen while making network calls to download necessary
         * data before launching the app Will use AsyncTask to make http call
         */

        PrefetchData data=new PrefetchData();
        data.execute();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SplashScreen.this, "Your Internet Connection is slow", Toast.LENGTH_SHORT).show();
            }
        },8000);


    }

    public static boolean searchContactList(HashMap<String,String > search)
    {
        int i =0;
        HashMap<String,String> contact;
        while(i<contactList.size())
        {
            contact=contactList.get(i);
            if(search.get(TAG_NAME).equals(contact.get(TAG_NAME)) && search.get(TAG_ADDRESS).equals(contact.get(TAG_ADDRESS)))
            {
                return true;
            }

            i++;
        }
        return false;
    }
    public static boolean searchContactList(String search)
    {
        int i =0;
        HashMap<String,String> contact;
        while(i<contactList.size())
        {
            contact=contactList.get(i);
            if(search.equals(contact.get(TAG_NAME)))
            {
                return true;
            }

            i++;
        }
        return false;
    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            /*
             * Will make http call here This call will download required data
             * before launching the app
             */
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url);

            if(jsonStr!=null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_CONTACTS);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);


                        String name = c.getString(TAG_NAME);
                        String address = c.getString(TAG_ADDRESS);
                        String imgurl=c.getString(TAG_IMAGE);
                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<>();
                        HashMap<String,String> product = new HashMap<>();

                        Bitmap mIcon11;

                        //Since we have only one test API, we use if condition to seperate contacts from product details
                        if(imgurl.equals("")&&name.contains("aquaguard.com")) {
                            // adding each child node to HashMap key => value
                            contact.put(TAG_ADDRESS, address);
                            contact.put(TAG_NAME, name);

                            // adding contact to contact list
                            contactList.add(contact);
                        }
                        else if(imgurl.contains("eureka")){
                            product.put(TAG_NAME, name);
                            address="Rs."+address;
                            product.put(TAG_ADDRESS, address);
                            try {
                                    InputStream in = new URL(imgurl).openStream();
                                    mIcon11 = BitmapFactory.decodeStream(in);
                                    ProductImages.add(mIcon11);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            // adding products to product list
                            productList.add(product);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and launch main activity
            handler.removeCallbacksAndMessages(null);
            startActivity(new Intent(SplashScreen.this, Login.class));

            // close this activity
            finish();
        }

    }
}