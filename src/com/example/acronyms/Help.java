package com.example.acronyms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Help extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Intent i=getIntent();
        i.getStringExtra("new");
        
    }


   
    
}
