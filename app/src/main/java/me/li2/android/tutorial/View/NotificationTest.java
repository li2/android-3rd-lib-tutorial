package me.li2.android.tutorial.View;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;

import me.li2.android.tutorial.BasicUI.SimpleListActivity;
import me.li2.android.tutorial.BasicWidget.NotificationCustomized;
import me.li2.android.tutorial.R;

/**
 * Created by weiyi on 06/05/2017.
 * https://github.com/li2
 */

public class NotificationTest extends SimpleListActivity {
    private NotificationManager mNotificationManager;
    private NotificationCustomized.Builder mNotificationBuilder;
    private static final int CUSTOMIZED_NOTIFICATION_ID = 21;

    @Override
    protected String getTitlePrefix() {
        return ViewTutorial.TAG;
    }

    @Override
    protected ArrayList<String> initListData() {
        // Create Notification Manager
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        ArrayList<String> list = new ArrayList<>();
        list.add("Notification");
        list.add("Customized Notification");
        list.add("Update Notification");
        return list;
    }

    @Override
    protected void onSimpleListItemClick(int position) {
        switch (position) {
            case 0:
                notification();
                break;

            case 1:
                customNotification();
                break;

            case 2:
                if (mNotificationBuilder != null) {
                    updateNotification();
                }
                break;
        }
    }

    public void notification() {
        // Open Activity on Notification Click
        Intent intent = new Intent(this, ViewTutorial.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.mipmap.ic_launcher)
                // Set Ticker Message
                .setTicker(getString(R.string.notification_ticker))
                // Set Title
                .setContentTitle(getString(R.string.notification_title))
                // Set Text
                .setContentText(getString(R.string.notification_customized_text))
                // Set Big Text
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.notification_customized_big_text)))
                // Add an Action Button below Notification
                .addAction(R.drawable.ic_android, getString(R.string.notification_action_button), pIntent)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Build Notification with Notification Manager
        mNotificationManager.notify(0, builder.build());
    }

    private void customNotification() {
        Intent intent = new Intent(this, ViewTutorial.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mNotificationBuilder = new NotificationCustomized.Builder(this)
                .setIcon(R.drawable.ic_android)
                .setContentTitle(getString(R.string.notification_customized_title))
                .setContentText(getString(R.string.notification_customized_text))
                .setContentIntent(pIntent)
                ;
        // Sets an ID for the notification, so it can be updated
        mNotificationManager.notify(CUSTOMIZED_NOTIFICATION_ID, mNotificationBuilder.build());
    }

    // Use the SAME Builder and ID for each update.
    private void updateNotification() {
        mNotificationBuilder
                .setContentText(getString(R.string.notification_customized_big_text));
        // Because the ID remains unchanged, the existing notification is updated.
        mNotificationManager.notify(CUSTOMIZED_NOTIFICATION_ID, mNotificationBuilder.build());
    }
}
