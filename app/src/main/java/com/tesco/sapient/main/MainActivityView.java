package com.tesco.sapient.main;

import com.tesco.sapient.dto.ItemTypeDTO;

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


}
