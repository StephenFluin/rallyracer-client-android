package com.mortalpowers.games.rallyracerclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class RallyRacerClientGame extends Activity {
	CardChooser chooser;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		chooser = new CardChooser(this);

		chooser.cards.add(new CardChooser.Message("No cards yet."));

		chooser.cleanUp();
		setContentView(chooser);
		// setContentView(R.layout.main);

		new Network().start();

	}

	public class Network extends Thread {
		public void run() {
			Socket s = null;
			DataOutputStream out;
			BufferedReader in;

			try {
				s = new Socket("lyra.mortalpowers.com", 6000);
				out = new DataOutputStream(s.getOutputStream());
				in = new BufferedReader(new InputStreamReader(s
						.getInputStream()));
				Log.d("network", "network started");
				out.writeBytes("GET /\n");
				String str = "Awaiting response.\n";
				while (str != null) {
					// .setText("Updating to string: " + str);
					str = in.readLine();
					Log.d("network", "Received " + str);
					if (str.startsWith("NewCard:")) {
						chooser.cards.clear();
						String[] command = str.split(":");
						String[] cards = command[1].split(";");
						for (String card : cards) {
							if (card.length() > 5) {
								chooser.cards.add(new CardChooser.Message(
										"card"));
							}
						}

					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// tv.setText(e.toString());
			}
		}
	}
}