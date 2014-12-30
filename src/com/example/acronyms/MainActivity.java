package com.example.acronyms;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	//String ff="";
	public String sf="",ff="",selection="";
	SQLiteDatabase db;
	Cursor c=null;
	String a[],b[];
	int pos,count=0,i=0;
	String s[],f[];
	ListView lv;
	Button btn1;
	ArrayAdapter <String> ad;
	String name="";
	View layout,layout1;
	ArrayAdapter<String> apat;
	Spinner sp;
	EditText et,et1;
	RadioGroup rg;
	RadioButton rb;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn1=(Button)findViewById(R.id.button2);
		Intent i=getIntent();
        a=i.getStringArrayExtra("Data");
	    listCall(); // shows data in list view
	    registerForContextMenu(lv); //Necessary for contextMenu i.e long click on list** 
	}
	
	public void listCall() 
	{	
	int i=0;
		while(a[i]!=null)
		i++;
		b=new String[i];
		for(int j=0;j<i;j++)
		b[j]=a[j];
		Arrays.sort(b);
		lv=(ListView)findViewById(R.id.listView1);
    	ad=new ArrayAdapter<String>(MainActivity.this,R.layout.text,R.id.textView2,b); //Setting Adapter for ListView
    	lv.setAdapter(ad);
    	Log.i("hey m done","bnjhbcsjb");
    	
    	//Setting listener on List
    	lv.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> parent, View arg1, int position,
					long id) 
			{
				// TODO Auto-generated method stub
				name=b[position];
				Log.i("Name i clicked is :",name);
				Intent in=new Intent(MainActivity.this,Display.class);
				in.putExtra("Name",name);
				startActivity(in);
			}
		});
    	lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long id) 
			{
				// TODO Auto-generated method stub
				pos=position;
				name=b[position];
				return false;
			}
		});
    	
		
	} //end of listcall

	
	@Override
	public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo menuinfo)
	{
		super.onCreateContextMenu(menu, view, menuinfo);
		menu.setHeaderTitle("Menu");
		menu.add(0, view.getId(),0,"Delete");
		
	}
	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		
		
		if(item.getTitle()=="Delete")
			Delete(item.getItemId());
		
	
		return false;
		
		
	}

	public void Add(View v) 
	{	
		LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
		 layout = inflater.inflate(R.layout.addwordsorcategory,null);
		 AlertDialog.Builder x=new AlertDialog.Builder(this);
		 x.setTitle("Option Menu");
		x.setView(layout);
		
		rg=(RadioGroup)layout.findViewById(R.id.radioGroup1);
		 x.setPositiveButton("Ok", new OnClickListener() 
		 {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				int id=rg.getCheckedRadioButtonId();
				rb=(RadioButton)layout.findViewById(id);
				String radioname=rb.getText().toString();
				
				if(radioname.equalsIgnoreCase("Word"))
					AddWord();
				else
					AddCategory();
				
			}
		});
		 
		 x.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				return;
			}
		});
		 x.show();
	}
	
	public void Delete(int id)

	{
	
		String query="";
		
		 AlertDialog.Builder x =new AlertDialog.Builder(this);
		   x.setTitle("Delete");
		   LinearLayout ll=new LinearLayout(this);
		   ll.setOrientation(LinearLayout.VERTICAL);
		   TextView tv=new TextView(this);
		   tv.setText("Do you really want to Delete "+name);
		   ll.addView(tv);
	       x.setView(ll);
	      
	       x.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
			db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
			db.execSQL("drop table '"+name+"'");
			db.execSQL("delete from category where names= '"+name+"'");
			Toast.makeText(getBaseContext(),""+name+" deleted",Toast.LENGTH_SHORT).show();
			Intent ins=new Intent(MainActivity.this,Load.class);
			startActivity(ins);
			finish();
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
	
	//exittt
	//exit
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			AlertDialog.Builder x =new AlertDialog.Builder(this);
			   x.setTitle("Exit");
			   LinearLayout ll=new LinearLayout(this);
			   ll.setOrientation(LinearLayout.VERTICAL);
			   TextView tv=new TextView(this);
			   tv.setText("Do you Really Want to Exit?");
			   tv.setId(0);
		       ll.addView(tv);
		       
		       x.setPositiveButton("Exit",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
					System.exit(0);
			
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
		return super.onKeyDown(keyCode, event);
	}

	
	//Just see this too..not Working ME Here
	 public boolean onCreateOptionsMenu(Menu menu) 
	 {
	      MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.activity_main, menu);
	      return true;
	    }
	
	 public boolean onOptionsItemSelected(MenuItem item) 
	 {
	      switch (item.getItemId()) 
	      {
	      case R.id.helps:
	         //Provide Intent to Help Activity Here!!!!!  
	    	  Intent in=new Intent(this,Help.class);
	    	  in.putExtra("new","hey");
	    	  startActivity(in);
	            return true;
	     
	      case R.id.abouts:
	    	  Intent inr=new Intent(this,about.class);
	    	  inr.putExtra("new1","hey");
	    	  startActivity(inr);
		            return true;
	    
	      default:
	            return super.onOptionsItemSelected(item);
	      }
	}

public void AddWord()
{
	db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
	c =db.rawQuery("Select * from category", null);
	a=new String[c.getCount()];
	i=0;
	while(c.moveToNext())
	a[i++]=c.getString(0);
	db.close();
	AlertDialog.Builder x =new AlertDialog.Builder(this);
	   x.setTitle("Add");
	  LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
    layout = inflater.inflate(R.layout.addnewword,null);
    et=(EditText)layout.findViewById(R.id.editText1);
	  et1=(EditText)layout.findViewById(R.id.editText2);
    sp=(Spinner)layout.findViewById(R.id.spinner1);
	  apat=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,a);
	  apat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	   sp.setAdapter(apat);
    x.setView(layout);
   sp.setOnItemSelectedListener(new OnItemSelectedListener() {

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long arg3) {
		// TODO Auto-generated method stub
		selection=parent.getItemAtPosition(pos).toString();	
		}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
});
    
    	x.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			
			db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
			sf=et.getText().toString().trim();
			ff=et1.getText().toString().trim();
			if(sf.equals("") || ff.equals(""))
		       {
		    	   Toast.makeText(getBaseContext(), "Please enter valid short form and fullforrm",Toast.LENGTH_SHORT).show();
		       //return;
		       }
			else
		       {
			
			Log.i("Short", sf);
			Log.i("Full Form", ff);
			Log.i("Category",selection);
			String query="Insert into "+selection+" values('"+sf+"','"+ff+"')";
			db.execSQL(query);
			Log.i("Query is", "exceuted");
			Toast.makeText(MainActivity.this, sf+" Added",Toast.LENGTH_SHORT).show();
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


public void AddCategory()
{
	 AlertDialog.Builder x =new AlertDialog.Builder(this);
	   x.setTitle("Add Category");
	   LinearLayout ll=new LinearLayout(this);
	   ll.setOrientation(LinearLayout.VERTICAL);
	   TextView tv=new TextView(this);
	   tv.setText("Enter Name of Category");
     ll.addView(tv);
     final EditText et=new EditText(this);
     ll.addView(et);
     
     x.setView(ll); //Setting Linear Layout to the Dialog Box
   
    x.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
		String catname1=et.getText().toString();
		if(catname1.equalsIgnoreCase(""))
		{
		Toast.makeText(MainActivity.this,"Name Field cannot be Empty",Toast.LENGTH_SHORT).show();	
		}
		else
		{
		 final String catname=catname1.substring(0,1).toUpperCase()+catname1.substring(1);
		db=openOrCreateDatabase("Acronym",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		db.execSQL("insert into category values('"+catname+"')");
		db.execSQL("Create table if not exists "+catname+" (shortform varchar,fullform varchar)");
		Toast.makeText(getBaseContext(),""+catname+" Added into category",Toast.LENGTH_SHORT).show();
		db.close();
		Intent ins=new Intent(MainActivity.this,Load.class);
		startActivity(ins);
		finish();
		}
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


}
	

