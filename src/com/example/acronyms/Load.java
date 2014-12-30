package com.example.acronyms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class Load extends Activity 
{

	ProgressBar pb1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadasync);
		Intent ins=getIntent();
		pb1=(ProgressBar)findViewById(R.id.progressBar1);
		AsyncTest at=new AsyncTest();
		at.execute();
	
	}
	
	class AsyncTest extends AsyncTask<Void, Void, String[]>
	{	
		String d[][];
		String lists[],a[]=new String[100];
		InputStream is=null;
		SQLiteDatabase db;
		Cursor c;
		AssetManager as;
		String s="";
		
		int i=0,count=0,j=0;
		@Override
		protected String[] doInBackground(Void... params) 
		{
			
		
			//Load.this.deleteDatabase("Acronym");
			db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
			db.execSQL("Create table if not exists Category(names varchar)");
	        Log.i("Table and datbasae created","Yesits working!!!!");
	        String query1="Select * from Category";
	        c=null;
			c=db.rawQuery(query1, null);
			count=c.getCount();
			Log.i("Count is:",count+"");
			if(count<=0)
			{
				as=getAssets();
				
				try {
					lists=as.list("");
					is=as.open("Category");
					BufferedReader br=new BufferedReader(new InputStreamReader(is),8192);
					s=br.readLine();
				 while(s!=null)
				{
					a[i]=s;
					Log.i("Dtaaaaaaaa", a[i]);
					s=br.readLine();
					i++;
				}
				 br.close();
				 for(j=0;j<i;j++)
				{
					db.execSQL("Insert into Category values('"+a[j]+"')");
					Log.i("Database Entry!!", a[j]);
				}
				br.close();
				
				
				/////////////////////////////////////////////////////////////////////////////
				
				as=getAssets();
					try {
						lists=as.list("Data");
					for(int i=0;i<lists.length;i++)
					{
					db.execSQL("Create table if not exists "+lists[i]+" (shortform varchar,fullform varchar)");
					Log.i("FIleeeeeeeeeeee",lists[i]);
					is=as.open("Data/"+lists[i]);
						Log.i("File opened",lists[i]);
					
				br=new BufferedReader(new InputStreamReader(is),8192);
				s=br.readLine();
				j=0;
				while(s!=null)
				{	
					j++;
					s=br.readLine();
				}
				Log.i("Value of j fsfdsssvsv while is :", j+"");
				d=new String[j][2];
				j=0;
				is=as.open("Data/"+lists[i]);
				br=new BufferedReader(new InputStreamReader(is),8192);
				s=br.readLine();
				while(s!=null)
				{	
					Log.i("String read",s);
					d[j][0]=s.substring(0,s.indexOf(" "));
					d[j][1]=s.substring(s.indexOf(" ")+1);
					Log.i("Dtaaaaaaaa", d[j][0]+" Full form "+d[j][1]);
					s=br.readLine();
					j++;
				}
				Log.i("Value of j after while is :", j+"");
				for(int k=0;k<j;k++)
				{
					db.execSQL("Insert into "+lists[i]+" values('"+d[k][0]+"','"+d[k][1]+"')");
					Log.i("Database Entry!!", d[k][0]+"  "+d[k][1]);
					
				}
				
				}
					}
					catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.close();
				db.close();
				
				
				
				
				
				
				
				
				
				
		//////////////////////////////////////////////////////////////////////////////		
				
				
			}
				 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//c.close();
				//db.close();
			} //end of if
			else
			{	Log.i("I have Data", "Pushing to other Activity");
				i=0;
				while(c.moveToNext())
				{
					a[i]=c.getString(0);
					i++;
							
				}
				c.close();
				db.close();
			}
	        
			return a;
					}
		
		 protected void onPostExecute(String[] params) 
		 { 
			Log.i("I am Here For PAssing Intent","I am doing my work");
			 Intent in=new Intent(Load.this,MainActivity.class);
				in.putExtra("Data",params);
				startActivity(in);
				Log.i("DOne!!!","My work is done!!!");
				finish();
			 return ;
		 }
				
	
	
}
		
}