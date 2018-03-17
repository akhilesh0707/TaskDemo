package com.tesco.sapient.main;

import com.tesco.sapient.db.DatabaseHandler;
import com.tesco.sapient.db.ItemRepository;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;

import java.util.List;

/**
 * Created by akhpatil on 3/16/2018.
 */

public class MainActivityPresenter {

    private MainActivityView view;
    private ItemRepository repository;

    public MainActivityPresenter(MainActivityView view, ItemRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void loadItemType() {
        List<ItemTypeDTO> typeDTOList = repository.getItemTypes();
        if (typeDTOList.size() > 0) {
            view.fillAddItemSpinner(typeDTOList);
        } else {
            view.noItemTypeRecord(typeDTOList);
        }
    }

    public void addItem(ItemDTO itemDTO) {
        long insertStatus = repository.insertItem(itemDTO);
        if (insertStatus == -1) {
            view.itemAddErrorMessage();
        } else {
            view.itemAddSuccessfullyMessage();
        }
    }
}
