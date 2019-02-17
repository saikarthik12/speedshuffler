package com.example.playergenre;

import android.view.View;

/**
 * Created by saidu on 11-Sep-18.
 */
public interface RecyclerViewItemClickListener
{
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}