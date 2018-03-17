package com.tesco.sapient.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.tesco.sapient.R;
import com.tesco.sapient.dto.ItemDTO;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyViewHolder extends RecyclerView.ViewHolder {
    public EmptyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}