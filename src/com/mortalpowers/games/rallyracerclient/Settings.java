package com.mortalpowers.games.rallyracerclient;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends Activity {
	static final String[] SERVERS = new String[] {"mortalpowers.com/rallyracer/", "zeno/rallyracer/" };
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings);
	    
	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_server);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, SERVERS);
	    textView.setAdapter(adapter);
	    
	    Button save = (Button)findViewById(R.id.save_settings);
	    save.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	    		SharedPreferences settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
	    		SharedPreferences.Editor editor = settings.edit();
	    		editor.putString("serverName","http://" + ((EditText)findViewById(R.id.autocomplete_server)).getText());

	    		editor.commit();
	    		Toast.makeText(Settings.this,"Saved",Toast.LENGTH_SHORT).show();
	    		finish();
	    	}
	    });
	    
	    Button cancel = (Button)findViewById(R.id.cancel_settings);
	    cancel.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
					finish();
	    	}
	    });
	    
	}
}
