package com.pandu.wisata.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.gcm.GcmListenerService;
import com.pandu.wisata.R;

public class GcmMessageHandler extends GcmListenerService {

	private static final String TAG = "TESTGCM";

	private static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
    	Log.d(TAG, "[GcmMessageHandler] onMessageReceived");

    	String title = data.getString("title");
        String message = data.getString("message");
        String url = data.getString("url");

        sendNotification(title, message, url);
    }

    // Creates notification based on title and body received
    /*
    private void createNotification(String title, String body) {
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                //.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }
    */

	private void sendNotification(String title, String message, String url) {

		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		NotificationManager mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

		/*
		Intent intent = new Intent(this, LandingPageActivity.class);
		intent.putExtra("KEY_NOTIFICATION_URL", url);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
		*/

		Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

		// Notification Style
		NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
		bigTextStyle.setBigContentTitle(title);
		bigTextStyle.bigText(message);               

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
			//.setStyle(bigTextStyle)
			.setWhen(System.currentTimeMillis())
			.setSmallIcon(R.drawable.ic_launcher)
			//.setLargeIcon(largeIcon)
			.setTicker(message)
	        .setContentTitle(title)
	        .setContentText(message)
			.setSound(soundUri)
			.setAutoCancel(true)
			.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR)
			.setLights(0xffff0000, 300, 5000)
			.setPriority(Notification.PRIORITY_MAX);
			//.setContentIntent(contentIntent);

        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, notificationBuilder.build());
	  }

}
