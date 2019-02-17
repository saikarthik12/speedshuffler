package com.example.playergenre;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by saidu on 26-Aug-18.
 */

public class playbgmusic extends Service {
    public static final String NOTIFY_PREVIOUS="com.example.saidu.genreplayer.previous";
    public static final String NOTIFY_DELETE="com.example.saidu.genreplayer.delete";
    public static final String NOTIFY_PAUSE="com.example.saidu.genreplayer.pause";
    public static final String NOTIFY_PLAY="com.example.saidu.genreplayer.play";
    public static final String NOTIFY_NEXT="com.example.saidu.genreplayer.next";
    public static MediaPlayer player;
    public static NotificationManager mNotificationManager;
    public static int progress;
    public static  DatabaseHelper mDatabaseHelper;
    public static String paths,paths2;
    int timess;
    static Context context;
    public static  Thread threadinga;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // String userID = intent.getStringExtra("paths2");
        if (intent != null && intent.getExtras() != null){
            paths = intent.getStringExtra("MyNumber");
            paths2 = intent.getStringExtra("MyNumber");
            timess = intent.getIntExtra("time",0);
        }
        context=playbgmusic.this;
        mDatabaseHelper=new DatabaseHelper(this);
        if(paths.contains("/storage/emulated/0"))
        {
            paths=paths.substring(paths.indexOf("/storage/emulated/0")+19);
        }
        player = MediaPlayer.create(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory().getPath() +paths));
        String s=(player.getDuration()/1000/60)+":"+(player.getDuration()/1000%60);
        musicplayer.t2.setText(s);

        checkifgenregiven();

        // player.start();
        getsongnamesinger();

        context = this;


        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.e("gg","Song over");
                playnextsong();



            }
        });
        ggwp();
        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }






    public  void setsong(String nowpath)
    {



    }




    public static void playnextsong()
    {
        threadinga.interrupt();
        Cursor data2=mDatabaseHelper.getallsong();
        while(data2.moveToNext())
        {

            String yorn=data2.getString(1);
            if(yorn.contains(paths))
            {
                break;
            }


        }
        try {
            data2.moveToNext();
            String nextsongpath=data2.getString(1);
            paths2=nextsongpath;
            Log.e("next",nextsongpath);
            if(nextsongpath.contains("/storage/emulated/0"))
            {
                nextsongpath=nextsongpath.substring(nextsongpath.indexOf("/storage/emulated/0")+19);
            }

            //setsong(nextsongpath);
            paths=nextsongpath;
            player = MediaPlayer.create(context, Uri.parse(Environment.getExternalStorageDirectory().getPath() +nextsongpath));
            musicplayer.seekBar.setMax(player.getDuration());
            String s=(player.getDuration()/1000/60)+":"+(player.getDuration()/1000%60);
            musicplayer.t2.setText(s);
            getsongnamesinger();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.e("gg","Song over");
                    playnextsong();



                }
            });

            player.start();
            notificationpause(context);


            ggwp();
        }catch (CursorIndexOutOfBoundsException e)
        {
      /*  data2.moveToFirst();

        String nextsongpath=data2.getString(1);
        paths2=nextsongpath;
        if(nextsongpath.contains("/storage/emulated/0"))
        {
            nextsongpath=nextsongpath.substring(nextsongpath.indexOf("/storage/emulated/0")+19);
        }
        paths=nextsongpath;
        player = MediaPlayer.create(context, Uri.parse(Environment.getExternalStorageDirectory().getPath() +nextsongpath));
        musicplayer.seekBar.setMax(player.getDuration());
        getsongnamesinger();
        String s=(player.getDuration()/1000/60)+":"+(player.getDuration()/1000%60);
        musicplayer.t2.setText(s);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.e("gg","Song over");
                playnextsong();



            }
        });

        player.start();
        notificationpause(context);
        ggwp();*/
            //  Log.e("exception","You have reached the end");
            musicplayer.seekBar.setProgress(player.getDuration());
            Toast.makeText(context, "You have reached the end of the current genre song list", Toast.LENGTH_LONG).show();
            musicplayer.closeapp();

        }
        catch (Exception e)
        {
            Log.e("exception",e.toString());
        }
    }

//ggg



    public static void playprevioussong()
    {
        threadinga.interrupt();
        Cursor data2=mDatabaseHelper.getallsong();
        while(data2.moveToNext())
        {

            String yorn=data2.getString(1);
            if(yorn.contains(paths))
            {
                break;
            }


        }
        try {
            data2.moveToPrevious();
            String nextsongpath=data2.getString(1);
            paths2=nextsongpath;
            Log.e("next",nextsongpath);
            if(nextsongpath.contains("/storage/emulated/0"))
            {
                nextsongpath=nextsongpath.substring(nextsongpath.indexOf("/storage/emulated/0")+19);
            }

            //setsong(nextsongpath);
            paths=nextsongpath;
            player = MediaPlayer.create(context, Uri.parse(Environment.getExternalStorageDirectory().getPath() +nextsongpath));
            musicplayer.seekBar.setMax(player.getDuration());
            String s=(player.getDuration()/1000/60)+":"+(player.getDuration()/1000%60);
            musicplayer.t2.setText(s);
            getsongnamesinger();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.e("gg","Song over");
                    playnextsong();



                }
            });

            player.start();
            notificationpause(context);
            ggwp();
        }catch (CursorIndexOutOfBoundsException e)
        {
          /*  data2.moveToFirst();

            String nextsongpath=data2.getString(1);
            paths2=nextsongpath;

            if(nextsongpath.contains("/storage/emulated/0"))
            {
                nextsongpath=nextsongpath.substring(nextsongpath.indexOf("/storage/emulated/0")+19);
            }
            paths=nextsongpath;
            player = MediaPlayer.create(context, Uri.parse(Environment.getExternalStorageDirectory().getPath() +nextsongpath));
            musicplayer.seekBar.setMax(player.getDuration());
            String s=(player.getDuration()/1000/60)+":"+(player.getDuration()/1000%60);
            musicplayer.t2.setText(s);
            getsongnamesinger();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.e("gg","Song over");
                    playnextsong();



                }
            });

            player.start();
            notificationpause(context);
            ggwp();*/
            // Log.e("exception","You have reached the start");
            musicplayer.seekBar.setProgress(0);
            musicplayer.closeapp();
            Toast.makeText(context, "You have reached the start of the current genre song list", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Log.e("exception",e.toString());
        }
    }

    public static void checkifgenregiven()
    {
        Cursor data2=mDatabaseHelper.getsongnamesinger(paths);
        if(data2.moveToNext())
        {
            String genreselected=data2.getString(3);
            String genre=data2.getString(4);
            if(genreselected.equals("no"))
            {

                musicplayer.viewgenre.performClick();
            }
            else
            {

                getgenreplaylist(genre);
                player.start();
                notificationpause(context);
            }
        }


    }

    public static void getgenreplaylist(String genre)
    {
        mDatabaseHelper.droptemptable();
        // mDatabaseHelper.createtemptable();


        String s[]=genre.split(",");

        for(int i=0;i<s.length;i++)
        {
            Log.e("genres", s[i]);
            Cursor data2=mDatabaseHelper.getgenreplaylist2(s[i]);
            while(data2.moveToNext())
            {
                String a= data2.getString(0);
                String b= data2.getString(1);
                String c= data2.getString(2);
                String d= data2.getString(3);
                String e= data2.getString(4);
                String f= data2.getString(4);
                boolean insertdata = mDatabaseHelper.adddata2(a,b,c,d,e,f);
                if (insertdata) {
                    // Toast.makeText(getApplicationContext(), "vetrii", Toast.LENGTH_SHORT).show();
                    Log.e("lol", "success");
                } else {
                    Log.e("lol", "fail");
                }


            }
        }
    }

    public static void getsongnamesinger()
    {
        try {
            MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
            metaRetriver.setDataSource(paths2);

            byte [] art = metaRetriver.getEmbeddedPicture();
            Bitmap songImage = BitmapFactory
                    .decodeByteArray(art, 0, art.length);
            musicplayer.album_art.setImageBitmap(songImage);

        } catch (Exception e) {
            musicplayer.album_art.setBackgroundColor(Color.GRAY);

        }




        Cursor data2=mDatabaseHelper.getsongnamesinger(paths);


        if(data2.moveToNext())
        {
            String songname=data2.getString(0);
            String songsinger=data2.getString(2);
            String genres=data2.getString(4);


            if(genres.length()>0)
            {
                genres=genres.substring(0,genres.length()-1);
            }
            musicplayer.displaygenre.setText(genres);
            musicplayer.sngname.setText(songname);
            musicplayer.sngsinger.setText(songsinger);
            MainActivity.textsongname.setText(songname);
        }
        else
        {
            // Toast.makeText(context,"running2",Toast.LENGTH_SHORT).show();


            String s1[]=MainActivity.arrayList2.get(0).split("---");
            //  String s1[]=data.split("---");
            musicplayer.sngname.setText(s1[0]);
            musicplayer.sngsinger.setText(s1[1]);
            MainActivity.textsongname.setText(s1[0]);



        }
        while(data2.moveToNext())
        {




        }
    }

    public static void getsongnamesinger2()
    {

        Cursor data2=mDatabaseHelper.getsongnamesinger(paths);

        if(data2.moveToNext())
        {

            String songname=data2.getString(0);

            MainActivity.textsongname.setText(songname);


        }
        else
        {
            String s1[]=MainActivity.arrayList2.get(0).split("---");
            MainActivity.textsongname.setText(s1[0]);
        }
    }
    static String songname22;
    public static String getsongname()
    {
        Cursor data2=mDatabaseHelper.getsongnamesinger(paths);
        if(data2.moveToNext())
        {
            songname22=data2.getString(0);
        }
        else
        {
            String s1[]=MainActivity.arrayList2.get(0).split("---");
            songname22=s1[0];
        }
        return songname22;
    }


    public static void ggwp()
    {

        try {
            threadinga = new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            sleep(1000);

                            musicplayer.pgss = player.getCurrentPosition();
                            musicplayer.seekBar.setProgress(player.getCurrentPosition());
                            //     String s2 = (player.getCurrentPosition() / 1000 / 60) + ":" + (player.getCurrentPosition() / 1000 % 60);
                            //  musicplayer.t1.setText(s2);//for samsung
                            //mediaPlayer.getDuration()
                            //        Log.w("gg",   musicplayer.pgss+"");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };


            threadinga.start();
        }catch (Exception e)
        {

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        //   thread.interrupt();
    }



    @SuppressLint("RestrictedApi")
    public static void notificationplay(Context context)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001");
        mNotificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        RemoteViews expandedView=new RemoteViews(context.getPackageName(),R.layout.big_notificationplay);

        Intent resultIntent = new Intent(context, musicplayer.class);
        resultIntent.setAction("android.intent.action.MAIN");
        resultIntent.addCategory("android.intent.category.LAUNCHER");

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        mBuilder.setContentIntent(resultPendingIntent);

        mBuilder.setCustomBigContentView(expandedView);
        mBuilder.setAutoCancel(true);
        mBuilder.setOngoing(true);
        mBuilder.getBigContentView().setTextViewText(R.id.textsongname,getsongname());
        mBuilder.setSound(null);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setVibrate(null);
        mBuilder.setPriority(Notification.PRIORITY_MAX);

        setListeners(expandedView,context);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Genre Player",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("no sound");
            channel.setSound(null,null);
            channel.enableLights(false);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(false);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(1, mBuilder.build());
    }

    @SuppressLint("RestrictedApi")
    public static void notificationpause(Context context)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001");
        mNotificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        RemoteViews expandedView=new RemoteViews(context.getPackageName(),R.layout.big_notification);

        Intent resultIntent = new Intent(context, musicplayer.class);
        resultIntent.setAction("android.intent.action.MAIN");
        resultIntent.addCategory("android.intent.category.LAUNCHER");

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        mBuilder.setContentIntent(resultPendingIntent);

        mBuilder.setCustomBigContentView(expandedView);
        mBuilder.setAutoCancel(true);
        mBuilder.setOngoing(true);
        mBuilder.getBigContentView().setTextViewText(R.id.textsongname,getsongname());
        mBuilder.setSound(null);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setVibrate(null);
        mBuilder.setPriority(Notification.PRIORITY_MAX);

        setListeners(expandedView,context);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Genre Player",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("no sound");
            channel.setSound(null,null);
            channel.enableLights(false);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(false);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(1, mBuilder.build());
    }

    private static void setListeners(RemoteViews view, Context context) {

        Intent pause = new Intent(context,NotificationBroadcast.class);
        pause.setAction(NOTIFY_PAUSE);

        Intent previous = new Intent(context,NotificationBroadcast.class);
        previous.setAction(NOTIFY_PREVIOUS);

        Intent next = new Intent(context,NotificationBroadcast.class);
        next.setAction(NOTIFY_NEXT);

        Intent delete = new Intent(context,NotificationBroadcast.class);
        delete.setAction(NOTIFY_DELETE);



        PendingIntent pPrevious=PendingIntent.getBroadcast(context,0,previous,PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPrevious,pPrevious);

        PendingIntent pdelete=PendingIntent.getBroadcast(context,0,delete,PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnDelete,pdelete);
        PendingIntent pPause=PendingIntent.getBroadcast(context,0,pause,PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPause,pPause);

        PendingIntent pNext=PendingIntent.getBroadcast(context,0,next,PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnNext,pNext);


    }

}

