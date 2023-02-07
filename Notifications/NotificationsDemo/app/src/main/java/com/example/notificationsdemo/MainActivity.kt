package com.example.notificationsdemo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationsdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var notificationManager: NotificationManagerCompat
    private var channelId = "krishna_entertainment_channel"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationManager = NotificationManagerCompat.from(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        simpleNotification()
        snackBarNotification()
        bigPictureNotification()
        inboxStyleNotification()
        actionTextStyleNotification()
        createNotificationChannel()
    }

    private fun simpleNotification(){
        binding.btnSimpleNotification.setOnClickListener {  }
        var notificationBuilder = NotificationCompat.Builder(this,channelId)
        notificationBuilder.setContentTitle("Simple Notification")
        notificationBuilder.setContentText("Bitcode Technologies - Android Batch Starts")

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
        notificationBuilder.setLights(Color.RED,200,200)
        notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_SECRET)         //you can check visibility = private, secret, public
        notificationBuilder.setOngoing(true)
        notificationBuilder.setAutoCancel(true)

        var largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        notificationBuilder.setLargeIcon(largeIcon)


        var actionIntent = Intent(this,SecondActivity::class.java)
        var actionPendingIntent = PendingIntent.getActivity(
            this,
            1,
            actionIntent,
            PendingIntent.FLAG_MUTABLE              //you can check changing FLAGS
        )

        notificationBuilder.setContentIntent(actionPendingIntent)
        var notification = notificationBuilder.build()
        notificationManager.notify(123,notification)

    }

    fun inboxStyleNotification(){
        binding.btnInboxStyleNotification.setOnClickListener {
            var builder = NotificationCompat.Builder(this,channelId)
            builder.setContentTitle("Bitcode Technologies")
            builder.setContentText("Admissions open")
            builder.setSmallIcon(R.mipmap.ic_launcher)

            var inboxStyle = NotificationCompat.InboxStyle()
            inboxStyle.addLine("Android")
            inboxStyle.addLine("Java")
            inboxStyle.addLine("Web")
            inboxStyle.addLine("iOS")
            builder.setStyle(inboxStyle)
            notificationManager.notify(125,builder.build())
        }
    }

    fun actionTextStyleNotification(){
        binding.actionTextStyleNotification.setOnClickListener {
            var builder = NotificationCompat.Builder(this,channelId)
            builder.setContentTitle("Android At Bitcode")
            builder.setContentText("Android Batch Nov 22")
            builder.setSmallIcon(R.drawable.flag)

            var actionIntent = Intent(this,SecondActivity::class.java)
            var actionPendingIntent = PendingIntent
                .getActivity(
                    this,
                    1,
                    actionIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            builder.setAutoCancel(true)

            var actionTextStyleRegister = NotificationCompat.Action(
                R.drawable.ic_launcher_foreground,
                "Admissions At Bitcode",
                actionPendingIntent
            )

            var actionIntentForSharing = Intent(this,DashboardActivity::class.java)
            var actionPendingIntentForSharing = PendingIntent.getActivity(
                this,
                1,
                actionIntentForSharing,
                PendingIntent.FLAG_IMMUTABLE
            )

            builder.addAction(actionTextStyleRegister)
            builder.addAction(R.mipmap.ic_launcher,"Share",actionPendingIntentForSharing)
            builder.addAction(R.mipmap.ic_launcher,"Useful",actionPendingIntent)
            notificationManager.notify(127,builder.build())
        }
    }


    fun bigPictureNotification(){
        binding.bigPictureStyleNotification.setOnClickListener {
            var builder = NotificationCompat.Builder(this,channelId)
            builder.setContentTitle("Android At Bitcode")
            builder.setContentText("Batch Nov 2022")
            builder.setSmallIcon(R.mipmap.ic_launcher)

            var bigPictureStyle = NotificationCompat.BigPictureStyle()
            bigPictureStyle.setSummaryText("Contact for admission")
            bigPictureStyle.setBigContentTitle("Admissions for Web, iOS, Android,java")

            var bitmapImage = BitmapFactory.decodeResource(resources,R.drawable.nature_image4)
            builder.setStyle(bigPictureStyle)
            bigPictureStyle.bigPicture(bitmapImage)

            notificationManager.notify(124,builder.build())
        }
    }

    fun snackBarNotification(){
        binding.btnSnackBarNotification.setOnClickListener { 
            Snackbar.make(
                binding.root,
                "Completed",
                Snackbar.LENGTH_LONG
            ).setTextColor(Color.MAGENTA)
                .setAction("This is a View") { Log.e("tag", "SnackBar Notification") }
                .show()
        //important - lambda function syntax
        }
    }


    fun createNotificationChannel(){
            var channelKrishnaEntBuilder = NotificationChannelCompat.Builder(
                channelId,
                NotificationManager.IMPORTANCE_HIGH)

        channelKrishnaEntBuilder.setName("Krishna Channel")
        channelKrishnaEntBuilder.setDescription("Bitcode machine tests")
        channelKrishnaEntBuilder.setShowBadge(true)

        notificationManager.createNotificationChannel(channelKrishnaEntBuilder.build())
    }
}