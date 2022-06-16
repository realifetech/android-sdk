package com.realifetech.sample.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.realifetech.sample.service.FirebaseMessageListenerService
import java.text.SimpleDateFormat
import java.util.*

class NotificationUtil {


    /**
     * Show the notification to the user.
     *
     * @param context The context.
     * @param title   The notification title.
     * @param message The message to be displayed.
     * @param action  The action (link) to be launched when the notification is opened.
     */
    fun showNotification(
        context: Context,
        title: String?,
        message: String?,
        action: String?,
        referenceId: String?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context)
        }
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setBigContentTitle(title)
                    .bigText(message)
            )
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(getContentIntent(context, action!!, referenceId!!, title))
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            createNotificationId(),
            notificationBuilder.build()
        )
        val notificationReceivedIntent = Intent().setAction(REFRESH_ACTION)
        context.sendBroadcast(notificationReceivedIntent)
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
        notificationManager?.createNotificationChannel(channel)
    }

    /**
     * Get a PendingIntent that will fire a NOTIFICATION_OPENED event, containing all the notification data.
     *
     * @param context The Context
     * @param
     * @param action  The notification action (link).
     * @return A PendingIntent broadcasting a NOTIFICATION_OPENED Intent.
     */
    private fun getContentIntent(
        context: Context,
        action: String,
        referenceId: String,
        title: String?
    ): PendingIntent {
        var action: String? = action
        if (action.isNullOrEmpty()) {
            // Check if action is empty
            action = defaultLink
        } else {
            // Check if Intent can't be resolved
            val testIntent = Intent(Intent.ACTION_VIEW, Uri.parse(action))
            val componentName = testIntent.resolveActivity(context.packageManager)
            if (componentName == null) {
                action = defaultLink
            }
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(action))
        intent.putExtra(FirebaseMessageListenerService.REFERENCE_ID, referenceId)
        intent.putExtra(FirebaseMessageListenerService.NOTIFICATION_TITLE_EXTRA, title)
        val requestCode = System.currentTimeMillis().toInt()
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntentWithParentStack(intent)
        return stackBuilder.getPendingIntent(requestCode, PendingIntent.FLAG_IMMUTABLE)
    }

    private val defaultLink: String
        private get() = "empty_deep_link"

    private fun createNotificationId(): Int {
        val cal = Calendar.getInstance()
        return SimpleDateFormat("ddHHmmss", Locale.US).format(cal.time).toInt()
    }

    companion object {
        const val NAME = "Push Notification"
        const val DESCRIPTION = "Informational push notification"
        const val CHANNEL_ID = "notification_channel"
        const val REFRESH_ACTION = "refresh_widget_action"
    }


}