package com.tesco.sapient.main;

import android.util.Log;

import com.tesco.sapient.db.DataManager;
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
    private DataManager dataManager;

    public MainActivityPresenter(MainActivityView view, DataManager dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }

    public void getItemTypes() {
        List<ItemTypeDTO> typeDTOList = dataManager.getItemTypes();
        if (typeDTOList.size() > 0) {
            view.fillAddItemSpinner(typeDTOList);
        } else {
            view.noItemTypeRecord(typeDTOList);
        }
    }

    public void getItems() {
        List<ItemDTO> itemDTOList = dataManager.getItemList();
        if (itemDTOList.size() > 0) {
            view.showItems(itemDTOList);
        } else {
            view.noItemsErrorMessage();
        }
    }

    public void getProductBarCodes() {
        List<ProductDto> productBarCodeList = dataManager.getProductBarCodeList();
        if (productBarCodeList.size() > 0) {
            view.productBarCodeList(productBarCodeList);
        } else {
            view.noProductBarCodeErrorMessage();
        }
    }

    public void addItem(ItemDTO itemDTO) {
        boolean insertStatus = dataManager.insertItem(itemDTO);
        if (!insertStatus) {
            view.itemAddErrorMessage();
        } else {
            view.itemAddSuccessfullyMessage();
        }
    }

    public void deleteItem(ItemDTO itemDTO) {
        int deleteStatus = dataManager.deleteItemFromDB(itemDTO);
        if (deleteStatus == 1) {
            view.itemDeleteSuccessfully();
        } else {
            view.itemDeleteError();
        }
    }

    public void getFilterItemTypes() {
        List<ItemTypeDTO> typeDTOList = dataManager.getItemTypes();
        if (typeDTOList.size() > 1) {
            view.fillFilterSpinner(typeDTOList);
        } else {
            view.fillFilterNoRecordError(typeDTOList);
        }
    }
}
