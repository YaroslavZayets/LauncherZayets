package com.example.impression.launchzayets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ApssListActivity {
    Button buttonCall;
    Button buttonAppsList;
    Button buttonMessage;
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCall = (Button) findViewById(R.id.buttonCall);
        buttonAppsList = (Button) findViewById(R.id.buttonAppsList);
        buttonMessage = (Button) findViewById(R.id.buttonMessage);
        gridView = (GridView) findViewById(R.id.gridView);








        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonCall:
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        startActivity(callIntent);
                        break;
                    case R.id.buttonAppsList:
                        Intent appsListIntent = new Intent(MainActivity.this, ApssListActivity.class);
                        startActivity(appsListIntent);
                        break;
                    case R.id.buttonMessage:
                        Intent messIntent = new Intent(Intent.ACTION_VIEW);
                        messIntent.setType("vnd.android-dir/mms-sms");
                        startActivity(messIntent);
                        break;
                }

            }
        };

        buttonCall.setOnClickListener(onClickListener);
        buttonAppsList.setOnClickListener(onClickListener);
        buttonMessage.setOnClickListener(onClickListener);









    }


}
