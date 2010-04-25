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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		chooser = new CardChooser(this);

		chooser.addCard(new CardChooser.Card("No cards yet..."));

		
		
		
		chooser.cleanUp();
		setContentView(chooser);
		// setContentView(R.layout.main);
		Log.w("net", "testing logging");
		String n = Network.request("game-server.php?action=startGame");

		try {
			JSONArray cards = new JSONArray(new JSONTokener(n));
			chooser.clearCards();
			for(int i = 0;i<cards.length();i++) {
				JSONObject card = cards.getJSONObject(i);
				String msg = card.getString("priority") + "," + card.getString("action") + "," + card.getString("quantity");
				chooser.addCard(new CardChooser.Card(msg));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			debugMsg("Invalid JSON received from server.");
		}
		chooser.cleanUp();

	}

	public void debugMsg(String message) {
		Toast.makeText(this, "Debug:" + message, Toast.LENGTH_LONG)
				.show();
	}

}