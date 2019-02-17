package com.example.playergenre;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class musicplayer extends AppCompatActivity {

    public static final String NOTIFY_PREVIOUS="com.example.saidu.genreplayer.previous";
    public static final String NOTIFY_DELETE="com.example.saidu.genreplayer.delete";
    public static final String NOTIFY_PAUSE="com.example.saidu.genreplayer.pause";
    public static final String NOTIFY_PLAY="com.example.saidu.genreplayer.play";
    public static final String NOTIFY_NEXT="com.example.saidu.genreplayer.next";

    public static NotificationCompat.Builder mBuilder;
    public static NotificationManager mNotificationManager;
    musicplayer mp;
    SharedPreferences mPrefs;
    public static SeekBar seekBar;
    MediaPlayer mediaPlayer;
    DatabaseHelper mDatabaseHelper;
    Handler handler;
    Runnable runnable;
    public static TextView t1,t2,sngname,sngsinger,displaygenre;
    public static int pgss;
    static  musicplayer mpp;
    public static String songstate="playing";
    public static Button porp,viewgenre;
    public static Thread threadbar;
    public static RemoteViews expandedView;
    //public static ImageView album_art ;
    public static CircleImageView album_art;
    public String kk1,kk2,kk3,kk4,kk5,kk6,kk7,kk8;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplayer);
        t1=(TextView)findViewById(R.id.timestart);
        t2=(TextView)findViewById(R.id.timeend);
        sngname=(TextView)findViewById(R.id.sngname);
        sngsinger=(TextView)findViewById(R.id.sngsinger);
        displaygenre=(TextView)findViewById(R.id.displaygenre);
        porp=(Button)findViewById(R.id.playorpause);
        viewgenre=(Button)findViewById(R.id.viewgenrebut);
        album_art = (CircleImageView) findViewById(R.id.song_image);


        mPrefs = getSharedPreferences("IDvalue", 0);
        mp=musicplayer.this;
        mpp=this;
        mDatabaseHelper=new DatabaseHelper(this);
        //notification part
        //     playnotif();
        viewgenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(mpp);
                mBuilder.setCancelable(false);

                View mView= getLayoutInflater().inflate(R.layout.genrelistshow,null);
                final Button heavymetal=(Button)mView.findViewById(R.id.heavymetal);
                final Button rock=(Button)mView.findViewById(R.id.rock);
                final Button hardrock=(Button)mView.findViewById(R.id.hardrock);
                final Button alternative=(Button)mView.findViewById(R.id.alternative);
                final  Button classical=(Button)mView.findViewById(R.id.classical);
                final  Button pop=(Button)mView.findViewById(R.id.pop);
                final  Button jazz=(Button)mView.findViewById(R.id.jazz);
                final  Button rap=(Button)mView.findViewById(R.id.rap);
                final  Button confirm=(Button)mView.findViewById(R.id.confirmgenre);


                Cursor data2=mDatabaseHelper.getsongnamesinger(playbgmusic.paths);
                if(data2.moveToNext())
                {
                    String genreselected=data2.getString(3);
                    String genre=data2.getString(4);
                    if(genreselected.equals("no"))
                    {
                        kk1=kk2=kk3=kk4=kk5=kk6=kk7=kk8="no";
                        Toast.makeText(getApplicationContext(),"Select Genre",Toast.LENGTH_SHORT).show();
                        heavymetal.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        rock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        hardrock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        alternative.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        classical.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        pop.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        jazz.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        rap.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                       /* heavymetal.setBackgroundColor(Color.RED);
                        rock.setBackgroundColor(Color.RED);
                        hardrock.setBackgroundColor(Color.RED);
                        alternative.setBackgroundColor(Color.RED);
                        classical.setBackgroundColor(Color.RED);
                        pop.setBackgroundColor(Color.RED);
                        jazz.setBackgroundColor(Color.RED);
                        rap.setBackgroundColor(Color.RED);*/


                    }
                    else
                    {
                        kk1=kk2=kk3=kk4=kk5=kk6=kk7=kk8="no";
                        heavymetal.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        rock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        hardrock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        alternative.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        classical.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        pop.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        jazz.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        rap.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        Toast.makeText(getApplicationContext(),genre,Toast.LENGTH_SHORT).show();
                        if(genre.contains("heavymetal"))
                        {
                            kk1="yes";
                            heavymetal.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }
                        if(genre.contains("rock"))
                        {
                            kk2="yes";
                            rock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }
                        if(genre.contains("hardrock"))
                        {
                            kk3="yes";
                            hardrock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }
                        if(genre.contains("alternative"))
                        {
                            kk4="yes";
                            alternative.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }
                        if(genre.contains("classical"))
                        {
                            kk5="yes";
                            classical.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }
                        if(genre.contains("pop"))
                        {
                            kk6="yes";
                            pop.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }
                        if(genre.contains("jazz"))
                        {
                            kk7="yes";
                            jazz.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }
                        if(genre.contains("rap"))
                        {
                            kk8="yes";
                            rap.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }








                    }


                }
                heavymetal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  ColorDrawable buttonColor = (ColorDrawable) heavymetal.getBackground();
                        //  int colorId = buttonColor.getColor();
                        if (kk1.equals("yes")) {
                            kk1="no";
                            heavymetal.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        }
                        else
                        {
                            kk1="yes";
                            heavymetal.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }

                    }
                });


                rock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //    ColorDrawable buttonColor = (ColorDrawable) rock.getBackground();
                        //      int colorId = buttonColor.getColor();
                        if (kk2.equals("yes")) {
                            kk2="no";
                            rock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        }
                        else
                        {
                            kk2="yes";
                            rock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }

                    }
                });

                hardrock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                         /*   ColorDrawable buttonColor2 = (ColorDrawable) hardrock.getBackground();
                            int colorId = buttonColor2.getColor();*/
                            if (kk3.equals("yes")) {
                                kk3="no";
                                hardrock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                            } else {
                                kk3="yes";
                                hardrock.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                            }
                        }catch (Exception e)
                        {

                        }

                    }
                });


                alternative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ColorDrawable buttonColor = (ColorDrawable) alternative.getBackground();
                        int colorId = buttonColor.getColor();
                        if (kk4.equals("yes")) {kk4="no";
                            alternative.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        }
                        else
                        {kk4="yes";
                            alternative.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }

                    }
                });

                classical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     /*   ColorDrawable buttonColor = (ColorDrawable) classical.getBackground();
                        int colorId = buttonColor.getColor();*/
                        if (kk5.equals("yes")) {kk5="no";
                            classical.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        }
                        else
                        {kk5="yes";
                            classical.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }

                    }
                });


                pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*ColorDrawable buttonColor = (ColorDrawable) pop.getBackground();
                        int colorId = buttonColor.getColor();*/
                        if (kk6.equals("yes")) {kk6="no";
                            pop.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        }
                        else
                        {kk6="yes";
                            pop.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }

                    }
                });

                jazz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*ColorDrawable buttonColor = (ColorDrawable) jazz.getBackground();
                        int colorId = buttonColor.getColor();*/
                        if (kk7.equals("yes")) {kk7="no";
                            jazz.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        }
                        else
                        {kk7="yes";
                            jazz.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }

                    }
                });

                rap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       /* ColorDrawable buttonColor = (ColorDrawable) rap.getBackground();
                        int colorId = buttonColor.getColor();*/
                        if (kk8.equals("yes")) {kk8="no";
                            rap.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbg));
                        }
                        else
                        {   kk8="yes";
                            rap.setBackground(ContextCompat.getDrawable(musicplayer.this, R.drawable.buttonbgg));
                        }

                    }
                });
                mBuilder.setView(mView);
                final AlertDialog dialog=mBuilder.create();

                dialog.show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String genres="";
                      /*  ColorDrawable buttonColor = (ColorDrawable) heavymetal.getBackground();
                        int colorId = buttonColor.getColor();*/
                        if (kk1.equals("yes")) {
                            genres=genres+"heavymetal,";
                        }


                        if (kk2.equals("yes")) {
                            genres=genres+"rock,";
                        }


                        if (kk3.equals("yes")) {
                            genres=genres+"hardrock,";
                        }



                        if (kk4.equals("yes")) {
                            genres=genres+"alternative,";
                        }

                        if (kk5.equals("yes")) {
                            genres=genres+"classical,";
                        }


                        if (kk6.equals("yes")) {
                            genres=genres+"pop,";
                        }


                        if (kk7.equals("yes")) {
                            genres=genres+"jazz,";
                        }


                        if (kk8.equals("yes")) {
                            genres=genres+"rap,";
                        }

                        if(genres.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Give a genre",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"genres Updated",Toast.LENGTH_SHORT).show();
                            mDatabaseHelper.updategenre(playbgmusic.paths,genres);
                            dialog.dismiss();
                            playbgmusic.getgenreplaylist(genres);
                            playbgmusic.getsongnamesinger();
                            playbgmusic.player.start();
                            playbgmusic.notificationpause(musicplayer.this);
                        }




                    }
                });



            }
        });


        boolean abc= isMyServiceRunning(playbgmusic.class);
        if(abc==true)
        { Intent intent = new Intent(this, playbgmusic.class);
            //     Toast.makeText(getApplicationContext(),"running",Toast.LENGTH_SHORT).show();
            stopService(intent);
        }
        else
        {
            //Toast.makeText(getApplicationContext(),"not running",Toast.LENGTH_SHORT).show();
        }
        //  handler=new Handler();
        seekBar=(SeekBar)findViewById(R.id.seekBar);

        String message=mPrefs.getString("path","");

        // Bundle bundle = getIntent().getExtras();
        //String message = bundle.getString("path");



        //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        try {
            // Intent serviceIntent = new Intent(playbgmusic.class.getName());
            //  serviceIntent.putExtra("paths2", message);
            Intent intent = new Intent(this, playbgmusic.class);
            intent.putExtra("MyNumber", message);
            intent.putExtra("time", 0);

            startService(intent);

            if(message.contains("/storage/emulated/0"))
            {
                message=message.substring(message.indexOf("/storage/emulated/0")+19);
            }

            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + message));
            //mediaPlayer= MediaPlayer.create(getApplicationContext(), Uri.parse("/storage/emulated/0/storage/emulated/0/SHAREit/audios/Download/Shape of You - Ed Sheeran (DJJOhAL.Com).mp3"));
            // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    seekBar.setMax(mediaPlayer.getDuration());

                    //   playcycle();
                    //   mediaPlayer.start();
                }
            });

            threadbar = new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            sleep(1000);
                            mp.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int a=playbgmusic.player.getCurrentPosition() / 1000 % 60;
                                    String sai="";
                                    if(a<10)
                                    {
                                        sai="0"+a;
                                    }
                                    else
                                    {
                                        sai=a+"";
                                    }
                                    String s2 = (playbgmusic.player.getCurrentPosition() / 1000 / 60) + ":" + sai;
                                    t1.setText(s2);
                                }
                            });


                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };


            threadbar.start();

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {

                    if (input) {
                        Intent intent = new Intent(musicplayer.this, playbgmusic.class);

                        playbgmusic.player.seekTo(progress);
                        //  t1.setText(playbgmusic.player.getCurrentPosition()/1000/60+":"+playbgmusic.player.getCurrentPosition()/1000%60);


                        //  mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }




    }

    private void setListeners(RemoteViews view, Context context) {

        Intent pause = new Intent(this,NotificationBroadcast.class);
        pause.setAction(NOTIFY_PAUSE);

        Intent previous = new Intent(this,NotificationBroadcast.class);
        previous.setAction(NOTIFY_PREVIOUS);

        Intent next = new Intent(this,NotificationBroadcast.class);
        next.setAction(NOTIFY_NEXT);

        Intent delete = new Intent(this,NotificationBroadcast.class);
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

    public void playcycle()
    {
        seekBar.setProgress(pgss);
        if(mediaPlayer.isPlaying())
        {
            runnable=new Runnable() {
                @Override
                public void run() {
                    playcycle();
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(playbgmusic.player.isPlaying())
        {
            playbgmusic.notificationpause(musicplayer.this);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playbgmusic.threadinga.interrupt();
        threadbar.interrupt();
        Intent intent = new Intent(this, playbgmusic.class);
        stopService(intent);
        Log.e("closethread","threadinga deadd");

        playbgmusic.mNotificationManager.cancel(1);
        //      mediaPlayer.release();
        //   handler.removeCallbacks(runnable);
    }

    public void notif(View view) {

        if(songstate.equals("playing"))
        {
            playbgmusic.player.pause();
            songstate="paused";
            porp.setBackgroundResource(R.drawable.ic_play_circle_outline_white);

            playbgmusic.notificationplay(musicplayer.this);

        }
        else if(songstate.equals("paused"))
        {
            playbgmusic.player.start();
            // playnotif();
            songstate="playing";
            porp.setBackgroundResource(R.drawable.ic_pause_circle_outline_white);
            playbgmusic.notificationpause(musicplayer.this);
        }


       /* Intent intent=new Intent(getApplicationContext(),mediaplayerservice.class);
        intent.setAction(mediaplayerservice.ACTION_PLAY);
        startService(intent);*/


    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



    @Override
    public void onBackPressed() {

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public static void closeapp()
    {
        playbgmusic.threadinga.interrupt();
        threadbar.interrupt();
        // Intent intent = new Intent(mpp, playbgmusic.class);

        Log.e("closethread","threadinga deadd");

        playbgmusic.mNotificationManager.cancel(1);
        mpp.finish();
    }

    @SuppressLint("RestrictedApi")
    public  void playnotif()
    {
        mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        mNotificationManager =(NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        expandedView=new RemoteViews(this.getPackageName(),R.layout.big_notification);

        Intent resultIntent = new Intent(this, musicplayer.class);
        resultIntent.setAction("android.intent.action.MAIN");
        resultIntent.addCategory("android.intent.category.LAUNCHER");

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        mBuilder.setContentIntent(resultPendingIntent);

        mBuilder.setCustomBigContentView(expandedView);
        mBuilder.setAutoCancel(true);
        mBuilder.setOngoing(true);
        mBuilder.getBigContentView().setTextViewText(R.id.textsongname,"ggwp");

        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);

        mBuilder.setPriority(Notification.PRIORITY_MAX);

        setListeners(expandedView,this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Genre Player",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(1, mBuilder.build());
    }
    public static musicplayer getInstance(){
        return mpp;
    }


    public void playnextsong(View view) {
        playbgmusic.player.stop();
        songstate="playing";
        porp.setBackgroundResource(R.drawable.ic_pause_circle_outline_white);
        playbgmusic.playnextsong();
    }

    public void playprevioussong(View view) {
        playbgmusic.player.stop();
        songstate="playing";
        porp.setBackgroundResource(R.drawable.ic_pause_circle_outline_white);
        playbgmusic.playprevioussong();


    }

    public static void gfgdffg()
    {

    }


}
