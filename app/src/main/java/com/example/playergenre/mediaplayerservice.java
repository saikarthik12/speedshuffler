package com.example.playergenre;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

/**
 * Created by saidu on 26-Aug-18.
 */

public class mediaplayerservice extends Service {

    private MediaSession mSession;
    private MediaSessionManager mManager;
    private MediaController mController;
    private MediaPlayer mMediaPlayer;

    public static final String ACTION_PLAY="action_play";
    public static final String ACTION_PAUSE="action_pause";
    public static final String ACTION_PREVIOUS="action_previous";
    public static final String ACTION_NEXT="action_next";
    public static final String ACTION_STOP="action_stop";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mSession.release();
        return super.onUnbind(intent);
    }

    private void handleIntent(Intent intent)
    {
        if(intent== null || intent.getAction()==null)
        {
            return;

        }
        String action=intent.getAction();
        if(action.equalsIgnoreCase(ACTION_PLAY))
        {
            mController.getTransportControls().play();
        }
        else if(action.equalsIgnoreCase(ACTION_PAUSE))
        {
            mController.getTransportControls().pause();
        }else if(action.equalsIgnoreCase(ACTION_NEXT))
        {
            mController.getTransportControls().skipToNext();
        }else if(action.equalsIgnoreCase(ACTION_PREVIOUS))
        {
            mController.getTransportControls().skipToPrevious();
        }else if(action.equalsIgnoreCase(ACTION_STOP))
        {
            mController.getTransportControls().stop();
        }




    }

    private Notification.Action generateAction(int icon,String title,String intentaction)
    {
        Intent intent=new Intent(getApplicationContext(),mediaplayerservice.class);
        intent.setAction(intentaction);
        PendingIntent pendingIntent=PendingIntent.getService(getApplicationContext(),1,intent,0);
        return new Notification.Action.Builder(icon,title,pendingIntent).build();
    }

    private void buildNotification(Notification.Action action)
    {
        Notification.MediaStyle style=new Notification.MediaStyle();
        Intent intent=new Intent(getApplicationContext(),mediaplayerservice.class);
        intent.setAction(ACTION_STOP);
        PendingIntent pendingIntent=PendingIntent.getService(getApplicationContext(),1,intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"gg").setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("lock screen media")
                .setContentText("ggwp")
                .setDeleteIntent(pendingIntent)
                ;
        //   style.setShowActionsInCompactView(0,1,2);

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mManager==null)
        {
            initMediaSession();
        }
        handleIntent(intent );

        return super.onStartCommand(intent, flags, startId);
    }

    private void initMediaSession()
    {
        mMediaPlayer = new MediaPlayer();
        mSession=new MediaSession(getApplicationContext(),"example player session");
        mController=new MediaController(getApplicationContext(),mSession.getSessionToken());

        mSession.setCallback(new MediaSession.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                buildNotification(generateAction(android.R.drawable.ic_media_pause,"pause",ACTION_PAUSE));
            }

            @Override
            public void onPause() {
                super.onPause();
                buildNotification(generateAction(android.R.drawable.ic_media_play,"play",ACTION_PLAY));
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                buildNotification(generateAction(android.R.drawable.ic_media_pause,"pause",ACTION_PAUSE));
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                buildNotification(generateAction(android.R.drawable.ic_media_pause,"pause",ACTION_PAUSE));
            }

            @Override
            public void onStop() {
                super.onStop();
                NotificationManager notificationManager=(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(1);
                Intent intent=new Intent(getApplicationContext(),mediaplayerservice.class);
                stopService(intent);


            }
        });
    }
}
