package com.pravin.gorestwithkotlin.service

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pravin.gorestwithkotlin.view.GoRestActivity

class FcmCloudMessagingService:FirebaseMessagingService(){
    private val TAG = "**" + this.javaClass.simpleName
    companion object{
        private val REQUEST_CODE = 123
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)
        val intent = Intent(this, GoRestActivity::class.java)
        val fcmToActivityIntent: PendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

        Log.e(TAG, "onMessageReceived: " )

        if (msg.notification!=null){
            val title  = msg.notification?.title
            val body   = msg.notification?.body
            val imgUrl = msg.notification?.imageUrl

            LocalNotification.show(this,title,body, imgUrl.toString(), fcmToActivityIntent)
        }

    }



    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("**FCM TOKEN", "onNewFcmToken: "+token  )
        //UpdateToken
    }

}