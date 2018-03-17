package com.tesco.sapient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tesco.sapient.di.AppScope;
import com.tesco.sapient.di.ApplicationContext;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDto;
import com.tesco.sapient.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@AppScope
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    private static final String TAG = DatabaseHandler.class.getSimpleName();
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Tesco";
    // User table name
    private static final String TABLE_USER = "user_info";
    // Item Type table name
    private static final String TABLE_ITEM_TYPE = "item_type";
    // Item Type table name
    private static final String TABLE_ITEMS = "items";
    // Product BarCode Type table name
    private static final String TABLE_PRODUCT = "product";

    // User Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "userName";
    private static final String KEY_PASSWORD = "userPassword";
    private static final String KEY_STORE_ID = "storeId";
    private static final String KEY_STORE_NAME = "storeName";

    // Item type Table Columns names
    private static final String KEY_ITEM_TYPE_ID = "id";
    private static final String KEY_ITEM_TYPE_NAME = "name";

    // Item Table Columns names
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_BARCODE = "itemBarCode";
    private static final String KEY_ITEM_TYPE = "itemTypeCode";
    private static final String KEY_ITEM_QUANTITY = "itemQuantity";
    private static final String KEY_ITEM_PRICE = "itemPrice";

    // Item Table Columns names
    private static final String KEY_PRODUCT_ID = "id";
    private static final String KEY_PRODUCT_NAME = "name";
    private static final String KEY_PRODUCT_BARCODE = "barCode";
    private static final String KEY_PRODUCT_PRICE = "price";

    @Inject
    public DatabaseHandler(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USERNAME + " TEXT, "
                + KEY_PASSWORD + " TEXT,"
                + KEY_STORE_ID + " INTEGER, "
                + KEY_STORE_NAME + " TEXT)";

        String CREATE_ITEM_TYPE_TABLE = "CREATE TABLE " + TABLE_ITEM_TYPE + "("
                + KEY_ITEM_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ITEM_TYPE_NAME + " TEXT )";

        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ITEM_BARCODE + " INTEGER, "
                + KEY_ITEM_TYPE + " INTEGER, "
                + KEY_ITEM_QUANTITY + " INTEGER, "
                + KEY_ITEM_PRICE + " REAL)";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PRODUCT_NAME + " TEXT, "
                + KEY_PRODUCT_BARCODE + " INTEGER,"
                + KEY_PRODUCT_PRICE + " REAL)";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ITEM_TYPE_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        // Inserting dummy user record
        insertUsers(dummyUserList(), db);
        insertItemType(dummyItemTypeList(), db);
        insertBarcodeProduct(dummyProductBarcodeList(), db);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
    }

    /**
     * Check user exists or not in user table
     *
     * @param userDTO User object which trying to login
     * @return UserDTO is record found in DB table
     */
    public UserDTO checkUser(UserDTO userDTO) {
        Cursor cursor = null;
        SQLiteDatabase db = null;
        UserDTO user = null;
        try {
            db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?";
            cursor = db.rawQuery(query, new String[]{userDTO.getUserName(), userDTO.getPassword()});
            Log.d(TAG, DatabaseUtils.dumpCursorToString(cursor));
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    user = new UserDTO();
                    user.setUserId(cursor.getInt(0));
                    user.setUserName(cursor.getString(1));
                    user.setStoreId(cursor.getInt(3));
                    user.setStoreName(cursor.getString(4));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return user;
    }

    /**
     * Insert user dummy records
     *
     * @param list Dummy user list
     * @param db   db reference to insert record in table
     */
    public void insertUsers(List<UserDTO> list, SQLiteDatabase db) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (UserDTO userDTO : list) {
                values.put(KEY_USERNAME, userDTO.getUserName());
                values.put(KEY_PASSWORD, userDTO.getPassword());
                values.put(KEY_STORE_ID, userDTO.getStoreId());
                values.put(KEY_STORE_NAME, userDTO.getStoreName());
                db.insert(TABLE_USER, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        Log.d(TAG, "Dummy Record inserted");
    }

    /**
     * User dummy records
     *
     * @return List Dummy users
     */
    private List<UserDTO> dummyUserList() {
        List<UserDTO> list = new ArrayList<>();
        UserDTO userDTO = null;
        userDTO = new UserDTO("test", "test", 9001, "Record Wastage");
        list.add(userDTO);
        userDTO = new UserDTO("akhilesh", "akhilesh", 9023, "Record Wastage One");
        list.add(userDTO);
        userDTO = new UserDTO("sushant", "sushant", 9477, "Record Wastage Two");
        list.add(userDTO);
        userDTO = new UserDTO("sid", "sid", 9901, "Record Wastage Thee");
        list.add(userDTO);
        return list;
    }

    /**
     * Insert Item type dummy list to table
     *
     * @param list dummy item list
     * @param db   db reference to insert record in table
     */
    public void insertItemType(List<ItemTypeDTO> list, SQLiteDatabase db) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (ItemTypeDTO itemTypeDTO : list) {
                values.put(KEY_ITEM_TYPE_NAME, itemTypeDTO.getName());
                db.insert(TABLE_ITEM_TYPE, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        Log.d(TAG, "Dummy Record inserted item type");
    }

    /**
     * Dummy Item record
     *
     * @return dummy item type record list
     */
    private List<ItemTypeDTO> dummyItemTypeList() {
        List<ItemTypeDTO> list = new ArrayList<>();
        ItemTypeDTO itemTypeDTO = null;
        itemTypeDTO = new ItemTypeDTO("Out Of Code");
        list.add(itemTypeDTO);
        itemTypeDTO = new ItemTypeDTO("Damaged");
        list.add(itemTypeDTO);
        itemTypeDTO = new ItemTypeDTO("Promotion");
        list.add(itemTypeDTO);
        itemTypeDTO = new ItemTypeDTO("Animal feed - out of code");
        list.add(itemTypeDTO);
        itemTypeDTO = new ItemTypeDTO("Animal feed - damaged");
        list.add(itemTypeDTO);
        return list;
    }

    /**
     * All the item type list
     *
     * @return Item type list
     */
    public List<ItemTypeDTO> getItemTypeList() {
        List<ItemTypeDTO> itemTypeDTOList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEM_TYPE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemTypeDTO itemTypeDTO = new ItemTypeDTO();
                itemTypeDTO.setId(cursor.getInt(0));
                itemTypeDTO.setName(cursor.getString(1));
                // Adding Item type to list
                itemTypeDTOList.add(itemTypeDTO);
            } while (cursor.moveToNext());
        }
        // return pending Item type list
        return itemTypeDTOList;
    }

    /**
     * Used to add item on DB table
     *
     * @param itemDTO
     * @return Insertion status failed or success
     */
    public long addItemToTable(ItemDTO itemDTO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_BARCODE, itemDTO.getItemBarCode());
        values.put(KEY_ITEM_TYPE, itemDTO.getItemTypeCode());
        values.put(KEY_ITEM_QUANTITY, itemDTO.getItemQuantity());
        values.put(KEY_ITEM_PRICE, itemDTO.getItemPrice());
        // Inserting Row
        return db.insert(TABLE_ITEMS, null, values);
    }

    /**
     * Item list to render RecylcerView
     *
     * @return item list
     */
    public List<ItemDTO> getAllItems() {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT ITEM.*,PROD.* FROM " + TABLE_ITEMS + " AS ITEM INNER JOIN " + TABLE_PRODUCT + " AS PROD ON ITEM." + KEY_ITEM_BARCODE + "=" + "PROD." + KEY_PRODUCT_BARCODE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Log.d("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        //Log.d("Query ", selectQuery);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setId(cursor.getInt(0));
                itemDTO.setItemBarCode(cursor.getInt(1));
                itemDTO.setItemTypeCode(cursor.getInt(2));
                itemDTO.setItemQuantity(cursor.getInt(3));
                itemDTO.setItemPrice(cursor.getDouble(4));
                itemDTO.setItemTypeName(cursor.getString(6));
                //Log.d(TAG, itemDTO.toString());
                // Adding Items to list
                itemDTOList.add(itemDTO);
            } while (cursor.moveToNext());
        }
        // return Item list
        return itemDTOList;
    }


    /**
     * Delete item from table
     *
     * @param
     */
    public int deletePendingFile(ItemDTO itemDTO) {
        int returnValue = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        returnValue = db.delete(TABLE_ITEMS, KEY_ITEM_ID + " = ?", new String[]{String.valueOf(itemDTO.getId())});
        db.close();
        return returnValue;
    }


    /**
     * Dummy Product for barcode record
     *
     * @return dummy Product barcode record list
     */
    private List<ProductDto> dummyProductBarcodeList() {
        List<ProductDto> list = new ArrayList<>();
        ProductDto productDto = null;
        productDto = new ProductDto("Candy mix", 1234, 22.90);
        list.add(productDto);
        productDto = new ProductDto("Dell damaged laptop", 1235, 43.00);
        list.add(productDto);
        productDto = new ProductDto("HP damaged laptop", 1236, 34.60);
        list.add(productDto);
        productDto = new ProductDto("Cool mint expired", 1237, 80.89);
        list.add(productDto);
        productDto = new ProductDto("Tread jam mix", 1238, 77.10);
        list.add(productDto);
        productDto = new ProductDto("Apple Iphone damaged", 1239, 77.90);
        list.add(productDto);
        return list;
    }

    /**
     * inserting dummy product barcode to db
     *
     * @param list
     * @param db
     */
    public void insertBarcodeProduct(List<ProductDto> list, SQLiteDatabase db) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (ProductDto productDto : list) {
                values.put(KEY_PRODUCT_NAME, productDto.getName());
                values.put(KEY_PRODUCT_BARCODE, productDto.getBarCode());
                values.put(KEY_PRODUCT_PRICE, productDto.getPrice());
                db.insert(TABLE_PRODUCT, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        Log.d(TAG, "Dummy Record inserted product barcode");
    }

    /**
     * ProductBarCode list for AutoComplete
     *
     * @return ProductDto list
     */
    public List<ProductDto> getProductBarCodes() {
        List<ProductDto> productDtoList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Log.d("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        //Log.d("Query ", selectQuery);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductDto productDto = new ProductDto();
                productDto.setId(cursor.getInt(0));
                productDto.setName(cursor.getString(1));
                productDto.setBarCode(cursor.getInt(2));
                productDto.setPrice(cursor.getDouble(3));
                // Adding Items to list
                productDtoList.add(productDto);
            } while (cursor.moveToNext());
        }
        // return Item list
        return productDtoList;
    }


}