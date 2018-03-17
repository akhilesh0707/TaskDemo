package com.tesco.sapient.main;

import android.util.Log;

import com.tesco.sapient.db.DatabaseHandler;
import com.tesco.sapient.db.ItemRepository;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDto;

import java.util.List;

/**
 * Created by akhpatil on 3/16/2018.
 */

public class MainActivityPresenter {

    private MainActivityView view;
    private ItemRepository repository;
    private final static String TAG = MainActivityPresenter.class.getSimpleName();

    public MainActivityPresenter(MainActivityView view, ItemRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void getItemTypes() {
        List<ItemTypeDTO> typeDTOList = repository.getItemTypes();
        if (typeDTOList.size() > 0) {
            view.fillAddItemSpinner(typeDTOList);
        } else {
            view.noItemTypeRecord(typeDTOList);
        }
    }

    public void getItems() {
        List<ItemDTO> itemDTOList = repository.getItemList();
        Log.d(TAG, "ItemList size: " + itemDTOList.size());
        if (itemDTOList.size() > 0) {
            view.showItems(itemDTOList);
        } else {
            view.noItemsErrorMessage();
        }
    }

    public void getProductBarCodes() {
        List<ProductDto> productBarCodeList = repository.getProductBarCodeList();
        if (productBarCodeList.size() > 0) {
            view.productBarCodeList(productBarCodeList);
        } else {
        }

    }

    public void addItem(ItemDTO itemDTO) {
        long insertStatus = repository.insertItem(itemDTO);
        if (insertStatus == -1) {
            view.itemAddErrorMessage();
        } else {
            view.itemAddSuccessfullyMessage();
        }
    }


    public void deleteItem(ItemDTO itemDTO) {
        int deleteStatus = repository.deleteItemFromDB(itemDTO);
        Log.d(TAG, "Delete status value is:" + deleteStatus);
        if (deleteStatus == 1) {
            view.itemDeleteSuccessfully();
        } else {
            view.itemDeleteError();
        }
    }

    public void getFilterItemTypes() {
        List<ItemTypeDTO> typeDTOList = repository.getItemTypes();
        typeDTOList.add(0, new ItemTypeDTO("All"));
        if (typeDTOList.size() > 1) {
            view.fillFilterSpinner(typeDTOList);
        } else {
            view.fillFilterNoRecordError(typeDTOList);
        }
    }
}
