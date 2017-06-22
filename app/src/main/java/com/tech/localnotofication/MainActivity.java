package com.tech.localnotofication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //link:==http://www.theappguruz.com/blog/easy-way-to-send-local-notification-to-user-in-android

    EditText etNotiTitile, etNotiMessage;
    Button btnSimpleNoti, btnExpandNoti, btnWithActionButton,
            btnMaxPriority, btnMinPriority, btnCombine;
    String notificationTitle;
    String notificationText;

    NotificationManager notificationManager;
    NotificationCompat.Builder notificationBuilder;

    int currentNotificationID = 0;
    Bitmap icon;
    int combinedNotificationCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAllWidgetRef();

    }

    public void getAllWidgetRef() {
        etNotiTitile = (EditText) findViewById(R.id.etNotiTitile);
        etNotiMessage = (EditText) findViewById(R.id.etNotiMessage);
        btnSimpleNoti = (Button) findViewById(R.id.btnSimpleNoti);
        btnExpandNoti = (Button) findViewById(R.id.btnExpandNoti);
        btnWithActionButton = (Button) findViewById(R.id.btnWithActionButton);
        btnMaxPriority = (Button) findViewById(R.id.btnMaxPriority);
        btnMinPriority = (Button) findViewById(R.id.btnMinPriority);
        btnCombine = (Button) findViewById(R.id.btnCombine);

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher);
        btnSimpleNoti.setOnClickListener(this);
        btnExpandNoti.setOnClickListener(this);
        btnWithActionButton.setOnClickListener(this);
        btnMaxPriority.setOnClickListener(this);
        btnMinPriority.setOnClickListener(this);
        btnCombine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        setNotificationData();
        switch (v.getId()) {
            case R.id.btnSimpleNoti:
                setDataForSimpleNotification();
                break;
            case R.id.btnExpandNoti:
                setDataForExpandLayoutNotification();
                break;
            case R.id.btnWithActionButton:
                setDataForNotificationWithActionButton();
                break;
            case R.id.btnMaxPriority:
                setDataForMaxPriorityNotification();
                break;
            case R.id.btnMinPriority:
                setDataForMinPriorityNotification();
                break;
            case R.id.btnCombine:
                setDataForCombinedNotification();
                break;
            default:
                break;
        }
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        currentNotificationID++;
        int notificationId = currentNotificationID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;
        notificationManager.notify(notificationId, notification);
    }

    private void setNotificationData() {
        notificationTitle = this.getString(R.string.app_name);
        notificationText = "Hello..This is a Notification Test";
        if (!etNotiMessage.getText().toString().equals("")) {
            notificationText = etNotiMessage.getText().toString();
        }
        if (!etNotiTitile.getText().toString().equals("")) {
            notificationTitle = etNotiTitile.getText().toString();
        }
    }

    public void setDataForSimpleNotification() {
        notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText);
        sendNotification();
    }

    private void setDataForExpandLayoutNotification() {
        notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setContentText(notificationText);
        sendNotification();
    }

    private void setDataForNotificationWithActionButton() {
        notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setContentText(notificationText);
        Intent answerIntent = new Intent(this, AnswerReceiveActivity.class);
        answerIntent.setAction("Yes");
        PendingIntent pendingIntentYes = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.thumbs_up, "Yes", pendingIntentYes);
        answerIntent.setAction("No");
        PendingIntent pendingIntentNo = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.thumbs_down, "No", pendingIntentNo);
        sendNotification();
    }

    private void setDataForMaxPriorityNotification() {
        notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText(notificationText);
        sendNotification();
    }

    private void setDataForMinPriorityNotification() {
        notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                .setPriority(Notification.PRIORITY_MIN)
                .setContentText(notificationText);
        sendNotification();
    }

    private void setDataForCombinedNotification() {
        combinedNotificationCounter++;
        notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle(notificationTitle)
                .setGroup("group_emails")
                .setGroupSummary(true)
                .setContentText(combinedNotificationCounter + " new messages");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(notificationTitle);
        inboxStyle.setSummaryText("mehulrughani@gmail.com");
        for (int i = 0; i < combinedNotificationCounter; i++) {
            inboxStyle.addLine("This is Test" + i);
        }
        currentNotificationID = 500;
        notificationBuilder.setStyle(inboxStyle);
        sendNotification();
    }
}
