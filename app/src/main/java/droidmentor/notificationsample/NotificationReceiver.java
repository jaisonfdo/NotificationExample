package droidmentor.notificationsample;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.util.Log;

/**
 * Created by Jaison on 09/11/16.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int noti_id=intent.getIntExtra("notiID",0);

        if(action.equals(NotificationHelper.POSITIVE_CLICK)) {
            Log.d("Action Positive","Pressed");
            cancel(context,noti_id);
        } else if(action.equals(NotificationHelper.NEGATIVE_CLICK)) {
            Log.d("Action Negative","Pressed");
            cancel(context,noti_id);
        }
        else if(action.equals(NotificationHelper.REPLY_CLICK)) {
            Log.d("Action Reply","Pressed"+getMessageText(intent));
            cancel(context,noti_id);
        }
    }

    public static void cancel(Context context, int id){
        NotificationManager notificacaoManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        notificacaoManager.cancel(id);
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(NotificationHelper.REPLY_TEXT_KEY);
        }
        return null;
    }
}
