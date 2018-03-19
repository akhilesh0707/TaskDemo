package com.tesco.sapient.main.adapter;

import android.support.v7.widget.RecyclerView;
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

/**
 * ItemAdapter to display no records
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-18
 */
public class ItemAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> implements Filterable {

    private List<ItemDTO> itemDTOList;
    private List<ItemDTO> itemDTOListOrigional = null;
    private EventFilter itemFilter = new EventFilter();
    private OnItemClickListener clickListener;
    private static final int VIEW_TYPE_DATA = 1;
    private static final int VIEW_TYPE_EMPTY = 2;

    /**
     * ItemAdapter constructor to initialize ItemList
     *
     * @param itemDTOList
     */
    public ItemAdapter(List<ItemDTO> itemDTOList) {
        this.itemDTOList = itemDTOList;
        this.itemDTOListOrigional = itemDTOList;
    }

    /**
     * Method is used to update the ItemList on this adapter
     *
     * @param itemDTOList
     */
    public void setDataChange(List<ItemDTO> itemDTOList) {
        this.itemDTOList = itemDTOList;
        this.itemDTOListOrigional = itemDTOList;
        //now, tell the adapter about the update
        notifyDataSetChanged();
    }

    /**
     * Method is used to remove Item from ItemList and update UI
     *
     * @param position
     */
    public void setItemRemoved(int position) {
        itemDTOList.remove(position);
        notifyItemRemoved(position);
        //now, tell the adapter about the update
        notifyItemRangeChanged(position, itemDTOList.size());
    }

    /**
     * Method is used to set Click Listener
     *
     * @param itemClickListener
     */
    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    /**
     * Method is used to initialize xml item and inflate view to ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_TYPE_DATA) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyleview_item_list, parent, false);
            viewHolder = new ItemViewHolder(view, clickListener);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false);
            viewHolder = new EmptyViewHolder(view);
        }
        return viewHolder;
    }

    /**
     * Method is used to bind data to ViewHolder
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_EMPTY) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) viewHolder;
        } else {
            final ItemDTO itemDTO = itemDTOList.get(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            itemViewHolder.bind(itemDTO);
            // mItemManger is member in RecyclerSwipeAdapter Class
            mItemManger.bindView(viewHolder.itemView, position);
        }
    }

    /**
     * Method is used to get total no. of record in List
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (itemDTOList != null) {
            if (itemDTOList.size() == 0) {
                return 1;
            } else {
                return itemDTOList.size();
            }
        }
        return 1;
    }

    /**
     * Method is used to get ItemType and decide which ViewHolder should display on screen
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (itemDTOList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_DATA;
        }
    }

    /**
     * Method is used to return Swipe resource id
     *
     * @param position
     * @return
     */
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    /**
     * Method is used to get FilterObject
     *
     * @return
     */
    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    /**
     * Filter object class
     */
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