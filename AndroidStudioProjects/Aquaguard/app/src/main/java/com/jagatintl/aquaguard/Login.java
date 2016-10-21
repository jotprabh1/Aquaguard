package com.jagatintl.aquaguard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText email, pass;
    Button login;
    TextView register;
    HashMap<String,String> contact=new HashMap<>();
    public static String eml="";
    String password;
            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.eText_username);
        pass=(EditText)findViewById(R.id.eText_Password);
        login=(Button)findViewById(R.id.login_button);
        register=(TextView)findViewById(R.id.textregister);
        checkDB();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eml=email.getText().toString()+"@aquaguard.com";
                password=pass.getText().toString();
                contact.put(SplashScreen.TAG_NAME,eml);
                contact.put(SplashScreen.TAG_ADDRESS,password);
                loginEvent();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegistrationActivity.class));
                finish();
            }
        });
    }
    private void loginEvent()
    {
        if(SplashScreen.searchContactList(contact))
        {
            SQLiteDatabase sb;

            sb = openOrCreateDatabase("myaquaguarddetails", MODE_PRIVATE, null);
            sb.execSQL("create table if not exists MyDetails(Prod_Name varchar, Prod_Num varchar, Dealer_Name varchar, Purchase_dt varchar, Warranty_varchar, email varchar, pass varchar)");
            sb.execSQL("insert into MyDetails values('','','','','','" + eml + "','" + password + "')");
            startActivity(new Intent(Login.this,ProductsActivity.class));
            finish();
        }else
        {
            Toast.makeText(Login.this, "Username or Password Invalid! Please check or register.", Toast.LENGTH_LONG).show();
            email.setText("");
            pass.setText("");
        }
    }
    private void checkDB()
    {
        SQLiteDatabase s=openOrCreateDatabase("myaquaguarddetails",MODE_PRIVATE,null);
        s.execSQL("create table if not exists MyDetails(Prod_Name varchar, Prod_Num varchar, Dealer_Name varchar, Purchase_dt varchar, Warranty_varchar, email varchar, pass varchar)");
        Cursor c=s.rawQuery("select *  from MyDetails",null);
        if(c.moveToFirst())
        {
            eml=c.getString(c.getColumnIndex("email"));
            contact.put(SplashScreen.TAG_NAME,eml);
            contact.put(SplashScreen.TAG_ADDRESS,c.getString(c.getColumnIndex("pass")));
            c.close();
            s.close();
            loginEvent();
        }
        c.close();
    }
}
