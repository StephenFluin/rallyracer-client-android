package com.mortalpowers.games.rallyracerclient;

import java.io.DataOutputStream;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class RallyRacerClientGame extends Activity {
	CardChooser chooser;
	Network n;
	int playerId = 0;

	/**
	 * Lifecycle tutorial for myself
	 */
	// Only if you hit home.
	public void onRestart() {

		super.onRestart();
	}
	// Any way you launch, including out of memory
	public void onResume() {
		//debugMsg("Resuming");
		super.onResume();
	}
	// Home Button or back button
	public void onStop() {
		//debugMsg("Stop");
		super.onStop();
	}
	//  back button
	public void onDestroy() {
		debugMsg("destroy");
		Network.request("game-server.php?action=clientQuit");
		super.onDestroy();
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		chooser = new CardChooser(this);

		setContentView(chooser);
		
		Log.w("net", "testing logging");
		String n = Network.request("game-server.php?action=connectToGame");
		
		String[] parts = n.split(",");
		playerId = Integer.parseInt(parts[0]);
		int unit = Integer.parseInt(parts[1]);
		
		chooser.addCard(new CardChooser.Card("You are unit " + unit));

	}

	public void debugMsg(String message) {
		Toast.makeText(this, "Debug:" + message, Toast.LENGTH_LONG)
				.show();
	}

}