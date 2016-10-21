package com.jagatintl.aquaguard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatActivity extends AppCompatActivity {
    EditText dealerEmail, message;
    Button send;
    Pattern emailCheck= Pattern.compile("^[a-zA-Z\\d_.]{6,}@aquaguard.com$");
    String dEmail="";

    public static ArrayList<HashMap<String,String>> chatList=new ArrayList<>();

    public static String TAG_EMAIL="Sender";
    public static String TAG_MESSAGE="Message";

    Handler handler=new Handler();
    ListView lv;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dealerEmail=(EditText)findViewById(R.id.eTDealerEmail);
        message=(EditText)findViewById(R.id.eTChatMessage);
        send=(Button)findViewById(R.id.SendButton);
        lv=(ListView)findViewById(R.id.listView);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dEmail=dealerEmail.getText().toString();
                String mess=message.getText().toString();

                Matcher m=emailCheck.matcher(dEmail);
                if(m.matches())
                {
                    insertToOnlineDB(Login.eml,dEmail,mess);
                    new PrefetchData().execute();
                    if(i==0) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new PrefetchData().execute();
                            }
                        }, 60000);
                    }
                    i++;
                    message.setText("");
                }else
                {
                    Toast.makeText(ChatActivity.this, "Dealer Email Invalid!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacksAndMessages(null);
    }

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
            String jsonStr = sh.makeServiceCall(SplashScreen.url);

            if(jsonStr!=null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray(SplashScreen.TAG_CONTACTS);

                    // looping through All Contacts
                    chatList.clear();
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);


                        String name = c.getString(SplashScreen.TAG_NAME);
                        String address = c.getString(SplashScreen.TAG_ADDRESS);
                        String imgurl=c.getString(SplashScreen.TAG_IMAGE);
                        // tmp hashmap for single contact
                        HashMap<String, String> chat = new HashMap<>();

                        //Since we have only one test API, we use if condition to seperate contacts from product details and chats
                        if((name.equals(dEmail)&&address.equals(Login.eml))||(name.equals(Login.eml)&&address.equals(dEmail))) {
                            // adding each child node to HashMap key => value
                            chat.put(TAG_EMAIL, name);
                            chat.put(TAG_MESSAGE, imgurl);

                            // adding contact to contact list
                            chatList.add(chat);
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
            ChatCustomAdapter c=new ChatCustomAdapter(ChatActivity.this);
            lv.setAdapter(c);

        }

    }
    public static void insertToOnlineDB(String... p) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected String doInBackground(String... params) {

                HashMap<String, String> chat = new HashMap<>();
                chat.put(SplashScreen.TAG_NAME, params[0]);
                chat.put(SplashScreen.TAG_ADDRESS, params[1]);
                chat.put(SplashScreen.TAG_IMAGE,params[2]);


                try {
                    URL url = new URL("http://nanhea.netne.net/insert-db.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "ISO-8859-1"));
                    writer.write(RegistrationActivity.getPostDataString(chat));

                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    conn.disconnect();
                    if (responseCode == HttpURLConnection.HTTP_OK)
                        return "Successful";
                    else
                        return "Failed";


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Failed";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }

        }
        new SendPostReqAsyncTask().execute(p);
    }

}
