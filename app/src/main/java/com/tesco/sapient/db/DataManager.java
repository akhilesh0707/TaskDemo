package com.tesco.sapient.db;

import android.content.Context;
import android.content.res.Resources;

import com.tesco.sapient.db.DatabaseHandler;
import com.tesco.sapient.db.UserRepository;
import com.tesco.sapient.di.AppScope;
import com.tesco.sapient.di.ApplicationContext;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDto;
import com.tesco.sapient.dto.UseDTO;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    public UseDTO authenticate(UseDTO useDTO) {
        return mDbHelper.checkUser(useDTO);
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