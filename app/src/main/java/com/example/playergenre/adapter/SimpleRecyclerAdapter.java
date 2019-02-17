package com.example.playergenre.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.playergenre.DatabaseHelper;
import com.example.playergenre.MainActivity;
import com.example.playergenre.R;
import com.example.playergenre.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.simpleViewHolder> {
    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    List<String> datasource;
    List<String> datasource2;
    Context context;
    public static DatabaseHelper mDatabaseHelper;


    public SimpleRecyclerAdapter(ArrayList<String> e,Context ctx)
    {
        context=ctx;
         datasource = new ArrayList<>();
        datasource2 = new ArrayList<>();
        for(String data:e){
            String s[]=data.split("---");
            datasource.add(s[0]);
            datasource2.add(data);
        }
         /*for (int i=0;i<=10;i++)
         {
             datasource.add(i);
         }*/
    }

    @Override
    public simpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item,parent,false);
        ViewHolder holder =new ViewHolder(view);
        return new simpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(simpleViewHolder holder, final int position) {
        String name=datasource.get(position);
        String paths2;
        holder.textView.setText(String.valueOf(name));
      try
      {
          for(String data:datasource2){
              if(data.contains(name))
              {
                  String s[]=data.split("---");
                  paths2=s[2];
                 // Toast.makeText(context,paths2, Toast.LENGTH_SHORT).show();
                  MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
                  metaRetriver.setDataSource(paths2);

                  byte [] art = metaRetriver.getEmbeddedPicture();
                  Bitmap songImage = BitmapFactory
                          .decodeByteArray(art, 0, art.length);
                  holder.cimg.setImageBitmap(songImage);
              }

          }



        /* */

    } catch (Exception e) {
        holder.cimg.setBackgroundColor(Color.GRAY);

    }


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context,datasource.get(position),Toast.LENGTH_SHORT).show();
                String s[]=datasource.get(position).split("---");
                MainActivity.gotosong(s[0]);

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView albumName;
        LinearLayout parentLayout;
        CircleImageView cimg;

        public ViewHolder(View v) {
            super(v);
            albumName=v.findViewById(R.id.textview);
            parentLayout=v.findViewById(R.id.parent_layout);
            cimg=v.findViewById(R.id.profile_image);


        }
    }


    @Override
    public int getItemCount() {
        return datasource.size();
    }

    public static class simpleViewHolder extends RecyclerView.ViewHolder{
     public TextView textView;
     public  LinearLayout parentLayout;
     public   CircleImageView cimg;
     public simpleViewHolder(View itemView)
     {
         super(itemView);
         textView = (TextView)itemView.findViewById(R.id.textview);
         parentLayout=(LinearLayout)itemView.findViewById(R.id.parent_layout);
         cimg=(CircleImageView)itemView.findViewById(R.id.profile_image);
     }
    }
}
