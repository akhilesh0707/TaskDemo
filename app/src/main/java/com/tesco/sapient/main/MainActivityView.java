package com.tesco.sapient.main;

import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDto;

import java.util.List;

/**
 * Created by akhpatil on 3/16/2018.
 */

interface MainActivityView {

    void fillAddItemSpinner(List<ItemTypeDTO> list);

    void noItemTypeRecord(List<ItemTypeDTO> list);

    void itemAddSuccessfullyMessage();

    void itemAddErrorMessage();

    void resetAddItemForm();

    void showItems(List<ItemDTO> list);

    void noItemsErrorMessage();

    void productBarCodeList(List<ProductDto> list);

    void noProductBarCodeErrorMessage();

    void itemDeleteSuccessfully();

    void itemDeleteError();


}
