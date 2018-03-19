package com.tesco.sapient.main;

import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDTO;

import java.util.List;

/**
 * MainActivityPresenter to manage View and Business logic(Data model)
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class MainActivityPresenter {

    private MainActivityView view;
    private DataManager dataManager;

    /**
     * Constructor to initialize MainActivityView and DataManger
     *
     * @param view
     * @param dataManager
     */
    public MainActivityPresenter(MainActivityView view, DataManager dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }

    /**
     * Method is used to get ItemTypes from Database
     */
    public void getItemTypes() {
        List<ItemTypeDTO> typeDTOList = dataManager.getItemTypes();
        if (typeDTOList.size() > 0) {
            view.fillAddItemSpinner(typeDTOList);
        } else {
            view.noItemTypeRecord(typeDTOList);
        }
    }

    /**
     * Method is used to get all items from database
     */
    public void getItems() {
        List<ItemDTO> itemDTOList = dataManager.getItemList();
        if (itemDTOList.size() > 0) {
            view.showItems(itemDTOList);
        } else {
            view.noItemsErrorMessage();
        }
    }

    /**
     * Method is used to get all ProductBarCode from database
     */
    public void getProductBarCodes() {
        List<ProductDTO> productBarCodeList = dataManager.getProductBarCodeList();
        if (productBarCodeList.size() > 0) {
            view.productBarCodeList(productBarCodeList);
        } else {
            view.noProductBarCodeErrorMessage();
        }
    }

    /**
     * Method is used to add item to database
     *
     * @param itemDTO
     */
    public void addItem(ItemDTO itemDTO) {
        boolean insertStatus = dataManager.insertItem(itemDTO);
        if (!insertStatus) {
            view.itemAddErrorMessage();
        } else {
            view.itemAddSuccessfullyMessage();
        }
    }

    /**
     * Method is used to delete item from Database
     *
     * @param itemDTO
     */
    public void deleteItem(ItemDTO itemDTO) {
        int deleteStatus = dataManager.deleteItemFromDB(itemDTO);
        if (deleteStatus == 1) {
            view.itemDeleteSuccessfully();
        } else {
            view.itemDeleteError();
        }
    }

    /**
     * Method is used to get List of ItemTypes
     */
    public void getFilterItemTypes() {
        List<ItemTypeDTO> typeDTOList = dataManager.getItemTypes();
        if (typeDTOList.size() > 1) {
            view.fillFilterSpinner(typeDTOList);
        } else {
            view.fillFilterNoRecordError(typeDTOList);
        }
    }
}
