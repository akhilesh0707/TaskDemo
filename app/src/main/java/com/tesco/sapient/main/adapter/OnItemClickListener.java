package com.tesco.sapient.main.adapter;

import com.tesco.sapient.dto.ItemDTO;

/**
 * OnItemClickListener used to perform click event on RecyclerView
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-18
 */
public interface OnItemClickListener {
    /**
     * Method is used to remove item
     *
     * @param itemDTO
     */
    void onItemRemoveClicked(ItemDTO itemDTO);
}
