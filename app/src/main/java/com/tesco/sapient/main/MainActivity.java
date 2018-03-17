package com.tesco.sapient.main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.tesco.sapient.R;
import com.tesco.sapient.custom.CustomSpinnerAdapter;
import com.tesco.sapient.custom.ProductAutocompleteAdapter;
import com.tesco.sapient.db.DatabaseHandler;
import com.tesco.sapient.dto.ItemDTO;
import com.tesco.sapient.dto.ItemTypeDTO;
import com.tesco.sapient.dto.ProductDto;
import com.tesco.sapient.main.adapter.ItemAdapter;
import com.tesco.sapient.main.adapter.OnItemClickListener;
import com.tesco.sapient.util.CollapseExpandAnimation;
import com.tesco.sapient.util.KeyboardUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainActivityView, OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter presenter;
    private Context context;
    private Unbinder unbinder;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recyclerViewItem)
    RecyclerView recyclerViewItem;

    // Add new Item fields
    @BindView(R.id.addItemView)
    View addItemView;
    @BindView(R.id.buttonAddItem)
    Button buttonAddItem;
    @BindView(R.id.editTextProductCode)
    AutoCompleteTextView editTextProductCode;
    @BindView(R.id.spinnerItemType)
    Spinner spinnerItemType;
    @BindView(R.id.editTextAmountSingle)
    EditText editTextAmountSingle;
    @BindView(R.id.editTextPrice)
    EditText editTextPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing ButterKnife
        unbinder = ButterKnife.bind(this);
        // Initializing login activity context
        context = this;
        //Initialize DataBase
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        // Initializing Presenter
        presenter = new MainActivityPresenter(this, databaseHandler);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getItemTypes();
        presenter.getItems();
        presenter.getProductBarCodes();
    }

    @OnClick(R.id.buttonAddItem)
    public void showAddItemClick(View view) {
        if (addItemView.isShown()) {
            buttonAddItem.setVisibility(View.VISIBLE);
            CollapseExpandAnimation.expandOrCollapse(addItemView, "collapse");
        } else {
            buttonAddItem.setVisibility(View.GONE);
            CollapseExpandAnimation.expandOrCollapse(addItemView, "expand");
        }
    }

    @OnClick(R.id.buttonCancel)
    public void addItemCancelClick(View view) {
        buttonAddItem.setVisibility(View.VISIBLE);
        CollapseExpandAnimation.expandOrCollapse(addItemView, "collapse");
    }

    @OnClick(R.id.buttonSubmit)
    public void addItemClick(View view) {
        KeyboardUtil.hideSoftKeyboard(context);
        int barCode = Integer.parseInt(editTextProductCode.getText().toString().trim());
        ItemTypeDTO itemTypeDTO = (ItemTypeDTO) spinnerItemType.getSelectedItem();
        int quantity = Integer.parseInt(editTextAmountSingle.getText().toString().trim());
        double price = Double.parseDouble(editTextPrice.getText().toString().trim());

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemBarCode(barCode);
        itemDTO.setItemTypeCode(itemTypeDTO.getId());
        itemDTO.setItemQuantity(quantity);
        itemDTO.setItemPrice(price);

        Log.d(TAG, itemDTO.toString());
        presenter.addItem(itemDTO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void fillAddItemSpinner(List<ItemTypeDTO> list) {
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(context, list);
        spinnerItemType.setAdapter(customSpinnerAdapter);
    }

    @Override
    public void noItemTypeRecord(List<ItemTypeDTO> list) {

    }

    @Override
    public void itemAddSuccessfullyMessage() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_add_successfully), Snackbar.LENGTH_LONG).show();
        resetAddItemForm();
        buttonAddItem.performClick();
    }

    @Override
    public void itemAddErrorMessage() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.add_an_item_record_add_fail), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void resetAddItemForm() {
        editTextProductCode.setText(null);
        spinnerItemType.setSelection(0);
        editTextAmountSingle.setText(null);
        editTextPrice.setText(null);
    }

    @Override
    public void showItems(List<ItemDTO> itemDTOList) {
        ItemAdapter adapter = new ItemAdapter(context, itemDTOList);
        adapter.setClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewItem.setLayoutManager(mLayoutManager);
        ((ItemAdapter) adapter).setMode(Attributes.Mode.Single);
        recyclerViewItem.setAdapter(adapter);
    }

    @Override
    public void noItemsErrorMessage() {

    }

    @Override
    public void productBarCodeList(List<ProductDto> list) {
        final ProductAutocompleteAdapter adapter = new ProductAutocompleteAdapter(this, list);
        editTextProductCode.setThreshold(1);
        editTextProductCode.setAdapter(adapter);
        editTextProductCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDto productDto = adapter.getItem(position);
                editTextProductCode.setText(String.valueOf(productDto.getBarCode()));
                editTextPrice.setText(String.valueOf(productDto.getPrice()));
            }
        });
    }

    @Override
    public void noProductBarCodeErrorMessage() {

    }

    @Override
    public void onItemRemoveClicked(ItemDTO itemDTO) {
        Log.d(TAG, "Delete item: " + itemDTO.getId());
    }
}
