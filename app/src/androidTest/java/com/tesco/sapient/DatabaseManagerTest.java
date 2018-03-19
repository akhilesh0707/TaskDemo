package com.tesco.sapient;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.db.DatabaseHandler;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDTO;
import com.tesco.sapient.dto.UserDTO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DatabaseManagerTest {

    private DatabaseHandler databaseHandler;
    private DataManager dataManager;
    private static final String TAG = DatabaseManagerTest.class.getCanonicalName();

    @Before
    public void setUp() throws Exception {
        databaseHandler = new DatabaseHandler(InstrumentationRegistry.getTargetContext());
        dataManager = new DataManager(InstrumentationRegistry.getTargetContext(), databaseHandler);
    }

    @Test
    public void shouldPassedCreateDataBaseAndDataHandler() {
        assertNotNull(databaseHandler);
        assertNotNull(dataManager);
    }

    @Test
    public void shouldPassUserAuthenticateUsingUsernameAndPassword() throws Exception {
        UserDTO userDTO = dataManager.authenticate("sid", "sid");
        assertNotNull(userDTO);
        //List<Rate> rate = mDataSource.getAllRates();
        assertTrue(userDTO.getUserName().equals("sid"));
        assertTrue(userDTO.getUserId() == 4);
    }

    @Test
    public void shouldFailedUserAuthenticationUsingIncorrectUsernamePassword() throws Exception {
        UserDTO userDTO = dataManager.authenticate("sid", "sid1");
        assertNull(userDTO);
    }

    @Test
    public void shouldGetItemTypesListSuccessfully() throws Exception {
        List<ItemTypeDTO> itemTypes = dataManager.getItemTypes();
        assertThat(itemTypes.size(), is(5));
        assertTrue(itemTypes.get(0).getId() == 1);
    }

    @Test
    public void shouldInsertItemUsingItemDtoAndReturnSuccessMessage() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemBarCode(1234);
        itemDTO.setItemTypeCode(2);
        itemDTO.setItemQuantity(3);
        itemDTO.setItemPrice(22.9);
        boolean status = dataManager.insertItem(itemDTO);
        assertTrue(status);
    }

    @Test
    public void shouldGetItemList() throws Exception {
        List<ItemDTO> itemList = dataManager.getItemList();
        assertNotNull(itemList);
    }

    @Test
    public void shouldGetProductBarCodes() throws Exception {
        List<ProductDTO> productBarCodeList = dataManager.getProductBarCodeList();
        assertNotNull(productBarCodeList);
    }

    @Test
    public void shouldFailDeleteItem() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(2);
        itemDTO.setItemBarCode(1234);
        itemDTO.setItemTypeCode(2);
        itemDTO.setItemQuantity(3);
        itemDTO.setItemPrice(22.9);
        int status = dataManager.deleteItemFromDB(itemDTO);
        assertEquals(status,0);

    }

    @After
    public void tearDown() throws Exception {
        databaseHandler = null;
        dataManager = null;
    }


}