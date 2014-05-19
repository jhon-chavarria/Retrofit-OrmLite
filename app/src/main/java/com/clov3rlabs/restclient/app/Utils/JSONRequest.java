package com.clov3rlabs.restclient.app.Utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONRequest implements Request{



    public String requestContent(String path) {

		StringBuilder jsonString=new StringBuilder();
		try {
		HttpClient client=new DefaultHttpClient();
		HttpGet httpGet=new HttpGet(path);
		HttpResponse response;

		response = client.execute(httpGet);
		
		HttpEntity entity=response.getEntity();
		if(entity!=null){
			
			InputStreamReader reader=new InputStreamReader(entity.getContent());
			
			// Wrap a BufferedReader around the InputStream
		    BufferedReader rd = new BufferedReader(reader);
		    String line=null;
		    // Read response until the end
		    while ((line = rd.readLine()) != null) { 
		    	jsonString.append(line); 
		    }
			
			Log.d("BCN",jsonString.toString());
		}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonString.toString();
	}

}
