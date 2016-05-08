package com.pandu.wisata.gcm;

import java.io.IOException;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.pandu.wisata.api.Connector;
import com.pandu.wisata.api.DeviceInfo;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class GcmRegistrationIntentService extends IntentService {

	private static final String TAG = "PanduWisata";

	private static final String GCM_SENDER_ID = "685563417249";

	public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String GCM_TOKEN = "gcmToken";
    
    private SharedPreferences mSharedPreferences;

	public GcmRegistrationIntentService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
        	// Make a call to Instance API
            InstanceID instanceID = InstanceID.getInstance(this);

            // Request token that will be used by the server to send push notifications
            String token = instanceID.getToken(GCM_SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.d(TAG, "[GcmRegistrationIntentService] onHandleIntent GCM Registration Token: " + token);

            // Save token
            mSharedPreferences.edit().putString(GCM_TOKEN, token).apply();

            // Pass along this data
            sendRegistrationToServer(token);
        } catch (IOException e) {
            e.printStackTrace();

            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            mSharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        }

	}

	private void sendRegistrationToServer(final String token) {
		final Connector api = new Connector();
		final DeviceInfo device = new DeviceInfo(this);

		new AsyncTask<String, Void, Intent>() {
			String response = null;
			Bundle data = new Bundle();

			@Override
			protected Intent doInBackground(String... params) {
				try {
					response = api.shareGcmToken(token, device);
					data.putString("RESPONSE", response);
				} catch (Exception e) {
					data.putString("ERROR", e.getMessage());
				}

				final Intent resultIntent = new Intent();
				resultIntent.putExtras(data);

				return resultIntent;
			}
			
			@Override
			protected void onPostExecute(Intent intent) {
				if (intent.hasExtra("ERROR")) {
					mSharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
				} else {
					if (intent.hasExtra("RESPONSE")) {
						mSharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
					}
				}
			}

		}.execute();
    }

}
