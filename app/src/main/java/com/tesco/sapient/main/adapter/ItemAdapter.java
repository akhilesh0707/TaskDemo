package com.tesco.sapient.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.tesco.sapient.R;
import com.tesco.sapient.dto.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerSwipeAdapter<ItemViewHolder> implements Filterable {
    private Context mContext;
    private List<ItemDTO> itemDTOList;
    private List<ItemDTO> itemDTOListOrigional = null;
    private EventFilter itemFilter = new EventFilter();
    private OnItemClickListener clickListener;
    private boolean swipeEnabled = false;

    public ItemAdapter(Context context, List<ItemDTO> itemDTOList) {
        this.mContext = context;
        this.itemDTOList = itemDTOList;
        this.itemDTOListOrigional = itemDTOList;
    }

    public void setDataChange(List<ItemDTO> itemDTOList) {
        this.itemDTOList = itemDTOList;
        this.itemDTOListOrigional = itemDTOList;
        //now, tell the adapter about the update
        notifyDataSetChanged();
    }

    public void setItemRemoved(int position) {
        itemDTOList.remove(position);
        notifyItemRemoved(position);
        //now, tell the adapter about the update
        notifyItemRangeChanged(position, itemDTOList.size());
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyleview_item_list, parent, false);
        return new ItemViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder viewHolder, final int position) {
        final ItemDTO itemDTO = itemDTOList.get(position);
        viewHolder.bind(itemDTO);
        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return itemDTOList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private class EventFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            List<ItemDTO> allItem = itemDTOListOrigional;
            if (constraint == null || Integer.parseInt(constraint.toString()) == 0) {
                result.values = allItem;
                result.count = allItem.size();
            } else {
                ArrayList<ItemDTO> filteredList = new ArrayList<>();
                for (ItemDTO itemDTO : allItem) {
                    if (itemDTO.getItemTypeCode() == Integer.parseInt(constraint.toString()))
                        filteredList.add(itemDTO);
                }
                result.values = filteredList;
                result.count = filteredList.size();
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemDTOList = (ArrayList<ItemDTO>) results.values;
            notifyDataSetChanged();
        }
    }
}