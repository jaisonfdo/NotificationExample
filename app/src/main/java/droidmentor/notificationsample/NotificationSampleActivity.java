package droidmentor.notificationsample;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import static droidmentor.notificationsample.NotificationHelper.GROUP_KEY;
import static droidmentor.notificationsample.NotificationHelper.NEGATIVE_CLICK;
import static droidmentor.notificationsample.NotificationHelper.POSITIVE_CLICK;
import static droidmentor.notificationsample.NotificationHelper.REPLY_CLICK;
import static droidmentor.notificationsample.NotificationHelper.REPLY_TEXT_KEY;

public class NotificationSampleActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_simple,btn_action,btn_remote,btn_summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_sample);

        btn_simple=(Button)findViewById(R.id.btn_simple);
        btn_action=(Button)findViewById(R.id.btn_action);
        btn_remote=(Button)findViewById(R.id.btn_remote);
        btn_summary=(Button)findViewById(R.id.btn_summary);

        btn_simple.setOnClickListener(this);
        btn_action.setOnClickListener(this);
        btn_remote.setOnClickListener(this);
        btn_summary.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_simple:
                showStandardNotification();
                break;
            case R.id.btn_action:
                showHeadsUpNotification();
                break;
            case R.id.btn_remote:
                showRemoteInputNotification();
                break;
            case R.id.btn_summary:
                showSummaryNotification();
                break;
        }
    }

    public void showStandardNotification() {

        int notificationId = new Random().nextInt();

        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notification = NotificationHelper.createNotificationBuider(this,
                "Notification", "Standard notification!", R.drawable.ic_notifications, pIntent);

        NotificationHelper.showNotification(this, notificationId, notification.build());
    }

    public void showHeadsUpNotification() {
        int notificationId = new Random().nextInt();

        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent positive = new Intent(this, NotificationReceiver.class);
        positive.putExtra("notiID", notificationId);
        positive.setAction(POSITIVE_CLICK);

        PendingIntent pIntent_positive = PendingIntent.getBroadcast(this, notificationId, positive, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent negative = new Intent(this, NotificationReceiver.class);
        negative.putExtra("notiID", notificationId);
        negative.setAction(NEGATIVE_CLICK);

        PendingIntent pIntent_negative = PendingIntent.getBroadcast(this, notificationId, negative, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder notification = NotificationHelper.createNotificationBuider(this,
                "Notification", "Heads-Up Notification!", R.drawable.ic_notifications, pIntent);

        notification.setPriority(Notification.PRIORITY_HIGH).setVibrate(new long[0]);

        notification.addAction(new NotificationCompat.Action(R.drawable.ic_notifications, "Postive", pIntent_positive));
        notification.addAction(new NotificationCompat.Action(R.drawable.ic_notifications, "Negative", pIntent_negative));

        NotificationHelper.showNotification(this, notificationId, notification.build());
    }

    public void showRemoteInputNotification() {

        int notificationId = new Random().nextInt();

        NotificationCompat.Builder notification = NotificationHelper.createNotificationBuider(this,
                "Remote Input", "Notification with Remote Input", R.drawable.ic_notifications);

        notification.setGroupSummary(true);
        notification.setGroup("KEY_NOTIFICATION_GROUP1");

        Intent remote_intent = new Intent(this, NotificationReceiver.class);
        remote_intent.putExtra("notiID", notificationId);
        remote_intent.setAction(REPLY_CLICK);

        PendingIntent pIntent_positive = PendingIntent.getBroadcast(this, notificationId, remote_intent, PendingIntent.FLAG_CANCEL_CURRENT);


        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_TEXT_KEY)
                .setLabel("Type Something")
                .build();

        // Create the reply action and add the remote input.

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_send, "Reply", pIntent_positive)
                        .addRemoteInput(remoteInput)
                        .build();

        notification.addAction(action);
        notification.setColor(ContextCompat.getColor(this, R.color.colorAccent));

        NotificationHelper.showNotification(this, notificationId, notification.build());
    }


    public void showSummaryNotification() {

        int notificationId = new Random().nextInt();

        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.icon_email);

        NotificationCompat.Builder summary_notification = NotificationHelper.createNotificationBuider(this,
                "2 new messages", "Bundle notification!", R.drawable.ic_notifications, pIntent);

        summary_notification.setStyle(new NotificationCompat.InboxStyle()
                .addLine("Ashwin  Check this out")
                .addLine("Ranjith  Launch Party")
                .setBigContentTitle("2 new messages")
                .setSummaryText("2 new messages"))
                .setLargeIcon(largeIcon)
                .setGroupSummary(true)
                .setContentIntent(pIntent)
                .setGroup(GROUP_KEY).build();

        int notificationId1 = new Random().nextInt();

        NotificationCompat.Builder notification1 = NotificationHelper.createNotificationBuider(this,
                "Ashwin", "Check this out", R.drawable.ic_notifications, pIntent);

        int notificationId2 = new Random().nextInt();

        NotificationCompat.Builder notification2 = NotificationHelper.createNotificationBuider(this,
                "Ranjith", "Launch Party", R.drawable.ic_notifications, pIntent);

        NotificationHelper.showNotification(this, notificationId1, notification1.build());
        NotificationHelper.showNotification(this, notificationId2, notification2.build());

        NotificationHelper.showNotification(this, notificationId, summary_notification.build());
    }

}
