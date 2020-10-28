package com.itbeebd.medicare.service;

import android.content.Context;

import com.google.firebase.messaging.FirebaseMessaging;

public class TopicManager {
    private Context context;

    public TopicManager(Context context) {
        this.context = context;
    }

    public void subscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnCompleteListener(task -> {
            /*
            String msg = "subscribe Successful";
            if (!task.isSuccessful()) {
                msg = "subscribe not successful";
            }
          //  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

             */
        });
    }

    public void unsubscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnCompleteListener(task -> {
            /*
            String msg = "unsubscribe Successful";
            if (!task.isSuccessful()) {
                msg = "unsubscribe not successful";
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

             */
        });
    }
}