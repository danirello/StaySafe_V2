package com.example.pruebafirebase;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.app.Notification;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class NotificationService extends FirebaseMessagingService {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference refDatos;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("token", s);
        //FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        //firestore.collection("DeviceTokens").document().set(tokenData);
        inicializarFirebase();
        databaseReference.child("tokens").child(s).setValue(tokenData);
    }



    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        refDatos =  firebaseDatabase.getReference("tokens");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        final String CHANNEL_ID = "HEADS_UP_NOTIFICATIONS";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "MyNotification",
                NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.lock)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1, notification.build());

        super.onMessageReceived(remoteMessage);
    }






/*
    private String CHANNEL_ID = "myChannel";


    public void onNewToken(String newToken) {
        super.onNewToken(newToken);
        HashMap<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("token", newToken);
        FirebaseDatabase.getInstance().getReference("device_token").child(newToken).setValue(newToken);
    }

    @SuppressLint({"UnspecifiedImmutableFlag"})
    public void onMessageReceived(RemoteMessage message) {

        super.onMessageReceived(message);
        Notification var10000 = message.getNotification();
        String title = var10000 != null ? var10000.getTitle() : null;
        var10000 = message.getNotification();
        String body = var10000 != null ? var10000.getBody() : null;
        Object var7 = this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (var7 == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.app.NotificationManager");
        } else {
            NotificationManager notificationManager = (NotificationManager)var7;
            int notificationID = (new Random()).nextInt();
            if (VERSION.SDK_INT >= 26) {
                this.createNotificationChannel(notificationManager);
            }

            android.app.Notification var8 = (new Builder((Context)this, "myChannel")).setContentTitle((CharSequence)title).setContentText((CharSequence)body).setSmallIcon(700007).setAutoCancel(true).build();
            android.app.Notification notification = var8;
            notificationManager.notify(notificationID, notification);
        }
    }

    @RequiresApi(26)
    private final void createNotificationChannel(NotificationManager notificationManager) {
        String channelName = "channelName";
        NotificationChannel channel = new NotificationChannel("myChannel", (CharSequence)channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("My channel description");
        channel.enableLights(true);
        channel.setLightColor(-16711936);
        notificationManager.createNotificationChannel(channel);
    }


 */
}