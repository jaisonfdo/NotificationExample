package droidmentor.notificationsample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

/**
 * Created by Jaison on 09/11/16.
 */

public class NotificationHelper {

    public static String POSITIVE_CLICK = "POSITIVE_CLICK";
    public static String NEGATIVE_CLICK = "NEGATIVE_CLICK";
    public static String REPLY_CLICK = "REPLY_CLICK";
    public static String REPLY_TEXT_KEY = "REPLY_TEXT_KEY";
    public static String GROUP_KEY = "GROUP_KEY";


    public static NotificationCompat.Builder createNotificationBuider(Context context, String title,
                                                                      String message, int smallIcon) {
        return createNotificationBuider(context, title, message, smallIcon, null, 0, true, null);
    }

    public static NotificationCompat.Builder createNotificationBuider(Context context, String title,
                                                                      String message, int smallIcon, PendingIntent contentIntent) {
        return createNotificationBuider(context, title, message, smallIcon, null, 0, true, contentIntent);
    }

    public static NotificationCompat.Builder createNotificationBuider(Context context, String title, String message,
                                                                      int smallIcon, Bitmap largeIcon, int colorID,
                                                                      boolean isAutoCancel, PendingIntent contentIntent) {
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setAutoCancel(isAutoCancel);
            builder.setGroup(GROUP_KEY);

            if (!TextUtils.isEmpty(title))
                builder.setContentTitle(title);
            if (!TextUtils.isEmpty(message))
                builder.setContentText(message);
            if (smallIcon != 0)
                builder.setSmallIcon(smallIcon);
            if (largeIcon != null)
                builder.setLargeIcon(largeIcon);
            if (colorID != 0)
                builder.setColor(ContextCompat.getColor(context, colorID));
            if (contentIntent != null)
                builder.setContentIntent(contentIntent);

            return builder;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void showNotification(Context context, int notiID, Notification notification) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notiID, notification);
    }
}
