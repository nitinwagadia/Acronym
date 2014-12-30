package com.example.acronyms;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends Activity 
{
	String s[],f[];
	String name="",sf="",ff="";
	ListView lv;
	SQLiteDatabase db;
	Cursor c=null;
	ArrayAdapter ad;
	Button b;
	int pos,count,i=0;
	View layout;
	ArrayAdapter<String> apat;
	Spinner sp;
	EditText et,et1;
	TextView tvs;
	private static Toast t;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent in=getIntent();
		name=in.getStringExtra("Name");
		b=(Button)findViewById(R.id.button2);
		tvs=(TextView)findViewById(R.id.textView1);
		tvs.setText("Words");
		t=new Toast(this);
		LoadData();
		listcall();
		registerForContextMenu(lv);
		
	}
	
	private void listcall() 
	{
		lv=(ListView)findViewById(R.id.listView1);
    	ad=new ArrayAdapter<String>(Display.this,R.layout.text,R.id.textView2,s); //Setting Adapter for ListView
    	lv.setAdapter(ad);
    	if(s.length<=0)
    	{
    		t=Toast.makeText(Display.this,"List is Emplty",Toast.LENGTH_SHORT);
    		t.show();
    	}
    	else
    	{
    	lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int pos,long arg3) 
			{
				t=Toast.makeText(Display.this,"Fullform of "+s[pos]+" is "+f[pos],Toast.LENGTH_LONG);
						t.show();	
						
			}});
    	
    	
    		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long id) 
			{
				// TODO Auto-generated method stub
				pos=position;
				return false;
			}
		});
    	
    	
    	
    	
    	
    	}
	} //end of listcall

	public void LoadData()
	{
		//db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		Log.i("Yes i reached","background");
        String query1="Select * from "+name +" order by shortform";
        c=db.rawQuery(query1, null);
		 Log.i("query is ", "executed");
		count=c.getCount();
		f=new String[count];
		s=new String[count]; 
		Log.i("Count is:",count+"");
		Log.i("I have Data", "Pushing to other Activity");
			i=0;
			while(c.moveToNext())
			{
				//d[i][0]=c.getString(0);
				//d[i][1]=c.getString(1);	
				s[i]=c.getString(0);
				f[i]=c.getString(1);
				i++;
		}
			db.close();
			c.close();
			

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo menuinfo)
	{
		super.onCreateContextMenu(menu, view, menuinfo);
		menu.setHeaderTitle("Menu");
		
		menu.add(0, view.getId(),0,"Edit");
		menu.add(0, view.getId(),0,"Delete");
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		if(item.getTitle()=="Edit")
			Edit(item.getItemId());
		
		else if(item.getTitle()=="Delete")
			Delete(item.getItemId());
		
	
		return false;
		
		
	}

	public void Delete(int id)

	{
	
		String query="";
		 AlertDialog.Builder x =new AlertDialog.Builder(this);
		   x.setTitle("Delete");
		   LinearLayout ll=new LinearLayout(this);
		   ll.setOrientation(LinearLayout.VERTICAL);
		   TextView tv=new TextView(this);
		   tv.setText("Do yoy really want to Delete "+s[pos]+"?");
		   ll.addView(tv);
	       x.setView(ll);
	      
	       x.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
			db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
			Log.i("My position is", pos+"");
			String txt="";
			txt=s[pos];
			Log.i("My name is :", txt);
			String query="delete from "+name+" where shortform='"+txt+"'";
			db.execSQL(query);
			Toast.makeText(Display.this,txt+" deleted",Toast.LENGTH_SHORT).show();
			db.close();
			finish();
			startActivity(getIntent());
			}
			
			
		});
	       
	       x.setNegativeButton("Cancel",new DialogInterface.OnClickListener() 
	       {
	   		
	   		@Override
	   		public void onClick(DialogInterface dialog, int which) {
	   			return;
	   			
	   		}
	   	});
	       
		x.show();
	}
	
	
	public void Edit(int id)
	{
		
		final String sf=s[pos];
		//*-********************************
		
		AlertDialog.Builder x =new AlertDialog.Builder(this);
		   x.setTitle("Edit");
		   LinearLayout ll=new LinearLayout(this);
		   ll.setOrientation(LinearLayout.VERTICAL);
		   TextView tv=new TextView(this);
		   tv.setText("Short-Form");
	       ll.addView(tv);
	       TextView tv1=new TextView(this);
		   tv1.setText(sf);
	       final TextView tv2=new TextView(this);
	       tv2.setText("Enter new Full-Form");
	       tv2.setId(1);
	      ll.addView(tv1);
	       final EditText et1=new EditText(this);
	       et1.setId(1);
	       ll.addView(et1);
	      x.setView(ll);
	      
	      x.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{	
				String ff="";
				ff=et1.getText().toString().trim();
				if(ff.equals(""))
				{
					Toast.makeText(Display.this,"Field Cannot be Blank",Toast.LENGTH_SHORT).show();
				}
				else
				{
				db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
				String query="Update "+name+ " set fullform= '"+ff+"' where shortform= '"+sf+"'";
				db.execSQL(query);
				Toast.makeText(Display.this, sf+" changed from "+f[pos]+ " to "+ ff,Toast.LENGTH_LONG).show();
				db.close();
				finish();
				startActivity(getIntent());
				}
			}
		});
	      
	      x.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
				
			}
		});
	       x.show();
	 	
	}


	public void Add(View v) 
	{
		// Forming DialogBox for Addition
		 AlertDialog.Builder x =new AlertDialog.Builder(this);
		   x.setTitle("Add");
		   LinearLayout ll=new LinearLayout(this);
		   ll.setOrientation(LinearLayout.VERTICAL);
		   TextView tv=new TextView(this);
		   tv.setText("Enter Short-Form");
	       ll.addView(tv);
	       final EditText et=new EditText(this);
	       ll.addView(et);
	       et.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
	       final TextView tv1=new TextView(this);
	       tv1.setText("Enter Full-Form");
	      ll.addView(tv1);
	       final EditText et1=new EditText(this);
	       ll.addView(et1);
	       x.setView(ll); //Setting Linear Layout to the Dialog Box
	     
			
		       
	      x.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				
				db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
				sf=et.getText().toString().toUpperCase().trim();
				ff=et1.getText().toString().trim();
				if(sf.equals("") || ff.equals(""))
			       {
			    	   Toast.makeText(Display.this, "Please enter valid short form and fullforrm",Toast.LENGTH_SHORT).show();
			       //return;
			       }
				else
			       {
				Log.i("Short", sf);
				Log.i("Full Form", ff);
				String query="Insert into "+name+" values('"+sf+"','"+ff+"')";
				db.execSQL(query);
				Toast.makeText(Display.this, sf+ " Added",Toast.LENGTH_LONG).show();
				db.close();
				finish();
				startActivity(getIntent());
			       }
			}
		});
		       
	      x.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
				
			}
		});
	       x.show();

		
		
	}
	
	

}

