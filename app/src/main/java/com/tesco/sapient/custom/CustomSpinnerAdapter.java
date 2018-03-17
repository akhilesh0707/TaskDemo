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

public class CustomSpinnerAdapter extends BaseAdapter {

    Context context;
    List<ItemTypeDTO> itemTypeDTOList;
    LayoutInflater inflter;

    public CustomSpinnerAdapter(Context applicationContext, List<ItemTypeDTO> itemTypeDTOList) {
        this.context = applicationContext;
        this.itemTypeDTOList = itemTypeDTOList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setDataChange(List<ItemTypeDTO> asList) {
        this.itemTypeDTOList = asList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemTypeDTOList.size();
    }

    @Override
    public ItemTypeDTO getItem(int i) {
        return itemTypeDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_item, null);
        ItemTypeDTO itemTypeDTO = itemTypeDTOList.get(i);
        TextView names = (TextView) view.findViewById(R.id.textView);
        view.setTag(itemTypeDTO);
        names.setText(itemTypeDTO.getName());
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        } else {
            return true;
        }
    }
}