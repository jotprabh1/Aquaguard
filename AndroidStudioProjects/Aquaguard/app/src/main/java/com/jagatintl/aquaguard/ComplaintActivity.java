package com.jagatintl.aquaguard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComplaintActivity extends AppCompatActivity {
    EditText subject, message;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subject=(EditText)findViewById(R.id.editTextSubject);
        message=(EditText)findViewById(R.id.editTextMessage);
        submit=(Button)findViewById(R.id.buttonSend);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub = subject.getText().toString();
                String mes = message.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "prabhjot.narula@gmail.com");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
                emailIntent.putExtra(Intent.EXTRA_TEXT, mes);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    onBackPressed();
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ComplaintActivity.this, "No Email Client Installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
