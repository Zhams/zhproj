package com.pandu.wisata.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class Connector {

	private static final String TAG					= "PanduWisata";
	//public static final String API_HOST			= "192.168.2.100";
	//private static final String API_HOST			= "search.fira.id";
	//private static final String API_PATH			= "firatv/request.php";
	//private static final String API_URL				= "http://" + API_HOST + "/" + API_PATH;
	private static final String API_URL				= "http://panduwisata.comxa.com/api/sharegcm.php";

	public Connector() {		
	}

	public String shareGcmToken(String token, DeviceInfo device)
			throws Exception, InterruptedIOException, MalformedURLException {
		String result = null;

		URL url = null;
		HttpURLConnection connection = null;

		String paramData = "{\"device\":{\"manufacturer\":\""+ device.getManufacture() + 
					"\",\"model\":\"" + device.getHandset_version() + 
					"\",\"os\":\"" + device.getOs_version() + 
					"\",\"imei\":\"" + device.getImei1() + 
					"\"},\"app_version\":\"" + device.getApp_version() + 
					"\",\"gcm_token\":\""+ token +"\"}";
		Log.d(TAG, "[Connector] POST request\n\tURL: " + API_URL + "\n\tData: " + paramData);
		try {
			url = new URL(API_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

	        connection.setRequestProperty("Content-Type", "application/json; charset=ISO-8859-1");
			connection.setRequestProperty("Accept-Encoding", "deflate");
			connection.setRequestProperty("Accept", "application/json, text/plain, */*");
			connection.setRequestProperty("Accept-Charset", "utf-8");
			connection.setRequestProperty("User-Agent", "Pandu-Wisata");
			connection.setRequestProperty("Connection", "Keep-Alive");

			// For POST
			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			//Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(paramData);
			wr.flush ();
			wr.close ();
			//

			/*
			int responseCode = connection.getResponseCode();
			if (responseCode==200) {
				String cookie = connection.getHeaderField("Set-Cookie");
				if (cookie == null) {
					//Log.d(TAG, "[LiveTvGridFragment] Key 'Server' is not found!");
				} else {
					//Log.d(TAG, "[LiveTvGridFragment] Cookie - " + cookie);
					FanatikServerConfig.setCookie(context, cookie);
				}
			} else if (responseCode==403) {
				Exception e = new Exception("403 Forbidden");
	    		throw new Exception(e);
			}
			*/

			InputStream in = new BufferedInputStream(connection.getInputStream());
			result = extractResponse(in);
		} catch (Exception e) {
			//data.putString("KEY_ERROR_MESSAGE", e.getMessage());
			//Log.d(TAG, "[FiraApi] getSchedule ERROR\n"+e.toString());
    		throw new Exception(e);
		} finally {
			//Log.d(TAG, "[FiraApi] getSchedule DISCONNECT");
			connection.disconnect();
		}

		Log.d(TAG, "[Connector] shareGcmToken serverResponse:\n" + result);

		return result;
	}

	/////
    private static String extractResponse(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        String result="";
        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) { }           
            }
        }
        return result;
    }

}
