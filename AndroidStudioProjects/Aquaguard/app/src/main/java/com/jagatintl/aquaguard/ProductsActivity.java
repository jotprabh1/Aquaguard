package com.jagatintl.aquaguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsActivity extends AppCompatActivity {
    EditText searchText;
    ImageButton searchButton;
    public static ArrayList<HashMap<String,String>> searchProductList = new ArrayList<>();
    public static ArrayList<Bitmap> searchProductImages=new ArrayList<>();
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final GridView grid=(GridView)findViewById(R.id.gridViewProducts);
        searchText=(EditText)findViewById(R.id.editTextSearch);
        searchButton=(ImageButton)findViewById(R.id.imageButtonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSearch=searchText.getText().toString();
                searchProductList.clear();
                searchProductImages.clear();
                for(int i=0;i<SplashScreen.ProductImages.size();i++)
                {
                    if(SplashScreen.productList.get(i).get(SplashScreen.TAG_NAME).toLowerCase().contains(toSearch.toLowerCase()))
                    {
                        searchProductList.add(SplashScreen.productList.get(i));
                        searchProductImages.add(SplashScreen.ProductImages.get(i));
                    }
                }
                CustomAdapter c=new CustomAdapter(ProductsActivity.this);
                grid.setAdapter(c);
            }
        });
        searchProductImages.clear();
        searchProductList.clear();
        for(int i=0;i<SplashScreen.ProductImages.size();i++) {
            searchProductList.add(SplashScreen.productList.get(i));
            searchProductImages.add(SplashScreen.ProductImages.get(i));
        }

        CustomAdapter c=new CustomAdapter(ProductsActivity.this);
        grid.setAdapter(c);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ProductsActivity.this,DescriptionActivity.class);
                i.putExtra("Key",position);
                startActivity(i);
            }
        });
        preferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);
        int pos=preferences.getInt("Position",-1);
        if(pos>=0)
        {
            startActivity(new Intent(ProductsActivity.this,ReviewActivity.class));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.complaint)
        {
            startActivity(new Intent(ProductsActivity.this,ComplaintActivity.class));
        }
        if (id == R.id.chat) {
            startActivity(new Intent(ProductsActivity.this,ChatActivity.class));
        }
        if(id == R.id.review)
        {
            startActivity(new Intent(ProductsActivity.this,ReviewActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
