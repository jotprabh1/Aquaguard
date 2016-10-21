package com.jagatintl.aquaguard;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    Spinner spinnerProd;
    EditText review;
    Button submit;

    ArrayList<String> products = new ArrayList<>();

    String prod="",rev="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerProd=(Spinner)findViewById(R.id.spinnerProducts);
        review=(EditText)findViewById(R.id.editTextReview);
        submit=(Button)findViewById(R.id.buttonSubmitReview);

        for(int i=0;i<SplashScreen.productList.size();i++)
            products.add(SplashScreen.productList.get(i).get(SplashScreen.TAG_NAME));

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,products);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProd.setAdapter(adapter);
        spinnerProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prod=products.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!prod.equals(""))
                {
                    rev=review.getText().toString();
                    if(!rev.equals(""))
                    {
                        rev="Review: "+rev;
                        ChatActivity.insertToOnlineDB(prod,rev,Login.eml);
                        review.setText("");
                        onBackPressed();
                        finish();
                    }else
                        Toast.makeText(ReviewActivity.this, "Review Can't be Blank", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(ReviewActivity.this, "Please Select a Product", Toast.LENGTH_LONG).show();
            }
        });

    }

}
