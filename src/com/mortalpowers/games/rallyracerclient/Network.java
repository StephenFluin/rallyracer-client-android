/**
 * 
 */
package com.mortalpowers.games.rallyracerclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Network {

	public static String serverPrefix = "http://mortalpowers.com/rallyracer/";
	public static String request(String url) {
		url = serverPrefix + url;
		
		
		Log.d("net", "Starting network class.");
		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url);
		Log.w("net", "query crafted.");
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					entity.getContent()));
			String line;
			StringBuilder results = new StringBuilder();

			while ((line = r.readLine()) != null) {
				results.append(line);

			}

			return results.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}