package com.example.acronyms;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Search extends Activity 
{
	String a[];
	ListView lv;
	ArrayAdapter<String> ad;
	SQLiteDatabase db;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchxml);
		Intent i=getIntent();
		db=openOrCreateDatabase("Mydata",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        db.execSQL("Create table if not exists Database(shortform varchar,fullform varchar)");
		a=i.getStringArrayExtra("Search");
		lv=(ListView)findViewById(R.id.listView2);
		
    	ad=new ArrayAdapter<String>(this,R.layout.text,R.id.textView2,a);
    	lv.setAdapter(ad);
    	
    	
    	lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,
					long id) 
			{
				// TODO Auto-generated method stub
				Log.i("Position", position+"");
				Log.i("Position", a[position]);
				
				String sf=a[position];
				String ff="";
				String query1="Select fullform from database where shortform ='"+sf+"'";
				Cursor c=db.rawQuery(query1, null);
				if(c.moveToNext())
				{
				ff=c.getString(0);
				}
				Log.i("Position",ff);
				Toast.makeText(getApplicationContext(),"Fullform of"+"  "+sf+"  "+"is"+"  "+ff, Toast.LENGTH_LONG).show();
				
			}
		});
		
	}
}
