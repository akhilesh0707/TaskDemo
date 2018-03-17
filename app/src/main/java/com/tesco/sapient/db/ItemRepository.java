package com.tesco.sapient.db;

import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDto;

import java.util.List;

/**
 * Created by Aki on 3/17/2018.
 */

public interface ItemRepository {
    List<ItemTypeDTO> getItemTypes();

    long insertItem(ItemDTO itemDTO);

    List<ItemDTO> getItemList();

    List<ProductDto> getProductBarCodeList();

    int deleteItemFromDB(ItemDTO itemDTO);
}
