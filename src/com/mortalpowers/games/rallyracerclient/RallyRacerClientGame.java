package com.mortalpowers.games.rallyracerclient;

import java.io.DataOutputStream;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class RallyRacerClientGame extends Activity {
	private static final int MENU_SETTINGS = 0;
	private static final int MENU_QUIT = 1;
	CardChooser chooser;
	Network n;
	int playerId = 0;

	/**
	 * Lifecycle tutorial for myself
	 */
	// Only if you hit home.
	public void onRestart() {
		debugMsg("Restart");
		super.onRestart();
	}

	// Any way you launch, including out of memory
	public void onResume() {
		debugMsg("Resuming");
		super.onResume();
	}

	// Home Button or back button
	public void onStop() {
		debugMsg("Stop");
		super.onStop();
	}

	// back button
	public void onDestroy() {
		debugMsg("destroy");
		Network.request("game-server.php?action=clientQuit");
		Network.reset();
		super.onDestroy();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Network.serverPrefix = getUrlPrefix();
		
		chooser = new CardChooser(this);

		setContentView(chooser);

		initializeGameConnection();

	}

	public void initializeGameConnection() {
		String n = Network.request("game-server.php?action=connectToGame");

		String[] parts = n.split(",");
		try{
			playerId = Integer.parseInt(parts[0]);
			int unit = Integer.parseInt(parts[1]);

			chooser.addCard(new CardChooser.Card("You are unit " + unit));
		} catch(NumberFormatException e) {
			debugMsg("Invalid response received:" + n);
		}
		
	}
	
	public void debugMsg(String message) {
		Toast.makeText(this, "Debug:" + message, Toast.LENGTH_LONG).show();
	}

	/* Creates the menu items */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SETTINGS, 0, "Settings");
		menu.add(0, MENU_QUIT, 0, "Quit");
		return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SETTINGS:
			startActivity(new Intent(this, Settings.class));
			return true;
		case MENU_QUIT:
			finish();
			return true;
		}
		return false;
	}

	public String getUrlPrefix() {
		SharedPreferences settings = getSharedPreferences("settings", MODE_WORLD_READABLE);
		return settings.getString("serverName",
				"http://mortalpowers.com/rallyracerdefault/");

	}
}