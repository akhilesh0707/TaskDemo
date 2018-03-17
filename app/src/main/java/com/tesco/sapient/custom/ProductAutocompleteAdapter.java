package com.tesco.sapient.custom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tesco.sapient.R;
import com.tesco.sapient.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class ProductAutocompleteAdapter extends ArrayAdapter implements Filterable {
    public List<ProductDto> productDtoList;
    public List<ProductDto> productDtoListFiltered;
    private static final String TAG = ProductAutocompleteAdapter.class.getSimpleName();
    public Context context;

    public ProductAutocompleteAdapter(Context context, List<ProductDto> productDtoList) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        this.productDtoList = productDtoList;
        this.productDtoListFiltered = productDtoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productDtoListFiltered.size();
    }

    @Override
    public ProductDto getItem(int position) {
        return productDtoListFiltered.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                List<ProductDto> productDtos = productDtoList;
                if (constraint == null || constraint.length() == 0) {
                    result.values = productDtos;
                    result.count = productDtos.size();
                } else {
                    ArrayList<ProductDto> filteredList = new ArrayList<>();
                    for (ProductDto productDto : productDtos) {
                        Log.d(TAG, productDto.getBarCode() + " - " + productDto.getBarCode());
                        if (String.valueOf(productDto.getBarCode()).contains(constraint)) {
                            filteredList.add(productDto);
                        }
                    }
                    result.values = filteredList;
                    result.count = filteredList.size();
                }
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productDtoListFiltered = (ArrayList<ProductDto>) results.values;
                notifyDataSetChanged();
            }
        };
        return myFilter;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.autocomplete_list_item, parent, false);
        //get Student
        ProductDto productDto = productDtoListFiltered.get(position);
        TextView textViewName = (TextView) view.findViewById(R.id.autocompleteText);
        textViewName.setText(productDto.getBarCode() + " - " + productDto.getName());
        view.setTag(productDto);
        return view;
    }

}