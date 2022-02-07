package com.pravin.gorestwithkotlin.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.pravin.gorestwithkotlin.R


object LocalNotification {
    private val CHANNEL_ID   = "FCM_CHANNEL"
    private val CHANNEL_NAME = "FCM_CHANNEL"

        fun show(context: Context, title:String?, body:String?, imgUrl: String?, pendingIntent: PendingIntent?){
        val notificationManager = NotificationManagerCompat.from(context)

        val attributeSet: AudioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
        val soundUri: Uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://" + context.packageName + "/" + R.raw.britania)

        val notificationBuilder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_api)
                .setContentTitle(title)
                .setContentText(body)
                .setColor(ContextCompat.getColor(context, R.color.theme_green))
                .setContentIntent(pendingIntent)
                .setSound(soundUri)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel: NotificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(soundUri,attributeSet)
            notificationManager.createNotificationChannel(channel)
        }

            imgUrl?.let {
                val bitmap: Bitmap? = getBitmapfromUrl(context,imgUrl.toString())
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                    .setLargeIcon(bitmap)
            }

        notificationManager.notify(1,notificationBuilder.build())
    }


    private fun getBitmapfromUrl(context: Context,imageUrl: String): Bitmap? {
        return try {
           Glide.with(context).asBitmap().load(imageUrl).submit().get()
        } catch (e: Exception) {
            Log.e("**", "Error in getting notification image: " + e.localizedMessage)
            null
        }
    }

}
