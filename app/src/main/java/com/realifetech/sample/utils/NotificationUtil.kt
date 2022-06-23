package com.realifetech.sample.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.realifetech.sample.R
import com.realifetech.sample.service.FirebaseMessageListenerService.Companion.NOTIFICATION_TITLE_EXTRA
import com.realifetech.sample.service.FirebaseMessageListenerService.Companion.REFERENCE_ID
import java.text.SimpleDateFormat
import java.util.*


object NotificationUtil {
    const val NAME = "Push Notification"
    const val IS_PN = "isFromPN"
    const val PN_PAYLOAD = "pn_payload"
    const val DESCRIPTION = "Informational push notification"
    const val CHANNEL_ID = "notification_channel"
    const val DEFAULT_LINK = "rtlsmpapp://cawidgets"

    /**
     * Show the notification to the user.
     *
     * @param context The context.
     * @param title   The notification title.
     * @param message The message to be displayed.
     * @param action  The action (link) to be launched when the notification is opened.
     */
    fun showNotification(
        context: Context, title: String?, message: String?, action: String?,
        referenceId: String?,
        trackInfo: HashMap<String, Any>?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context)
        }
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setBigContentTitle(title)
                    .bigText(message)
            )
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(getContentIntent(context, action, referenceId, title, trackInfo))
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            createNotificationId(),
            notificationBuilder.build()
        )
    }

    /**
     * Create a notification channel to support Android 8 and above.
     *
     * @param context The context.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, NAME, importance)
        // Configure the notification channel.
        channel.description = DESCRIPTION
        channel.setShowBadge(false)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Get a PendingIntent that will fire a NOTIFICATION_OPENED event, containing all the notification data.
     *
     * @param context The Context
     * @param action  The notification action (link).
     * @return A PendingIntent broadcasting a NOTIFICATION_OPENED Intent.
     */
    private fun getContentIntent(
        context: Context,
        action: String?,
        referenceId: String?,
        title: String?,
        trackInfo: HashMap<String, Any>?
    ): PendingIntent {
        var url = action
        action?.let {
            val testIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            val componentName = testIntent.resolveActivity(context.packageManager)
            if (componentName == null) {
                url = DEFAULT_LINK
            }
        } ?: run {
            url = DEFAULT_LINK
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            putExtra(REFERENCE_ID, referenceId)
            putExtra(NOTIFICATION_TITLE_EXTRA, title)
            putExtra(IS_PN, true)
            putExtra(PN_PAYLOAD, trackInfo)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val requestCode = System.currentTimeMillis().toInt()
        return PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotificationId(): Int {
        val cal = Calendar.getInstance()
        return SimpleDateFormat("ddHHmmss", Locale.US).format(cal.time).toInt()
    }

}