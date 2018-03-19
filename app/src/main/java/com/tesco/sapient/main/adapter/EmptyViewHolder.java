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

/**
 * EmptyViewHolder to display no record message
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-18
 */
public class EmptyViewHolder extends RecyclerView.ViewHolder {
    /**
     * ViewHolder Constructor to set view
     *
     * @param itemView
     */
    public EmptyViewHolder(View itemView) {
        super(itemView);
    }

}