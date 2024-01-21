package pl.atrolide.androidappproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationUtils {

    private static final String CHANNEL_ID = "MyAppChannel";
    private static int notificationCounter = 0;

    public static void showNotification(Context context) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("App Notification")
                .setContentText("Activity Stopped - Count: " + ++notificationCounter)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.notify(0, builder.build());
        }
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void createNotificationChannel(Context context) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "MyAppChannel",
                NotificationManager.IMPORTANCE_DEFAULT);
        context.getSystemService(NotificationManager.class).createNotificationChannel(channel);
    }
}
