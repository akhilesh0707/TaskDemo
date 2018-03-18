package com.tesco.sapient;

import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDto;
import com.tesco.sapient.dto.UserDTO;
import com.tesco.sapient.login.LoginPresenter;
import com.tesco.sapient.login.LoginView;
import com.tesco.sapient.main.MainActivityPresenter;
import com.tesco.sapient.main.MainActivityView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Aki on 3/17/2018.
 */
@RunWith(MockitoJUnitRunner.class)
//@RunWith(MockitoJUnitRunner.Silent.class)
public class MainActivityPresenterTest {

    @Mock
    MainActivityView view;
    @Mock
    DataManager dataManager;

    private MainActivityPresenter presenter;
    private List<ItemTypeDTO> itemTypeDTOList = Arrays.asList(new ItemTypeDTO(), new ItemTypeDTO(), new ItemTypeDTO());
    private List<ItemDTO> itemDTOList = Arrays.asList(new ItemDTO(), new ItemDTO(), new ItemDTO());
    private List<ProductDto> productBarCodeList = Arrays.asList(new ProductDto(), new ProductDto(), new ProductDto());

    @Before
    public void setUp() throws Exception {
        presenter = new MainActivityPresenter(view, dataManager);
    }

    @Test
    public void shouldSuccessfullyGetItemTypeListToFillAddAnItemField() {
        //Given
        when(dataManager.getItemTypes()).thenReturn(itemTypeDTOList);
        //When
        presenter.getItemTypes();
        //Then
        verify(view).fillAddItemSpinner(itemTypeDTOList);
    }

    @Test
    public void shouldFailedItemTypeListToFillAddAnItemField() {
        //Given
        when(dataManager.getItemTypes()).thenReturn(Collections.EMPTY_LIST);
        //When
        presenter.getItemTypes();
        //Then
        verify(view).noItemTypeRecord(Collections.EMPTY_LIST);
    }

    @Test
    public void shouldSuccessfullyGetItemTypeListToFillFilterData() {
        //Given
        when(dataManager.getItemTypes()).thenReturn(itemTypeDTOList);
        //When
        presenter.getFilterItemTypes();
        //Then
        verify(view).fillFilterSpinner(itemTypeDTOList);
    }

    @Test
    public void shouldFailedGetItemTypeListToFillFilterData() {
        //Given
        List<ItemTypeDTO> list = Arrays.asList(new ItemTypeDTO());
        when(dataManager.getItemTypes()).thenReturn(list);
        //When
        presenter.getFilterItemTypes();
        //Then
        verify(view).fillFilterNoRecordError(list);
    }

    @Test
    public void shouldSuccessfullyGetTheItemListAndDisplayOnRecyclerView() {
        //Given
        when(dataManager.getItemList()).thenReturn(itemDTOList);
        //When
        presenter.getItems();
        //Then
        verify(view).showItems(itemDTOList);
    }

    @Test
    public void shouldFailedToGetItemListAndDisplayNoRecordMessageOnRecyclerView() {
        //Given
        when(dataManager.getItemList()).thenReturn(Collections.EMPTY_LIST);
        //When
        presenter.getItems();
        //Then
        verify(view).noItemsErrorMessage();
    }

    @Test
    public void shouldSuccessfullyGetTheProductBarCodesAndFillAutoCompleteAdapter() {
        //Given
        when(dataManager.getProductBarCodeList()).thenReturn(productBarCodeList);
        //When
        presenter.getProductBarCodes();
        //Then
        verify(view).productBarCodeList(productBarCodeList);
    }

    @Test
    public void shouldFailToGetProductBarCodeListAndShowErrorMessage() {
        //Given
        when(dataManager.getProductBarCodeList()).thenReturn(Collections.EMPTY_LIST);
        //When
        presenter.getProductBarCodes();
        //Then
        verify(view).noProductBarCodeErrorMessage();
    }

    @Test
    public void shouldSuccessfullyAddItemToDatabaseTable() {
        //Given
        ItemDTO itemDTO = new ItemDTO();
        when(dataManager.insertItem(itemDTO)).thenReturn(true);
        //When
        presenter.addItem(itemDTO);
        //Then
        verify(view).itemAddSuccessfullyMessage();
    }

    @Test
    public void shouldFailToAddItemToDatabaseTable() {
        //Given
        ItemDTO itemDTO = new ItemDTO();
        when(dataManager.insertItem(itemDTO)).thenReturn(false);
        //When
        presenter.addItem(itemDTO);
        //Then
        verify(view).itemAddErrorMessage();
    }

    @Test
    public void shouldPassToDeleteItemRecordFromDatabaseTable() {
        //Given
        ItemDTO itemDTO = new ItemDTO();
        when(dataManager.deleteItemFromDB(itemDTO)).thenReturn(1);
        //When
        presenter.deleteItem(itemDTO);
        //Then
        verify(view).itemDeleteSuccessfully();
    }

    @Test
    public void shouldFailToDeleteItemRecordFromDatabaseTable() {
        //Given
        ItemDTO itemDTO = new ItemDTO();
        when(dataManager.deleteItemFromDB(itemDTO)).thenReturn(2);
        //When
        presenter.deleteItem(itemDTO);
        //Then
        verify(view).itemDeleteError();
    }

}
