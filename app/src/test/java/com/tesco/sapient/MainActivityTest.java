package com.tesco.sapient;

import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.dto.ItemTypeDTO;
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
public class MainActivityTest {

    @Mock
    MainActivityView view;
    @Mock
    DataManager dataManager;

    private MainActivityPresenter presenter;
    private List<ItemTypeDTO> itemTypeDTOList = Arrays.asList(new ItemTypeDTO(), new ItemTypeDTO(), new ItemTypeDTO());

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
    public void shouldSucessfullyGetItemTypeListToFillFilterData() {
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




}
