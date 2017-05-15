package me.li2.android.tutorial.BasicWidget;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import junit.framework.Assert;

import java.util.Calendar;

import me.li2.android.tutorial.R;

/**
 * Created by weiyi on 05/05/2017.
 * https://github.com/li2
 */

public class NotificationCustomized {
    public static class Builder {
        /**
         * The NotificationCompat.Builder that handles most of the work for this function.
         */
        private NotificationCompat.Builder mBuilder;
        /**
         * The text field that will be (potentially) too long for an ordinary notification. Used as the text field
         * in a default notification, or as the expanded text in a big-style notification.
         */
        /**
         * The context passed to the constructor; must be the same context any resources will use.
         */
        private Context mContext;

        private RemoteViews mContentNormalView;
        /**
         * The custom expanded view that used in the expanded form.
         */
        private RemoteViews mContentExpandedView;
        /**
         * The text field that will be (potentially) too long for an ordinary notification. Used as the text field
         * in a default notification, or as the expanded text in a big-style notification.
         */
        private CharSequence mContentText;

        public Builder(Context context) {
            Assert.assertNotNull(context);
            mBuilder = new NotificationCompat.Builder(context);
            mContext = context;
            mContentNormalView = new RemoteViews(context.getPackageName(), R.layout.view_notification);
            mContentExpandedView = new RemoteViews(context.getPackageName(), R.layout.view_notification_expanded);
            mContentText = "";

            // Set RemoteViews into Notification
            mBuilder.setCustomContentView(mContentNormalView);
            mBuilder.setCustomBigContentView(mContentExpandedView);

            // push the notification to the top of the list to expand big view by default.
            setPriority(Notification.PRIORITY_HIGH);
            setOngoing(true);
            setWhen(Calendar.getInstance().getTimeInMillis());
            setSmallIcon(R.drawable.ic_android);
            setAutoCancel(true);
        }

        /**
         * Builds and returns a new Notification, with potentially a custom layout depending
         * on Android version.
         *
         * @return
         *    A new Notification object.
         * @see android.support.v4.app.NotificationCompat.Builder#build()
         */
        public Notification build() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(mContentText));
            }
            return mBuilder.build();
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setContentIntent(PendingIntent)
         */
        public Builder setContentIntent(PendingIntent intent) {
            mBuilder.setContentIntent(intent);
            return this;
        }

        public Builder setIcon(int resourceId) {
            mContentNormalView.setImageViewResource(R.id.notification_image, resourceId);
            mContentExpandedView.setImageViewResource(R.id.notification_image, resourceId);
            return this;
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setContentTitle(CharSequence)
         */
        public Builder setContentTitle(CharSequence title) {
            mBuilder.setContentTitle(title);
            mContentNormalView.setTextViewText(R.id.notification_title, title);
            mContentExpandedView.setTextViewText(R.id.notification_title, title);
            return this;
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setContentText(CharSequence)
         */
        public Builder setContentText(CharSequence text) {
            mBuilder.setContentText(text);
            mContentText = text;
            mContentNormalView.setTextViewText(R.id.notification_text, text);
            mContentExpandedView.setTextViewText(R.id.notification_text, text);
            return this;
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setWhen(long)
         */
        public Builder setWhen(long when) {
            mBuilder.setWhen(when);
            mContentNormalView.setLong(R.id.notification_time, "setTime", when);
            mContentExpandedView.setLong(R.id.notification_time, "setTime", when);
            return this;
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setAutoCancel(boolean)
         */
        public Builder setAutoCancel(boolean autoCancel) {
            mBuilder.setAutoCancel(autoCancel);
            return this;
        }

        public Builder setOnlyAlertOnce(boolean onlyAlertOnce) {
            mBuilder.setOnlyAlertOnce(onlyAlertOnce);
            return this;
        }

        public Builder setPriority(int priority) {
            mBuilder.setPriority(priority);
            return this;
        }

        public Builder setOngoing(boolean ongoing) {
            mBuilder.setOngoing(ongoing);
            return this;
        }

        /**
         * @param resourceId Same as NotificationCompat.Builder. Resource must be in the same package
         *                   as the context passed to the Builder constructor.
         * @return this, for chaining.
         * @see android.support.v4.app.NotificationCompat.Builder#setSmallIcon(int)
         */
        public Builder setSmallIcon(int resourceId) {
            mBuilder.setSmallIcon(resourceId);
            mContentExpandedView.setImageViewResource(R.id.icon, resourceId);
            return this;
        }
    }
}
