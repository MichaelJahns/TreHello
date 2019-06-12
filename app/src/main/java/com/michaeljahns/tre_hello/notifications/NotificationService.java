package com.michaeljahns.tre_hello.notifications;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    public static final String TAG = "NOTICE";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, remoteMessage.getFrom());
        Log.d(TAG, remoteMessage.getNotification().getTitle());
        Log.d(TAG, remoteMessage.getNotification().getBody());
    }
}

