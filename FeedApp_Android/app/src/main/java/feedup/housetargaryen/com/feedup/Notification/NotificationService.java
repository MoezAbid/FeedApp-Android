package feedup.housetargaryen.com.feedup.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import feedup.housetargaryen.com.feedup.Connection.Model.Application;
import feedup.housetargaryen.com.feedup.Description;
import feedup.housetargaryen.com.feedup.R;

//Other

public class NotificationService extends Service {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefApplications = database.getReference("Application");

    public NotificationService() {

    }


    //Git
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        myRefApplications.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Application app = dataSnapshot.getValue(Application.class);
                sendNotification(app);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    public void sendNotification(Application app) {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, Description.class);
        intent.putExtra("afn", app);
        intent.putExtra("cf", "notif");
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification n = new Notification.Builder(this)
                .setContentTitle("New application added ! Try it out !")
                .setContentText(app.getNomAPP())
                .setSmallIcon(R.mipmap.green)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0, n);
    }
}
