package com.tesco.sapient.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tesco.sapient.R;
import com.tesco.sapient.dto.ItemTypeDTO;

import java.util.List;

/**
 * Custom Adapter to display data in android spinner
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class CustomSpinnerAdapter extends BaseAdapter {

    Context context;
    List<ItemTypeDTO> itemTypeDTOList;
    LayoutInflater inflter;

    /**
     * @param applicationContext ApplicationContext or activity context
     * @param itemTypeDTOList    List<ItemTypeDTO>
     */
    public CustomSpinnerAdapter(Context applicationContext, List<ItemTypeDTO> itemTypeDTOList) {
        this.context = applicationContext;
        this.itemTypeDTOList = itemTypeDTOList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    /**
     * Set data after changes in List
     *
     * @param itemTypeDTOList
     */
    public void setDataChange(List<ItemTypeDTO> itemTypeDTOList) {
        this.itemTypeDTOList = itemTypeDTOList;
        notifyDataSetChanged();
    }

    /**
     * @return No. of items in list
     */
    @Override
    public int getCount() {
        return itemTypeDTOList.size();
    }

    /**
     * @param position to get selected item
     * @return ItemTypeDTO selected item
     */
    @Override
    public ItemTypeDTO getItem(int position) {
        return itemTypeDTOList.get(position);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * @param position
     * @param view
     * @param viewGroup
     * @return View after binding data
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_item, null);
        ItemTypeDTO itemTypeDTO = itemTypeDTOList.get(position);
        TextView names = (TextView) view.findViewById(R.id.textView);
        view.setTag(itemTypeDTO);
        names.setText(itemTypeDTO.getName());
        return view;
    }

    /**
     * @param position
     * @return boolean if item is not selectable
     */
    @Override
    public boolean isEnabled(int position) {
        return true;
    }

}