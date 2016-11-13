package com.jagatintl.aquaguard;

import android.content.Context;
import android.content.SharedPreferences;
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
    int pos;
    SharedPreferences preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img=(ImageView)findViewById(R.id.imageViewProductDescription);
        desc=(TextView)findViewById(R.id.textViewDescription);
        purchasebutton=(Button)findViewById(R.id.purchasebutton);

        pos=getIntent().getExtras().getInt("Key");

        preference=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        img.setImageBitmap(ProductsActivity.searchProductImages.get(pos));
        desc.setText(ProductsActivity.searchProductList.get(pos).get(SplashScreen.TAG_NAME));
        purchasebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DescriptionActivity.this, "Thanks for Your Purchase!!!", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=preference.edit();
                editor.putInt("Position",pos);
                editor.apply();

            }
        });

    }

}
