package com.example.playergenre;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by saidu on 29-Aug-18.
 */

public class NotificationBroadcast extends BroadcastReceiver {


    @SuppressLint("RestrictedApi")
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(musicplayer.NOTIFY_PLAY))
        {
            Toast.makeText(context,"play",Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction().equals(musicplayer.NOTIFY_PAUSE))
        {
            if(playbgmusic.player.isPlaying()){
                playbgmusic.player.pause();
                musicplayer.porp.setBackgroundResource(R.drawable.ic_play_circle_outline_white);
                playbgmusic.notificationplay(context);
                MainActivity.btnporp.setBackgroundResource(R.drawable.ic_play_circle_outline_white);
              /* mBuilder.getBigContentView().setImageViewResource(R.id.btnPause, R.drawable.ic_play_circle_outline_white);

               musicplayer.mBuilder.setCustomBigContentView(musicplayer.expandedView);
              // musicplayer.mNotificationManager.notify(1, musicplayer.mBuilder.build());
               musicplayer.mNotificationManager.notify(1,  musicplayer.mBuilder.build());*/
            }
            else
            {
                musicplayer.porp.setBackgroundResource(R.drawable.ic_pause_circle_outline_white);
                playbgmusic.player.start();
                playbgmusic.notificationpause(context);
                MainActivity.btnporp.setBackgroundResource(R.drawable.ic_pause_circle_outline_white);
            }
        }
        else if(intent.getAction().equals(musicplayer.NOTIFY_DELETE))
        {

            playbgmusic.mNotificationManager.cancel(1);
            if(playbgmusic.player.isPlaying()){
                playbgmusic.player.pause();
                musicplayer.porp.setBackgroundResource(R.drawable.ic_play_circle_outline_white);
                musicplayer.songstate="paused";
            }
            //  Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction().equals(musicplayer.NOTIFY_NEXT))
        {
            playbgmusic.player.stop();
            playbgmusic.playnextsong();
            musicplayer.porp.setBackgroundResource(R.drawable.ic_pause_circle_outline_white);
            musicplayer.songstate="paused";
            //Toast.makeText(context,"next",Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction().equals(musicplayer.NOTIFY_PREVIOUS))
        {
            playbgmusic.player.stop();
            playbgmusic.playprevioussong();
        }

    }
}
