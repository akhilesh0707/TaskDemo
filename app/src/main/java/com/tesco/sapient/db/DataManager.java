package com.tesco.sapient.db;

import android.content.Context;

import com.tesco.sapient.di.AppScope;
import com.tesco.sapient.di.ApplicationContext;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDto;
import com.tesco.sapient.dto.UserDTO;

import java.util.List;

import javax.inject.Inject;

@AppScope
public class DataManager implements UserRepository, ItemRepository {

    private Context mContext;
    private DatabaseHandler mDbHelper;

    @Inject
    public DataManager(@ApplicationContext Context context, DatabaseHandler dbHelper) {
        mContext = context;
        mDbHelper = dbHelper;
    }

    @Override
    public UserDTO authenticate(UserDTO userDTO) {
        return mDbHelper.checkUser(userDTO);
    }

    @Override
    public List<ItemTypeDTO> getItemTypes() {
        return mDbHelper.getItemTypeList();
    }

    @Override
    public long insertItem(ItemDTO itemDTO) {
        return mDbHelper.addItemToTable(itemDTO);
    }

    @Override
    public List<ItemDTO> getItemList() {
        return mDbHelper.getAllItems();
    }

    @Override
    public List<ProductDto> getProductBarCodeList() {
        return mDbHelper.getProductBarCodes();
    }

    @Override
    public int deleteItemFromDB(ItemDTO itemDTO) {
        return mDbHelper.deletePendingFile(itemDTO);
    }
}