package me.li2.android.tutorial.BasicWidget;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
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
        /**
         * The custom view that we will put inside the notification if we can't get a big-style notification.
         */
        private RemoteViews mCustomContentView;
        /**
         * The text field that will be (potentially) too long for an ordinary notification. Used as the text field
         * in a default notification, or as the expanded text in a big-style notification.
         */
        private CharSequence mExpandedText;

        public Builder(Context context) {
            Assert.assertNotNull(context);
            mBuilder = new NotificationCompat.Builder(context);
            mContext = context;
            mCustomContentView = new RemoteViews(context.getPackageName(), R.layout.view_notification_expanded);
            mExpandedText = "";
            setWhen(Calendar.getInstance().getTimeInMillis());
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
            mBuilder.setContent(mCustomContentView);
            return mBuilder.build();
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setContentIntent(PendingIntent)
         */
        public Builder setContentIntent(PendingIntent intent) {
            mBuilder.setContentIntent(intent);
            return this;
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setContentTitle(CharSequence)
         */
        public Builder setContentTitle(CharSequence title) {
            mBuilder.setContentTitle(title);
            mCustomContentView.setTextViewText(R.id.title, title);
            return this;
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setContentText(CharSequence)
         */
        public Builder setContentText(CharSequence text) {
            mBuilder.setContentText(text);
            mExpandedText = text;
            mCustomContentView.setTextViewText(R.id.text, text);
            return this;
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setWhen(long)
         */
        public Builder setWhen(long when) {
            mBuilder.setWhen(when);
            mCustomContentView.setLong(R.id.time, "setTime", when);
            return this;
        }

        /**
         * @see android.support.v4.app.NotificationCompat.Builder#setAutoCancel(boolean)
         */
        public Builder setAutoCancel(boolean autoCancel) {
            mBuilder.setAutoCancel(autoCancel);
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
            mCustomContentView.setImageViewResource(R.id.icon, resourceId);
            return this;
        }
    }
}
