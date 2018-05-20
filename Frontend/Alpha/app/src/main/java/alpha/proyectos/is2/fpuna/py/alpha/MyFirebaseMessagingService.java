package alpha.proyectos.is2.fpuna.py.alpha;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived (RemoteMessage message) {
        System.err.println("MyFirebaseMessagingService From: " + message.getFrom());

        // Check if message contains a data payload.
        if (message.getData().size() > 0) {
            System.err.println("Message data payload: " + message.getData());
        }

        // Check if message contains a notification payload.
        if (message.getNotification() != null) {
            String mensaje = message.getNotification().getBody();
            System.err.println("Message Notification Body: " + mensaje);
            /*try {
                DataSource datasource = new DataSource(this);
                datasource.open();
                datasource.crearNotificacion(mensaje, mensaje);
            } catch (Exception e) {
                Log.d(TAG, "Error al guardar Notificacion : " + e.getMessage());
            }*/
        }

        /*NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Setting up Notification channels for android O and above
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels();
        }*/

        /*int notificationId = new Random().nextInt(60000);

        //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "ADMIN_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_home)  //a resource for your custom small icon
                .setContentTitle(message.getData().get("title")) //the "title" value you sent in your notification
                .setContentText(message.getData().get("message")) //ditto
                .setAutoCancel(true);  //dismisses the notification on click
                //.setSound(defaultSoundUri);

        //NotificationManager notificationManager =
           //     (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());*/

    }

    @Override
    public void onDeletedMessages () {
        ;
    }

}
