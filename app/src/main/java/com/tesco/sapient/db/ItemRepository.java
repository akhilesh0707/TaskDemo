package com.tesco.sapient.db;

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
public interface ItemRepository {
    List<ItemTypeDTO> getItemTypes();

    boolean insertItem(ItemDTO itemDTO);

    List<ItemDTO> getItemList();

    List<ProductDTO> getProductBarCodeList();

    int deleteItemFromDB(ItemDTO itemDTO);
}
