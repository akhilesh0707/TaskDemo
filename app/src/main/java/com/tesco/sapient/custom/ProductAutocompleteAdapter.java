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
import com.tesco.sapient.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom AutoComplete Adapter to display and render autocomplete on scan barcode and select barcode product
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class ProductAutocompleteAdapter extends ArrayAdapter implements Filterable {
    public List<ProductDTO> productDTOList;
    public List<ProductDTO> productDTOListFiltered;
    private static final String TAG = ProductAutocompleteAdapter.class.getSimpleName();
    public Context context;

    /**
     * Constructor to fill the data and pass the Context
     *
     * @param context
     * @param productDTOList
     */
    public ProductAutocompleteAdapter(Context context, List<ProductDTO> productDTOList) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        this.productDTOList = productDTOList;
        this.productDTOListFiltered = productDTOList;
        this.context = context;
    }

    /**
     * GetCont used to calculate no. of record in list
     *
     * @return Int value to get no. of record in list
     */
    @Override
    public int getCount() {
        return productDTOListFiltered.size();
    }

    /**
     * GetItem method to return selected Item from List
     *
     * @param position
     * @return ProductDTO
     */
    @Override
    public ProductDTO getItem(int position) {
        return productDTOListFiltered.get(position);
    }

    /**
     * Filter class to filter data while typing in AutoComplete
     *
     * @return Filter
     */
    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                List<ProductDTO> productDTOS = productDTOList;
                if (constraint == null || constraint.length() == 0) {
                    result.values = productDTOS;
                    result.count = productDTOS.size();
                } else {
                    ArrayList<ProductDTO> filteredList = new ArrayList<>();
                    for (ProductDTO productDto : productDTOS) {
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
                productDTOListFiltered = (ArrayList<ProductDTO>) results.values;
                notifyDataSetChanged();
            }
        };
        return myFilter;
    }

    /**
     * Bind the View while searching or AutoComplete
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.autocomplete_list_item, parent, false);
        //get Student
        ProductDTO productDTO = productDTOListFiltered.get(position);
        TextView textViewName = (TextView) view.findViewById(R.id.autocompleteText);
        textViewName.setText(productDTO.getBarCode() + " - " + productDTO.getName());
        view.setTag(productDTO);
        return view;
    }

}