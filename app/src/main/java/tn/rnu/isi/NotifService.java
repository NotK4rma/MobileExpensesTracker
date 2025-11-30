package tn.rnu.isi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class NotifService extends Service {

    private static final String CHANNEL_ID = "expense_reminder_channel";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
//        CountDownTimer timer = new CountDownTimer(3000, 600) {
//            public void onTick(long millisUntilFinished) {
//
//            }
//            @Override
//            public void onFinish(){
//                showReminderNotification();
//            }
//        };
//        timer.start();
        new Handler().postDelayed(() -> showReminderNotification(), 5000);

        return START_NOT_STICKY;
    }


    private void showReminderNotification() {

        Intent intent = new Intent(this, AddExpenseActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add)
                .setContentTitle("Expense Reminder")
                .setContentText("Don’t forget to add your expenses for today!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Expense Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
