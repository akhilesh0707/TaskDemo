package com.tesco.sapient.db;

import android.content.Context;

import com.tesco.sapient.di.AppScope;
import com.tesco.sapient.di.ApplicationContext;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDTO;
import com.tesco.sapient.dto.UserDTO;

import java.util.List;

import javax.inject.Inject;

/**
 * DataManager class to handle all DataBase CRUD operation
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */

@AppScope
public class DataManager implements UserRepository, ItemRepository {

    private Context mContext;
    private DatabaseHandler mDbHelper;

    /**
     * Constructor of DataManager with ApplicationContext inject and DataBaseHandler to manager CRUD Operation
     *
     * @param context
     * @param dbHelper
     */
    @Inject
    public DataManager(@ApplicationContext Context context, DatabaseHandler dbHelper) {
        mContext = context;
        mDbHelper = dbHelper;
    }

    /**
     * Check user authentication on Database
     *
     * @param username
     * @param password
     * @return
     */

    @Override
    public UserDTO authenticate(String username, String password) {
        return mDbHelper.checkUser(username, password);
    }

    /**
     * Get all Item type to render list on Add Item form and filter
     *
     * @return List<ItemTypeDTO>
     */
    @Override
    public List<ItemTypeDTO> getItemTypes() {
        return mDbHelper.getItemTypeList();
    }

    /**
     * Insert item into Item table and return success result as boolean
     *
     * @param itemDTO
     * @return Boolean
     */
    @Override
    public boolean insertItem(ItemDTO itemDTO) {
        return mDbHelper.addItemToTable(itemDTO);
    }

    /**
     * Get All Items
     *
     * @return List<ItemDTO>
     */
    @Override
    public List<ItemDTO> getItemList() {
        return mDbHelper.getAllItems();
    }

    /**
     * Get all the product barcode
     *
     * @return List<ProductDTO>
     */
    @Override
    public List<ProductDTO> getProductBarCodeList() {
        return mDbHelper.getProductBarCodes();
    }

    /**
     * Delete item from DB item table
     *
     * @param itemDTO
     * @return Int
     */
    @Override
    public int deleteItemFromDB(ItemDTO itemDTO) {
        return mDbHelper.deletePendingFile(itemDTO);
    }
}