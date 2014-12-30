package com.example.acronyms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class about extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.about);
	        Intent i=getIntent();
	        i.getStringExtra("new1");
	         
}
}
