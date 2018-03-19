package com.tesco.sapient.waste;

import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDTO;

import java.util.List;
/**
 * Item Repository interface to handle items and product barcode DB operation
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public interface WasteView {

    void fillAddItemSpinner(List<ItemTypeDTO> list);

    void noItemTypeRecord(List<ItemTypeDTO> list);

    void itemAddSuccessfullyMessage();

    void itemAddErrorMessage();

    void resetAddItemForm();

    void showItems(List<ItemDTO> list);

    void noItemsErrorMessage();

    void productBarCodeList(List<ProductDTO> list);

    void noProductBarCodeErrorMessage();

    void itemDeleteSuccessfully();

    void itemDeleteError();

    void fillFilterSpinner(List<ItemTypeDTO> list);

    void fillFilterNoRecordError(List<ItemTypeDTO> list);

}
