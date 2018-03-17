package com.tesco.sapient.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.tesco.sapient.R;
import com.tesco.sapient.dto.ItemDTO;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.swipeLayout)
    SwipeLayout swipeLayout;

    @BindView(R.id.textViewProductName)
    TextView textViewProductName;
    @BindView(R.id.textViewBarCode)
    TextView textViewBarCode;
    @BindView(R.id.textViewQuantity)
    TextView textViewQuantity;
    @BindView(R.id.textViewPrice)
    TextView textViewPrice;
    @BindView(R.id.textViewValue)
    TextView textViewValue;
    @BindView(R.id.linearLayoutDelete)
    LinearLayout linearLayoutDelete;

    private OnItemClickListener onItemClickListener;

    public ItemViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        this.onItemClickListener = onItemClickListener;
        ButterKnife.bind(this, itemView);

    }

    public void bind(final ItemDTO itemDTO) {
        textViewProductName.setText(itemDTO.getItemTypeName());
        textViewBarCode.setText(String.valueOf(itemDTO.getItemBarCode()));
        textViewQuantity.setText(String.valueOf(itemDTO.getItemQuantity()));
        textViewPrice.setText("£" + itemDTO.getItemPrice());
        textViewValue.setText("£" + new DecimalFormat("#0.00").format((itemDTO.getItemPrice() * itemDTO.getItemQuantity())));

        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // Drag From Right
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper));

        linearLayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemRemoveClicked(itemDTO);
            }
        });
    }

}