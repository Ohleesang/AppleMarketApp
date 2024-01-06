package com.example.applemarketapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class Notification(private val context: Context) {
    private val myNotificationID = 1
    private val channelID = "default"


    //채널 생성
    //안드로이드 8.0 이상인 경우 채널을 생성해야함
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
            val channel = NotificationChannel(
                channelID, "default channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "description text of this channel."
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    //알림 생성
    fun showNotification() {
        //전달되어질 값들 지정
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("애플 마켓!")
            .setContentText("벨 버튼을 누르셨군요!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        //퍼미션 권한 확인
        if (ActivityCompat.checkSelfPermission(
                context,
                "android.permission.POST_NOTIFICATIONS"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 만약,권환이 없을경우 암시적 인텐트 발생
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
            context.startActivity(intent)
            return
        }
        // 지정된 빌드에 해당채널에 알림발생
        NotificationManagerCompat.from(context).notify(myNotificationID, builder.build())
    }
}