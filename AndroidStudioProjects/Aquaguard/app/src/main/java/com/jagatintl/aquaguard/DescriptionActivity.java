package com.jagatintl.aquaguard;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DescriptionActivity extends AppCompatActivity {
    ImageView img;
    TextView desc;
    Button purchasebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img=(ImageView)findViewById(R.id.imageViewProductDescription);
        desc=(TextView)findViewById(R.id.textViewDescription);
        purchasebutton=(Button)findViewById(R.id.purchasebutton);

        int i=getIntent().getExtras().getInt("Key");

        img.setImageBitmap(ProductsActivity.searchProductImages.get(i));
        desc.setText(ProductsActivity.searchProductList.get(i).get(SplashScreen.TAG_NAME));
        purchasebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DescriptionActivity.this, "Thanks for Your Purchase!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
