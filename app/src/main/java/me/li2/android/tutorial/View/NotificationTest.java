package me.li2.android.tutorial.View;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.ArrayList;

import me.li2.android.tutorial.BasicUI.SimpleListActivity;
import me.li2.android.tutorial.R;

/**
 * Created by weiyi on 06/05/2017.
 * https://github.com/li2
 */

public class NotificationTest extends SimpleListActivity {
    @Override
    protected ArrayList<String> initListData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Notification");
        list.add("Customized Notification");
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
                .setContentText(getString(R.string.notification_text))
                // Add an Action Button below Notification
                .addAction(R.drawable.ic_android, getString(R.string.notification_action_button), pIntent)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());
    }

    public void customNotification() {
        // Using RemoteViews to bind custom layouts into Notification

        // normal content view
        RemoteViews normalView = new RemoteViews(getPackageName(),
                R.layout.view_notification);
        normalView.setImageViewResource(R.id.notification_image, R.drawable.ic_android);
        normalView.setTextViewText(R.id.notification_title, getString(R.string.notification_customized_title));
        normalView.setTextViewText(R.id.notification_text, getString(R.string.notification_customized_text));

        // big content view
        RemoteViews bigView = new RemoteViews(getPackageName(),
                R.layout.view_notification_expanded);
        bigView.setImageViewResource(R.id.notificationExpanded_image, R.drawable.ic_android);
        bigView.setTextViewText(R.id.notificationExpanded_title, getString(R.string.notification_customized_title));
        bigView.setTextViewText(R.id.notificationExpanded_text, getString(R.string.notification_customized_text));


        Intent intent = new Intent(this, ViewTutorial.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(getString(R.string.notification_customized_ticker))
                // no need to set contentTitle & contentText & actionButton
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                // Set RemoteViews into Notification
                .setCustomContentView(normalView)
                .setCustomBigContentView(bigView)
                // push the notification to the top of the list to expand big view by default.
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(true)
                ;

        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationmanager.notify(0, builder.build());
    }
}
